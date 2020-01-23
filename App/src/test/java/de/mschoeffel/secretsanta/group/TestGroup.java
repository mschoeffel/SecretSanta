package de.mschoeffel.secretsanta.group;

import de.mschoeffel.secretsanta.SecretSantaApplication;
import de.mschoeffel.secretsanta.error.GroupNameExistsException;
import de.mschoeffel.secretsanta.error.GroupNameValidationException;
import de.mschoeffel.secretsanta.error.NotEnoughMembersException;
import de.mschoeffel.secretsanta.model.v1.GroupClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SecretSantaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TestGroup {

    static final Logger LOG = LoggerFactory.getLogger(TestGroup.class);

    @Autowired
    GroupClientService groupClientService;

    @Test
    public void testGroupCreation(){

        int membercount = 5;
        String groupname = "unittestgroupcrea";
        if(groupClientService.checkGroupName(groupname)){
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(5);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for(int i = 1; i <= membercount; i++){
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + i);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        LOG.info("Group created performing assertions");
        Assert.assertEquals(groupClientDto.getName(), result.getName());
        Assert.assertEquals(groupClientDto.getRerolls(), result.getRerolls());
        Assert.assertEquals(membercount, result.getMembers().size());
        Assert.assertNotNull(result.getId());

        Set<String> nameSet = new HashSet<>();
        for(GroupMemberClientDto member : result.getMembers()){
            Assert.assertEquals((groupClientDto.getRerolls() + 1), (int) member.getRerolls());
            Assert.assertNotNull(member.getKey());
            Assert.assertNull(member.getPartner());
            Assert.assertFalse(nameSet.contains(member.getName()));
            nameSet.add(member.getName());
        }
        LOG.info("Group checks finished");

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    @Test(expected = GroupNameExistsException.class)
    public void testDuplicateGroupnameException(){
        String groupname = "unittestgroupcrea";
        int membercount = 5;

        if(groupClientService.checkGroupName(groupname)){
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(5);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for(int i = 1; i <= membercount; i++){
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + i);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        groupClientService.createGroup(groupClientDto);
        LOG.info("Group 1 created");


        GroupClientDto secondGroupClientDto = new GroupClientDto();
        secondGroupClientDto.setName(groupname);
        secondGroupClientDto.setRerolls(3);

        List<GroupMemberClientDto> secondGroupMemberClientDtos = new ArrayList<>();
        for(int i = 1; i <= membercount + 5; i++){
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("secondTestGroupCreationM" + i);
            secondGroupMemberClientDtos.add(member);
        }
        secondGroupClientDto.setMembers(secondGroupMemberClientDtos);

        groupClientService.createGroup(groupClientDto);
        LOG.info("Group 2 created");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testGroupnameNotFoundException(){
        String groupname = "unittestgroupcrea123";

        groupClientService.findGroupByName(groupname);
    }

    @Test(expected = GroupNameValidationException.class)
    public void testGroupnameValidation(){
        String groupname = "";
        int membercount = 5;

        if(groupClientService.checkGroupName(groupname)){
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(5);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for(int i = 1; i <= membercount; i++){
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + i);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        groupClientService.createGroup(groupClientDto);
        LOG.info("Group 1 created");
    }

    @Test(expected = NotEnoughMembersException.class)
    public void testOneOrLessMembersException(){
        String groupname = "unittestgroupcrea";
        int membercount = 1;

        if(groupClientService.checkGroupName(groupname)){
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(5);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for(int i = 1; i <= membercount; i++){
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + i);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        groupClientService.createGroup(groupClientDto);
        LOG.info("Group 1 created");
    }
}
