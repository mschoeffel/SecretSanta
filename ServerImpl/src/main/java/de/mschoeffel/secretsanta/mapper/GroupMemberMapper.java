package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;
import de.mschoeffel.secretsanta.model.GroupMember;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberMapper {

    GroupMemberDto entityToDto(GroupMember groupMember){
        GroupMemberDto groupMemberDto = new GroupMemberDto();

        return groupMemberDto;
    }

    GroupMember dtoToEntity(GroupMemberDto groupMemberDto){
        GroupMember groupMember = new GroupMember();

        return groupMember;
    }

    List<GroupMemberDto> entityToDto(List<GroupMember> groupMember){
        return groupMember.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    List<GroupMember> dtoToEntity(List<GroupMemberDto> groupMemberDtos){
        return groupMemberDtos.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
