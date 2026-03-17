package ru.praktikum.util;

import ru.praktikum.model.Courier;

import java.util.UUID;

public class CourierGenerator {

    public static Courier getRandomCourier() {
        String unique = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return new Courier(
                "login_" + unique,
                "pass_" + unique,
                "name_" + unique
        );
    }
}