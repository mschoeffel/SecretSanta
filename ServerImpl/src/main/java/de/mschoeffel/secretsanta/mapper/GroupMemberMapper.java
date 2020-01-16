package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.model.GroupMember;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberMapper {

    public GroupMemberDto entityToDto(GroupMember groupMember, int layer){
        if(groupMember != null && layer < 2) {
            GroupMemberDto groupMemberDto = new GroupMemberDto();

            groupMemberDto.setId(groupMember.getId());
            groupMemberDto.setName(groupMember.getName());
            groupMemberDto.setRerolls(groupMember.getRerolls());
            groupMemberDto.setKey(groupMember.getKey());

            //FIX: This will break: Infinite loop of evaluating partner -> Should be fixed using layer
            groupMemberDto.setPartner(entityToDto(groupMember.getPartner(), layer + 1));

            return groupMemberDto;
        }
        return null;
    }

    public GroupMember dtoToEntity(GroupMemberDto groupMemberDto, int layer){
        if(groupMemberDto != null && layer < 2) {
            GroupMember groupMember = new GroupMember();

            groupMember.setId(groupMemberDto.getId());
            groupMember.setName(groupMemberDto.getName());
            groupMember.setRerolls(groupMemberDto.getRerolls());
            groupMember.setKey(groupMemberDto.getKey());

            //FIX: This will break: Infinite loop of evaluating partner -> Should be fixed using layer
            groupMember.setPartner(dtoToEntity(groupMemberDto.getPartner(), layer + 1));

            return groupMember;
        }
        return null;
    }

    List<GroupMemberDto> entityToDto(List<GroupMember> groupMember){
        return groupMember.stream().map(member -> entityToDto(member, 0)).collect(Collectors.toList());
    }

    List<GroupMember> dtoToEntity(List<GroupMemberDto> groupMemberDtos){
        return groupMemberDtos.stream().map(member -> dtoToEntity(member, 0)).collect(Collectors.toList());
    }

}
