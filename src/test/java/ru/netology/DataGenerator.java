package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGenerator {
    DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void makeRequest(Registration registration) {
        given()
                .spec(requestSpec)
                .body(registration)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static Registration validActiveUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        Registration registration = new Registration(login, password, status);
        makeRequest(registration);
        return registration;
    }

    public static Registration validBlockedUser() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "blocked";
        Registration registration = new Registration(login, password, status);
        makeRequest(registration);
        return registration;
    }

    public static Registration userWithIncorrectPassword() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        Registration registration = new Registration(login, password, status);
        makeRequest(registration);
        return new Registration(login, "incorrect", status);
    }

    public static Registration userWithIncorrectLogin() {
        Faker faker = new Faker(new Locale("en"));
        String login = faker.name().firstName();
        String password = faker.internet().password();
        String status = "active";
        Registration registration = new Registration(login, password, status);
        makeRequest(registration);
        return new Registration("petya", password, status);
    }
}
