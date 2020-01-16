package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;

public interface GroupMemberClientService {

    String drawPartner(DrawRequestClientDto groupMember);

}
