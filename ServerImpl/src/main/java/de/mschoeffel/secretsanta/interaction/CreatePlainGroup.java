package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;

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
    public CreatePlainGroup(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, GroupClientService groupClientService){
        this.groupRepository= groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupClientService = groupClientService;
    }

    public void initialize(Group group){
        this.group = group;
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
