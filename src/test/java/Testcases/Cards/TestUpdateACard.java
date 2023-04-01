package Testcases.Cards;

import PojoData.Cards.CardPojo;
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

public class TestUpdateACard extends BaseSetup {

    @DataProvider(name = "getDataCard")
    public Object[][] provideACard() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        final Object[][] cardData = excelHelpers.getDataHashTable(Helpers.getCurrentDir()
                + Constants.EXCEL_DATA_FILE_PATH, "Card", 2, 2);
        return cardData;
    }

    @Test(dataProvider = "getDataCard")
    public void testUpdateACard(final Hashtable<String, String> data) {

        CardPojo updateACardPojo = new CardPojo(data.get("name"), data.get("desc"), data.get("start"));

        String response = given().spec(request())
                .body(updateACardPojo)
                .when()
                .pathParam("cardId", TestCreateACard.cardId)
                .put("/cards/{cardId}?key={key}&token={token}")
                .then()
                .assertThat()
                .spec(response200()).extract().asString();

        Gson gson = new Gson();
        CardPojo cardPojo = gson.fromJson(response, CardPojo.class);
        Assert.assertEquals(cardPojo.getName(), data.get("name"));
        Assert.assertEquals(cardPojo.getDesc(), data.get("desc"));
        Assert.assertTrue(cardPojo.getStart().contains(data.get("start")));

        if (ExtentTestManager.getExtentTest() != null) {
            ExtentReportManager.pass("Test Update A Card Successfully");
        }
    }
}
