package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class InvestecApiService {

    @Value("${investec.api.base-url}")
    private String baseUrl;

    @Value("${investec.api.access-token}")
    private String accessToken;

    @Value("${INVESTEC_CLIENT_ID}")
    private String investecClientId;

    @Value("${INVESTEC_CLIENT_KEY}")
    private String investecClientKey;

    private final OkHttpClient client = new OkHttpClient();

    private static final Logger logger = LoggerFactory.getLogger(InvestecApiService.class);

    public List<Transaction> fetchTransactions(String accountId) {
        logger.debug("Fetching transactions for accountId: {}", accountId);
        String url = "https://openapisandbox.investec.com/za/pb/v1/accounts/" + accountId + "/transactions?toDate=2025-03-31&fromDate=2024-02-01";
        logger.debug("Request URL: {}", url);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "No response body";
            logger.debug("Response from API: {}", responseBody);

            if (response.isSuccessful()) {
                logger.debug("Response body: {}", responseBody);
                return parseTransactions(responseBody);
            } else {
                logger.error("Failed to fetch transactions: {} - {}", response.code(), responseBody);
            }
        } catch (IOException e) {
            logger.error("IOException while fetching transactions: {}", e.getMessage());
        }

        return Collections.emptyList();
    }

    public String fetchAccessToken() {
        String url = "https://openapisandbox.investec.com/identity/v2/oauth2/token";

        // Use the correct injected values for clientId and clientSecret
        String credentials = Base64.getEncoder().encodeToString((investecClientId + ":" + investecClientKey).getBytes());

        RequestBody body = new FormBody.Builder()
                .add("grant_type", "client_credentials")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Basic " + credentials)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> responseMap = objectMapper.readValue(response.body().string(), Map.class);
                return (String) responseMap.get("access_token");
            }
        } catch (IOException e) {
            logger.error("Error fetching access token: {}", e.getMessage());
        }
        return null;
    }


    public void createTransactionRule(String userId, String category, BigDecimal maxAmount) {
        String url = "https://api.investec.com/za/pb/v1/accounts/rules";
        String requestBody = String.format(
                "{\"userId\": \"%s\", \"category\": \"%s\", \"maxAmount\": %s}",
                userId, category, maxAmount);

        RequestBody body = RequestBody.create(requestBody, MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("Transaction rule created successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BigDecimal fetchAccountBalance(String accountId) {
        String url = baseUrl + "/za/pb/v1/accounts/" + accountId + "/balance";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                return parseBalance(responseBody); // Parse balance from response
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }

    public Map<String, BigDecimal> calculateSpendingByCategory(List<Transaction> transactions) {
        Map<String, BigDecimal> spendingByCategory = new HashMap<>();

        for (Transaction transaction : transactions) {
            String category = transaction.getCategory();
            BigDecimal amount = transaction.getAmount();
            spendingByCategory.put(category, spendingByCategory.getOrDefault(category, BigDecimal.ZERO).add(amount));
        }

        return spendingByCategory;
    }

    private BigDecimal fetchBalanceForAccount(String accountId) {
        String url = baseUrl + "/za/pb/v1/accounts/" + accountId + "/balance";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .header("Accept", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "No body";

            // Log raw balance response
            logger.debug("Balance Response for account {}: {}", accountId, responseBody);

            if (response.isSuccessful()) {
                return parseBalance(responseBody);
            } else {
                logger.error("Failed to fetch balance: {} - {}", response.code(), responseBody);
            }
        } catch (IOException e) {
            logger.error("IOException while fetching balance: {}", e.getMessage());
        }

        return BigDecimal.ZERO;
    }

    private String extractAccountId(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> responseMap = objectMapper.readValue(json, Map.class);
            List<Map<String, String>> accounts = (List<Map<String, String>>) responseMap.get("accounts");
            if (accounts != null && !accounts.isEmpty()) {
                return accounts.get(0).get("accountId"); // Get first account ID
            }
        } catch (IOException e) {
            logger.error("Error parsing account response: {}", e.getMessage());
        }
        return null;
    }


    public BigDecimal calculateTotalSpent() {
        List<Transaction> transactions = fetchTransactions("3353431574710166878182963"); // Replace with actual account ID
        return transactions.stream()
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<String> getTopSpendingCategories() {
        // Implement logic to fetch and sort transactions by category
        return List.of("Food", "Transport", "Entertainment"); // Example categories
    }

    public Map<String, BigDecimal> getMonthlySpendingTrends() {
        // Implement logic to fetch transactions and group them by month
        return Map.of("January", BigDecimal.valueOf(1200.50), "February", BigDecimal.valueOf(980.25));
    }

    private BigDecimal parseBalance(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map<String, Object> responseMap = objectMapper.readValue(json, Map.class);
            Map<String, Object> data = (Map<String, Object>) responseMap.get("data");
            return new BigDecimal(data.get("availableBalance").toString());
        } catch (IOException e) {
            logger.error("Error parsing balance: {}", e.getMessage());
        }
        return BigDecimal.ZERO;
    }

    private List<Transaction> parseTransactions(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Parse the JSON response into a Map
            Map<String, Object> responseMap = objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});

            // Extract the "data" field
            Map<String, Object> data = (Map<String, Object>) responseMap.get("data");

            // Extract the "transactions" array from the "data" field
            List<Map<String, Object>> transactionsData = (List<Map<String, Object>>) data.get("transactions");

            // Convert the transactions data into a list of Transaction objects
            List<Transaction> transactions = new ArrayList<>();
            for (Map<String, Object> transactionData : transactionsData) {
                Transaction transaction = new Transaction();
                transaction.setId((String) transactionData.get("uuid")); // Use "uuid" as the ID
                transaction.setAccountId((String) transactionData.get("accountId"));
                transaction.setType((String) transactionData.get("type"));
                transaction.setDescription((String) transactionData.get("description"));
                transaction.setAmount(new BigDecimal(transactionData.get("amount").toString()));
                transaction.setTransactionDate((String) transactionData.get("transactionDate"));
                transactions.add(transaction);
            }
            return transactions;
        } catch (IOException e) {
            logger.error("Error parsing transactions: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}