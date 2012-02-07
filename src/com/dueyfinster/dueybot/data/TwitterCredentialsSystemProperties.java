package com.dueyfinster.dueybot.data;

public class TwitterCredentialsSystemProperties {

	public static String getAccessToken() {
		return System.getProperty("oauth.accessToken");
	}

	public static String getAccessTokenSecret() {
		return System.getProperty("oauth.accessTokenSecret");
	}

	public static String getConsumerKey() {
		return System.getProperty("oauth.consumerKey");
	}

	public static String getConsumerKeySecret() {
		return System.getProperty("oauth.consumerKeySecret");
	}

}
