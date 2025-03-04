package tests;

import org.testng.annotations.Test;
import pages.FormFieldsPage;

public class FormFieldsTest extends BaseTest {

    @Test
    public void testFormSubmission() {
        driver.get("https://practice-automation.com/form-fields/");

        FormFieldsPage formPage = new FormFieldsPage(driver);

        // Заполнение формы
        formPage.enterName("Daniil");
        formPage.enterPassword("password");
        formPage.selectDrink("Milk");
        formPage.selectDrink("Coffee");
        formPage.selectColor("Yellow");
        formPage.selectAutomationOption("yes");
        formPage.enterEmail("pankratovdv@example.com");
        formPage.enterMessageWithLongestTool();
        formPage.submitForm();
    }
}