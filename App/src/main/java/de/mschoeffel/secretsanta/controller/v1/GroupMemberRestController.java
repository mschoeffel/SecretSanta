package de.mschoeffel.secretsanta.controller.v1;

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
    public String drawPartner(@RequestBody DrawRequestClientDto drawRequestClientDto){
        try {
            return groupMemberClientService.drawPartner(drawRequestClientDto);
        } catch(EntityNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid credentials");
        }
    }

}
