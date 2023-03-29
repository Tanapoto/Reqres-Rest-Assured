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

public class TestUpdateBoard extends BaseSetup {


    @DataProvider(name ="updateBoardData")
    public Object[][] provideABoard () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] cardData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Board", 2, 2);
        return cardData;
    }


    @Test(dataProvider = "updateBoardData")
    public void testUpdateBoard(Hashtable<String, String> data) {

        System.out.println("KK: " + TestCreateBoard.boardId);
        String response = given().spec(request())
                .pathParam("boardId", TestCreateBoard.boardId)
                .pathParam("name", data.get("name"))
                .pathParam("desc", data.get("desc"))
                .when()
                .put("/boards/{boardId}?&key={key}&token={token}&name={name}&desc={desc}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        Gson gson = new Gson();
        BoardsPojo updateBoardsPojo = gson.fromJson(response, BoardsPojo.class);

        Assert.assertEquals(updateBoardsPojo.getName(),data.get("name"));
        Assert.assertEquals(updateBoardsPojo.getDesc(),data.get("desc"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Update Boards Successfully");
        }
    }





}
