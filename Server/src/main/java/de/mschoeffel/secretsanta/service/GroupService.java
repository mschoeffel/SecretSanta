package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupDto;

public interface GroupService {

    GroupDto createGroup(GroupDto groupDto);
    GroupDto findGroupByName(String name);

}
