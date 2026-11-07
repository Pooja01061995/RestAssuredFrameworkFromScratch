package Endpoints;
import Payloads.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.RestAssured.given;
public class UserEndpoint {
    public static Response createUser(User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.postuser_url);

        return response;
    }

    public static Response getUser(String username) {
        Response response = given()
                .pathParam("user_name", username)
                .when()
                .get(Routes.getuser_url);

        return response;
    }
    public static Response updateUser(String username, User payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .pathParam("user_name", username)
                .body(payload)
                .when()
                .put(Routes.updateuser_url);

        return response;
    }
    public static Response deleteUser(String username) {
        Response response = given()
                .pathParam("user_name", username)
                .when()
                .delete(Routes.deleteuser_url);

        return response;
    }

}