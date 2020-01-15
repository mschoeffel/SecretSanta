package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.model.v1.GroupClientDto;

public interface GroupClientService {

    GroupClientDto createGroup(GroupClientDto group);
    GroupClientDto findGroupByName(String name);
    boolean checkGroupName(String name);
}
