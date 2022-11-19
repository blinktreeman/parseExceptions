package ru.bcomms;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NamesDaDataRequest {

    public void sendDaDataRequest() throws IOException, InterruptedException {
        String requestParameters = "?query=" +
                UserDataHolder.getUserData().getSurname() + "%20" +
                UserDataHolder.getUserData().getName() + "%20" +
                UserDataHolder.getUserData().getPatronymic();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(
                        URI.create("https://suggestions.dadata.ru/suggestions/api/4_1/rs/suggest/fio"
                                + requestParameters))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .header("Authorization", "Token " + DaDataAPIKeyConfig.getAPIKey())
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        Map<String, Object> responseMap = toHashMap(response.body());
        assert responseMap != null;
        Map<?, ?> names = (Map<?, ?>) ((Map<?, ?>) ((List<?>) responseMap.get("suggestions")).get(0)).get("data");

        UserDataHolder.getUserData().setName((String) names.get("name"));
        UserDataHolder.getUserData().setSurname((String) names.get("surname"));
        UserDataHolder.getUserData().setPatronymic((String) names.get("patronymic"));
    }

    private Map<String, Object> toHashMap(String content) {
        try {
            return new ObjectMapper().readValue(content, HashMap.class);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

}
