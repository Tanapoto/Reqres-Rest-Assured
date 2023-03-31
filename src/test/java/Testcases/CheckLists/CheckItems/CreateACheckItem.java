package Testcases.CheckLists.CheckItems;

import PojoData.CheckLists.CheckItemPojo;
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

public class CreateACheckItem extends BaseSetup {

    public static String checkItemId;

    @DataProvider(name = "getACheckItemData")
    public Object[][] provideAChecklist() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] CheckListData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "CheckItem", 1, 1);
        return CheckListData;
    }

    @Test(priority = 0, dataProvider = "getCheckListData")
    public void testCreateACheckList(final Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParams("checkListId","")
                .pathParams("name", data.get("name"))
                .when()
                .pathParam("listColumnId", TestGetListColumn.listColumnId)
                .post("checklists/{checkListId}/checkItems?name={name}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CheckItemPojo checkListPojo = gson.fromJson(response, CheckItemPojo.class);
        Assert.assertEquals(checkListPojo.getName(), data.get("name"));

        checkItemId = checkListPojo.getId();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create A CheckItem Successfully");
        }
    }

    @Test(priority = 1)
    public void testGetACheckItem() {

      given().spec(request())
                .pathParam("checkListId", checkItemId)
                .pathParams("checkItemId",checkItemId)
                .when()
                .get("checklists/{checkListId}/checkItems/{checkItemId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get A CheckItem in Boards Successfully");
        }
    }

}
