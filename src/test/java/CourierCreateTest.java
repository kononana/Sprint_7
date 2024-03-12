import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.is;

public class CourierCreateTest {
    public final String login = "kon39";
    public final String password = "12345678";
    public final String firstName = "pavel";

    CreateCourier createCourier = new CreateCourier(login, password,firstName);
    LoginCourier loginCourier = new LoginCourier(login, password);
    CourierAPI courierAPI = new CourierAPI();

    @Before
    public void setup() {
        RestAssured.baseURI =  "http://qa-scooter.praktikum-services.ru/";
    }


    @Test
    @Description("create courier without password")
    public void createCourierWithoutPassword(){
        createCourier.setPassword(null);
       Response response = courierCreate(createCourier);
       checkResponseMessageToExpected(response, 400, "Недостаточно данных для создания учетной записи");
    }
    @Test
    @Description("create courier without login")
    public void createCourierWithoutLogin(){
        createCourier.setLogin(null);
        Response response = courierCreate(createCourier);
        checkResponseMessageToExpected(response, 400, "Недостаточно данных для создания учетной записи");
    }

    @Test
    @Description("new courier successful creation. Status: 201")
    public void createNewCourierOk() {
        Response response = courierCreate(createCourier);
        checkResponseToTrue(response, 201);
    }

    @Test
    @Description("create the same courier, error: 409")
    public void createTwoSameCouriers() {
        courierCreate(createCourier);
        Response response = courierCreate(createCourier);
        checkResponseMessageToExpected(response, 409, "Этот логин уже используется. Попробуйте другой.");
    }


    @After
    public void tearDown() {
        try {
            int courierId = courierIdLogin(loginCourier);
            deleteCourier(courierId);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    // шаг "Создать курьера"
    @Step("Create courier")
    public Response courierCreate(CreateCourier createCourier){
        Response response = courierAPI.createNewCourierAPI(createCourier);
        return response;
    }

    // шаг "Получить id при авторизации курьера":
    @Step("Return courier id")
    public int courierIdLogin(LoginCourier loginCourier){
        courierAPI.loginCourierAPI(loginCourier);
        int id = courierAPI.getCourierId(loginCourier);
        return id;
    }

    // шаг "Удалить курьера":
    @Step("Delete courier by  id")
    public void deleteCourier(int courierId){
       courierAPI.deleteCourier(courierId);
    }

    // шаг Проверить сообщение в ответе
    @Step("Check if response message is equal to expected message")
    public void checkResponseMessageToExpected(Response response, int statusCode, String someText){
        response
                .then()
                .statusCode(statusCode)
                .and()
                .assertThat()
                .body("message", is(someText));
    }
    // шаг Проверить содержится ли значение в ответе
    @Step("Check if response contains: true")
    public void checkResponseToTrue(Response response, int statusCode){
        response
                .then()
                .assertThat()
                .statusCode(statusCode)
                .body("ok", is(true));
    }

}
