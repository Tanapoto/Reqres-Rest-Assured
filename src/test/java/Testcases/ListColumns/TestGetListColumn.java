package Testcases.ListColumns;

import PojoData.Boards.ColumnBoardPojo;
import Testcases.Boards.TestGetListBoards;
import com.google.gson.Gson;
import common.BaseSetup;
import org.json.JSONArray;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;

public class TestGetListColumn extends BaseSetup {

    public static String listColumnId;

    @Test()
    public void testGetListColumn() {
        final String response = given().spec(request())
                .pathParam("boardId", TestGetListBoards.boardId)
                .when()
                .get("boards/{boardId}/lists?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        JSONArray jsonArray = new JSONArray(response);
        int numberOfObjects = jsonArray.length();

        Gson gson = new Gson();
        ColumnBoardPojo[] getColumnPojo = gson.fromJson(response, ColumnBoardPojo[].class);

        for (int i = 0; i < numberOfObjects; i++) {
            if (getColumnPojo[i].getName().equals("To Do")) {
                listColumnId = getColumnPojo[i].getId();
            }
        }

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Get List Column In Boards Successfully");
        }
    }
}
