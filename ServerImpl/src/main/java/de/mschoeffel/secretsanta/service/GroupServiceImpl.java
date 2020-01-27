package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupDto;
import de.mschoeffel.secretsanta.interaction.*;
import de.mschoeffel.secretsanta.mapper.GroupMapper;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FindGroupByName findGroupByName;

    @Autowired
    private CreateGroup createGroup;

    @Autowired
    private DeleteGroupByName deleteGroupByName;

    @Autowired
    private ClearAllPartnerFromGroup clearAllPartnerFromGroup;

    @Autowired
    private FindGroupByNameAndToken findGroupByNameAndToken;

    @Autowired
    private DeleteGroupByNameAndToken deleteGroupByNameAndToken;

    /**
     * Creates a new group with all necessary information like key generation and member-group connection.
     * @param groupDto Group to create.
     * @return Created group.
     */
    @Override
    public GroupDto createGroup(GroupDto groupDto) {
        GroupMapper mapper = new GroupMapper();

        createGroup.initialize(mapper.dtoToEntity(groupDto));
        Group group = createGroup.execute();

        return mapper.entityToDto(group);
    }

    @Override
    public GroupDto findGroupByName(String name) {
        GroupMapper mapper = new GroupMapper();
        findGroupByName.initialize(name);
        return mapper.entityToDto(findGroupByName.execute());
    }

    @Override
    public GroupDto findGroupByNameAndToken(String name, String token) {
        GroupMapper mapper = new GroupMapper();
        findGroupByNameAndToken.initialize(name, token);
        return mapper.entityToDto(findGroupByNameAndToken.execute());
    }

    @Override
    public void deleteGroupByNameAndToken(String name, String token) {
        GroupMapper mapper = new GroupMapper();
        deleteGroupByNameAndToken.initialize(name, token);
       deleteGroupByNameAndToken.execute();
    }

    /**
     * Checks if a group with the given name already exists.
     * @param name Name to check for.
     * @return true if the already is a group with the given name, false if there isn't.
     */
    public boolean checkGroupName(String name){
        return groupRepository.existsByName(name);
    }

    @Override
    public void deleteGroup(String name){
        deleteGroupByName.initialize(name);
        deleteGroupByName.execute();
    }

    @Override
    public GroupDto clearAllPartner(String name) {
        GroupMapper mapper = new GroupMapper();
        clearAllPartnerFromGroup.initialize(name);
        return mapper.entityToDto(clearAllPartnerFromGroup.execute());
    }
}
