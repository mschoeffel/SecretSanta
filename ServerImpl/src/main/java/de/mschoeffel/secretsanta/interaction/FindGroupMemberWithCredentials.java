package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import javax.persistence.EntityNotFoundException;

@Component
@RequestScope
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
        initialize(groupname, name, key);
        this.group = group;
    }

    public void initialize(String groupname, String name, String key) {
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

}
