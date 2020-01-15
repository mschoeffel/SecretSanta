package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.model.GroupMember;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberMapper {

    public GroupMemberDto entityToDto(GroupMember groupMember){
        if(groupMember != null) {
            GroupMemberDto groupMemberDto = new GroupMemberDto();

            groupMemberDto.setId(groupMember.getId());
            groupMemberDto.setName(groupMember.getName());
            groupMemberDto.setRerolls(groupMember.getRerolls());
            groupMemberDto.setKey(groupMember.getKey());

            //TODO: This will break: Infinite loop of evaluating partner
            groupMemberDto.setPartner(entityToDto(groupMember.getPartner()));

            return groupMemberDto;
        }
        return null;
    }

    public GroupMember dtoToEntity(GroupMemberDto groupMemberDto){
        if(groupMemberDto != null) {
            GroupMember groupMember = new GroupMember();

            groupMember.setId(groupMemberDto.getId());
            groupMember.setName(groupMemberDto.getName());
            groupMember.setRerolls(groupMemberDto.getRerolls());
            groupMember.setKey(groupMemberDto.getKey());

            //TODO: This will break: Infinite loop of evaluating partner
            groupMember.setPartner(dtoToEntity(groupMemberDto.getPartner()));

            return groupMember;
        }
        return null;
    }

    List<GroupMemberDto> entityToDto(List<GroupMember> groupMember){
        return groupMember.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    List<GroupMember> dtoToEntity(List<GroupMemberDto> groupMemberDtos){
        return groupMemberDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
