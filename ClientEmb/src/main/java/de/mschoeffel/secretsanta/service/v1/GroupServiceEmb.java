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

    /**
     * Creates a new group with all necessary information like key generation and member-group connection.
     * @param group Group to create.
     * @return Created group.
     */
    @Override
    public GroupClientDto createGroup(GroupClientDto group) {
        GroupClientMapper mapper = new GroupClientMapper();
        return mapper.dtoToClientDto(groupService.createGroup(mapper.clientDtoToDto(group)));
    }

    @Override
    public GroupClientDto findGroupByName(String name){
        GroupClientMapper mapper = new GroupClientMapper();
        return mapper.dtoToClientDto(groupService.findGroupByName(name));
    }

    @Override
    public GroupClientDto findGroupByNameAndToken(String name, String token) {
        GroupClientMapper mapper = new GroupClientMapper();
        return mapper.dtoToClientDto(groupService.findGroupByNameAndToken(name, token));
    }

    /**
     * Checks if a group with the given name already exists.
     * @param name Name to check for.
     * @return true if the already is a group with the given name, false if there isn't.
     */
    @Override
    public boolean checkGroupName(String name) {
        return groupService.checkGroupName(name);
    }

    @Override
    public void deleteGroup(String name) {
        groupService.deleteGroup(name);
    }

    @Override
    public GroupClientDto clearAllPartner(String name) {
        GroupClientMapper mapper = new GroupClientMapper();
        return mapper.dtoToClientDto(groupService.clearAllPartner(name));
    }

    @Override
    public void deleteGroupByNameAndToken(String name, String token){
        groupService.deleteGroupByNameAndToken(name, token);
    }
}
