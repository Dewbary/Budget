"use client";

import React from "react";
import {
  usePlaidLink,
  PlaidLinkOptions,
  PlaidLinkOnSuccess,
  PlaidLink,
} from "react-plaid-link";

export const PlaidSetUp = () => {
  const [token, setToken] = React.useState<string | null>(null);
  const isOAuthRedirect = window.location.href.includes("?oauth_state_id=");

  React.useEffect(() => {
    if (isOAuthRedirect) {
      setToken(localStorage.getItem("link_token"));
      return;
    }

    const createLinkToken = async () => {
      const response = await fetch("http://localhost:8080/link_token");
      const link_token = await response.text();
      setToken(link_token);
      localStorage.setItem("link_token", link_token);
    };
    createLinkToken();
  }, []);

  const onSuccess = React.useCallback<PlaidLinkOnSuccess>(
    (publicToken, metadata) => {
      // send public_token to your server
      // https://plaid.com/docs/api/tokens/#token-exchange-flow
      console.log(publicToken, metadata);
      fetch("http://localhost:8080/exchange_token", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          public_token: publicToken,
        }),
      });
    },
    []
  );

  const config: PlaidLinkOptions = {
    token,
    onSuccess,
  };
  if (isOAuthRedirect) {
    // receivedRedirectUri must include the query params
    config.receivedRedirectUri = window.location.href;
  }

  const { open, ready } = usePlaidLink({
    token,
    onSuccess,
  });

  React.useEffect(() => {
    // If OAuth redirect, instantly open link when it is ready instead of
    // making user click the button
    if (isOAuthRedirect && ready) {
      open();
    }
  }, [ready, open, isOAuthRedirect]);

  if (isOAuthRedirect) {
    return <></>;
  }

  return (
    <div>
      <PlaidLink
        // clientName="React Plaid Setup"
        env="sandbox"
        token={token}
        // onExit={this.handleOnExit}
        onSuccess={onSuccess}
        className="test"
      >
        test
      </PlaidLink>
      <button
        className="bg-slate-700 text-white p-4 rounded-lg"
        onClick={() => open()}
        disabled={!ready}
      >
        Connect a bank account
      </button>
    </div>
  );
};
