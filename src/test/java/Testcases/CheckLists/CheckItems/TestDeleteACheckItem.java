package Testcases.CheckLists.CheckItems;

import Testcases.Cards.TestCreateACard;
import Testcases.CheckLists.CheckLists.TestCreateACheckList;
import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;

public class TestDeleteACheckItem extends BaseSetup {

    @Test(priority = 0)
    public void testDeleteACheckItem() {

        given().spec(request())
                .pathParams("cardId", TestCreateACard.cardId)
                .pathParams("checkListId", TestCreateACheckList.checkListId)
                .pathParams("checkItemId",TestCreateACheckItem.checkItemId)
                .when()
                .delete("cards/{cardId}/checklist/{checkListId}/checkItem/{checkItemId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete A CheckItem Successfully");
        }
    }

}
