package de.mschoeffel.secretsanta.controller.v1;

import de.mschoeffel.secretsanta.controller.BackendController;
import de.mschoeffel.secretsanta.error.*;
import de.mschoeffel.secretsanta.mapper.GroupMemberResultMapper;
import de.mschoeffel.secretsanta.model.GroupMember;
import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.results.GroupMemberResult;
import de.mschoeffel.secretsanta.service.v1.GroupMemberClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController("GroupMemberRestController_v1")
@RequestMapping("/api/v1")
public class GroupMemberRestController {

    @Autowired
    private GroupMemberClientService groupMemberClientService;

    @RequestMapping("/groupmember/draw")
    public GroupMemberResult drawPartner(@RequestBody DrawRequestClientDto drawRequestClientDto){
        GroupMemberResultMapper mapper = new GroupMemberResultMapper();

        try {
            return mapper.clientDtoToResult(groupMemberClientService.drawPartner(drawRequestClientDto));
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        } catch(NoMoreRerollsException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No more rerolls");
        } catch(AlreadyAllDrawnException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Already all drawn");
        } catch(AlreadyPartnerAcceptedException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Already Partner accepted");
        }
    }

    @RequestMapping("/groupmember/accept")
    public GroupMemberResult acceptPartner(@RequestBody DrawRequestClientDto drawRequestClientDto){
        GroupMemberResultMapper mapper = new GroupMemberResultMapper();

        try {
            return mapper.clientDtoToResult(groupMemberClientService.acceptPartner(drawRequestClientDto));
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        } catch(NoPartnerToAcceptException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No Partner to accept");
        }
    }

    @RequestMapping("/groupmember/partner")
    public GroupMemberResult getPartner(@RequestBody DrawRequestClientDto drawRequestClientDto){
        GroupMemberResultMapper mapper = new GroupMemberResultMapper();

        try{
            return mapper.clientDtoToResult(groupMemberClientService.getPartner(drawRequestClientDto));
        } catch(NoPartnerFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No Partner found");
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
    }

    @RequestMapping("/groupmember/member")
    public GroupMemberResult getMember(@RequestBody DrawRequestClientDto drawRequestClientDto){
        GroupMemberResultMapper mapper = new GroupMemberResultMapper();

        try{
            return mapper.clientDtoToResult(groupMemberClientService.getMember(drawRequestClientDto));
        } catch(EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
    }

}
