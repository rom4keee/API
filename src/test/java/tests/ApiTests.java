package tests;

import static driver.WDriver.getDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import helpers.MapHelper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pages.CartPage;
import templates.Payload;
import util.PostUrl;

public class ApiTests {

    private final CartPage cartPage = new CartPage();
    private final PostUrl postUrls = new PostUrl();

    private final String contentType = "application/json";
    private final String productID = "5603599";
    private final int qty = 1;

    @Test
    public void createNewCartAndAddProduct() {
        // Create a new cart and get its ID
        Response createNewCartResponse = given()
                .contentType(contentType)
                .accept(contentType)
                .when()
                .post(PostUrl.getCreateNewCartUrl())
                .then()
                .extract()
                .response();
        String cartId = createNewCartResponse.jsonPath().getString("guid");

        // Add a product to the cart
        String request = Payload.getTemplatePayload("cartTemplate.txt",
                MapHelper.mapOf("code", productID, "quantity", String.valueOf(qty)));
        given()
                .header("Content-Type", contentType)
                .body(request)
                .post(PostUrl.getCreatedCartUrl(cartId))
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .body(matchesJsonSchemaInClasspath("schemaResponseCart.json"))
                .body("entry.product.code", equalTo(productID))
                .body("quantity", equalTo(qty));

        // Verify that the product was added to the cart
        cartPage.openCartOnUiWithCookie(cartId);
        assertThat(cartPage.getProductLink().getAttribute("href").contains(productID))
                .as("Cart does not contain expected product")
                .isTrue();

        getDriver().quit();
    }
}
