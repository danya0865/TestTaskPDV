package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.interactions.Actions;

public class FormFieldsPage {
    private WebDriver driver;

    // Локаторы элементов
    private By nameField = By.id("name-input");
    private By passwordField = By.cssSelector("input[type='password']");
    private By automationDropdown = By.id("automation");
    private By emailField = By.id("email");
    private By messageField = By.id("message");
    private By submitButton = By.id("submit-btn");

    public FormFieldsPage(WebDriver driver) {
        this.driver = driver;
    }

    private void scroll(WebElement element) {
        Actions actions = new Actions(driver);
        actions.moveToElement(element).perform();
    }

    //заполняем поле "name"
    public void enterName(String name) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement nameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(nameField));
        scroll(nameInput);
        nameInput.clear();
        nameInput.sendKeys(name);
    }

    //заполняем поле "password"
    public void enterPassword(String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        scroll(passwordInput);
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    //выбираем из чекбоксом
    public void selectDrink(String drinkValue) {
        String xpath = String.format("//input[@name='fav_drink' and @value='%s']", drinkValue);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        scroll(checkbox);
        // Проверка, что чекбокс выбран
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    //выбираем радиобаттон
    public void selectColor(String colorValue) {
        String xpath = String.format("//input[@name='fav_color' and @value='%s']", colorValue);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        scroll(radio);
        // Проверка, что выбран пункт
        if (!radio.isSelected()) {
            radio.click();
        }
    }

    //выпадающий список
    public void selectAutomationOption(String optionValue) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(automationDropdown));
        scroll(dropdown);
        //Select
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dropdown);
        select.selectByValue(optionValue);
    }

    //почта
    public void enterEmail(String email) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        scroll(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    //находим количество инструментов и самый длинный
    public void enterMessageWithLongestTool() {
        By toolList = By.xpath("//label[text()='Automation tools']/following-sibling::ul/li");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> tools = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(toolList));

        if (tools.isEmpty()) {
            throw new RuntimeException("No tools found in the list.");
        }

        int toolCount = 0;
        String longestTool = "";

        for (WebElement tool : tools) {
            String toolName = tool.getText().trim();
            if (!toolName.isEmpty() && tool.isDisplayed()) { // Проверяем, что элемент видимый и не пустой
                toolCount++;
                if (toolName.length() > longestTool.length()) {
                    longestTool = toolName;
                }
            }
        }

        String message = toolCount + " " + longestTool;

        WebDriverWait waitMessage = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement messageInput = waitMessage.until(ExpectedConditions.visibilityOfElementLocated(messageField));
        scroll(messageInput);
        messageInput.clear();
        messageInput.sendKeys(message);
    }

    //Submit
    public void submitForm() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(this.submitButton));
        scroll(submitButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            if (alert != null) {
                alert.accept();
            }
        } catch (NoAlertPresentException | TimeoutException e) {}
    }
}