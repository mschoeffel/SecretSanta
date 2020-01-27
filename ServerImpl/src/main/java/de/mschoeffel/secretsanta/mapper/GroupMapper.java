package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupDto;
import de.mschoeffel.secretsanta.model.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public GroupDto entityToDto(Group group){
        if(group != null) {
            GroupDto groupDto = new GroupDto();

            groupDto.setId(group.getId());
            groupDto.setName(group.getName());
            groupDto.setRerolls(group.getRerolls());
            groupDto.setToken(group.getToken());

            GroupMemberMapper mapper = new GroupMemberMapper();
            groupDto.setMembers(mapper.entityToDto(group.getGroupMember()));

            return groupDto;
        }
        return null;
    }

    public Group dtoToEntity(GroupDto groupDto){
        if(groupDto != null) {
            Group group = new Group();

            group.setId(groupDto.getId());
            group.setName(groupDto.getName());
            group.setRerolls(groupDto.getRerolls());
            group.setToken(groupDto.getToken());

            GroupMemberMapper mapper = new GroupMemberMapper();
            group.setGroupMember(mapper.dtoToEntity(groupDto.getMembers()));

            return group;
        }
        return null;
    }

    public List<GroupDto> entityToDto(List<Group> groups){
        return groups.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public List<Group> dtoToEntity(List<GroupDto> groupDtos){
        return groupDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
