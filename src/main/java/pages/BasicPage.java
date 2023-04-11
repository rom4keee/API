package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import util.WDriverWaiters;

import static driver.WDriver.getDriver;

public class BasicPage extends WDriverWaiters {

    public WebElement getElement(By by) {
        return getDriver().findElement(by);
    }

    public void waitUntilElementClickable(By by) {
        waitUntilElementClickable(by);
    }

    public void waitUntilElementVisible(By by, int timeout) {
        waitUntilElementVisible(by, timeout);
    }
}