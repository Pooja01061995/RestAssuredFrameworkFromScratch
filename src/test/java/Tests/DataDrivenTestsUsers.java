package Tests;
import Endpoints.UserEndpoint;
import org.testng.Assert;
import org.testng.annotations.Test;
import Utilities.DataProviders;
import Payloads.User;
import io.restassured.response.Response;
public class DataDrivenTestsUsers {
    @Test(priority = 1, dataProvider = "userData", dataProviderClass = DataProviders.class)
    public void postUser(String UserID, String Username, String FirstName, String LastName, String Email, String Password, String Phone) {
        User userPayload = new User();
        userPayload.setId(Integer.parseInt(UserID));
        userPayload.setUsername(Username);
        userPayload.setFirstName(FirstName);
        userPayload.setLastName(LastName);
        userPayload.setEmail(Email);
        userPayload.setPassword(Password);
        userPayload.setPhone(Phone);

        Response response = UserEndpoint.createUser(userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);

    }


    @Test(priority = 4, dataProvider = "userNames", dataProviderClass = DataProviders.class)
    public void deleteUser(String Username) {
        Response response = UserEndpoint.deleteUser(Username);
        Assert.assertEquals(response.getStatusCode(), 200);
    }
    @Test(priority = 3, dataProvider = "userData", dataProviderClass = DataProviders.class)
    public void updateUser(String UserID, String Username, String FirstName, String LastName,
                           String Email, String Password, String Phone) {

        User userPayload = new User();
        userPayload.setId(Integer.parseInt(UserID));
        userPayload.setUsername(Username);
        userPayload.setFirstName(FirstName + "Updated");   // changed value, so you can see the update
        userPayload.setLastName(LastName);
        userPayload.setEmail(Email);
        userPayload.setPassword(Password);
        userPayload.setPhone(Phone);

        Response response = UserEndpoint.updateUser(Username, userPayload);
        Assert.assertEquals(response.getStatusCode(), 200);

        // optional: confirm the change was saved
        Response getResponse = UserEndpoint.getUser(Username);
        Assert.assertEquals(getResponse.jsonPath().getString("firstName"), FirstName + "Updated");
    }

        @Test(priority = 2, dataProvider = "userNames", dataProviderClass = DataProviders.class)
        public void getUser(String Username){
            Response response1 = UserEndpoint.getUser(Username);
            Assert.assertEquals(response1.getStatusCode(), 200);
        }

}