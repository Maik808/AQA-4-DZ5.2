package ru.netology;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AuthTest {
    @Test
    void shouldValidActiveUser() {
        Registration registration = DataGenerator.getUser("active");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        Selenide.$$(".heading").find(exactText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitBlockedUser() {
        Registration registration = DataGenerator.getUser("blocked");
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(exist);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectLogin() {
        Registration registration = DataGenerator.getUser("active");
        Faker faker = new Faker();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(faker.name().firstName());
        $("[data-test-id=password] input").setValue(registration.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(exist);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectPassword() {
        Registration registration = DataGenerator.getUser("active");
        Faker faker = new Faker();
        open("http://localhost:9999");
        $("[data-test-id=login] input").setValue(registration.getLogin());
        $("[data-test-id=password] input").setValue(faker.internet().password());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(exist);
    }
}
