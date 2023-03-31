package Testcases.CheckLists.CheckLists;

import PojoData.Cards.CardPojo;
import PojoData.CheckLists.CheckListPojo;
import Testcases.ListColumns.TestGetListColumn;
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

public class CreateACheckList extends BaseSetup {

    public static String checkListId;

    @DataProvider(name = "getCheckListData")
    public Object[][] provideAChecklist() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] CheckListData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "CheckList", 1, 1);
        return CheckListData;
    }

    @Test(priority = 0, dataProvider = "getCheckListData")
    public void testCreateACheckList(final Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParams("name",data.get("name"))
                .pathParams("cardId", "64268c4b7f8478072fbd2205")
                .when()
                .pathParam("listColumnId", TestGetListColumn.listColumnId)
                .post("checklists?idCard={cardId}&key={key}&token={token}&name={name}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CheckListPojo CheckListPojo = gson.fromJson(response, CheckListPojo.class);
        Assert.assertEquals(CheckListPojo.getName(), data.get("name"));

        checkListId = CheckListPojo.getId();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create A CheckList Successfully");
        }
    }

    @Test(priority = 1, dataProvider = "getCheckListData")
    public void testGetACheckList(Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParam("checkListId", checkListId)
                .when()
                .get("checklists/{checkListId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        Gson gson = new Gson();
        CardPojo cardPojo = gson.fromJson(response, CardPojo.class);

        Assert.assertEquals(cardPojo.getName(), data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get A Card in Boards Successfully");
        }
    }



}
