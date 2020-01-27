package de.mschoeffel.secretsanta.interaction;

import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class DeleteGroupByNameAndToken {

    private String name;
    private String token;

    private GroupRepository groupRepository;

    @Autowired
    public DeleteGroupByNameAndToken(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public void initialize(String name, String token){
        this.name = name;
        this.token = token;
    }

    @Transactional
    public void execute(){
        groupRepository.deleteByNameAndToken(name, token);
    }
}
