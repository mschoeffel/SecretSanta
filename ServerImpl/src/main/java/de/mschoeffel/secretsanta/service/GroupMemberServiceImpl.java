package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.interaction.AcceptPartnerToMember;
import de.mschoeffel.secretsanta.interaction.DrawPartnerToMember;
import de.mschoeffel.secretsanta.mapper.GroupMemberMapper;
import de.mschoeffel.secretsanta.model.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberServiceImpl implements GroupMemberService{

    @Autowired
    private DrawPartnerToMember drawPartnerToMember;

    @Autowired
    private AcceptPartnerToMember acceptPartnerToMember;

    @Override
    public GroupMemberDto drawPartner(String groupname, String name, String key) {
        GroupMemberMapper mapper = new GroupMemberMapper();

        drawPartnerToMember.initialize(groupname, name, key);
        GroupMember member = drawPartnerToMember.execute();

        return mapper.entityToDto(member.getPartner(),0);
    }

    @Override
    public GroupMemberDto acceptPartner(String groupname, String name, String key) {
        GroupMemberMapper mapper = new GroupMemberMapper();

        acceptPartnerToMember.initialize(groupname, name, key);
        return mapper.entityToDto(acceptPartnerToMember.execute(), 0);
    }
}
