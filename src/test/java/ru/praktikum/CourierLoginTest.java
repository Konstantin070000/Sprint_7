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
import static org.hamcrest.CoreMatchers.notNullValue;

public class CourierLoginTest {

    private CourierClient courierClient;
    private Courier courier;
    private Integer courierId;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
        courierClient.createCourier(courier);
        courierId = courierClient.getCourierId(CourierCredentials.from(courier));
    }

    @After
    public void tearDown() {
        if (courierId != null) {
            courierClient.deleteCourier(courierId);
        }
    }

    @Test
    public void courierCanLogin() {
        Response response = courierClient.loginCourier(CourierCredentials.from(courier));

        response.then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    public void cannotLoginWithWrongLogin() {
        CourierCredentials credentials = new CourierCredentials("wrong_" + courier.getLogin(), courier.getPassword());

        Response response = courierClient.loginCourier(credentials);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void cannotLoginWithWrongPassword() {
        CourierCredentials credentials = new CourierCredentials(courier.getLogin(), "wrong_password");

        Response response = courierClient.loginCourier(credentials);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    public void cannotLoginWithoutLogin() {
        CourierCredentials credentials = new CourierCredentials(null, courier.getPassword());

        Response response = courierClient.loginCourier(credentials);

        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    public void cannotLoginWithNonexistentCourier() {
        CourierCredentials credentials = new CourierCredentials("not_exist_login", "not_exist_password");

        Response response = courierClient.loginCourier(credentials);

        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}