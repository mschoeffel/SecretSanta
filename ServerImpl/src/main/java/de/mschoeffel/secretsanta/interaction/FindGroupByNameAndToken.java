package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class FindGroupByNameAndToken {

    private String name;
    private String token;

    private GroupRepository groupRepository;

    @Autowired
    public FindGroupByNameAndToken(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public void initialize(String name, String token){
        this.name = name;
        this.token = token;
    }

    public Group execute(){
        return groupRepository.findByNameAndToken(name, token).orElseThrow(EntityNotFoundException::new);
    }
}
