package org.example.service;
import com.plaid.client.ApiClient;
import com.plaid.client.model.AccountBalance;
import com.plaid.client.model.AccountBase;
import com.plaid.client.model.AccountsBalanceGetRequest;
import com.plaid.client.model.AccountsBalanceGetRequestOptions;
import com.plaid.client.model.AccountsGetRequest;
import com.plaid.client.model.AccountsGetResponse;
import com.plaid.client.model.CountryCode;
import com.plaid.client.model.ItemPublicTokenExchangeRequest;
import com.plaid.client.model.ItemPublicTokenExchangeResponse;
import com.plaid.client.model.LinkTokenCreateRequest;
import com.plaid.client.model.LinkTokenCreateRequestUser;
import com.plaid.client.model.LinkTokenCreateResponse;
import com.plaid.client.model.Products;
import com.plaid.client.model.SandboxPublicTokenCreateRequest;
import com.plaid.client.model.SandboxPublicTokenCreateRequestOptions;
import com.plaid.client.model.SandboxPublicTokenCreateResponse;
import com.plaid.client.request.PlaidApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class PlaidService {

    private static final String PLAID_CLIENT_ID = null;
    private static final String PLAID_SECRET = null;

    public static String linkToken;
    public static String publicToken;
    public static String accessToken;

    private PlaidApi plaidClient;
    private DatabaseService databaseService;
    private ApiClient apiClient;
    public String plaidEnv;

    @Autowired
    public PlaidService(PlaidApi plaidClient, DatabaseService databaseService) {
        this.plaidClient = plaidClient;
        this.databaseService = databaseService;
    }

    public String generateLinkToken() throws IOException {

        String clientUserId = "brendan_user";
        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(clientUserId);

        LinkTokenCreateRequest linkTokenCreateRequest = new LinkTokenCreateRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .user(user)
                .clientName("Dewberry Plaid Test App")
                .products(List.of(Products.AUTH))
                .language("en")
                .redirectUri("http://localhost:3000")
                .countryCodes(List.of(CountryCode.US));

        Response<LinkTokenCreateResponse> linkTokenCreateResponse = plaidClient.linkTokenCreate(linkTokenCreateRequest).execute();
        linkToken = linkTokenCreateResponse.body().getLinkToken();
        System.out.println("Link Token: " + linkToken);
        return linkToken;
    }

    public void exchangeToken(String publicToken) throws IOException {
        ItemPublicTokenExchangeRequest accessTokenRequest = new ItemPublicTokenExchangeRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .publicToken(publicToken);
        Response<ItemPublicTokenExchangeResponse> accessTokenResponse = plaidClient.itemPublicTokenExchange(accessTokenRequest).execute();
        String plaidAccessToken = accessTokenResponse.body().getAccessToken();
        String plaidItemId = accessTokenResponse.body().getItemId();

        databaseService.savePlaidTokens(plaidAccessToken, plaidItemId);

        System.out.println("Access Token: " + plaidAccessToken + " Item ID: " + plaidItemId);
    }

    public List<AccountBalance> run() throws IOException {
        plaidEnv = ApiClient.Sandbox;

        HashMap<String, String> apiKeys = new HashMap<String, String>();
        apiKeys.put("clientId", PLAID_CLIENT_ID);
        apiKeys.put("secret", PLAID_SECRET);
        apiKeys.put("plaidVersion", "2020-09-14");

        apiClient = new ApiClient(apiKeys);
        apiClient.setPlaidAdapter(plaidEnv);

        plaidClient = apiClient.createService(PlaidApi.class);

        // server talks to plaid in order to generate a link token
        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser().clientUserId(Long.toString((new Date()).getTime()));

        LinkTokenCreateRequest linkTokenCreateRequest = new LinkTokenCreateRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .user(user)
                .clientName("Dewberry Plaid Test App")
                .products(List.of(Products.AUTH))
                .language("en")
                .redirectUri("http://localhost:3000")
                .countryCodes(List.of(CountryCode.US));

        Response<LinkTokenCreateResponse> linkTokenCreateResponse = plaidClient.linkTokenCreate(linkTokenCreateRequest).execute();
        linkToken = linkTokenCreateResponse.body().getLinkToken();
        System.out.println("Link Token: " + linkToken);

        // Send link token back up to the client

        // The client uses the plaid client side library with the link token to initialize a link

        // User signs in to their bank and selects what accounts to share

        // When they finish, then we get back a public token

        // Client sends the public token to the server

        // Generates a mock public token
        SandboxPublicTokenCreateRequest sandboxPublicTokenCreateRequest = new SandboxPublicTokenCreateRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .institutionId("ins_109509")
                .initialProducts(List.of(Products.AUTH))
                .options(new SandboxPublicTokenCreateRequestOptions()
                        .overrideUsername("custom_brendan")
                        .overridePassword("1234"));
        Response<SandboxPublicTokenCreateResponse> sandboxPublicTokenCreateResponse = plaidClient
                .sandboxPublicTokenCreate(sandboxPublicTokenCreateRequest).execute();
        publicToken = sandboxPublicTokenCreateResponse.body().getPublicToken();
        System.out.println("Public Token: " + publicToken);

        // The server exchanges the public token for an access token
        ItemPublicTokenExchangeRequest accessTokenRequest = new ItemPublicTokenExchangeRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .publicToken(publicToken);
        Response<ItemPublicTokenExchangeResponse> accessTokenResponse = plaidClient.itemPublicTokenExchange(accessTokenRequest).execute();
        accessToken = accessTokenResponse.body().getAccessToken();
        System.out.println("Access Token: " + accessToken);

        // Now you can use the api

        // make an /accounts/get call and pick an account ID that is either of subtype checking or savings.
        // Then you should be able to use that account ID to generate the Stripe bank account token.

        AccountsGetRequest accountsGetRequest = new AccountsGetRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .accessToken(accessToken);

        Response<AccountsGetResponse> accountsGetResponse = plaidClient.accountsGet(accountsGetRequest).execute();
        List<AccountBase> accounts = accountsGetResponse.body().getAccounts();
        System.out.println("Accounts: " + accounts.size());

        AccountsBalanceGetRequest request = new AccountsBalanceGetRequest()
                .clientId(PLAID_CLIENT_ID)
                .secret(PLAID_SECRET)
                .accessToken(accessToken)
                .options(
                        new AccountsBalanceGetRequestOptions()
                                .accountIds(accounts.stream().map(AccountBase::getAccountId).toList())
                );
        Response<AccountsGetResponse> response = plaidClient.accountsBalanceGet(request).execute();
        System.out.println(response.body().getAccounts().get(0).getBalances().toString());
        return response.body().getAccounts().stream().map(AccountBase::getBalances).toList();




        // Create link token
        // Create public token
        // Exchange public token for access token
        // Get Balance


    }
}
