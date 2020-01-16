package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberClientMapper {

    public GroupMemberDto clientDtoToDto(GroupMemberClientDto groupMemberClientDto, int layer){
        if(groupMemberClientDto != null && layer < 2) {
            GroupMemberDto groupMemberDto = new GroupMemberDto();

            groupMemberDto.setId(groupMemberClientDto.getId());
            groupMemberDto.setName(groupMemberClientDto.getName());
            groupMemberDto.setRerolls(groupMemberClientDto.getRerolls());
            groupMemberDto.setKey(groupMemberClientDto.getKey());

            //FIX: This will break: Infinite loop of evaluating partner -> Should be fixed using layer
            groupMemberDto.setPartner(clientDtoToDto(groupMemberClientDto.getPartner(), layer + 1));

            return groupMemberDto;
        }
        return null;
    }

    public GroupMemberClientDto dtoToClientDto(GroupMemberDto groupMemberDto, int layer){
        if(groupMemberDto != null && layer < 2) {
            GroupMemberClientDto groupMemberClientDto = new GroupMemberClientDto();

            groupMemberClientDto.setId(groupMemberDto.getId());
            groupMemberClientDto.setName(groupMemberDto.getName());
            groupMemberClientDto.setRerolls(groupMemberDto.getRerolls());
            groupMemberClientDto.setKey(groupMemberDto.getKey());

            //FIX: This will break: Infinite loop of evaluating partner -> Should be fixed using layer
            groupMemberClientDto.setPartner(dtoToClientDto(groupMemberDto.getPartner(), layer + 1));

            return groupMemberClientDto;
        }
        return null;
    }

    public List<GroupMemberDto> clientDtoToDto(List<GroupMemberClientDto> groupMemberClientDtos){
        return groupMemberClientDtos.stream().map(member -> clientDtoToDto(member, 0)).collect(Collectors.toList());
    }

    public List<GroupMemberClientDto> dtoToClientDto(List<GroupMemberDto> groupMemberDtos){
        return groupMemberDtos.stream().map(member -> dtoToClientDto(member, 0)).collect(Collectors.toList());
    }

}
