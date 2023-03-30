package Testcases.Attachments;

import Testcases.Cards.TestCreateACard;
import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

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
