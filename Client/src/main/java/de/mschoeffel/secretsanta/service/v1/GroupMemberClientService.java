package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;

public interface GroupMemberClientService {

    String drawPartner(DrawRequestClientDto groupMember);
    GroupMemberClientDto acceptPartner(DrawRequestClientDto groupMember);

}
