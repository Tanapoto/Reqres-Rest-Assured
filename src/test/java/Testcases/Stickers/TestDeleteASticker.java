package Testcases.Stickers;

import Testcases.Cards.TestCreateACard;
import Testcases.Cards.TestUpdateACard;
import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;
import static io.restassured.RestAssured.given;

public class TestDeleteASticker extends BaseSetup {

    @Test(priority = 0)
    public void testDeleteASticker() {

       given().spec(request())
                .pathParams("cardId", TestCreateACard.cardId)
                .pathParams("stickerId", TestCreateASticker.stickerId)
                .when()
                .delete("cards/{cardId}/stickers/{stickerId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete A Sticker Successfully");
        }
    }

}
