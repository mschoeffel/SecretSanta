package de.mschoeffel.secretsanta.controller.v1;

import de.mschoeffel.secretsanta.SecretSantaApplication;
import de.mschoeffel.secretsanta.model.v1.DrawRequestClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupClientDto;
import de.mschoeffel.secretsanta.model.v1.GroupMemberClientDto;
import de.mschoeffel.secretsanta.results.GroupMemberResult;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SecretSantaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class TestGroupMemberController {

    static final Logger LOG = LoggerFactory.getLogger(TestGroupMemberController.class);

    @LocalServerPort
    private int port;

    @Autowired
    GroupClientService groupService;

    @Before
    public void init() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    public void testGetMember(){
        GroupClientDto data = getTestGroupData();
        String groupname = data.getName();

        if(groupService.checkGroupName(groupname)){
            groupService.deleteGroup(groupname);
        }

        Map<String, Object> random = new HashMap<>();
        random.put("groupname", groupname);
        random.put("name", "testname");
        random.put("key", "testkey");

        LOG.info("Checking member not created...");
        given().contentType(ContentType.JSON).body(random).post("/api/v1/groupmember/member").then().statusCode(HttpStatus.SC_NOT_FOUND);
        LOG.info("Done checking member not created");

        LOG.info("Creating group...");
        data = groupService.createGroup(data);
        LOG.info("Done group created");

        LOG.info("Checking member created...");
        Map<String, Object> member = getTestMemberCredentialsOfGroup(data);
        String expectedResult = "{\"name\":\"" + member.get("name") + "\",\"rerolls\":" + (data.getRerolls() + 1) + ",\"drawAccepted\":false,\"partner\":null}";
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/member").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo(expectedResult)));
        LOG.info("Done checking member created...");

    }

    @Test
    public void testGetPartner(){
        GroupClientDto data = getTestGroupData();
        String groupname = data.getName();

        if(groupService.checkGroupName(groupname)){
            groupService.deleteGroup(groupname);
        }

        Map<String, Object> random = new HashMap<>();
        random.put("groupname", groupname);
        random.put("name", "testname");
        random.put("key", "testkey");

        LOG.info("Checking member not created...");
        given().contentType(ContentType.JSON).body(random).post("/api/v1/groupmember/partner").then().statusCode(HttpStatus.SC_NOT_FOUND);
        LOG.info("Done checking member not created");

        LOG.info("Creating group...");
        data = groupService.createGroup(data);
        LOG.info("Done group created");

        LOG.info("Checking member partner...");
        Map<String, Object> member = getTestMemberCredentialsOfGroup(data);
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/partner").then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
        LOG.info("Done checking member partner...");

        LOG.info("Drawing member...");
        GroupMemberResult resultMember = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        LOG.info("Done drawing member...");

        LOG.info("Checking drawn member partner...");
        String expectedResult = "{\"name\":\"" + resultMember.getPartner() + "\",\"rerolls\":" + (resultMember.getRerolls() + 1) + ",\"drawAccepted\":false,\"partner\":null}";
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/partner").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo(expectedResult)));
        LOG.info("Done checking drawn member partner");

        LOG.info("Drawing again member...");
        resultMember = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        LOG.info("Done again drawing member...");

        LOG.info("Checking again drawn member partner...");
        expectedResult = "{\"name\":\"" + resultMember.getPartner() + "\",\"rerolls\":" + (resultMember.getRerolls() + 2) + ",\"drawAccepted\":false,\"partner\":null}";
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/partner").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo(expectedResult)));
        LOG.info("Done again checking drawn member partner");

    }

    @Test
    public void testAcceptPartner(){
        GroupClientDto data = getTestGroupData();
        String groupname = data.getName();

        if(groupService.checkGroupName(groupname)){
            groupService.deleteGroup(groupname);
        }

        Map<String, Object> random = new HashMap<>();
        random.put("groupname", groupname);
        random.put("name", "testname");
        random.put("key", "testkey");

        LOG.info("Checking member not created...");
        given().contentType(ContentType.JSON).body(random).post("/api/v1/groupmember/accept").then().statusCode(HttpStatus.SC_NOT_FOUND);
        LOG.info("Done checking member not created");

        LOG.info("Creating group...");
        data = groupService.createGroup(data);
        LOG.info("Done group created");

        LOG.info("Checking member partner...");
        Map<String, Object> member = getTestMemberCredentialsOfGroup(data);
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/accept").then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
        LOG.info("Done checking member partner...");

        LOG.info("Drawing member...");
        GroupMemberResult resultMember = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        LOG.info("Done drawing member...");

        LOG.info("Checking accepted partner...");
        String expectedResult = "{\"name\":\"" + resultMember.getName() + "\",\"rerolls\":" + resultMember.getRerolls() + ",\"drawAccepted\":true,\"partner\":\"" + resultMember.getPartner() + "\"}";
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/accept").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo(expectedResult)));

        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/member").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo(expectedResult)));
        LOG.info("Done checking accepted partner");

        LOG.info("Checking again accepting partner...");
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/accept").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo(expectedResult)));
        LOG.info("Done checking again accepting partner");

    }

    @Test
    public void testDrawPartner(){

        GroupClientDto data = getTestGroupData();
        String groupname = data.getName();

        if(groupService.checkGroupName(groupname)){
            groupService.deleteGroup(groupname);
        }

        Map<String, Object> random = new HashMap<>();
        random.put("groupname", groupname);
        random.put("name", "testname");
        random.put("key", "testkey");

        LOG.info("Checking member not created...");
        given().contentType(ContentType.JSON).body(random).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_NOT_FOUND);
        LOG.info("Done checking member not created");

        LOG.info("Creating group...");
        data = groupService.createGroup(data);
        LOG.info("Done group created");

        LOG.info("Checking draw partner...");
        Map<String, Object> member = getTestMemberCredentialsOfGroup(data);
        GroupMemberResult firstDraw = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        Assert.assertNotNull(firstDraw.getPartner());
        LOG.info("Done checking draw partner...");

        LOG.info("Check socond draw partner...");
        GroupMemberResult secondDraw = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        Assert.assertNotNull(secondDraw.getPartner());
        Assert.assertEquals(firstDraw.getRerolls() -1, (int) secondDraw.getRerolls());
        LOG.info("Done check socond draw partner...");

        LOG.info("Check third draw partner...");
        GroupMemberResult thirdDraw = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        Assert.assertNotNull(thirdDraw.getPartner());
        Assert.assertEquals(secondDraw.getRerolls() -1, (int) thirdDraw.getRerolls());
        LOG.info("Done check third draw partner...");

        LOG.info("Check no more rerolls...");
        GroupMemberResult emptyDraw = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        while(emptyDraw.getRerolls() > 0){
            emptyDraw = given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK).extract().response().as(GroupMemberResult.class);
        }
        given().contentType(ContentType.JSON).body(member).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
        LOG.info("Done check no more rerolls...");


        LOG.info("Check already accepted...");
        Map<String, Object> member2 = getTestMemberCredentialsOfGroup(data, 1);
        given().contentType(ContentType.JSON).body(member2).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_OK);
        given().contentType(ContentType.JSON).body(member2).post("/api/v1/groupmember/accept").then().statusCode(HttpStatus.SC_OK);
        given().contentType(ContentType.JSON).body(member2).post("/api/v1/groupmember/draw").then().statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY);
        LOG.info("Done check already accepted...");


    }

    private DrawRequestClientDto getTestMemberCredentials(GroupMemberClientDto member, String groupname){
        DrawRequestClientDto data = new DrawRequestClientDto();

        data.setGroupname(groupname);
        data.setName(member.getName());
        data.setKey(member.getKey());

        return data;
    }

    private Map<String, Object> getTestMemberCredentialsOfGroup(GroupClientDto group){
        return getTestMemberCredentialsOfGroup(group, 0);
    }

    private Map<String, Object> getTestMemberCredentialsOfGroup(GroupClientDto group, int index){
        Map<String, Object> data = new HashMap<>();
        GroupMemberClientDto member = group.getMembers().get(index);

        data.put("name", member.getName());
        data.put("groupname", group.getName());
        data.put("key", member.getKey());

        return data;
    }

    private GroupClientDto getTestGroupData(){
        GroupClientDto data = new GroupClientDto();
        data.setName("unittestgrouprest");
        data.setRerolls(5);

        List<GroupMemberClientDto> members = new ArrayList<>();
        GroupMemberClientDto member1 = new GroupMemberClientDto();
        member1.setName("member1");
        members.add(member1);
        GroupMemberClientDto member2 = new GroupMemberClientDto();
        member2.setName("member2");
        members.add(member2);
        GroupMemberClientDto member3 = new GroupMemberClientDto();
        member3.setName("member3");
        members.add(member3);
        GroupMemberClientDto member4 = new GroupMemberClientDto();
        member4.setName("member4");
        members.add(member4);
        GroupMemberClientDto member5 = new GroupMemberClientDto();
        member5.setName("member5");
        members.add(member5);

        data.setMembers(members);

        return data;
    }
}
