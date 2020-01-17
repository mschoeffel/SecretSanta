package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class GetPartnerToMember {

    private String groupname;
    private String name;
    private String key;

    private FindGroupByName findGroupByName;
    private FindGroupMemberWithCredentials findGroupMemberWithCredentials;

    @Autowired
    public GetPartnerToMember(FindGroupByName findGroupByName, FindGroupMemberWithCredentials findGroupMemberWithCredentials) {
        this.findGroupByName = findGroupByName;
        this.findGroupMemberWithCredentials = findGroupMemberWithCredentials;
    }

    public void initialize(String groupname, String name, String key) {
        this.groupname = groupname;
        this.name = name;
        this.key = key;
    }

    public GroupMember execute() {
        findGroupByName.initialize(groupname);
        Group group = findGroupByName.execute();

        findGroupMemberWithCredentials.initialize(groupname, name, key, group);
        GroupMember member = findGroupMemberWithCredentials.execute();

        return member.getPartner();

    }

}
