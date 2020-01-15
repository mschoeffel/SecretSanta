package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupDto;

import java.util.Optional;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto);
    GroupDto findGroupByName(String name);
    boolean checkGroupName(String name);

}
