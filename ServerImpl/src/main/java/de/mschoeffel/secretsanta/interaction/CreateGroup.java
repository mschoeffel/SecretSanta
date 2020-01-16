package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
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

    @Autowired
    public CreateGroup(CreatePlainGroup createPlainGroup,
                       CopyRerollsToMember copyRerollsToMember,
                       GenerateKeysToGroup generateKeysToGroup,
                       SetDrawAcceptedFalseToMember setDrawAcceptedFalseToMember){
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

        return group;
    }

}
