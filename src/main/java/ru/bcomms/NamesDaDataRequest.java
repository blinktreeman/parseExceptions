package ru.bcomms;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Map;

public class NamesDaDataRequest {

    public void sendDaDataRequest() throws IOException, InterruptedException {
        String requestString = "[ \"" +
                UserDataHolder.getUserData().getSurname() + " " +
                UserDataHolder.getUserData().getName() + " " +
                UserDataHolder.getUserData().getPatronymic() +
                "\" ]";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(
                            URI.create("https://cleaner.dadata.ru/api/v1/clean/name"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Authorization", "Token " + DaDataAPIKeyConfig.getAPIKey())
                    .header("X-Secret", DaDataAPIKeyConfig.getSecretKey())
                    .POST(HttpRequest.BodyPublishers.ofString(requestString))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ArrayList<Map<String, String>> responseList =
                        new ObjectMapper().readValue(response.body(), ArrayList.class);
                Map<String, String> responseMap = responseList.get(0);
                UserDataHolder.getUserData().setName(responseMap.get("name"));
                UserDataHolder.getUserData().setSurname(responseMap.get("surname"));
                UserDataHolder.getUserData().setPatronymic(responseMap.get("patronymic"));
            }
        } catch (IOException e) {
            throw new IOException("Error request to DaData API");
        } catch (InterruptedException e) {
            throw new InterruptedException("Request execution aborted");
        }
    }
}
