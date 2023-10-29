import client.User;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import page_object.LoginPageStellarBurgers;
import page_object.MainPageStellarBurgers;
import page_object.RegisterPageStellarBurgers;
import page_object.ResetPasswordPageStellarBurgers;


public class LoginTest extends BaseRule {
    private final static String EMAIL = "samuraj999@ya.ru";
    private final static String PASSWORD = "qwerty12345";
    private final static String NAME = "Peter";

    static MainPageStellarBurgers mainPage;
    static LoginPageStellarBurgers loginPage;
    static RegisterPageStellarBurgers registerPage;
    ResetPasswordPageStellarBurgers resetPasswordPage;
    public static String accessToken;
    User user;


    @Before
    public void setUp() {
        mainPage = new MainPageStellarBurgers(driver);
        loginPage = new LoginPageStellarBurgers(driver);
        registerPage = new RegisterPageStellarBurgers(driver);
        resetPasswordPage = new ResetPasswordPageStellarBurgers(driver);
        user = new User(EMAIL, PASSWORD, NAME);
        UserClient.postCreateNewUser(user);
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт'.")
    @Description("Проверка кнопки 'Войти в аккаунт' на главной странице лендинга.")
    public void enterByLoginButtonTest() {
        mainPage.clickOnLoginButton();
        loginPage.authorize(EMAIL, PASSWORD);
        mainPage.waitForLoadMainPage();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
    }

    @Test
    @DisplayName("Вход по кнопке 'Личный Кабинет'.")
    @Description("Проверка кнопки 'Личный Кабинет' на хедере главной страницы.")
    public void enterByPersonalAccountButtonTest() {
        mainPage.clickOnAccountButton();
        loginPage.authorize(EMAIL, PASSWORD);
        mainPage.waitForLoadMainPage();
    }

    @Test
    @DisplayName("Вход через кнопку в форме регистрации.")
    @Description("Проверка входа через форму регистрации.")
    public void enterByRegistrationFormTest() {
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegister();
        registerPage.waitForLoadRegisterPage();
        registerPage.clickOnLoginButton();
        loginPage.waitForLoadEntrance();
        loginPage.authorize(EMAIL, PASSWORD);
        mainPage.waitForLoadMainPage();
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля.")
    @Description("Проверка входа через форму восстановления пароля.")
    public void enterByPasswordRecoveryFormatTest() {
        mainPage.clickOnAccountButton();
        loginPage.clickOnForgotPasswordLink();
        resetPasswordPage.waitForLoadedResetPassword();
        resetPasswordPage.clickOnLoginLink();
        loginPage.authorize(EMAIL, PASSWORD);
        mainPage.waitForLoadMainPage();
    }

    @AfterClass
    public static void afterClass() {
        UserClient.deleteUser(accessToken);
    }

}
