package com.example.Budgetbuddy.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProgrammableCardService {

    @Value("${investec.card.api.url}")
    private String cardApiUrl;

    @Value("${investec.card.api.key}")
    private String cardApiKey;

    public void setSpendingLimit(String cardId, double limit) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + cardApiKey);

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("limit", limit);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        restTemplate.postForEntity(cardApiUrl + "/cards/" + cardId + "/limits", entity, String.class);
    }
}
