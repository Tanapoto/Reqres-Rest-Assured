package Testcases.Cards;

import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;

public class TestDeleteACard extends BaseSetup {

    @Test
    public void testDeleteACard() {

        given().spec(request())
                .when()
                .pathParam("cardId", TestCreateACard.cardId)
                .delete("/cards/{cardId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Delete A Card Successfully");
        }
    }
}
