package de.mschoeffel.secretsanta.functional;

import de.mschoeffel.secretsanta.SecretSantaApplication;
import de.mschoeffel.secretsanta.error.AlreadyPartnerAcceptedException;
import de.mschoeffel.secretsanta.error.NoMoreRerollsException;
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

import javax.transaction.Transactional;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SecretSantaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TestFunctional {

    static final Logger LOG = LoggerFactory.getLogger(TestFunctional.class);

    @Autowired
    GroupClientService groupClientService;

    @Autowired
    GroupMemberClientService groupMemberClientService;

    /**
     * Test to check rerolls are subtracted.
     */
    @Test
    @Transactional
    public void testMaxRerolls(){
        int membercount = 3;
        String groupname = "unittestgroupfunc";
        int numberRerolls = 10;

        if (groupClientService.checkGroupName(groupname)) {
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        LOG.info("Data creation");

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(numberRerolls);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for (int j = 1; j <= membercount; j++) {
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + j);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        LOG.info("Data created");

        LOG.info("Check init setting...");
        DrawRequestClientDto user = new DrawRequestClientDto();
        user.setName(result.getMembers().get(0).getName());
        user.setKey(result.getMembers().get(0).getKey());
        user.setGroupname(result.getName());
        GroupMemberClientDto userInit = groupMemberClientService.getMember(user);
        Assert.assertEquals(numberRerolls + 1, (int) userInit.getRerolls());
        LOG.info("Init setting checked");

        LOG.info("Start drawing...");
        for(int i = 0; i <= numberRerolls; i++) {
            result = groupClientService.clearAllPartner(result.getName());
            GroupMemberClientDto userResult = groupMemberClientService.drawPartner(user);
            Assert.assertEquals(numberRerolls - i, (int) userResult.getRerolls());
        }
        LOG.info("Finished drawing");

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    /**
     * Test exception with no more rerolls.
     */
    @Test(expected = NoMoreRerollsException.class)
    @Transactional
    public void testNoMoreRerollsException(){
        int membercount = 3;
        String groupname = "unittestgroupfunc";
        int numberRerolls = 10;

        if (groupClientService.checkGroupName(groupname)) {
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        LOG.info("Data creation");

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(numberRerolls);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for (int j = 1; j <= membercount; j++) {
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + j);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        LOG.info("Data created");

        LOG.info("Check init setting...");
        DrawRequestClientDto user = new DrawRequestClientDto();
        user.setName(result.getMembers().get(0).getName());
        user.setKey(result.getMembers().get(0).getKey());
        user.setGroupname(result.getName());
        GroupMemberClientDto userInit = groupMemberClientService.getMember(user);
        Assert.assertEquals(numberRerolls + 1, (int) userInit.getRerolls());
        LOG.info("Init setting checked");

        LOG.info("Start drawing...");
        for(int i = 0; i <= numberRerolls; i++) {
            result = groupClientService.clearAllPartner(result.getName());
            GroupMemberClientDto userResult = groupMemberClientService.drawPartner(user);
            Assert.assertEquals(numberRerolls - i, (int) userResult.getRerolls());
        }
        LOG.info("Finished drawing");

        LOG.info("Checking to draw with no more rerolls...");
        groupMemberClientService.drawPartner(user);

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    /**
     * Test accepting partner
     */
    @Test()
    @Transactional
    public void testAcceptingPartner(){
        int membercount = 3;
        String groupname = "unittestgroupfunc";
        int numberRerolls = 10;

        if (groupClientService.checkGroupName(groupname)) {
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        LOG.info("Data creation");

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(numberRerolls);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for (int j = 1; j <= membercount; j++) {
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + j);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        LOG.info("Data created");

        LOG.info("Starting drawing...");
        DrawRequestClientDto user = new DrawRequestClientDto();
        user.setName(result.getMembers().get(0).getName());
        user.setKey(result.getMembers().get(0).getKey());
        user.setGroupname(result.getName());
        GroupMemberClientDto userInit = groupMemberClientService.drawPartner(user);
        GroupMemberClientDto userAccepted = groupMemberClientService.acceptPartner(user);
        GroupMemberClientDto userResult = groupMemberClientService.getMember(user);

        Assert.assertEquals(true, userAccepted.getDrawAccepted());
        Assert.assertEquals(true, userResult.getDrawAccepted());
        Assert.assertEquals(userInit.getPartner().getName(), userAccepted.getPartner().getName());
        Assert.assertEquals(userAccepted.getPartner().getName(), userResult.getPartner().getName());
        LOG.info("Finished drawing");

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    /**
     * Test exception when drawing again with accepted partner
     */
    @Test(expected = AlreadyPartnerAcceptedException.class)
    @Transactional
    public void testDrawWithAcceptedPartnerException(){
        int membercount = 3;
        String groupname = "unittestgroupfunc";
        int numberRerolls = 10;

        if (groupClientService.checkGroupName(groupname)) {
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        LOG.info("Data creation");

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(numberRerolls);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for (int j = 1; j <= membercount; j++) {
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + j);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        LOG.info("Data created");

        LOG.info("Starting drawing...");
        DrawRequestClientDto user = new DrawRequestClientDto();
        user.setName(result.getMembers().get(0).getName());
        user.setKey(result.getMembers().get(0).getKey());
        user.setGroupname(result.getName());
        GroupMemberClientDto userInit = groupMemberClientService.drawPartner(user);
        GroupMemberClientDto userAccepted = groupMemberClientService.acceptPartner(user);
        GroupMemberClientDto userResult = groupMemberClientService.getMember(user);

        Assert.assertEquals(true, userAccepted.getDrawAccepted());
        Assert.assertEquals(true, userResult.getDrawAccepted());
        Assert.assertEquals(userInit.getPartner().getName(), userAccepted.getPartner().getName());
        Assert.assertEquals(userAccepted.getPartner().getName(), userResult.getPartner().getName());
        LOG.info("Finished drawing");

        LOG.info("Checking to draw again with accepted partner...");
        groupMemberClientService.drawPartner(user);

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    /**
     * Test removing all partner from a group
     */
    @Test
    @Transactional
    public void testRemovingAllPartner(){
        int[] membercountArr = {2, 3, 5, 10, 50};
        String groupname = "unittestgroupfunc";

        for(int i = 1; i <= membercountArr.length; i++) {
            LOG.info("Run: " + i);

            int membercount = membercountArr[i-1];

            if (groupClientService.checkGroupName(groupname)) {
                LOG.info(groupname + " already existed trying to delete...");
                groupClientService.deleteGroup(groupname);
                LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
            }

            LOG.info("Data creation");

            GroupClientDto groupClientDto = new GroupClientDto();
            groupClientDto.setName(groupname);
            groupClientDto.setRerolls(500);

            List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
            for (int j = 1; j <= membercount; j++) {
                GroupMemberClientDto member = new GroupMemberClientDto();
                member.setName("testGroupCreationM" + j);
                groupMemberClientDtos.add(member);
            }
            groupClientDto.setMembers(groupMemberClientDtos);

            GroupClientDto result = groupClientService.createGroup(groupClientDto);

            LOG.info("Data created");

            LOG.info("Start drawing...");
            for (GroupMemberClientDto member : result.getMembers()) {
                DrawRequestClientDto drawRequestClientDto = new DrawRequestClientDto();
                drawRequestClientDto.setGroupname(result.getName());
                drawRequestClientDto.setName(member.getName());
                drawRequestClientDto.setKey(member.getKey());
                groupMemberClientService.drawPartner(drawRequestClientDto);
            }
            LOG.info("Finished drawing");

            LOG.info("Start clearing and checking partner...");
            GroupClientDto groupResult = groupClientService.clearAllPartner(result.getName());
            for(GroupMemberClientDto member : groupResult.getMembers()){
                Assert.assertNull(member.getPartner());
            }
            LOG.info("Finished clearing and checking partner...");

            LOG.info("Group deletion");
            groupClientService.deleteGroup(result.getName());

            boolean groupStillExists = groupClientService.checkGroupName(result.getName());
            Assert.assertFalse(groupStillExists);
            LOG.info("Group deletion finished");
        }
    }

    /**
     * Test to check that the last one to draw isn't forced to draw himself.
     */
    @Test
    @Transactional
    public void testLastIsntForcedToPickHimself(){
        int membercount = 3;
        String groupname = "unittestgroupfunc";

        if (groupClientService.checkGroupName(groupname)) {
            LOG.info(groupname + " already existed trying to delete...");
            groupClientService.deleteGroup(groupname);
            LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
        }

        LOG.info("Data creation");

        GroupClientDto groupClientDto = new GroupClientDto();
        groupClientDto.setName(groupname);
        groupClientDto.setRerolls(3000);

        List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
        for (int j = 1; j <= membercount; j++) {
            GroupMemberClientDto member = new GroupMemberClientDto();
            member.setName("testGroupCreationM" + j);
            groupMemberClientDtos.add(member);
        }
        groupClientDto.setMembers(groupMemberClientDtos);

        GroupClientDto result = groupClientService.createGroup(groupClientDto);

        LOG.info("Data created");

        LOG.info("Start drawing...");
        DrawRequestClientDto user1 = new DrawRequestClientDto();
        user1.setName(result.getMembers().get(0).getName());
        user1.setKey(result.getMembers().get(0).getKey());
        user1.setGroupname(result.getName());
        DrawRequestClientDto user2 = new DrawRequestClientDto();
        user2.setName(result.getMembers().get(1).getName());
        user2.setKey(result.getMembers().get(1).getKey());
        user2.setGroupname(result.getName());
        DrawRequestClientDto user3 = new DrawRequestClientDto();
        user3.setName(result.getMembers().get(2).getName());
        user3.setKey(result.getMembers().get(2).getKey());
        user3.setGroupname(result.getName());
        for(int i = 0; i < result.getRerolls()+1; i++){
            result = groupClientService.clearAllPartner(result.getName());
            GroupMemberClientDto partner1 = groupMemberClientService.drawPartner(user1).getPartner();
            GroupMemberClientDto partner2 = groupMemberClientService.drawPartner(user2).getPartner();
            Assert.assertTrue(partner1.getName().equals(user3.getName()) || partner2.getName().equals(user3.getName()));
        }
        LOG.info("Finished drawing");

        LOG.info("Group deletion");
        groupClientService.deleteGroup(result.getName());

        boolean groupStillExists = groupClientService.checkGroupName(result.getName());
        Assert.assertFalse(groupStillExists);
        LOG.info("Group deletion finished");
    }

    /**
     * Test to check that no one is drawing himself
     */
    @Test
    @Transactional
    public void testNoOneCanPickHimself2(){
        int[] membercountArr = {2, 3, 5, 10, 50};
        String groupname = "unittestgroupfunc";

        for(int i = 1; i <= membercountArr.length; i++) {
            LOG.info("Run: " + i);

            int membercount = membercountArr[i-1];

            if (groupClientService.checkGroupName(groupname)) {
                LOG.info(groupname + " already existed trying to delete...");
                groupClientService.deleteGroup(groupname);
                LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
            }

            LOG.info("Data creation");

            GroupClientDto groupClientDto = new GroupClientDto();
            groupClientDto.setName(groupname);
            groupClientDto.setRerolls(500);

            List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
            for (int j = 1; j <= membercount; j++) {
                GroupMemberClientDto member = new GroupMemberClientDto();
                member.setName("testGroupCreationM" + j);
                groupMemberClientDtos.add(member);
            }
            groupClientDto.setMembers(groupMemberClientDtos);

            GroupClientDto result = groupClientService.createGroup(groupClientDto);

            LOG.info("Data created");

            LOG.info("Start drawing...");
            for (GroupMemberClientDto member : result.getMembers()) {
                DrawRequestClientDto drawRequestClientDto = new DrawRequestClientDto();
                drawRequestClientDto.setGroupname(result.getName());
                drawRequestClientDto.setName(member.getName());
                drawRequestClientDto.setKey(member.getKey());
                for (int j = 0; j < member.getRerolls(); j++) {
                    result = groupClientService.clearAllPartner(result.getName());
                    GroupMemberClientDto resultMember = groupMemberClientService.drawPartner(drawRequestClientDto);
                    Assert.assertNotEquals(resultMember.getName(), resultMember.getPartner().getName());
                }
            }
            LOG.info("Finished drawing");

            LOG.info("Group deletion");
            groupClientService.deleteGroup(result.getName());

            boolean groupStillExists = groupClientService.checkGroupName(result.getName());
            Assert.assertFalse(groupStillExists);
            LOG.info("Group deletion finished");
        }
    }

    /**
     * Test no one gets drawn multiple times
     */
    @Test
    @Transactional
    public void testNoOneGetsDrawnMultipleTimes(){
        int[] memberArr = {2, 3, 5, 10, 50};
        String groupname = "unittestgroupfunc";

        for(int i = 1; i <= memberArr.length; i++) {
            LOG.info("Run: " + i);
            int membercount = memberArr[i-1];

            if (groupClientService.checkGroupName(groupname)) {
                LOG.info(groupname + " already existed trying to delete...");
                groupClientService.deleteGroup(groupname);
                LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
            }

            LOG.info("Data creation");

            GroupClientDto groupClientDto = new GroupClientDto();
            groupClientDto.setName(groupname);
            groupClientDto.setRerolls(3000);

            List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
            for (int j = 1; j <= membercount; j++) {
                GroupMemberClientDto member = new GroupMemberClientDto();
                member.setName("testMember" + j);
                groupMemberClientDtos.add(member);
            }
            groupClientDto.setMembers(groupMemberClientDtos);

            GroupClientDto result = groupClientService.createGroup(groupClientDto);

            LOG.info("Data created");

            LOG.info("Start drawing...");
            for (int j = 0; j < result.getRerolls() + 1; j++) {
                result = groupClientService.clearAllPartner(result.getName());
                Map<String, Integer> nameOccur = new HashMap<>();
                for (GroupMemberClientDto memberResult : result.getMembers()) {
                    DrawRequestClientDto user = new DrawRequestClientDto();
                    user.setName(memberResult.getName());
                    user.setKey(memberResult.getKey());
                    user.setGroupname(result.getName());
                    String name = groupMemberClientService.drawPartner(user).getPartner().getName();
                    if (nameOccur.containsKey(name)) {
                        nameOccur.put(name, nameOccur.get(name) + 1);
                    } else {
                        nameOccur.put(name, 1);
                    }
                }
                boolean onlySingleEntries = true;
                for (Integer v : nameOccur.values()) {
                    if (v != 1) {
                        onlySingleEntries = false;
                        break;
                    }
                }
                Assert.assertTrue(onlySingleEntries);
            }
            LOG.info("Finished drawing");

            LOG.info("Group deletion");
            groupClientService.deleteGroup(result.getName());

            boolean groupStillExists = groupClientService.checkGroupName(result.getName());
            Assert.assertFalse(groupStillExists);
            LOG.info("Group deletion finished");
        }
    }

    /**
     * Test everybody gets drawn once
     */
    @Test
    @Transactional
    public void testEverybodyGetsDrawnOnce(){
        int[] memberArr = {2, 3, 5, 10, 50};
        String groupname = "unittestgroupfunc";

        for(int i = 1; i <= memberArr.length; i++) {
            LOG.info("Run: " + i);

            int membercount = memberArr[i-1];
            if (groupClientService.checkGroupName(groupname)) {
                LOG.info(groupname + " already existed trying to delete...");
                groupClientService.deleteGroup(groupname);
                LOG.info("Deletion: " + (groupClientService.checkGroupName(groupname) ? "failed" : "successful"));
            }

            LOG.info("Data creation");

            GroupClientDto groupClientDto = new GroupClientDto();
            groupClientDto.setName(groupname);
            groupClientDto.setRerolls(3000);

            List<GroupMemberClientDto> groupMemberClientDtos = new ArrayList<>();
            for (int j = 1; j <= membercount; j++) {
                GroupMemberClientDto member = new GroupMemberClientDto();
                member.setName("testMember" + j);
                groupMemberClientDtos.add(member);
            }
            groupClientDto.setMembers(groupMemberClientDtos);

            GroupClientDto result = groupClientService.createGroup(groupClientDto);

            LOG.info("Data created");

            LOG.info("Start drawing...");
            for (int j = 0; j < result.getRerolls() + 1; j++) {
                result = groupClientService.clearAllPartner(result.getName());

                Set<String> drawnPartner = new HashSet<>();
                for (GroupMemberClientDto member : result.getMembers()) {
                    DrawRequestClientDto user = new DrawRequestClientDto();
                    user.setName(member.getName());
                    user.setKey(member.getKey());
                    user.setGroupname(result.getName());
                    drawnPartner.add(groupMemberClientService.drawPartner(user).getPartner().getName());
                }
                for (GroupMemberClientDto member : result.getMembers()) {
                    Assert.assertTrue(drawnPartner.contains(member.getName()));
                }
            }
            LOG.info("Finished drawing");

            LOG.info("Group deletion");
            groupClientService.deleteGroup(result.getName());

            boolean groupStillExists = groupClientService.checkGroupName(result.getName());
            Assert.assertFalse(groupStillExists);
            LOG.info("Group deletion finished");
        }
    }
}
