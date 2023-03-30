package Testcases.Cards;

import PojoData.Cards.CardPojo;
import Testcases.ListColumns.TestGetListColumn;
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

public class TestCreateACard extends BaseSetup {

    public static String cardId;

    @DataProvider(name = "getDataCard")
    public Object[][] provideACard() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] cardData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Card", 1, 1);
        return cardData;
    }

    @Test(priority = 0, dataProvider = "getDataCard")
    public void testCreateACard(final Hashtable<String, String> data) {

        CardPojo createACardPojo = new CardPojo(data.get("name"), data.get("desc"), data.get("start"));

        String response = given().spec(request())
                .body(createACardPojo)
                .when()
                .pathParam("listColumnId", TestGetListColumn.listColumnId)
                .post("/cards?idList={listColumnId}&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CardPojo cardPojo = gson.fromJson(response, CardPojo.class);
        Assert.assertEquals(cardPojo.getName(), data.get("name"));
        Assert.assertEquals(cardPojo.getDesc(), data.get("desc"));
        Assert.assertTrue(cardPojo.getStart().contains(data.get("start")));

        cardId = cardPojo.getId();

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Create A Card Successfully");
        }
    }

    @Test(priority = 1, dataProvider = "getDataCard")
    public void testGetACard(Hashtable<String, String> data) {


        String response = given().spec(request())
                .pathParam("cardId", cardId)
                .when()
                .get("/cards/{cardId}?&key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200())
                .extract().asString();

        Gson gson = new Gson();
        CardPojo cardPojo = gson.fromJson(response, CardPojo.class);

        Assert.assertEquals(cardPojo.getName(), data.get("name"));
        Assert.assertEquals(cardPojo.getDesc(), data.get("desc"));
        Assert.assertTrue(cardPojo.getStart().contains(data.get("start")));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.info("Test Get A Card in Boards Successfully");
        }
    }
}
