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

public class DeleteACheckItem extends BaseSetup {

    @Test(priority = 0)
    public void testCreateACheckList() {

        given().spec(request())
                .pathParams("cardId","cardId")
                .pathParams("checkListId","checkListId")
                .pathParams("checkItemId","checkItemId")
                .when()
                .delete("cards/{{cardId}}/checklist/{{checkListId}}/checkItem/{{checkItemId}}?key={{key}}&token={{token}}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete A CheckItem Successfully");
        }
    }

}
