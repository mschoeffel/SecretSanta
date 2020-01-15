package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CopyRerollsToMember {

    private Group group;
    private List<GroupMember> groupMember;

    private GroupMemberRepository groupMemberRepository;

    @Autowired
    public CopyRerollsToMember(GroupMemberRepository groupMemberRepository){
        this.groupMemberRepository = groupMemberRepository;
    }

    public void initialize(Group group){
        this.group = group;
        this.groupMember = group.getGroupMember();
    }

    public Group execute(){
        Integer groupRerolls = group.getRerolls();

        for(GroupMember member : groupMember){
            member.setRerolls(groupRerolls);
            groupMemberRepository.save(member);
        }

        return group;
    }


}
