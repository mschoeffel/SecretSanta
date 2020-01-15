package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;

import javax.persistence.EntityExistsException;

public class CreatePlainGroup {

    private Group group;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;
    private GroupClientService groupClientService;

    public CreatePlainGroup(Group group, GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, GroupClientService groupClientService){
        this.group = group;
        this.groupRepository= groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupClientService = groupClientService;
    }

    public Group execute(){
        if(groupClientService.checkGroupName(group.getName())){
            throw new EntityExistsException();
        }

        groupRepository.save(group);

        for(GroupMember member : group.getGroupMember()){
            member.setGroup(group);
            groupMemberRepository.save(member);
        }

        return group;
    }

}
