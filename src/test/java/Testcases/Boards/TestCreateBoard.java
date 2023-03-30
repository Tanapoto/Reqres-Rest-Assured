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

public class TestCreateBoard extends BaseSetup {

    public static String boardId;

    @DataProvider(name ="createBoardData")
    public Object[][] provideABoard () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] boardData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Board", 1, 1);
        return boardData;
    }


    @Test(priority = 0, dataProvider = "createBoardData")
    public void testCreateABoard(Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParam("name", data.get("name"))
                .pathParam("desc", data.get("desc"))
                .when()
                .post("/boards?name={name}&desc={desc}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        Gson gson = new Gson();
        BoardsPojo createBoardsPojo = gson.fromJson(response, BoardsPojo.class);
        boardId =  createBoardsPojo.getId();


        Assert.assertEquals(createBoardsPojo.getName(),data.get("name"));
        Assert.assertEquals(createBoardsPojo.getDesc(),data.get("desc"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Created a Board Successfully");
        }

    }


    @Test(priority = 1, dataProvider = "createBoardData")
    public void testBoardDetail(Hashtable<String, String> data) {


        String response = given().spec(request())
                .pathParam("boardId", boardId)
                .when()
                .get("/boards/{boardId}?&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        Gson gson = new Gson();
        BoardsPojo createBoardsPojo = gson.fromJson(response, BoardsPojo.class);
        boardId =  createBoardsPojo.getId();

        Assert.assertEquals(createBoardsPojo.getName(),data.get("name"));
        Assert.assertEquals(createBoardsPojo.getDesc(),data.get("desc"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Get Created Boards Detail Successfully");
        }
    }





}
