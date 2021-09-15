package com.thai.anime.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/***
 * input: url
 * output: json
 */

@Component
public class SendHttpGETRequest {

    public SendHttpGETRequest() {}
    public String send(String url_string) throws IOException {
        URL url = new URL(url_string);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(2000);
        con.setReadTimeout(2000);
        int status = con.getResponseCode();
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while((inputLine =  reader.readLine()) != null) {
            content.append(inputLine);
        }

        String body = content.toString();
        reader.close();
        con.disconnect();
        if (status != 200) {
            throw new ConnectException(body);
        }
        return body;
    }
}
