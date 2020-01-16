package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcceptPartnerToMember {

    private String groupname;
    private String name;
    private String key;
    private GroupMember member;

    private GroupMemberRepository groupMemberRepository;
    private FindGroupMemberWithCredentials findGroupMemberWithCredentials;

    @Autowired
    public AcceptPartnerToMember(GroupMemberRepository groupMemberRepository, FindGroupMemberWithCredentials findGroupMemberWithCredentials){
        this.groupMemberRepository = groupMemberRepository;
        this.findGroupMemberWithCredentials = findGroupMemberWithCredentials;
    }

    public void initialize(GroupMember member){
        this.member = member;
    }

    public void initialize(String groupname, String name, String key){
        this.groupname = groupname;
        this.name = name;
        this.key = key;
    }

    public GroupMember execute() {
        if (member == null){
            findGroupMemberWithCredentials.initialize(groupname, name, key);
            member = findGroupMemberWithCredentials.execute();
        }

        if(member.getDrawAccepted() == null || !member.getDrawAccepted()) {
            member.setDrawAccepted(true);
            groupMemberRepository.save(member);
        }

        return member;
    }

}
