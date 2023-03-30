package Testcases.Attachments;

import PojoData.Attachments.AttachmentsPojo;
import Testcases.Cards.TestCreateACard;
import com.google.gson.Gson;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import org.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.Hashtable;

import static helpers.Helpers.getCurrentDir;
import static io.restassured.RestAssured.given;

public class TestGetAttachments extends BaseSetup {



    @Test(priority = 0)
    public void testGetListsAttachments() {
        String response = given().spec(request())
                .pathParam("cardId", TestCreateACard.cardId)
                .when()
                .get("/cards/{cardId}/attachments?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Get List Attachment Successfully");
        }
    }

}
