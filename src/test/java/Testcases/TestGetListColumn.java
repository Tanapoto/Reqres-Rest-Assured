package Testcases;

import PojoData.GetBoardsPojo;
import PojoData.GetListColumnPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestGetListColumn extends BaseSetup {

    @DataProvider()
    public Iterator<Object[]> getListColumns() {
        final List<Object[]> getTestData = new ArrayList<>();
        getTestData.add(new Object[]{"Backlog"});
        return getTestData.iterator();
    }


    @Test(dataProvider = "getListColumns")
    public void testGetListColumn(String name) {

        given().spec(request())
                .when()
                .pathParam("boardId",TestGetListBoards.getBoardId())
                .get("boards/{boardId}/lists?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .body("name[0]",equalTo(name));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get List Column Successfully");
        }
    }

    @Test(dataProvider = "getListColumns")
    public static String getBackLogId() {
        final String response = given().spec(request())
                .pathParam("boardId",TestGetListBoards.getBoardId())
                .when()
                .get("boards/{boardId}/lists?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();
        Gson gson = new Gson();
        GetListColumnPojo[] getBoardsPojo = gson.fromJson(response,GetListColumnPojo[].class);

        return getBoardsPojo[0].getId();
    }
}
