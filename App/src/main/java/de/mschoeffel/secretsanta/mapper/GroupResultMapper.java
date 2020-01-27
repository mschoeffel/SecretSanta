package de.mschoeffel.secretsanta.mapper;

import de.mschoeffel.secretsanta.model.v1.GroupClientDto;
import de.mschoeffel.secretsanta.results.GroupCreationResult;
import de.mschoeffel.secretsanta.results.GroupResult;

public class GroupResultMapper {

    public GroupResult clientDtoToResult(GroupClientDto groupClientDto){
        GroupResult groupResult = new GroupResult();

        groupResult.setName(groupClientDto.getName());

        GroupMemberResultMapper mapper = new GroupMemberResultMapper();
        groupResult.setMembers(mapper.clientDtoToResult(groupClientDto.getMembers()));

        return groupResult;
    }

    public GroupCreationResult clientDtoToCreationResult(GroupClientDto groupClientDto){
        GroupCreationResult groupResult = new GroupCreationResult();

        groupResult.setName(groupClientDto.getName());
        groupResult.setToken(groupClientDto.getToken());

        GroupMemberResultMapper mapper = new GroupMemberResultMapper();
        groupResult.setMembers(mapper.clientDtoToCreationResult(groupClientDto.getMembers()));

        return groupResult;
    }

}
