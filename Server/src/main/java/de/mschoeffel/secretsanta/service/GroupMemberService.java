package de.mschoeffel.secretsanta.service;

import de.mschoeffel.secretsanta.dto.GroupMemberDto;

public interface GroupMemberService {

    String drawPartner(String groupname, String name, String key);


}
