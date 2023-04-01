package Testcases.Stickers;

import PojoData.Stickers.StickersPojo;
import Testcases.Cards.TestCreateACard;
import Testcases.Cards.TestUpdateACard;
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

import java.util.Hashtable;

import static io.restassured.RestAssured.given;

public class TestUpdateASticker extends BaseSetup {

    @DataProvider(name = "getStickerData")
    public Object[][] provideASticker() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] CheckListData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Stickers", 2, 2);
        return CheckListData;
    }

    @Test(priority = 0, dataProvider = "getStickerData")
    public void testUpdateASticker(final Hashtable<String, String> data) {

        StickersPojo stickersRequest = new StickersPojo(data.get("top"),data.get("left"),data.get("zIndex"),data.get("rotate"),data.get("image"));
        String response = given().spec(request())
                .pathParams("cardId", TestCreateACard.cardId)
                .pathParams("stickerId", TestCreateASticker.stickerId)
                .body(stickersRequest)
                .when()
                .put("cards/{cardId}/stickers/{stickerId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        StickersPojo stickersPojo = gson.fromJson(response, StickersPojo.class);

        Assert.assertEquals(stickersPojo.getTop(), data.get("top"));
        Assert.assertEquals(stickersPojo.getLeft(), data.get("left"));
        Assert.assertEquals(stickersPojo.getZIndex(), data.get("zIndex"));
        Assert.assertEquals(stickersPojo.getRotate(), data.get("rotate"));
        Assert.assertEquals(stickersPojo.getImage(), data.get("image"));


        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Update A Sticker Successfully");
        }
    }

}
