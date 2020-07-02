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
        List<String> list = Arrays.asList("Владивосток", "Москва");
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
