package Testcases.CheckLists.CheckItems;

import PojoData.CheckLists.CheckItemPojo;
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

public class UpdateACheckItem extends BaseSetup {


    @DataProvider(name = "getACheckItemData")
    public Object[][] provideAChecklist() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] CheckListData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "CheckItem", 2, 2);
        return CheckListData;
    }

    @Test(priority = 0, dataProvider = "getCheckListData")
    public void testCreateACheckList(final Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParams("cardId","cardId")
                .pathParams("checkListId","checkListId")
                .pathParams("checkItemId","checkItemId")
                .pathParams("name", data.get("name"))
                .when()
                .put("cards/{{cardId}}/checklist/{{checkListId}}/checkItem/{{checkItemId}}?key={{key}}&token={{token}}&name={name}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CheckItemPojo checkListPojo = gson.fromJson(response, CheckItemPojo.class);
        Assert.assertEquals(checkListPojo.getName(), data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Update A CheckItem Successfully");
        }
    }

}
