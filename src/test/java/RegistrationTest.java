import client.User;
import client.UserClient;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.*;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import page_object.LoginPageStellarBurgers;
import page_object.MainPageStellarBurgers;
import page_object.RegisterPageStellarBurgers;


public class RegistrationTest extends BaseRule {
    private String accessToken;
    private User user;
    MainPageStellarBurgers mainPage;
    LoginPageStellarBurgers loginPage;
    RegisterPageStellarBurgers registerPage;

    @Before
    public void setUp() {
        mainPage = new MainPageStellarBurgers(driver);
        loginPage = new LoginPageStellarBurgers(driver);
        registerPage = new RegisterPageStellarBurgers(driver);
        user = User.createRandomUser();
    }


    @Test
    @DisplayName("Успешная регистрация")
    @Description("Проверка успешной регистрации")
    public void successfulRegistrationTest() {
        mainPage.clickOnLoginButton();
        loginPage.clickOnRegister();
        registerPage.waitForLoadRegisterPage();
        registerPage.register(user.getName(), user.getEmail(), user.getPassword());
        loginPage.waitForLoadEntrance();
        loginPage.authorize(user.getEmail(), user.getPassword());
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
        registerPage.register(user.getName(), user.getEmail(), "arg");
        //Проверка появление текста "Некорректный пароль"
        Assert.assertTrue("Текст об ошибке отсутствует", driver.findElement(registerPage.errorPasswordText).isDisplayed());
    }

    @After
    public void cleanUp() {
        if (accessToken != null) {
            UserClient.deleteUser(accessToken);
        }
    }
}
