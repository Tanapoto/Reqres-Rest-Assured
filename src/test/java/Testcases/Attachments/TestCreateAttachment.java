package Testcases.Attachments;

import PojoData.Attachments.AttachmentsPojo;
import Testcases.Cards.TestCreateACard;
import com.google.gson.Gson;
import common.BaseSetup;
import constants.Constants;
import helpers.ExcelHelpers;
import helpers.Helpers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import reports.ExtentReportManager;
import reports.ExtentTestManager;

import java.io.File;
import java.util.Hashtable;

import static helpers.Helpers.getCurrentDir;
import static io.restassured.RestAssured.given;

public class TestCreateAttachment extends BaseSetup {

    public static String    attachmentId;

    @DataProvider(name = "getAttachmentFileData")
    public Object[][] provideAAttachmentFile () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] attachmentsData  = excelHelpers.getDataHashTable(getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Attachment", 1, 1);
        return attachmentsData;
    }

    @DataProvider(name = "getAttachmentUrlData")
    public Object[][] provideAttachmentURL () {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] attachmentsData  = excelHelpers.getDataHashTable(getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Attachment", 2, 2);
        return attachmentsData;
    }

    @Test(priority = 0, dataProvider = "getAttachmentFileData")
    public void testCreateAttachmentWithFile(final Hashtable<String, String> data) {
        String response = given().spec(requestWithFormData())
                .pathParam("name", data.get("name"))
                .multiPart("file", new File(Helpers.getCurrentDir() + data.get("url")),"image/jpeg")
                .when()
                .pathParam("cardId", TestCreateACard.cardId)
                .post("/cards/{cardId}/attachments?name={name}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        AttachmentsPojo attachmentPojo = gson.fromJson(response, AttachmentsPojo.class);
        Assert.assertEquals(attachmentPojo.getName(),data.get("name"));
        attachmentId = attachmentPojo.getId();


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Create A Attachment File Successfully");
        }
    }

    @Test(priority = 1, dataProvider = "getAttachmentUrlData")
    public void testCreateAttachmentWithUrl(final Hashtable<String, String> data) {

        String response = given().spec(requestWithFormData())
                .pathParam("name", data.get("name"))
                .multiPart("url",data.get("url"))
                .when()
                .pathParam("cardId", TestCreateACard.cardId)
                .post("/cards/{cardId}/attachments?name={name}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        AttachmentsPojo attachmentPojo = gson.fromJson(response, AttachmentsPojo.class);
        Assert.assertEquals(attachmentPojo.getName(),data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Create A Attachment URL Successfully");
        }
    }

    @Test(priority = 2, dataProvider = "getAttachmentFileData")
    public void testGetAAttachment(final Hashtable<String, String> data) {

        String response = given().spec(requestWithFormData())
                .pathParam("attachmentId", attachmentId)
                .pathParam("cardId", TestCreateACard.cardId)
                .when()
                .get("/cards/{cardId}/attachments/{attachmentId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        AttachmentsPojo attachmentPojo = gson.fromJson(response, AttachmentsPojo.class);
        Assert.assertEquals(attachmentPojo.getName(),data.get("name"));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Get A Attachment Successfully");
        }
    }



}
