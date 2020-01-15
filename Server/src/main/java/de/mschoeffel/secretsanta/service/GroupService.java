package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupDto;

import java.util.Optional;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto);
    GroupDto findGroupByName(String name);

    /**
     * Checks if a group with the given name already exists.
     * @param name Name to check for.
     * @return true if the already is a group with the given name, false if there isn't.
     */
    boolean checkGroupName(String name);

}