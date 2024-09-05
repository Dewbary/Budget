package org.example.resource;

import com.plaid.client.model.AccountBalance;
import org.example.model.ExchangeTokenRequest;
import org.example.service.PlaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class PlaidResource {

    private final PlaidService plaidService;

    @Autowired
    public PlaidResource(PlaidService plaidService) {
        this.plaidService = plaidService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public String helloWorld(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello, %s! Here is your response", name);
    }

    @GetMapping("/link_token")
    public String generateLinkToken() throws IOException {
        return plaidService.generateLinkToken();
    }

    @PostMapping("/exchange_token")
    public void exchangeToken(@RequestBody ExchangeTokenRequest exchangeTokenRequest) throws IOException {
        plaidService.exchangeToken(exchangeTokenRequest.publicToken());
    }

    @GetMapping("/run")
    public List<AccountBalance> getAccountBalances() throws IOException {
        return plaidService.run();
    }
}
