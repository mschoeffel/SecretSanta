package de.mschoeffel.secretsanta.controller.v1;

import de.mschoeffel.secretsanta.error.AlreadyAllDrawnException;
import de.mschoeffel.secretsanta.error.AlreadyPartnerAcceptedException;
import de.mschoeffel.secretsanta.error.NoMoreRerollsException;
import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.service.v1.GroupMemberClientService;
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
    public GroupMemberClientDto drawPartner(@RequestBody DrawRequestClientDto drawRequestClientDto){
        try {
            return groupMemberClientService.drawPartner(drawRequestClientDto);
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
    public GroupMemberClientDto acceptPartner(@RequestBody DrawRequestClientDto drawRequestClientDto){
        try {
            return groupMemberClientService.acceptPartner(drawRequestClientDto);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
    }

}
