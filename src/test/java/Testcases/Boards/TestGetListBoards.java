package Testcases.Boards;

import PojoData.Boards.BoardsPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;


public class TestGetListBoards extends BaseSetup {

    public static String boardId;

    @Test
    public void testGetListBoards() {
        final String response = given().spec(request())
                .when()
                .get("/members/me/boards?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();
        Gson gson = new Gson();
        BoardsPojo[] getBoardsPojo = gson.fromJson(response, BoardsPojo[].class);

        boardId = getBoardsPojo[0].getId();
        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Get List Boards Successfully");
        }
    }
}

