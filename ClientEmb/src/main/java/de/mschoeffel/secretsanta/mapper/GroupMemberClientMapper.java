package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberClientMapper {

    public GroupMemberDto clientDtoToDto(GroupMemberClientDto groupMemberClientDto){
        if(groupMemberClientDto != null) {
            GroupMemberDto groupMemberDto = new GroupMemberDto();

            groupMemberDto.setId(groupMemberClientDto.getId());
            groupMemberDto.setName(groupMemberClientDto.getName());
            groupMemberDto.setRerolls(groupMemberClientDto.getRerolls());
            groupMemberDto.setKey(groupMemberClientDto.getKey());

            //TODO: This will break: Infinite loop of evaluating partner
            groupMemberDto.setPartner(clientDtoToDto(groupMemberClientDto.getPartner()));

            return groupMemberDto;
        }
        return null;
    }

    public GroupMemberClientDto dtoToClientDto(GroupMemberDto groupMemberDto){
        if(groupMemberDto != null) {
            GroupMemberClientDto groupMemberClientDto = new GroupMemberClientDto();

            groupMemberClientDto.setId(groupMemberDto.getId());
            groupMemberClientDto.setName(groupMemberDto.getName());
            groupMemberClientDto.setRerolls(groupMemberDto.getRerolls());
            groupMemberClientDto.setKey(groupMemberDto.getKey());

            //TODO: This will break: Infinite loop of evaluating partner
            groupMemberClientDto.setPartner(dtoToClientDto(groupMemberDto.getPartner()));

            return groupMemberClientDto;
        }
        return null;
    }

    public List<GroupMemberDto> clientDtoToDto(List<GroupMemberClientDto> groupMemberClientDtos){
        return groupMemberClientDtos.stream().map(this::clientDtoToDto).collect(Collectors.toList());
    }

    public List<GroupMemberClientDto> dtoToClientDto(List<GroupMemberDto> groupMemberDtos){
        return groupMemberDtos.stream().map(this::dtoToClientDto).collect(Collectors.toList());
    }

}
