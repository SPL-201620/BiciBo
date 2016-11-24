package co.edu.uniandes.bicibo.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by santi on 23/11/2016.
 */
public class AppConfig
{
    @JsonProperty
    private String consumerKey;

    @JsonProperty
    private String consumerSecret;

    public AppConfig()
    {
        consumerKey = "ci5bruo7M6uqpp1pBAbUTw0oI";
        consumerSecret = "Mklld5GPi1IsHIusQXABme2MuSX1NOiAd1nFOyHw1qvflKXMdP";
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }
}
