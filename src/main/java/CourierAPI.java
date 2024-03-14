import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierAPI {
    public String create_endpoint = "/api/v1/courier";
    public String login_endpoint =  "/api/v1/courier/login";
    public String delete_endpoint = "/api/v1/courier/";


    public Response createNewCourierAPI(CreateCourier createCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(createCourier)
                .when()
                .post(create_endpoint);
    }

    public Response loginCourierAPI(LoginCourier loginCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(login_endpoint);
    }

    public int getCourierId(LoginCourier loginCourier) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(loginCourier)
                .when()
                .post(login_endpoint)
                .then().extract().path("id");
    }

    public Response deleteCourier(int id) {
        return  given().delete(delete_endpoint  + id);
    }

}