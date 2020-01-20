package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.model.v1.GroupClientDto;

public interface GroupClientService {

    /**
     * Creates a new group with all necessary information like key generation and member-group connection.
     * @param group Group to create.
     * @return Created group.
     */
    GroupClientDto createGroup(GroupClientDto group);


    GroupClientDto findGroupByName(String name);

    /**
     * Checks if a group with the given name already exists.
     * @param name Name to check for.
     * @return true if the already is a group with the given name, false if there isn't.
     */
    boolean checkGroupName(String name);

    void deleteGroup(String name);

    GroupClientDto clearAllPartner(String name);
}
