package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupDto;
import de.mschoeffel.secretsanta.mapper.GroupMapper;
import de.mschoeffel.secretsanta.mapper.GroupMemberMapper;
import de.mschoeffel.secretsanta.model.Group;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.repository.GroupMemberRepository;
import de.mschoeffel.secretsanta.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService{

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Override
    public GroupDto createGroup(GroupDto groupDto) {
        if(checkGroupName(groupDto.getName())){
            throw new EntityExistsException();
        }
        GroupMapper mapper = new GroupMapper();
        GroupMemberMapper groupMemberMapper = new GroupMemberMapper();

        Group group = groupRepository.save(mapper.dtoToEntity(groupDto));

        groupDto.getMembers().forEach(member -> {
            GroupMember groupMember = groupMemberMapper.dtoToEntity(member);
            groupMember.setGroup(group);
            groupMemberRepository.save(groupMember);
        });

        return mapper.entityToDto(group);
    }

    @Override
    public GroupDto findGroupByName(String name) {
        GroupMapper mapper = new GroupMapper();
        return mapper.entityToDto(groupRepository.findByName(name).orElseThrow(EntityNotFoundException::new));
    }

    public boolean checkGroupName(String name){
        return groupRepository.existsByName(name);
    }
}
