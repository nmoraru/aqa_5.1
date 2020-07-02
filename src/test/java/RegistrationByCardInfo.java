import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class RegistrationByCardInfo {
    private final String city;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final LocalDate dateFirstMeeting;
    private final LocalDate dateSecondMeeting;
}
