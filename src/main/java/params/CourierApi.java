package params;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierApi {
    private static final String hand_courier = "/api/v1/courier";
    private static final String hand_login = "/api/v1/courier/login";

    public static Response createCourier(CreateCourier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post(hand_courier);
    }

    public static Response loginCourier(LoginCourier courier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post(hand_login);
    }

    public static void createTestCourier(String login, String password, String firstName) {
        CreateCourier courier = new CreateCourier(login, password, firstName);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .post("/api/v1/courier");
    }

    public static void deleteTestCourier(String login, String password) {
        LoginCourier loginCourier = new LoginCourier(login, password);
        int id = given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .post("/api/v1/courier/login")
                .then()
                .extract()
                .body()
                .path("id");
        given()
                .delete("/api/v1/courier/{id}", id)
                .then().assertThat().statusCode(SC_OK);

    }
}

