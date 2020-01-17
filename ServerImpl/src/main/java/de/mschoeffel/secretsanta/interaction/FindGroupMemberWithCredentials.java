package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.EntityNotFoundException;

@Component
public class FindGroupMemberWithCredentials {

    private String groupname;
    private String name;
    private String key;
    private Group group;

    private GroupMemberRepository groupMemberRepository;
    private FindGroupByName findGroupByName;

    @Autowired
    public FindGroupMemberWithCredentials(GroupMemberRepository groupMemberRepository, FindGroupByName findGroupByName) {
        this.groupMemberRepository = groupMemberRepository;
        this.findGroupByName = findGroupByName;
    }

    public void initialize(String groupname, String name, String key, Group group) {
        reset();
        this.groupname = groupname;
        this.name = name;
        this.key = key;
        this.group = group;
    }

    public void initialize(String groupname, String name, String key) {
        reset();
        this.groupname = groupname;
        this.name = name;
        this.key = key;
    }

    public GroupMember execute() {
        if (group == null) {
            findGroupByName.initialize(groupname);
            group = findGroupByName.execute();
        }
        return groupMemberRepository.findByNameAndKeyAndGroup(name, key, group).orElseThrow(EntityNotFoundException::new);
    }

    private void reset(){
        this.groupname = null;
        this.name = null;
        this.key = null;
        this.group = null;
    }

}
