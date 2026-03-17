package ru.praktikum.client;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import ru.praktikum.model.Order;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class OrderClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";
    private static final String ORDERS_PATH = "/api/v1/orders";

    public OrderClient() {
        RestAssured.baseURI = BASE_URL;
    }

    @Step("Создать заказ")
    public Response createOrder(Order order) {
        return given()
                .contentType(JSON)
                .body(order)
                .post(ORDERS_PATH);
    }

    @Step("Получить список заказов")
    public Response getOrdersList() {
        return given()
                .get(ORDERS_PATH);
    }
}