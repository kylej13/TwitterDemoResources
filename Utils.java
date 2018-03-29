package com.hcl.twitter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class Utils {

	public static final String AccessToken = "YOUR_ACCESS_TOKEN";
	public static final String AccessSecret = "YOUR_ACCESS_SECRET";
	public static final String ConsumerKey = "YOUR_CONSUMER_KEY";
	public static final String ConsumerSecret = "YOUR_CONSUMER_SECRET";


	public static JSONObject buildAndSendRequest(String url) {
		OAuthConsumer consumer = new CommonsHttpOAuthConsumer(Utils.ConsumerKey, Utils.ConsumerSecret);
		consumer.setTokenWithSecret(Utils.AccessToken, Utils.AccessSecret);

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		try {
			consumer.sign(request);
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e1) {
			e1.printStackTrace();
		}

		StringBuilder sb = new StringBuilder();
		try {
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		String response = sb.toString();
		try {
			JSONObject responseJSON = new JSONObject(response);
			return responseJSON;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}
}
