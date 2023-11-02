package page_object;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPageStellarBurgers {
    private final WebDriver driver;

    // Поле "Имя"
    private final By nameField = By.xpath(".//div[./label[text()='Имя']]/input[@name='name']");
    // Поле "Email"
    private final By emailField = By.xpath(".//div[./label[text()='Email']]/input[@name='name']");
    // Поле "Пароль"
    private final By passwordField = By.xpath(".//div[./label[text()='Пароль']]/input[@name='Пароль']");
    // Кнопка "Зарегистрироваться"
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    // Текст "Некорректный пароль"
    public final By errorPasswordText = By.className("input__error");
    // Текст заголовка "Регистрация" для проверки перехода на страницу регистрации
    public final By registerText = By.xpath(".//div/h2[text()='Регистрация']");

    public final By loginButton = By.className("Auth_link__1fOlj");


    public RegisterPageStellarBurgers(WebDriver driver) {
        this.driver = driver;
    }

    // Ввод имени
    public void setName(String name) {
        driver.findElement(nameField).sendKeys(name);
    }

    // Ввод Email
    public void setEmail(String email) {
        driver.findElement(emailField).sendKeys(email);
    }

    // Ввод пароля
    public void setPassword(String password) {
        driver.findElement(passwordField).sendKeys(password);
    }

    // Клик по кнопке "Зарегистрироваться"
    public void clickOnRegisterButton() {
        driver.findElement(registerButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    public void clickOnLoginButton() {
        driver.findElement(loginButton).click();
        waitForInvisibilityLoadingAnimation();
    }

    @Step("Регистрация пользователя")
    public void register(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
        clickOnRegisterButton();
    }

    @Step("Ожидание загрузки страницы регистрации через текст 'Регистрация'")
    public void waitForLoadRegisterPage() {
        new WebDriverWait(driver, Duration.ofSeconds(3))
                .until(ExpectedConditions.visibilityOfElementLocated(registerText));
    }

    @Step("Ожидание загрузки страницы полностью")
    public void waitForInvisibilityLoadingAnimation() {
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.invisibilityOfElementLocated
                        (By.xpath(".//img[@src='./static/media/loading.89540200.svg' and @alt='loading animation']")));
    }
}
