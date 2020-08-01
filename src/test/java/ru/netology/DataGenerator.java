package ru.netology;

import com.github.javafaker.Faker;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Data
public class DataGenerator {

    DataGenerator() {
    }

    public static String getCity() {
        Random rand = new Random();
        List<String> list = Arrays.asList("Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Магас", "Нальчик",
                "Элиста", "Черкесск", "Петрозаводск", "Сыктывкар", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск",
                "Владикавказ", "Казань", "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита",
                "Петропавловск-Камчатский", "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск",
                "Благовещенск", "Архангельск", "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда",
                "Воронеж", "Иваново", "Иркутск", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган",
                "Курск", "Санкт-Петербург", "Липецк", "Магадан", "Москва", "Мурманск", "Нижний Новгород",
                "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков", "Ростов-на-Дону",
                "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов", "Тверь", "Томск",
                "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Севастополь", "Биробиджан", "Нарьян-Мар",
                "Ханты-Мансийск", "Анадырь", "Салехард");
        int randomIndex = rand.nextInt(list.size());
        String randomElement = list.get(randomIndex);
        return randomElement;
    }

    public static String getFullName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().fullName();
    }

    public static String getSpecialCharacter() {
        Random rand = new Random();
        List<String> list = Arrays.asList("~", "`", "@", "!", "#", "$", "%", "^", "&", "*", "(", ")", "/", "+",
                "№", ";", ":", "?");
        int randomIndex = rand.nextInt(list.size());
        String randomElement = list.get(randomIndex);
        return randomElement;
    }

    public static String getIncorrectCity() {
        Random rand = new Random();
        List<String> list = Arrays.asList("Лондон", "Пекин");
        int randomIndex = rand.nextInt(list.size());
        String randomElement = list.get(randomIndex);
        return randomElement;
    }

    public static String getEngCity() {
        Faker faker = new Faker(new Locale("en-AU"));
        return faker.address().cityName();
    }

    public static String getNumericName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.random().nextInt(0, 1000).toString();
    }

    public static String getNameWithSpacesBeforeText() {
        Faker faker = new Faker(new Locale("ru"));
        return "      " + faker.name().firstName();
    }

    public static String getNameWithSpacesAfterText() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + "      ";
    }

    public static String getNameWithMoreSpacesBetweenNameAndSurname() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + "      " + faker.name().lastName();
    }

    public static String getNameWithHyphenInStartName() {
        Faker faker = new Faker(new Locale("ru"));
        return "-" + faker.name().firstName();
    }

    public static String getNameWithHyphenInFinishName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName() + "-";
    }

    public static String getNameWithDoubleSurname() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName() + "-" + faker.name().lastName();
    }

    public static String getDateUnderMin() {
        return LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static class Registration {

        private Registration() {
        }

        public static RegistrationByCardInfo generate() {
            Faker faker = new Faker(new Locale("ru"));

            LocalDate dateFirstMeeting = LocalDate.now().plusDays(4);
            LocalDate dateSecondMeeting = LocalDate.now().plusDays(5);

            return new RegistrationByCardInfo(
                    getCity(),
                    faker.name().firstName(),
                    faker.name().lastName(),
                    "+7" + faker.phoneNumber().cellPhone(),
                    dateFirstMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                    dateSecondMeeting.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            );
        }
    }
}
