package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteGroupByName {

    private String groupname;

    private FindGroupByName findGroupByName;
    private GroupRepository groupRepository;

    @Autowired
    public DeleteGroupByName(FindGroupByName findGroupByName, GroupRepository groupRepository){
        this.findGroupByName = findGroupByName;
        this.groupRepository = groupRepository;
    }

    public void initialize(String name){
        this.groupname = name;
    }

    public void execute(){
        findGroupByName.initialize(groupname);
        Group group = findGroupByName.execute();

        groupRepository.delete(group);
    }
}
