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
    public GroupMemberClientDto drawPartner(DrawRequestClientDto groupMember) {
        GroupMemberClientMapper mapper = new GroupMemberClientMapper();
        return mapper.dtoToClientDto(groupMemberService.drawPartner(groupMember.getGroupname(), groupMember.getName(), groupMember.getKey()), 0);
    }

    @Override
    public GroupMemberClientDto acceptPartner(DrawRequestClientDto groupMember) {
        GroupMemberClientMapper mapper = new GroupMemberClientMapper();
        return mapper.dtoToClientDto(groupMemberService.acceptPartner(groupMember.getGroupname(), groupMember.getName(), groupMember.getKey()), 0);
    }
}
