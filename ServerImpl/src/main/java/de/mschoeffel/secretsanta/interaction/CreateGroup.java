package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Interaction to create a new group wit all the necessary information like key generation and member - group connection.
 */
@Component
public class CreateGroup {

    private Group group;

    private CreatePlainGroup createPlainGroup;
    private CopyRerollsToMember copyRerollsToMember;
    private GenerateKeysToGroup generateKeysToGroup;
    private SetDrawAcceptedFalseToMember setDrawAcceptedFalseToMember;
    private GroupRepository groupRepository;

    @Autowired
    public CreateGroup(CreatePlainGroup createPlainGroup,
                       CopyRerollsToMember copyRerollsToMember,
                       GenerateKeysToGroup generateKeysToGroup,
                       SetDrawAcceptedFalseToMember setDrawAcceptedFalseToMember,
                       GroupRepository groupRepository){
        this.createPlainGroup = createPlainGroup;
        this.copyRerollsToMember = copyRerollsToMember;
        this.generateKeysToGroup = generateKeysToGroup;
        this.setDrawAcceptedFalseToMember = setDrawAcceptedFalseToMember;
    }

    public void initialize(Group group){
        this.group = group;
    }

    public Group execute(){
        createPlainGroup.initialize(group);
        Group group = createPlainGroup.execute();

        setDrawAcceptedFalseToMember.initialize(group);
        group = setDrawAcceptedFalseToMember.execute();

        copyRerollsToMember.initialize(group);
        group = copyRerollsToMember.execute();

        generateKeysToGroup.initialize(group);
        group = generateKeysToGroup.execute();

        groupRepository.save(group);

        return group;
    }

}
