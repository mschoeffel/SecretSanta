package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.results.GroupMemberCreationResult;
import de.mschoeffel.secretsanta.results.GroupMemberResult;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMemberResultMapper {

    public GroupMemberResult clientDtoToResult(GroupMemberClientDto groupMemberClientDto){
        GroupMemberResult groupMemberResult = new GroupMemberResult();

        groupMemberResult.setName(groupMemberClientDto.getName());
        groupMemberResult.setDrawAccepted(groupMemberClientDto.getDrawAccepted());
        groupMemberResult.setRerolls(groupMemberClientDto.getRerolls());
        if(groupMemberClientDto.getPartner() != null) {
            groupMemberResult.setPartner(groupMemberClientDto.getPartner().getName());
        }

        return groupMemberResult;
    }

    public GroupMemberCreationResult clientDtoToCreationResult(GroupMemberClientDto groupMemberClientDto){
        GroupMemberCreationResult groupMemberResult = new GroupMemberCreationResult();

        groupMemberResult.setName(groupMemberClientDto.getName());
        groupMemberResult.setDrawAccepted(groupMemberClientDto.getDrawAccepted());
        groupMemberResult.setRerolls(groupMemberClientDto.getRerolls());
        groupMemberResult.setKey(groupMemberClientDto.getKey());

        return groupMemberResult;
    }

    public List<GroupMemberResult> clientDtoToResult(List<GroupMemberClientDto> groupMemberClientDtos){
        return groupMemberClientDtos.stream().map(this::clientDtoToResult).collect(Collectors.toList());
    }

    public List<GroupMemberCreationResult> clientDtoToCreationResult(List<GroupMemberClientDto> groupMemberClientDtos){
        return groupMemberClientDtos.stream().map(this::clientDtoToCreationResult).collect(Collectors.toList());
    }

}
