package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;

import java.util.List;

public class CopyRerollsToMember {

    private Group group;
    private List<GroupMember> groupMember;
    private GroupMemberRepository groupMemberRepository;

    public CopyRerollsToMember(Group group, GroupMemberRepository groupMemberRepository){
        this.group = group;
        this.groupMember = group.getGroupMember();
        this.groupMemberRepository = groupMemberRepository;
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
