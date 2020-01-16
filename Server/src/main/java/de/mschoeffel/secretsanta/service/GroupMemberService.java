package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;

public interface GroupMemberService {

    GroupMemberDto drawPartner(String groupname, String name, String key);
    GroupMemberDto acceptPartner(String groupname, String name, String key);

}
