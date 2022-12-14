package ru.bcomms;

import ru.bcomms.ExceptionsClasses.UserDataParseException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserDataParser {
    public void parseUserData() throws UserDataParseException {
        UserData userData = new UserData();
        List<String> userDataList = UserDataHolder.getUserDataList();

        // Находим пол
        String gender = userDataList.stream()
                .filter(x -> x.length() == 1 && (x.equals("m") || x.equals("f")))
                .findFirst()
                .orElseThrow(() -> new UserDataParseException("Wrong gender data, " +
                        "please enter gender data as 'm' or 'f'"));
        userData.setGender(gender);

        // Находим телефон
        long phoneNumber = userDataList.stream()
                .map(this::tryParsePhoneNumber)
                .filter(x -> x != -1)
                .findFirst()
                .orElseThrow(() -> new UserDataParseException("Wrong phone number please enter as number"));
        userData.setPhoneNumber(phoneNumber);

        // Находим дату рождения
        Date dateOfBirth = userDataList.stream()
                .map(this::tryParseDateOfBirth)
                .filter(Objects::nonNull)
                .findFirst()
                .orElseThrow(() -> new UserDataParseException("Wrong date of birth please enter as dd.mm.yyyy"));
        userData.setDateOfBirth(dateOfBirth);

        // Находим имена (Ф, И и О состоит более чем из одной буквы и не содержит цифр)
        List<String> names = userDataList.stream().filter(x -> x.length() > 1
                && !stringContainsDigits(x)).toList();
        if (names.size() < 3) {
            throw new UserDataParseException("Name/surname/patronymic must be more than one letter and no numbers");
        } else {
            userData.setSurname(names.get(0));
            userData.setName(names.get(1));
            userData.setPatronymic(names.get(2));
        }

        UserDataHolder.setUserData(userData);
    }

    private long tryParsePhoneNumber(String str) {
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(str);
        } catch (RuntimeException e) {
            return -1L;
        }
        return phoneNumber;
    }

    private Date tryParseDateOfBirth(String str) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date;
        try {
            date = formatter.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return date;
    }

    private boolean stringContainsDigits(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}


