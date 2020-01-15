package de.mschoeffel.secretsanta.controller.v1;

import de.mschoeffel.secretsanta.controller.BackendController;
import de.mschoeffel.secretsanta.model.v1.GroupClientDto;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("GroupRestController_v1")
@RequestMapping("/api/v1")
public class GroupRestController {

    private static final Logger LOG = LoggerFactory.getLogger(GroupRestController.class);

    @Autowired
    private GroupClientService groupClientService;

    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public GroupClientDto createGroup(@RequestBody GroupClientDto group){

        LOG.info("v1: Data received: " + group.toString());

        GroupClientDto temp = groupClientService.createGroup(group);
        LOG.info("v1: Data created: " + temp.toString());
        return temp;
    }

    @RequestMapping(value = "/group/{name}", method = RequestMethod.GET)
    public GroupClientDto getGroup(@PathVariable(name = "name") String name){

        LOG.info("v1: Data requested: " + name);

        GroupClientDto temp = groupClientService.findGroupByName(name);
        LOG.info("v1: Data received: " + temp.toString());
        return temp;
    }

}
