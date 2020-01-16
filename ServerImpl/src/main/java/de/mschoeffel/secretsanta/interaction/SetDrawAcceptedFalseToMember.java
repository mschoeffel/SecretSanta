package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetDrawAcceptedFalseToMember {

    private Group group;
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    public SetDrawAcceptedFalseToMember(GroupMemberRepository groupMemberRepository){
        this.groupMemberRepository = groupMemberRepository;
    }

    public void initialize(Group group){
        this.group = group;
    }

    public Group execute(){
        for(GroupMember member : group.getGroupMember()){
            member.setDrawAccepted(false);
            groupMemberRepository.save(member);
        }
        return group;
    }
}
