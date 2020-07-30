import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    public DataGenerator() {
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

    public static String getFullName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    public static String getEngName() {
        Faker faker = new Faker(new Locale("en-AU"));
        return faker.name().fullName();
    }

    public static String getCnName() {
        Faker faker = new Faker(new Locale("zh-CN"));
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
                    faker.phoneNumber().cellPhone(),
                    dateFirstMeeting,
                    dateSecondMeeting
            );
        }
    }
}
