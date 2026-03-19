package ru.praktikum;

import io.restassured.response.Response;
import org.junit.Test;
import ru.praktikum.client.OrderClient;

import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    private final OrderClient orderClient = new OrderClient();

    @Test
    public void getOrdersListReturnsOrdersArray() {
        Response response = orderClient.getOrdersList();

        response.then()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}