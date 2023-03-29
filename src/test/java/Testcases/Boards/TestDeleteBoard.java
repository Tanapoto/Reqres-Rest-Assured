package Testcases.Boards;

import PojoData.Boards.BoardsPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.Hashtable;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestDeleteBoard extends BaseSetup {

    @Test()
    public void testDeleteBoard() {

        System.out.println("KK: " + TestCreateBoard.boardId);
        String response = given().spec(request())
                .pathParam("boardId", TestCreateBoard.boardId)
                .when()
                .delete("/boards/{boardId}?&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .body("_value",equalTo(null))
                .extract().asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get Created Boards Detail Successfully");
        }
    }





}
