package Tests;

import Endpoints.UserEndpoint;
import Payloads.User;
import com.github.javafaker.Faker;
import io.restassured.response.Response;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.logging.log4j.LogManager;
public class UserTests {
    Faker faker;
    User userPayload;
    public Logger logger;
    @BeforeClass
    public void setUpData() {
        faker = new Faker();
        userPayload = new User();
        userPayload.setId(faker.number().hashCode());
        userPayload.setUsername(faker.name().username());
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        userPayload.setPassword(faker.internet().password(8, 16));
        userPayload.setPhone(faker.phoneNumber().cellPhone());
        logger = LogManager.getLogger(this.getClass());
    }

    @Test(priority = 1)
    public void testPostUser() {
        logger.info("**********Create User************");
        Response response = UserEndpoint.createUser(userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
        logger.info("********** User Created ************");
    }

    @Test(priority = 2)
    public void getUserByName() {
        Response response = UserEndpoint.getUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void updateUserByName() {
        userPayload.setFirstName(faker.name().firstName());
        userPayload.setLastName(faker.name().lastName());
        userPayload.setEmail(faker.internet().safeEmailAddress());
        Response response = UserEndpoint.updateUser(this.userPayload.getUsername(), userPayload);
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);

        Response responseAfterUpdate = UserEndpoint.getUser(this.userPayload.getUsername());
        Assert.assertEquals(responseAfterUpdate.getStatusCode(), 200);   // use responseAfterUpdate
    }

    @Test(priority = 4)
    public void deleteUserByName() {
        Response response = UserEndpoint.deleteUser(this.userPayload.getUsername());
        response.then().log().all();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
