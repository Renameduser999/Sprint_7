package app;

import io.restassured.RestAssured;

public class BaseTest {
    public void baseTestURL() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

}

