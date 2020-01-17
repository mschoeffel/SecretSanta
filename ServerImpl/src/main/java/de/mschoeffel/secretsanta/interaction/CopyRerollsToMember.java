package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Interaction to copy the number of rerolls from the group to all the members of the group.
 */
@Component
public class CopyRerollsToMember {

    private Group group;

    private GroupMemberRepository groupMemberRepository;

    @Autowired
    public CopyRerollsToMember(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    public void initialize(Group group) {
        this.group = group;
    }

    public Group execute() {
        int groupRerolls = group.getRerolls();
        if(groupRerolls <= 0){
            groupRerolls = 1;
        }

        for (GroupMember member : group.getGroupMember()) {
            member.setRerolls(groupRerolls);
            groupMemberRepository.save(member);
        }

        return group;
    }


}
