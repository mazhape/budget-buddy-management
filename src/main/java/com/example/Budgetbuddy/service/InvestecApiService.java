package com.example.Budgetbuddy.service;


import com.example.Budgetbuddy.model.Transaction;
import lombok.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InvestecApiService {
    @Value("${investec.api.url}")
    private String investecApiUrl;

    @Value("${investec.api.key}")
    private String apiKey;

    public List<Transaction> fetchTransactions(String accountId) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + apiKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = investecApiUrl + "/accounts/" + accountId + "/transactions";
        ResponseEntity<List<Transaction>> response = restTemplate.exchange(
                url, HttpMethod.GET, entity, new ParameterizedTypeReference<List<Transaction>>() {});

        return response.getBody();
    }

}
