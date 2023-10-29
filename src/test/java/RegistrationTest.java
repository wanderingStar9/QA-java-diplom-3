import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import page_object.LoginPageStellarBurgers;
import page_object.MainPageStellarBurgers;
import page_object.RegisterPageStellarBurgers;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class RegistrationTest extends BaseRule {
    public static String accessToken;
    String NAME = randomAlphanumeric(4, 8);
    String EMAIL = randomAlphanumeric(6, 10) + "@ya.ru";
    String PASSWORD = randomAlphanumeric(8, 16);
    String PASSWORD_FAILED = randomAlphanumeric(1, 4);
    MainPageStellarBurgers mainPage;
    LoginPageStellarBurgers loginPage;
    RegisterPageStellarBurgers registerPage;

    @Before
    public void setUp() {
        mainPage = new MainPageStellarBurgers(driver);
        loginPage = new LoginPageStellarBurgers(driver);
        registerPage = new RegisterPageStellarBurgers(driver);
    }


    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации")
    public void successfulRegistrationTest() {
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegister();
        registerPage.waitForLoadRegisterPage();
        registerPage.register(NAME, EMAIL, PASSWORD);
        loginPage.waitForLoadEntrance();
        loginPage.authorize(EMAIL, PASSWORD);
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя.")
    @Description("Проверяем неуспешную регистрацию пользователя при вводе пароля меньше 6 символов, и появление сообщения 'Некорректный пароль'")
    public void failedPasswordRegistrationTest() {
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegister();
        registerPage.waitForLoadRegisterPage();
        registerPage.register(NAME, EMAIL, PASSWORD_FAILED);
        //Проверка появление текста "Некорректный пароль"
        Assert.assertTrue("Текст об ошибке отсутствует", driver.findElement(registerPage.errorPasswordText).isDisplayed());
    }

    @AfterClass
    public static void afterClass() {
        UserClient.deleteUser(accessToken);
    }
}
