import client.User;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import page_object.LoginPageStellarBurgers;
import page_object.MainPageStellarBurgers;
import page_object.RegisterPageStellarBurgers;
import page_object.ResetPasswordPageStellarBurgers;


public class LoginTest extends BaseRule {
    static MainPageStellarBurgers mainPage;
    static LoginPageStellarBurgers loginPage;
    static RegisterPageStellarBurgers registerPage;
    ResetPasswordPageStellarBurgers resetPasswordPage;
    private String accessToken;
    private User user;


    @Before
    public void setUp() {
        mainPage = new MainPageStellarBurgers(driver);
        loginPage = new LoginPageStellarBurgers(driver);
        registerPage = new RegisterPageStellarBurgers(driver);
        resetPasswordPage = new ResetPasswordPageStellarBurgers(driver);
        user = User.createRandomUser();
        UserClient.postCreateNewUser(user);
    }

    @Test
    @DisplayName("Вход по кнопке 'Войти в аккаунт'.")
    @Description("Проверка кнопки 'Войти в аккаунт' на главной странице лендинга.")
    public void enterByLoginButtonTest() {
        mainPage.clickOnLoginButton();
        loginPage.authorize(user.getEmail(), user.getPassword());
        mainPage.waitForLoadMainPage();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
    }

    @Test
    @DisplayName("Вход по кнопке 'Личный Кабинет'.")
    @Description("Проверка кнопки 'Личный Кабинет' на хедере главной страницы.")
    public void enterByPersonalAccountButtonTest() {
        mainPage.clickOnAccountButton();
        loginPage.authorize(user.getEmail(), user.getPassword());
        mainPage.waitForLoadMainPage();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
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
        loginPage.authorize(user.getEmail(), user.getPassword());
        mainPage.waitForLoadMainPage();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
    }

    @Test
    @DisplayName("Вход через кнопку в форме восстановления пароля.")
    @Description("Проверка входа через форму восстановления пароля.")
    public void enterByPasswordRecoveryFormatTest() {
        mainPage.clickOnAccountButton();
        loginPage.clickOnForgotPasswordLink();
        resetPasswordPage.waitForLoadedResetPassword();
        resetPasswordPage.clickOnLoginLink();
        loginPage.authorize(user.getEmail(), user.getPassword());
        mainPage.waitForLoadMainPage();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
    }

    @After
    public void cleanUp() {
        UserClient.deleteUser(accessToken);
    }

}
