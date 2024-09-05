package org.example;

import com.plaid.client.ApiClient;
import com.plaid.client.request.PlaidApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class PlaidClientConfiguration {

    private static final String PLAID_CLIENT_ID = null;
    private static final String PLAID_SECRET = null;
    private static final String PLAID_ENV = ApiClient.Sandbox;

    @Bean
    public PlaidApi plaidClient() {
        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("clientId", PLAID_CLIENT_ID);
        apiKeys.put("secret", PLAID_SECRET);
        apiKeys.put("plaidVersion", "2020-09-14");

        ApiClient apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(PLAID_ENV);

        PlaidApi plaidClient = apiClient.createService(PlaidApi.class);
        return plaidClient;
    }
}
