package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.error.AlreadyAllDrawnException;
import de.mschoeffel.secretsanta.error.AlreadyPartnerAcceptedException;
import de.mschoeffel.secretsanta.error.NoMoreRerollsException;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
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

    private GroupMember groupMember;

    private GroupMemberRepository groupMemberRepository;
    private GroupRepository groupRepository;
    private FindGroupMemberWithCredentials findGroupMemberWithCredentials;
    private FindGroupByName findGroupByName;
    private AcceptPartnerToMember acceptPartnerToMember;

    @Autowired
    public DrawPartnerToMember(GroupMemberRepository groupMemberRepository,
                               GroupRepository groupRepository,
                               FindGroupMemberWithCredentials findGroupMemberWithCredentials,
                               FindGroupByName findGroupByName,
                               AcceptPartnerToMember acceptPartnerToMember) {
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
        this.findGroupMemberWithCredentials = findGroupMemberWithCredentials;
        this.findGroupByName = findGroupByName;
        this.acceptPartnerToMember = acceptPartnerToMember;
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

        if(groupMember.getDrawAccepted() != null && groupMember.getDrawAccepted()){
            throw new AlreadyPartnerAcceptedException();
        }

        if (groupMember.getRerolls() <= 0 && groupMember.getPartner() != null) {
            throw new NoMoreRerollsException();
        }

        if(groupMember.getPartner() != null) {
            groupMember.setPartner(null);
        }

        Set<String> alreadyDrawn = new HashSet<>();
        for (GroupMember member : group.getGroupMember()) {
            if (member.getPartner() != null) {
                alreadyDrawn.add(member.getPartner().getName());
            }
        }

        if(alreadyDrawn.size() >= group.getGroupMember().size()){
            throw new AlreadyAllDrawnException();
        }

        List<GroupMember> possibleCandidates = new ArrayList<>();
        for (GroupMember member : group.getGroupMember()) {
            if (!alreadyDrawn.contains(member.getName())) {
                possibleCandidates.add(member);
            }
        }

        SecureRandom random = new SecureRandom();
        int randomIndex = random.nextInt(possibleCandidates.size());

        groupMember.setPartner(possibleCandidates.get(randomIndex));
        groupMember.setRerolls(groupMember.getRerolls() - 1);
        groupMember.setLastDrawTime(LocalDateTime.now());

        groupMemberRepository.save(groupMember);

        return groupMember;
    }


}
