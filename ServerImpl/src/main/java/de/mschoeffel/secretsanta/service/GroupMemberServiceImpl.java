package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.interaction.DrawPartnerToMember;
import de.mschoeffel.secretsanta.mapper.GroupMemberMapper;
import de.mschoeffel.secretsanta.model.GroupMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberServiceImpl implements GroupMemberService{

    @Autowired
    private DrawPartnerToMember drawPartnerToMember;

    @Override
    public String drawPartner(String groupname, String name, String key) {

        drawPartnerToMember.initialize(groupname, name, key);
        GroupMember member = drawPartnerToMember.execute();

        return member.getName();
    }
}
