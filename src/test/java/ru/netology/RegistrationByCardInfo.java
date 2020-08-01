package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RegistrationByCardInfo {
    private final String city;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String dateFirstMeeting;
    private final String dateSecondMeeting;
}
