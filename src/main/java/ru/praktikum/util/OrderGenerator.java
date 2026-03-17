package ru.praktikum.util;

import ru.praktikum.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class OrderGenerator {

    public static Order getOrder(List<String> color) {
        String unique = UUID.randomUUID().toString().substring(0, 8);

        return new Order(
                "TestName_" + unique,
                "TestSurname_" + unique,
                "Test address " + unique,
                4,
                "+79999999999",
                3,
                LocalDate.now().plusDays(1).toString(),
                "Test comment " + unique,
                color
        );
    }
}