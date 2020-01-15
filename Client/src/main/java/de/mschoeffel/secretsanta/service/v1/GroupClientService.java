package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.model.v1.GroupClientDto;

public interface GroupClientService {

    GroupClientDto createGroup(GroupClientDto group);
    GroupClientDto findGroupByName(String name);

    /**
     * Checks if a group with the given name already exists.
     * @param name Name to check for.
     * @return true if the already is a group with the given name, false if there isn't.
     */
    boolean checkGroupName(String name);
}
