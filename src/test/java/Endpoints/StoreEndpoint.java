package Endpoints;

import Payloads.Stores;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class StoreEndpoint {
    public static Response createorder(Stores payload) {
        Response response = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.postorder_url);

        return response;
    }

    public static Response getorder(int orderid) {
        Response response = given()
                .pathParam("order_id", orderid)
                .when()
                .get(Routes.getorder_url);

        return response;
    }

    public static Response deleteOrder(int orderid) {
        Response response = given()
                .pathParam("order_id", orderid)
                .when()
                .delete(Routes.deleteorder_url);

        return response;
    }

}
