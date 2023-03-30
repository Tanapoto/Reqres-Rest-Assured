package Testcases.ListColumns;

import PojoData.Boards.BoardsPojo;
import Testcases.Boards.TestCreateBoard;
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

public class TestCreateAListColumn extends BaseSetup {


    @DataProvider(name = "createListsInBoardData")
    public Object[][] provideABoard() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] cardData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "ListColumn", 1, 6);
        return cardData;
    }


    @Test(priority = 0, dataProvider = "createListsInBoardData")
    public void testCreateListInBoards(Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParam("boardId", TestCreateBoard.boardId)
                .pathParam("name", data.get("name"))
                .when()
                .post("/boards/{boardId}/lists?name={name}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        Gson gson = new Gson();
        BoardsPojo createBoardsPojo = gson.fromJson(response, BoardsPojo.class);

        Assert.assertEquals(createBoardsPojo.getName(), data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Create List Column in Board Successfully");
        }

    }

}
