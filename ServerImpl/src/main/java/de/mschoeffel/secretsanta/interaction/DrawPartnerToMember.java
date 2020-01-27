package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.error.AlreadyAllDrawnException;
import de.mschoeffel.secretsanta.error.AlreadyPartnerAcceptedException;
import de.mschoeffel.secretsanta.error.NoMoreRerollsException;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DrawPartnerToMember {

    private String groupname;
    private String name;
    private String key;

    private GroupMemberRepository groupMemberRepository;
    private FindGroupMemberWithCredentials findGroupMemberWithCredentials;
    private FindGroupByName findGroupByName;

    @Autowired
    public DrawPartnerToMember(GroupMemberRepository groupMemberRepository,
                               FindGroupMemberWithCredentials findGroupMemberWithCredentials,
                               FindGroupByName findGroupByName) {
        this.groupMemberRepository = groupMemberRepository;
        this.findGroupMemberWithCredentials = findGroupMemberWithCredentials;
        this.findGroupByName = findGroupByName;
    }

    public void initialize(String groupname, String name, String key) {
        this.groupname = groupname;
        this.name = name;
        this.key = key;
    }

    public GroupMember execute() {
        findGroupByName.initialize(groupname);
        Group group = findGroupByName.execute();

        findGroupMemberWithCredentials.initialize(groupname, name, key, group);
        GroupMember groupMember = findGroupMemberWithCredentials.execute();

        if (groupMember.getDrawAccepted() != null && groupMember.getDrawAccepted()) {
            throw new AlreadyPartnerAcceptedException();
        }

        if (groupMember.getRerolls() <= 0 && groupMember.getPartner() != null) {
            throw new NoMoreRerollsException();
        }

        if (groupMember.getPartner() != null) {
            groupMember.setPartner(null);
        }

        //List of all names that are already drawn by members
        Set<String> alreadyDrawn = new HashSet<>();
        for (GroupMember member : group.getGroupMember()) {
            if (member.getPartner() != null) {
                alreadyDrawn.add(member.getPartner().getName());
            }
        }

        if (alreadyDrawn.size() >= group.getGroupMember().size()) {
            throw new AlreadyAllDrawnException();
        }

        //All members that werent drawn already except groupMember
        List<GroupMember> possibleCandidates = new ArrayList<>();
        List<GroupMember> candidates = new ArrayList<>();
        List<GroupMember> memberWithoutPartner = new ArrayList<>();
        for (GroupMember member : group.getGroupMember()) {
            if (!alreadyDrawn.contains(member.getName())) {
                candidates.add(member);
                if (!member.equals(groupMember)) {
                    possibleCandidates.add(member);
                }
            }
            if (member.getPartner() == null) {
                memberWithoutPartner.add(member);
            }
        }

        if (candidates.size() == 2 && (candidates.contains(memberWithoutPartner.get(0)) || candidates.contains(memberWithoutPartner.get(1))) && !candidates.contains(groupMember)) {
            if (memberWithoutPartner.contains(candidates.get(0))) {
                groupMember.setPartner(candidates.get(0));
            } else {
                groupMember.setPartner(candidates.get(1));
            }
        } else {
            SecureRandom random = new SecureRandom();
            int randomIndex = random.nextInt(possibleCandidates.size());

            groupMember.setPartner(possibleCandidates.get(randomIndex));
        }

        groupMember.setRerolls(groupMember.getRerolls() - 1);
        groupMember.setLastDrawTime(LocalDateTime.now());

        groupMemberRepository.save(groupMember);

        return groupMember;
    }
}
