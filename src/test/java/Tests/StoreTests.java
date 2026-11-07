package Tests;

import Endpoints.StoreEndpoint;
import Payloads.Stores;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.response.Response;
import org.testng.Assert;
public class StoreTests {
        Faker faker;
        Stores orderPayload;
        int orderId;

        @BeforeClass
        public void setUpData() {
            faker = new Faker();
            orderPayload = new Stores();

            orderId = faker.number().numberBetween(1, 100000);
            orderPayload.setId(orderId);
            orderPayload.setPetId(faker.number().numberBetween(1, 1000));
            orderPayload.setQuantity(faker.number().numberBetween(1, 5));
            orderPayload.setShipDate("2026-11-10T10:00:00.000Z");
            orderPayload.setStatus("placed");
            orderPayload.setComplete(true);
        }

        @Test(priority = 1)
        public void testPostOrder() {
            Response response = StoreEndpoint.createorder(orderPayload);
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 2, dependsOnMethods = "testPostOrder")
        public void testGetOrderById() {
            Response response = StoreEndpoint.getorder(orderId);
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

        @Test(priority = 3, dependsOnMethods = "testGetOrderById")
        public void testDeleteOrderById() {
            Response response = StoreEndpoint.deleteOrder(orderId);
            response.then().log().all();
            Assert.assertEquals(response.getStatusCode(), 200);
        }

}
