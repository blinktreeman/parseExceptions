package ru.bcomms;

import ru.bcomms.ExceptionsClasses.WrongAmountOfDataException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UserDataRequest {
    public void requestDataFromUser() throws WrongAmountOfDataException {
        Scanner scanner = new Scanner(System.in);
        String userString;

        System.out.println("Enter your data separated by a space in the format: \n" +
                "Name Surname Patronymic as strings, phone number as number XXXXXXX, " +
                "date of birth as dd.mm.yyyy, gender as 'f' or 'm'");
        userString = scanner.nextLine();
        List<String> userStringsList = Arrays.stream(userString.split(" ")).toList();
        if (userStringsList.size() != 6) {
            String message = userStringsList.size() < 6
                    ? "You have entered less data than required"
                    : "You have entered more data than required";
            throw new WrongAmountOfDataException(message);
        } else {
            UserDataHolder.setUserDataList(userStringsList);
        }
    }
}
