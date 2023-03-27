package Testcases;

import PojoData.CreatePojo;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.Hashtable;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class TestPostRequests extends BaseSetup{

    Logger log = LogManager.getLogger(TestPostRequests.class);
    private static final String URL = "https://reqres.in";



    @DataProvider (name = "postData")
    public Object[][] providePostData () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] postData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Create", 1, 1);
        return postData;
    }


    @Test(dataProvider = "postData")
    public void testPostRequests(final Hashtable<String, String> data) {
        final CreatePojo createPojo = new CreatePojo(data.get("name"), data.get("job"));
        final String response = given().spec(request())
                .body(createPojo)
                .when()
                .post("api/users")
                .then()
                .assertThat()
                .spec(response201())
                .body("name", equalTo(data.get("name")))
                .body("job", equalTo(data.get("job")))
                .extract()
                .response()
                .body()
                .asString();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Post Requests Successfully");
        }

    }

}
