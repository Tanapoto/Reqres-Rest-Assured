package Testcases.CheckLists.CheckLists;

import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;
import java.util.Hashtable;
import static io.restassured.RestAssured.given;

public class DeleteACheckList extends BaseSetup {

    @Test(priority = 0)
    public void testCreateACheckList(final Hashtable<String, String> data) {

        given().spec(request())
                .pathParams("checkListId", CreateACheckList.checkListId)
                .when()
                .delete("checklists/{checkListId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200());

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Update A CheckList Successfully");
        }
    }


}
