package de.mschoeffel.secretsanta.service.v1;

import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;

public interface GroupMemberClientService {

    GroupMemberClientDto drawPartner(DrawRequestClientDto groupMember);
    GroupMemberClientDto acceptPartner(DrawRequestClientDto groupMember);
    GroupMemberClientDto getPartner(DrawRequestClientDto groupMember);
    GroupMemberClientDto getMember(DrawRequestClientDto groupMember);

}
