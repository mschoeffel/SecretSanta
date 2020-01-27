package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupDto;
import de.mschoeffel.secretsanta.model.v1.GroupClientDto;

import java.util.List;
import java.util.stream.Collectors;

public class GroupClientMapper {

    public GroupClientDto dtoToClientDto(GroupDto groupDto){
        if(groupDto != null) {
            GroupClientDto groupClientDto = new GroupClientDto();

            groupClientDto.setId(groupDto.getId());
            groupClientDto.setName(groupDto.getName());
            groupClientDto.setRerolls(groupDto.getRerolls());
            groupClientDto.setToken(groupDto.getToken());

            GroupMemberClientMapper mapper = new GroupMemberClientMapper();
            groupClientDto.setMembers(mapper.dtoToClientDto(groupDto.getMembers()));

            return groupClientDto;
        }
        return null;
    }

    public GroupDto clientDtoToDto(GroupClientDto groupClientDto){
        if(groupClientDto != null) {
            GroupDto groupDto = new GroupDto();

            groupDto.setId(groupClientDto.getId());
            groupDto.setName(groupClientDto.getName());
            groupDto.setRerolls(groupClientDto.getRerolls());
            groupDto.setToken(groupClientDto.getToken());

            GroupMemberClientMapper mapper = new GroupMemberClientMapper();
            groupDto.setMembers(mapper.clientDtoToDto(groupClientDto.getMembers()));

            return groupDto;
        }
        return null;
    }

    public List<GroupClientDto> dtoToClientDto(List<GroupDto> groupDtos){
        return groupDtos.stream().map(this::dtoToClientDto).collect(Collectors.toList());
    }

    public List<GroupDto> clientDtoToDto(List<GroupClientDto> groupClientDtos){
        return groupClientDtos.stream().map(this::clientDtoToDto).collect(Collectors.toList());
    }

}
