package Testcases.Attachments;

import PojoData.Cards.CardPojo;
import com.google.gson.Gson;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.util.Hashtable;

import static common.BaseSetup.request;
import static common.BaseSetup.response200;
import static io.restassured.RestAssured.given;

public class TestCreateAttachment {

    @DataProvider(name = "getAttachmentData")
    public Object[][] provideAAttachment () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] cardData  = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Card", 1, 1);
        return cardData;
    }

    @Test(priority = 0, dataProvider = "getAttachmentData")
    public void testCreateAttachment(final Hashtable<String, String> data) {

        CardPojo createACardPojo = new CardPojo(data.get("name"),data.get("desc"),data.get("start"));

        String response = given().spec(request())
                .body(createACardPojo)
                .when()
                .pathParam("backLogId", "6424506e3551de3aa15560d7")
                .post("/cards?idList={backLogId}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CardPojo cardPojo = gson.fromJson(response,CardPojo.class);
        Assert.assertEquals(cardPojo.getName(),data.get("name"));
        Assert.assertEquals(cardPojo.getDesc(),data.get("desc"));
        Assert.assertTrue(cardPojo.getStart().contains(data.get("start")));


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create A Card Successfully");
        }
    }

}
