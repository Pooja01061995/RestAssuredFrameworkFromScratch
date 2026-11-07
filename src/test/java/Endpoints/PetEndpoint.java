package Endpoints;

import Payloads.Pet;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PetEndpoint {
    public static Response createPet(Pet payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.postpet_url);

        return response;
    }

    public static Response getPet(int  petid) {
        Response response = given()
                .pathParam("pet_id", petid)
                .when()
                .get(Routes.getpet_url);

        return response;
    }
    public static Response updatePet(Pet payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .put(Routes.updatepet_url);

        return response;
    }

    public static Response deletePet(int petid) {
        Response response = given()
                .pathParam("pet_id", petid)
                .when()
                .delete(Routes.deletepet_url);

        return response;
    }
}
