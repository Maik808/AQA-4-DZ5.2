package ru.netology;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.*;

public class AuthTest {
   
    @Test
    void shouldValidActiveUser() {
        open("http://localhost:9999");
        Registration getValidActiveUser = validActiveUser();
        $("[data-test-id=login] input").setValue(getValidActiveUser.getLogin());
        $("[data-test-id=password] input").setValue(getValidActiveUser.getPassword());
        $(".button").click();
        $$(".heading").find(exactText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitBlockedUser() {
        open("http://localhost:9999");
        Registration getValidBlockedUser = validBlockedUser();
        $("[data-test-id=login] input").setValue(getValidBlockedUser.getLogin());
        $("[data-test-id=password] input").setValue(getValidBlockedUser.getPassword());
        $(".button").click();
        $(withText("Пользователь заблокирован")).shouldBe(exist);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectLogin() {
        open("http://localhost:9999");
        Registration getUserWithIncorrectLogin = userWithIncorrectLogin();
        $("[data-test-id=login] input").setValue(getUserWithIncorrectLogin.getLogin());
        $("[data-test-id=password] input").setValue(getUserWithIncorrectLogin.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(exist);
    }

    @Test
    void shouldGetErrorMessageIfWeSubmitWithIncorrectPassword() {
        open("http://localhost:9999");
        Registration getUserWithIncorrectPassword = userWithIncorrectLogin();
        $("[data-test-id=login] input").setValue(getUserWithIncorrectPassword.getLogin());
        $("[data-test-id=password] input").setValue(getUserWithIncorrectPassword.getPassword());
        $(".button").click();
        $(withText("Неверно указан логин или пароль")).shouldBe(exist);
    }
}
