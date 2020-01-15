package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.mapper.GroupMapper;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;

public class CreateGroup {

    private Group group;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;
    private  GroupClientService groupClientService;

    public CreateGroup(Group group, GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, GroupClientService groupClientService){
        this.group = group;
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.groupClientService = groupClientService;
    }

    public Group execute(){
        CreatePlainGroup createPlainGroup = new CreatePlainGroup(group, groupRepository, groupMemberRepository, groupClientService);
        Group group = createPlainGroup.execute();

        CopyRerollsToMember copyRerollsToMember = new CopyRerollsToMember(group, groupMemberRepository);
        group = copyRerollsToMember.execute();

        GenerateKeysToGroup generateKeysToGroup = new GenerateKeysToGroup(group, groupMemberRepository);
        group = generateKeysToGroup.execute();

        return group;
    }

}
