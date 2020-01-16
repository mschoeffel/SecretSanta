package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class FindGroupByName {

    private String name;
    private GroupRepository groupRepository;

    @Autowired
    public FindGroupByName(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public void initialize(String name){
        this.name = name;
    }

    public Group execute(){
        return groupRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
    }
}
