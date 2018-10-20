package pay;

import org.junit.Test;
import pay.model.Transfer;

import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class IntegrationTest {

    @Test
    public void test() {

        given().when().body("{\"amount\":4, \"recipient\":2, \"sender\": 1}").post("http://localhost:8080/broker/v1/transfer").then().statusCode(200).body("success",equalTo(true));
        given().when().body("{\"amount\":4, \"recipient\":2, \"sender\": 1}").post("http://localhost:8080/broker/v1/transfer").then().statusCode(200).body("success",equalTo(true));
        given().when().body("{\"amount\":4, \"recipient\":2, \"sender\": 1}").post("http://localhost:8080/broker/v1/transfer").then().statusCode(200).body("success",equalTo(false)).body("error",equalTo("Not enough balance"));

        given().when().body("{\"amount\":4, \"recipient\":2, \"sender\": 0}").post("http://localhost:8080/broker/v1/transfer").then().statusCode(200).body("success",equalTo(false)).body("error",equalTo("Sender not found"));
        given().when().body("{\"amount\":4, \"recipient\":3, \"sender\": 1}").post("http://localhost:8080/broker/v1/transfer").then().statusCode(200).body("success",equalTo(false)).body("error",equalTo("Sender not found"));



    }
}
