package Testcases.CheckLists.CheckLists;

import PojoData.CheckLists.CheckListPojo;
import Testcases.Cards.TestCreateACard;
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

public class TestCreateACheckList extends BaseSetup {

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
                .pathParams("cardId", TestCreateACard.cardId)
                .when()
                .post("checklists?idCard={cardId}&key={key}&token={token}&name={name}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CheckListPojo checkListPojo = gson.fromJson(response, CheckListPojo.class);
        Assert.assertEquals(checkListPojo.getName(), data.get("name"));

        checkListId = checkListPojo.getId();

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
        CheckListPojo checkListPojo = gson.fromJson(response, CheckListPojo.class);

        Assert.assertEquals(checkListPojo.getName(), data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get A CheckList in Boards Successfully");
        }
    }



}
