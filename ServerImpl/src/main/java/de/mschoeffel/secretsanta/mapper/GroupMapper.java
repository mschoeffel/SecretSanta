package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupDto;
import de.mschoeffel.secretsanta.model.Group;

public class GroupMapper {

    GroupDto entityToDto(Group group){
        GroupDto groupDto = new GroupDto();

        return groupDto;
    }

    Group dtoToEntity(GroupDto groupDto){
        Group group = new Group();

        return group;
    }

}
