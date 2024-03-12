import io.restassured.response.Response;
import static io.restassured.RestAssured.*;



public class OrderAPI {
public String order_endpoint = "/api/v1/orders";

public Response createOrder(OrderCreate orderCreate) {
    return given()
            .header("Content-type", "application/json")
            .and()
            .body(orderCreate)
            .when()
            .post(order_endpoint);
}
public Response getOrders() {
    return given()
            .when()
            .get(order_endpoint);
}

}
