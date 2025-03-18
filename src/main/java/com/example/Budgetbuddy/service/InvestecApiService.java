package com.example.Budgetbuddy.service;

import com.example.Budgetbuddy.model.Transaction;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class InvestecApiService {

    @Value("${investec.api.base-url}")
    private String baseUrl;

    @Value("${investec.api.access-token}")
    private String accessToken;

    private final OkHttpClient client = new OkHttpClient();

    public List<Transaction> fetchTransactions(String accountId) {
        String url = baseUrl + "/za/pb/v1/accounts/" + accountId + "/transactions";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                // Parse JSON response into List<Transaction>
                return parseTransactions(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
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

    public BigDecimal fetchAccountBalance() {
        String url = baseUrl + "/za/pb/v1/accounts/balance";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + accessToken)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                // Parse the balance from the JSON response
                return parseBalance(responseBody);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO; // Return a default value if the request fails
    }

    public BigDecimal calculateTotalSpent() {
        List<Transaction> transactions = fetchTransactions("your-account-id"); // Replace with actual account ID
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
        // Implement JSON parsing logic to extract the balance
        // Example: Use Jackson to parse the JSON
        // For now, return a placeholder value
        return BigDecimal.valueOf(1000.00); // Replace with actual parsing logic
    }
    private List<Transaction> parseTransactions(String json) {
        // Implement JSON parsing logic (e.g., using Jackson)
        return Collections.emptyList();
    }
}