package Testcases.CheckLists.CheckLists;

import PojoData.CheckLists.CheckListPojo;
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

public class TestUpdateACheckList extends BaseSetup {

    @DataProvider(name = "getCheckListData")
    public Object[][] provideAChecklist() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] CheckListData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "CheckList", 2, 2);
        return CheckListData;
    }


    @Test(priority = 0, dataProvider = "getCheckListData")
    public void testUpdateACheckList(final Hashtable<String, String> data) {

        String response = given().spec(request())
                .pathParams("checkListId", TestCreateACheckList.checkListId)
                .pathParams("name",data.get("name"))
                .when()
                .put("checklists/{checkListId}?key={key}&token={token}&name={name}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CheckListPojo CheckListPojo = gson.fromJson(response, CheckListPojo.class);
        Assert.assertEquals(CheckListPojo.getName(), data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Update A CheckList Successfully");
        }
    }


}
