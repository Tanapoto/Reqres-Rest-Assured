package Testcases;

import PojoData.CreateACardPojo;
import PojoData.GetListColumnPojo;
import com.google.gson.Gson;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestCreateACard extends BaseSetup {

    @DataProvider (name = "getDataCard")
    public Object[][] provideACard () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] cardData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "CreateACard", 1, 1);
        return cardData;
    }

//    name	desc	start	dueComplete	miniType

    @Test(dataProvider = "getDataCard")
    public void testCreateACard(final Hashtable<String, String> data) {

        System.out.println("HELLOL " + data.get("start"));

        CreateACardPojo createACardPojo = new CreateACardPojo(data.get("name"),data.get("desc"),data.get("start"),
                data.get("miniType"));

        given().spec(request())
                .body(createACardPojo)
                .when()
                .pathParam("backLogId", TestGetListColumn.getBackLogId())
                .post("/cards?idList={backLogId}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200());
//                .body("name[0]",equalTo(name));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get List Column Successfully");
        }
    }
}

//    @Test(dataProvider = "getListColumns")
//    public String getBackLogId() {
//        final String response = given().spec(request())
//                .pathParam("backLogId",TestGetListColumn.getBackLogId())
//                .when()
//                .get("/cards?idList={backLogId}&key={key}&token={token}")
//                .then()
//                .assertThat()
//                .spec(response200())
//                .extract().asString();
//        Gson gson = new Gson();
//        GetListColumnPojo[] getBoardsPojo = gson.fromJson(response,GetListColumnPojo[].class);
//
//        return getBoardsPojo[0].getId();
//    }
//}
