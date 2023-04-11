package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebElement;

import static driver.WDriver.getDriver;

public class CartPage extends BasicPage {

    private static final String CART_URL = "https://www.kruidvat.nl/cart";
    private static final By PRODUCT_LINK = By.xpath("//a[@class='product-summary__img-link']");

    public WebElement getProductLink() {
        return getElement(PRODUCT_LINK);
    }

    public void openCartOnUiWithCookie(String cartId) {
        Cookie cartCookie = new Cookie("kvn-cart", cartId);
        getDriver().get(CART_URL);
        deleteAllCookies();
        addCookie(cartCookie);
        refreshPage();
    }

    private void deleteAllCookies() {
        getDriver().manage().deleteAllCookies();
    }

    private void addCookie(Cookie cookie) {
        getDriver().manage().addCookie(cookie);
    }

    private void refreshPage() {
        getDriver().navigate().refresh();
    }
}