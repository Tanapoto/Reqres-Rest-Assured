package Testcases.Attachments;

import PojoData.Attachments.AttachmentsPojo;
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

        String response = given().spec(request())
                .pathParam("name", data.get("name"))
                .multiPart("file",Helpers.getCurrentDir() + "src/test/resources/config/ImageTest.jpeg")
                .when()
                .pathParam("cardId", "642567cbdf7cb0b7f05ce367")
                .post("/cards/{cardId}/attachments&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        AttachmentsPojo cardPojo = gson.fromJson(response,AttachmentsPojo.class);
        Assert.assertEquals(cardPojo.getName(),data.get("name"));
        Assert.assertEquals(cardPojo.getFileName(),data.get("fileName"));


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create A Card Successfully");
        }
    }

}
