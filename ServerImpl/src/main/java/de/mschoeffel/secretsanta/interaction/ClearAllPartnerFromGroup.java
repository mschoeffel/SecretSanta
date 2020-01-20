package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClearAllPartnerFromGroup {

    private String name;

    private FindGroupByName findGroupByName;
    private GroupMemberRepository groupMemberRepository;
    private  GroupRepository groupRepository;

    @Autowired
    public ClearAllPartnerFromGroup(FindGroupByName findGroupByName, GroupMemberRepository groupMemberRepository, GroupRepository groupRepository){
        this.findGroupByName = findGroupByName;
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
    }

    public void initialize(String name){
        this.name = name;
    }

    public Group execute(){
        findGroupByName.initialize(name);
        Group group = findGroupByName.execute();

        for(GroupMember member : group.getGroupMember()){
            member.setPartner(null);
            groupMemberRepository.save(member);
        }

        return groupRepository.save(group);
    }
}
