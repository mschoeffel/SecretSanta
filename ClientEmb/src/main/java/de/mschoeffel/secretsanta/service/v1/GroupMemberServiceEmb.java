package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.mapper.GroupMemberClientMapper;
import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupMemberServiceEmb implements GroupMemberClientService {

    @Autowired
    private GroupMemberService groupMemberService;

    @Override
    public String drawPartner(DrawRequestClientDto groupMember) {
        return groupMemberService.drawPartner(groupMember.getGroupname(), groupMember.getName(), groupMember.getKey());
    }
}
