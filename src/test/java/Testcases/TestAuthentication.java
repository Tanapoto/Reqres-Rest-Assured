package Testcases;

import PojoData.AuthenticationPojo;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAuthentication extends BaseSetup {

    @DataProvider (name = "getAuthenticationDataSuccessfully")
    public Object[][] providePostDataS () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] postData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Register", 1, 1);
        return postData;
    }

    @DataProvider (name = "getAuthenticationDataFailure")
    public Object[][] providePostDataF () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] postData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Register", 2, 2);
        return postData;
    }

    @Test(dataProvider = "getAuthenticationDataSuccessfully")
    public static void testRegisterSuccessfully(final Hashtable<String, String> data) {
        final AuthenticationPojo requestBody = new AuthenticationPojo(data.get("email"), data.get("password"));
        final String response = given().given().spec(BaseSetup.request())
                .body(requestBody)
                .when().post("api/register")
                .then()
                .spec(BaseSetup.response200())
                .extract().response().asString();
    }

    @Test(dataProvider = "getAuthenticationDataSuccessfully")
    public static Map<String, Object> getToken(final Hashtable<String, String> data) {
        final AuthenticationPojo requestBody = new AuthenticationPojo(data.get("email"), data.get("password"));
        final JsonPath jsonPath = given().given().spec(BaseSetup.request())
                .body(requestBody)
                .when().post("api/register")
                .then()
                .spec(BaseSetup.response200())
                .extract().jsonPath();

        final Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("id", jsonPath.getInt("id"));
        responseMap.put("token", jsonPath.getString("token"));
        return responseMap;
    }

    @Test(dataProvider = "getAuthenticationDataFailure")
    public static void testRegisterFailure(final Hashtable<String, String> data) {
        final AuthenticationPojo requestBody = new AuthenticationPojo(data.get("email"), data.get("password"));
        given().given().spec(BaseSetup.request())
                .body(requestBody)
                .when().post("api/register")
                .then()
                .spec(BaseSetup.response400())
                .assertThat().body("error",equalTo(data.get("STATUS_MESSAGE")));
    }

}











































