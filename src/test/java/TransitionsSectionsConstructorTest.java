import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import page_object.MainPageStellarBurgers;

public class TransitionsSectionsConstructorTest extends BaseRule {

    MainPageStellarBurgers mainPage;
    @Before
    public void setUp() {
        mainPage = new MainPageStellarBurgers(driver);
    }

    @Test
    @DisplayName("Переход в раздел 'Булки'")
    @Description("Проверка перехода в раздел 'Булки' и появления изображения с булкой")
    public void transitionToBunsInConstructorTest() throws InterruptedException {
        mainPage.clickOnSaucesButton();
        mainPage.clickOnBunsButton();
        mainPage.checkToppingBun();
    }

    @Test
    @DisplayName("Переход в раздел 'Соусы'")
    @Description("Проверка перехода в раздел 'Соусы' и появления изображения с соусом")
    public void transitionToSaucesInConstructorTest() throws InterruptedException {
        mainPage.clickOnSaucesButton();
        mainPage.checkToppingSauce();
    }

    @Test
    @DisplayName("Переход в раздел 'Начинки'")
    @Description("Проверка перехода в раздел 'Начинки' и появления изображения с начинкой")
    public void transitionToFillingsInConstructorTest() throws InterruptedException {
        mainPage.clickOnFillingButton();
        mainPage.checkToppingFillings();
    }
}
