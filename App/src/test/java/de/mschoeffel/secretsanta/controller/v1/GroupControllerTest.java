package de.mschoeffel.secretsanta.controller.v1;

import de.mschoeffel.secretsanta.SecretSantaApplication;
import de.mschoeffel.secretsanta.service.v1.GroupClientService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = SecretSantaApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class GroupControllerTest {

    static final Logger LOG = LoggerFactory.getLogger(GroupControllerTest.class);

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
    public void testGroupCreationREST() {

        Map<String, Object> data = getTestGroupData();
        String groupname = String.valueOf(data.get("name"));

        if(groupService.checkGroupName(groupname)){
            groupService.deleteGroup(groupname);
        }

        LOG.info("Checking valid group creation...");
        given().contentType(ContentType.JSON).body(data).post("/api/v1/group").then().statusCode(HttpStatus.SC_OK);
        LOG.info("Done checking valid group creation");

        LOG.info("Checking invalid group creation...");
        given().contentType(ContentType.JSON).body(data).post("/api/v1/group").then().statusCode(HttpStatus.SC_CONFLICT);
        LOG.info("Done checking valid group creation...");

    }

    @Test
    public void testGroupNameCheckREST() {

        Map<String, Object> data = getTestGroupData();
        String groupname = String.valueOf(data.get("name"));

        if(groupService.checkGroupName(groupname)){
            groupService.deleteGroup(groupname);
        }

        LOG.info("Checking group not created...");
        given().pathParam("name", groupname).get("/api/v1/group/check/{name}").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo("false")));
        LOG.info("Done checking group not created");

        LOG.info("Creating group...");
        given().contentType(ContentType.JSON).body(data).post("/api/v1/group").then().statusCode(HttpStatus.SC_OK);
        LOG.info("Done group created");

        LOG.info("Checking group created...");
        given().pathParam("name", groupname).get("/api/v1/group/check/{name}").then().statusCode(HttpStatus.SC_OK).assertThat().body(is(equalTo("true")));
        LOG.info("Done checking group created...");

    }

    private Map<String, Object> getTestGroupData(){
        Map<String, Object> data = new HashMap<>();
        String groupname = "testgrouprest";
        data.put("name", groupname);
        data.put("rerolls", 5);

        Map<String, String> member1 = new HashMap<>();
        member1.put("name", "memberOne");
        Map<String, String> member2 = new HashMap<>();
        member2.put("name", "memberTwo");
        Map<String, String> member3 = new HashMap<>();
        member3.put("name", "memberThree");
        Map<String, String> member4 = new HashMap<>();
        member4.put("name", "memberFour");
        Map<String, String> member5 = new HashMap<>();
        member5.put("name", "memberFive");

        List<Map<String, String>> members = new LinkedList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.add(member4);
        members.add(member5);

        data.put("members", members);

        return data;
    }
}
