package org.example.resource;

import com.plaid.client.model.LinkTokenCreateRequestUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class LinkTokenResource {

//    PlaidApi plaidClient;
//
//    LinkTokenResource(PlaidApi plaidClient) {
//        this.plaidClient = plaidClient;
//    }

    record LinkToken (String linkToken) {}

    @PostMapping("/{userId}/link-token")
    public LinkToken generateLinkToken(@RequestParam String userId) throws IOException {

        LinkTokenCreateRequestUser user = new LinkTokenCreateRequestUser()
                .clientUserId(userId);

//        LinkTokenCreateRequest linkTokenCreateRequest = new LinkTokenCreateRequest()
//                .clientId(PLAID_CLIENT_ID)
//                .secret(PLAID_SECRET)
//                .user(user)
//                .clientName("Dewberry Plaid Budget App")
//                .products(List.of(Products.AUTH))
//                .language("en")
//                .redirectUri("http://localhost:3000")
//                .countryCodes(List.of(CountryCode.US));
//
//        Response<LinkTokenCreateResponse> linkTokenCreateResponse = plaidClient.linkTokenCreate(linkTokenCreateRequest).execute();
//        LinkToken linkToken = new LinkToken(linkTokenCreateResponse.body().getLinkToken());
//        return linkToken;
        return null;
    }

}
