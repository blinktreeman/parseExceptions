package ru.bcomms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteUserDataToFile {
    UserData userData = UserDataHolder.getUserData();

    public void writeDataToFile() throws IOException {
        String filePath = "src/main/java/ru/bcomms/outputFiles/" + userData.getSurname() + ".txt";
        File file = new File(filePath);
        if (!file.exists()) {
            boolean fileCreated = file.createNewFile();
            if (fileCreated) {
                System.out.println("File " + file.getName() + " created");
            }
        }
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("<")
                    .append(userData.getSurname()).append("><")
                    .append(userData.getName()).append("><")
                    .append(userData.getPatronymic()).append("><")
                    .append(userData.getDateOfBirth()).append("><")
                    .append(userData.getPhoneNumber()).append("><")
                    .append(userData.getGender()).append(">\n");
            fileWriter.write(stringBuilder.toString());

        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
