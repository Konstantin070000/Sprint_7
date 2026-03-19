package ru.praktikum;

import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.praktikum.client.CourierClient;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;
import ru.praktikum.util.CourierGenerator;

import static org.hamcrest.CoreMatchers.equalTo;

public class CourierCreateTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanBeCreated() {
        Response createResponse = courierClient.createCourier(courier);

        createResponse.then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = courierClient.getCourierId(CourierCredentials.from(courier));
    }

    @Test
    public void cannotCreateDuplicateCourier() {
        courierClient.createCourier(courier);

        Response secondCreateResponse = courierClient.createCourier(courier);

        secondCreateResponse.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));

        courierId = courierClient.getCourierId(CourierCredentials.from(courier));
    }

    @Test
    public void cannotCreateCourierWithoutLogin() {
        courier.setLogin(null);

        Response response = courierClient.createCourier(courier);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void cannotCreateCourierWithoutPassword() {
        courier.setPassword(null);

        Response response = courierClient.createCourier(courier);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}