package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.error.GroupNameExistsException;
import de.mschoeffel.secretsanta.error.MemberNameExistsException;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.HashSet;
import java.util.Set;

/**
 * Interaction to create a plain group and members without any further information.
 */
@Component
public class CreatePlainGroup {

    private Group group;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;
    private GroupClientService groupClientService;

    @Autowired
    public CreatePlainGroup(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, GroupClientService groupClientService) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupClientService = groupClientService;
    }

    public void initialize(Group group) {
        this.group = group;
    }

    public Group execute() {
        if (groupClientService.checkGroupName(group.getName())) {
            throw new GroupNameExistsException();
        }

        Set<String> memberNames = new HashSet<>();
        for (GroupMember member : group.getGroupMember()) {
            if (memberNames.contains(member.getName())) {
                throw new MemberNameExistsException();
            }
            memberNames.add(member.getName());
        }

        group.setToken(RandomString.make(10));

        groupRepository.save(group);

        for (GroupMember member : group.getGroupMember()) {
            member.setGroup(group);
            groupMemberRepository.save(member);
        }

        groupRepository.save(group);

        return group;
    }

}
