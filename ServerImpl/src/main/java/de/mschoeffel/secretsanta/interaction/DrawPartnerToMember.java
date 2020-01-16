package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class DrawPartnerToMember {

    private String groupname;
    private String name;
    private String key;

    private GroupMember groupMember;

    private GroupMemberRepository groupMemberRepository;
    private GroupRepository groupRepository;

    @Autowired
    public DrawPartnerToMember(GroupMemberRepository groupMemberRepository, GroupRepository groupRepository){
        this.groupMemberRepository = groupMemberRepository;
        this.groupRepository = groupRepository;
    }

    public void initialize(String groupname, String name, String key){
        this.groupname = groupname;
        this.name = name;
        this.key = key;
    }

    public GroupMember execute(){
        Group group = groupRepository.findByName(groupname).orElseThrow(EntityNotFoundException::new);
        GroupMember groupMember = groupMemberRepository.findByNameAndKeyAndGroup(name, key, group).orElseThrow(EntityNotFoundException::new);

        return groupMember;
    }


}
