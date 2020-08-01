package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static ru.netology.DataGenerator.*;
import static ru.netology.DataGenerator.Registration.generate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class RegistrationByCardInfoTest {

    private RegistrationByCardInfo dataTest = DataGenerator.Registration.generate();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Test
    void shouldCorrectPlaningWithCorrectCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(Registration.generate().getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateSecondMeeting());
        $(".button").click();
        $$(".button").find(exactText("Перепланировать")).click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }


    @Test
    void shouldCorrectPlaningWithCityFromAutocomplete() {
        open("http://localhost:9999");
        $("[placeholder = Город].input__control").setValue("ка");
        $$(".menu-item").find(exactText("Сыктывкар")).click();
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $(".checkbox").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldCorrectPlaningWithDateFromCalendar() {
        Calendar calendar = Calendar.getInstance();
        int monthMaxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        String s = LocalDate.now().format(DateTimeFormatter.ofPattern("dd"));
        Integer nowDate = Integer.parseInt(s);

        int d = monthMaxDays - nowDate;

        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
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
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $(".checkbox").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldCorrectPlaningWithDateFromAutocomplete() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldCorrectPlaningWithHyphenBetweenNameAndSurname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=name] input").setValue(getNameWithDoubleSurname());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Встреча успешно запланирована на")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithDateUnderMin() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(getDateUnderMin());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Заказ на выбранную дату невозможен")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithIncorrectCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getIncorrectCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithEngCity() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getEngCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Доставка в выбранный город недоступна")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithEmptyDate() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Неверно введена дата")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithEmptyName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithEmptyTelNumber() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithEmptyAllFields() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $(".button").click();
        $(byText("Поле обязательно для заполнения")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithUncheckedCheckbox() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + " " + dataTest.getLastName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $(".button").click();
        $(".checkbox__text").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));
    }

    @Test
    void shouldIncorrectPlaningWithFullName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.getFullName("ru"));
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithEngName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.getFullName("en-AU"));
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithCnName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(DataGenerator.getFullName("zh-CN"));
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithNumericInName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(getNumericName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithSpecialCharacterInName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(dataTest.getFirstName() + DataGenerator.getSpecialCharacter());
        $("[data-test-id=phone] input").setValue("+7" + dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithSpacesBeforeTextName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(getNameWithSpacesBeforeText());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithSpacesAfterText() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(getNameWithSpacesAfterText());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithMoreSpacesBetweenNameAndSurname() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(getNameWithMoreSpacesBetweenNameAndSurname());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithHyphenInStartName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(getNameWithHyphenInStartName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }

    @Test
    void shouldIncorrectPlaningWithHyphenInFinishName() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue(getCity());
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.DELETE);
        $("[data-test-id=date] input").setValue(generate().getDateFirstMeeting());
        $("[data-test-id=name] input").setValue(getNameWithHyphenInFinishName());
        $("[data-test-id=phone] input").setValue(dataTest.getPhoneNumber());
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $(byText("Имя и Фамилия указаные неверно. " +
                "Допустимы только русские буквы, пробелы и дефисы.")).waitUntil(Condition.visible, 4000);
    }


}
