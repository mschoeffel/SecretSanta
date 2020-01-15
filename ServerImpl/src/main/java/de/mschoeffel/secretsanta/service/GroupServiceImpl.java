package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupDto;
import de.mschoeffel.secretsanta.interaction.CopyRerollsToMember;
import de.mschoeffel.secretsanta.interaction.CreateGroup;
import de.mschoeffel.secretsanta.interaction.CreatePlainGroup;
import de.mschoeffel.secretsanta.interaction.GenerateKeysToGroup;
import de.mschoeffel.secretsanta.mapper.GroupMapper;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private CreateGroup createGroup;

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
        return mapper.entityToDto(groupRepository.findByName(name).orElseThrow(EntityNotFoundException::new));
    }

    /**
     * Checks if a group with the given name already exists.
     * @param name Name to check for.
     * @return true if the already is a group with the given name, false if there isn't.
     */
    public boolean checkGroupName(String name){
        return groupRepository.existsByName(name);
    }
}
