import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseRule {
    public static WebDriver driver;
    private final String URL = "https://stellarburgers.nomoreparties.site/";

    @Before
    public void setBaseRule() {
        if ("Yandex".equals(System.getProperty("browser")))
            setUpYandex();
        else
            setUpChrome();

        WebDriverManager.chromedriver().setup();
        driver.navigate().to(URL);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    public void setUpChrome() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");

        driver = new ChromeDriver(options);
    }

    public void setUpYandex() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "src/main/resources/yandexdriver");
        driver = new ChromeDriver(options);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

}
