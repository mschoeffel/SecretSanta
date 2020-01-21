package de.mschoeffel.secretsanta.groupmember;

import de.mschoeffel.secretsanta.SecretSantaApplication;
import de.mschoeffel.secretsanta.group.TestGroup;
import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import de.mschoeffel.secretsanta.service.v1.GroupMemberClientService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SecretSantaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TestGroupMember {

    static final Logger LOG = LoggerFactory.getLogger(TestGroupMember.class);

    @Autowired
    GroupClientService groupClientService;

    @Autowired
    GroupMemberClientService groupMemberClientService;

    @Test
    public void testGroupMemberCreation(){
        int membercount = 5;
        String groupname = "testGroupCreation";
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
        Set<String> names = new HashSet<>();
        for(GroupMemberClientDto memberClientDto : result.getMembers()){
            names.add(memberClientDto.getName());
            Assert.assertNotNull(memberClientDto.getKey());
            Assert.assertEquals(groupClientDto.getRerolls() + 1, (int) memberClientDto.getRerolls());
        }

        for(int i = 1; i <= membercount; i++){
            Assert.assertTrue(names.contains("testGroupCreationM" + i));
        }
        LOG.info("Group checks finished");

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    @Test(expected = EntityNotFoundException.class)
    public void testWrongCredentialsException(){
        String groupname = "asjdfla";
        String name = "asdjflajsldf";
        String key = "jsojfwenm";

        DrawRequestClientDto draw = new DrawRequestClientDto();
        draw.setGroupname(groupname);
        draw.setName(name);
        draw.setKey(key);
        groupMemberClientService.getMember(draw);
    }

    @Test
    public void testCredentials(){
        String groupname = "testGroup";
        String nameone = "testNameOne";
        String nametwo = "testNameTwo";
        String namethree = "testNameThree";

        if(groupClientService.checkGroupName(groupname)){
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(5);

        GroupMemberClientDto memberOne = new GroupMemberClientDto();
        memberOne.setName(nameone);
        GroupMemberClientDto memberTwo = new GroupMemberClientDto();
        memberTwo.setName(nametwo);
        GroupMemberClientDto memberThree = new GroupMemberClientDto();
        memberThree.setName(namethree);

        groupClientDto.setMembers(Arrays.asList(memberOne, memberTwo, memberThree));

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        for(GroupMemberClientDto member : result.getMembers()){
            DrawRequestClientDto draw = new DrawRequestClientDto();
            draw.setName(member.getName());
            draw.setGroupname(result.getName());
            draw.setKey(member.getKey());
            GroupMemberClientDto memberResult = groupMemberClientService.getMember(draw);
            Assert.assertNotNull(memberResult);
            Assert.assertEquals(draw.getKey(), memberResult.getKey());
            Assert.assertEquals(draw.getName(), memberResult.getName());
        }

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }
}
