package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

/**
 * Interaction to generate the key for every member of a given group.
 */
@Component
public class GenerateKeysToGroup {

    private Group group;

    private GroupMemberRepository groupMemberRepository;

    @Autowired
    public GenerateKeysToGroup(GroupMemberRepository groupMemberRepository) {
        this.groupMemberRepository = groupMemberRepository;
    }

    public void initialize(Group group) {
        this.group = group;
    }

    public Group execute() {
        for (GroupMember member : group.getGroupMember()) {
            member.setKey(generateKey());
            groupMemberRepository.save(member);
        }
        return group;
    }

    private String generateKey() {
        return RandomString.make(8);
    }

}
