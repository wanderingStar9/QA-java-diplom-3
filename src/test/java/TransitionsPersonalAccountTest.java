import client.User;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import page_object.*;

public class TransitionsPersonalAccountTest extends BaseRule {
    private final static String EMAIL = "samuraj999@ya.ru";
    private final static String PASSWORD = "qwerty12345";
    private final static String NAME = "Peter";

    static MainPageStellarBurgers mainPage;
    static LoginPageStellarBurgers loginPage;
    static RegisterPageStellarBurgers registerPage;
    ProfilePageStellarBurgers profilePage;
    public static String accessToken;
    User user;


    @Before
    public void setUp() {
        mainPage = new MainPageStellarBurgers(driver);
        loginPage = new LoginPageStellarBurgers(driver);
        registerPage = new RegisterPageStellarBurgers(driver);
        profilePage = new ProfilePageStellarBurgers(driver);
        user = new User(EMAIL, PASSWORD, NAME);
        UserClient.postCreateNewUser(user);
    }

    @Test
    @DisplayName("Переход в личный кабинет")
    @Description("Проверка перехода по клику на 'Личный кабинет'")
    public void transitionToProfilePageTest() {
        mainPage.clickOnAccountButton();
        loginPage.waitForLoadEntrance();
        Assert.assertTrue("Страница авторизации не отобразилась", driver.findElement(loginPage.entrance).isDisplayed());
    }

    @Test
    @DisplayName("Переход в конструктор из личного кабинета")
    @Description("Проверка перехода на вкладку 'Конструктор' со страницы авторизации пользователя")
    public void transitionToConstructorFromProfilePageTest() {
        mainPage.waitForInvisibilityLoadingAnimation();
        mainPage.clickOnAccountButton();
        loginPage.waitForLoadEntrance();
        loginPage.clickOnConstructorButton();
        mainPage.waitForLoadMainPage();
        Assert.assertTrue("Переход  в конструктор из личного кабинете не прошел", driver.findElement(mainPage.textBurgerMainPage).isDisplayed());
    }

    @Test
    @DisplayName("Клик по логотипу 'Stellar Burgers'")
    @Description("Проверка перехода в конструктор при нажатии на логотип 'Stellar Burgers'")
    public void transitionToStellarBurgersFromProfilePageTest() {
        mainPage.clickOnAccountButton();
        loginPage.waitForLoadEntrance();
        loginPage.clickOnLogo();
        mainPage.waitForLoadMainPage();
        Assert.assertTrue("Конструктор при клике на логотип не загрузился", driver.findElement(mainPage.textBurgerMainPage).isDisplayed());
    }

    @Test
    @DisplayName("Выход из аккаунта")
    @Description("Проверка выхода по кнопке 'Выйти' в личном кабинете")
    public void exitFromProfileTest() {
        mainPage.clickOnAccountButton();
        loginPage.waitForLoadEntrance();
        loginPage.authorize(EMAIL, PASSWORD);
        mainPage.waitForLoadMainPage();
        LocalStorage localStorage = ((WebStorage) driver).getLocalStorage();
        accessToken = localStorage.getItem("accessToken");
        mainPage.clickOnAccountButton();
        profilePage.waitForLoadProfilePage();
        profilePage.clickOnExitButton();
        mainPage.waitForInvisibilityLoadingAnimation();
        Assert.assertTrue("Не удалось выйти из аккаунта", driver.findElement(loginPage.entrance).isDisplayed());
    }

    @AfterClass
    public static void afterClass() {
        UserClient.deleteUser(accessToken);
    }
}
