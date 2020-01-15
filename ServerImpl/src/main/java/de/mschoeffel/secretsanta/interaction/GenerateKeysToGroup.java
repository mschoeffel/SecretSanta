package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.service.GroupMemberService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GenerateKeysToGroup {

    private Group group;
    private List<GroupMember> groupMember;

    private GroupMemberRepository groupMemberRepository;

    public GenerateKeysToGroup(Group group, GroupMemberRepository groupMemberRepository){
        this.group = group;
        this.groupMember = group.getGroupMember();
        this.groupMemberRepository = groupMemberRepository;
    }

    public Group execute(){
        for(GroupMember member : groupMember){
            member.setKey(generateKey());
            groupMemberRepository.save(member);
        }
        return group;
    }

    private String generateKey(){
        return RandomString.make(8);
    }

}
