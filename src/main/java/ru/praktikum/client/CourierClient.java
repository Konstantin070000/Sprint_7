package ru.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.praktikum.model.Courier;
import ru.praktikum.model.CourierCredentials;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class CourierClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    public CourierClient() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создать курьера")
    public Response createCourier(Courier courier) {
        return given()
                .contentType(JSON)
                .body(courier)
                .post(COURIER_PATH);
    }

    @Step("Авторизовать курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return given()
                .contentType(JSON)
                .body(credentials)
                .post(LOGIN_PATH);
    }

    @Step("Удалить курьера по id: {id}")
    public Response deleteCourier(int id) {
        return given()
                .delete(COURIER_PATH + "/" + id);
    }

    @Step("Получить id курьера")
    public int getCourierId(CourierCredentials credentials) {
        return loginCourier(credentials)
                .then()
                .extract()
                .path("id");
    }
}