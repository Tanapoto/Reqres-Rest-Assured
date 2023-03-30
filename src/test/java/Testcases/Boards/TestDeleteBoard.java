package Testcases.Boards;

import common.BaseSetup;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestDeleteBoard extends BaseSetup {

    @Test()
    public void testDeleteBoard() {

        System.out.println("KK: " + TestCreateBoard.boardId);
        given().spec(request())
                .pathParam("boardId", TestCreateBoard.boardId)
                .when()
                .delete("/boards/{boardId}?&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .body("_value", equalTo(null))
                .extract().asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Delete Board Successfully");
        }
    }


}
