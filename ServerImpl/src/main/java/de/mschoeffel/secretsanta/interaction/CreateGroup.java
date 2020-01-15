package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateGroup {

    private Group group;

    private CreatePlainGroup createPlainGroup;
    private CopyRerollsToMember copyRerollsToMember;
    private GenerateKeysToGroup generateKeysToGroup;

    @Autowired
    public CreateGroup(CreatePlainGroup createPlainGroup, CopyRerollsToMember copyRerollsToMember, GenerateKeysToGroup generateKeysToGroup){
        this.createPlainGroup = createPlainGroup;
        this.copyRerollsToMember = copyRerollsToMember;
        this.generateKeysToGroup = generateKeysToGroup;
    }

    public void initialize(Group group){
        this.group = group;
    }

    public Group execute(){
        createPlainGroup.initialize(group);
        Group group = createPlainGroup.execute();

        copyRerollsToMember.initialize(group);
        group = copyRerollsToMember.execute();

        generateKeysToGroup.initialize(group);
        group = generateKeysToGroup.execute();

        return group;
    }

}
