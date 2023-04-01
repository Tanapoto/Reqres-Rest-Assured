package Testcases.CheckLists.CheckLists;

import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;

public class TestDeleteACheckList extends BaseSetup {

    @Test(priority = 0)
    public void testDeleteACheckList() {

        given().spec(request())
                .pathParams("checkListId", TestCreateACheckList.checkListId)
                .when()
                .delete("checklists/{checkListId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete A CheckList Successfully");
        }
    }


}
