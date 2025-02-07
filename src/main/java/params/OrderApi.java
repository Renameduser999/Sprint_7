package params;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderApi {
    private static final String hand_order = "/api/v1/orders";

    public static Response makeOrder(MakeOrder order) {
        return given()
                .body(order)
                .post(hand_order);

    }

    public static Response listOrders() {

        return given()
                .get(hand_order);

    }
}
