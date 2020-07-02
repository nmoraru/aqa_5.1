import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;


public class RegistrationByCardInfoTest {

    private DataGenerator generator = new DataGenerator();
    private RegistrationByCardInfo dataTest = DataGenerator.Registration.generate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldDataGeneratorCorrectCity() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(generator.getCity());
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        form.$("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        form.$("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(formatter.format(dataTest.getDateSecondMeeting()));
        form.$(".button").click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(".notification_status_ok").shouldBe(exist);
    }

    @Test
    void shouldDataGeneratorIncorrectCity() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(generator.getIncorrectCity());
        form.$("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        form.$("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        form.$("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        form.$("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        form.$("[data-test-id=agreement]").click();
        form.$(".button").click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 15000);
    }
    
}
