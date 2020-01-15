package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.mapper.GroupClientMapper;
import de.mschoeffel.secretsanta.model.v1.GroupClientDto;
import de.mschoeffel.secretsanta.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceEmb implements GroupClientService{

    @Autowired
    private GroupService groupService;

    @Override
    public GroupClientDto createGroup(GroupClientDto group) {

        group.getMembers().forEach(member -> member.setRerolls(group.getRerolls()));

        GroupClientMapper mapper = new GroupClientMapper();
        return mapper.dtoToClientDto(groupService.createGroup(mapper.clientDtoToDto(group)));
    }

    @Override
    public GroupClientDto findGroupByName(String name) {
        GroupClientMapper mapper = new GroupClientMapper();
        return mapper.dtoToClientDto(groupService.findGroupByName(name));
    }
}
