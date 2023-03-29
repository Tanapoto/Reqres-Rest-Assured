package Testcases;

import PojoData.GetBoardsPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class TestGetListBoards extends BaseSetup{






    @DataProvider ()
    public Iterator<Object[]> getListBoards() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{"Rest-Assured API"});
        return getTestData.iterator();
    }


    @Test(dataProvider = "getListBoards")
    public void testPostRequests(String name) {
       given().spec(request())
                .when()
                .get("/members/me/boards?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .body("name[1]",equalTo(name));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get List Boards Successfully");
        }
    }

    @Test(dataProvider = "getListBoards")
    public static String getBoardId() {
        final String response = given().spec(request())
                .when()
                .get("/members/me/boards?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();
        Gson gson = new Gson();
        GetBoardsPojo[] getBoardsPojo = gson.fromJson(response,GetBoardsPojo[].class);

        return getBoardsPojo[1].getId();
    }

}
