import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationByCardInfoTest {

    private DataGenerator generator = new DataGenerator();
    private RegistrationByCardInfo dataTest = DataGenerator.Registration.generate();
    private RegistrationByCardInfo dataTest2 = DataGenerator.Registration.generate();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    String dateUnderMin = LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Test
    void shouldDataGeneratorCorrectCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(formatter.format(dataTest.getDateSecondMeeting()));
        $(".button").click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldCityAutocomplete() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("ка");
        $$(".menu-item").find(exactText("Сыктывкар")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $(".checkbox").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldDateFromCalendar() {
        Calendar calendar = Calendar.getInstance();
        int monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        String s = LocalDate.now().format(DateTimeFormatter.ofPattern("dd"));
        Integer nowDate = Integer.parseInt(s);

        int d = monthMaxDays - nowDate;

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $(".icon_name_calendar").click();

        if (d >= 7) {
            $$("[data-day]").find(exactText(Integer.toString(nowDate + 7))).click();
        } else if (d <= 2) {
            $$("[data-day]").find(exactText(Integer.toString(7 - d))).click();
        } else {
            $("[data-step = '1'").click();
            $$("[data-day]").find(exactText(Integer.toString(7 - d))).click();
        }

        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $(".checkbox").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldDateAutocomplete() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldHyphenBetweenNameAndSurname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=name] input").setValue(
                dataTest.getFirstName() + " " + dataTest.getLastName() + "-" + dataTest2.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldDateUnderMin() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(dateUnderMin);
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldDataGeneratorIncorrectCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getIncorrectCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldDataGeneratorEngCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getEngCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldEmptyDate() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Неверно введена дата")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldEmptyName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldEmptyTelNumber() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldEmptyAll() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldUncheckedCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(generator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $(".button").click();
        $(".checkbox__text").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldFullName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(DataGenerator.getFullName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldEngName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(DataGenerator.getEngName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldCnName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(DataGenerator.getCnName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldNumericInName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue("123");
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldSpecialCharacterInName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + DataGenerator.getSpecialCharacter());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldSpacesBeforeTextName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue("      " + dataTest.getFirstName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldSpacesAfterText() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + "      ");
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldMoreSpacesBetweenNameAndSurname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + "      " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldHyphenInStartName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue("-" + dataTest.getFirstName());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldHyphenInFinishName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(DataGenerator.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue((formatter.format(dataTest.getDateFirstMeeting())));
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName() + "-");
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

}
