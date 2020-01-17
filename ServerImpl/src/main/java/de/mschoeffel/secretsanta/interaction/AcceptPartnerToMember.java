package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.annotation.RequestScope;

@Component
public class AcceptPartnerToMember {

    private String groupname;
    private String name;
    private String key;
    private GroupMember member;

    private GroupMemberRepository groupMemberRepository;
    private FindGroupMemberWithCredentials findGroupMemberWithCredentials;

    @Autowired
    public AcceptPartnerToMember(GroupMemberRepository groupMemberRepository, FindGroupMemberWithCredentials findGroupMemberWithCredentials) {
        this.groupMemberRepository = groupMemberRepository;
        this.findGroupMemberWithCredentials = findGroupMemberWithCredentials;
    }

    public void initialize(GroupMember member) {
        reset();
        this.member = member;
    }

    public void initialize(String groupname, String name, String key) {
        reset();
        this.groupname = groupname;
        this.name = name;
        this.key = key;
    }

    public GroupMember execute() {
        if (member == null) {
            findGroupMemberWithCredentials.initialize(groupname, name, key);
            member = findGroupMemberWithCredentials.execute();
        }

        if (member.getDrawAccepted() == null || !member.getDrawAccepted()) {
            member.setDrawAccepted(true);
            member = groupMemberRepository.save(member);
        }

        return member;
    }

    private void reset(){
        this.groupname = null;
        this.name = null;
        this.key = null;
        this.member = null;
    }

}
