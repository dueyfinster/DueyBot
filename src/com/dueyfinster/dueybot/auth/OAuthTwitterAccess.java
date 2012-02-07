package com.dueyfinster.dueybot.auth;

import java.io.BufferedReader;

import javax.jdo.PersistenceManager;

import com.dueyfinster.dueybot.data.PMF;
import com.dueyfinster.dueybot.data.TwitterCredentialsDataModel;
import com.dueyfinster.dueybot.data.TwitterCredentialsSystemProperties;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class OAuthTwitterAccess {
	private Twitter twitter;
	private AccessToken aT;
	private static TwitterFactory factory;
	private long aTID;

	/*
	 * This is the start of oauth twitter access. This is the code for a Java
	 * console program and needs to be adapted to work as servlet.
	 */
	public Twitter getTwitterInstance() throws TwitterException {
		
		// The factory instance is re-useable and thread safe.
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TwitterCredentialsSystemProperties.getConsumerKey(),
				TwitterCredentialsSystemProperties.getConsumerKeySecret());
		RequestToken requestToken = twitter.getOAuthRequestToken();
		aT = null;

		// Enter PIN
		// BufferedReader br = new BufferedReader(new
		// InputStreamReader(System.in));
		while (null == aT) {
			System.out
					.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out
					.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			// String pin = br.readLine();
			String pin = null;
			try {
				if (pin.length() > 0) {
					aT = twitter.getOAuthAccessToken(requestToken, pin);
				} else {
					aT = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
			aTID = twitter.verifyCredentials().getId();
		}
		
		// Write to the Datastore...
		PersistenceManager pm = PMF.get().getPersistenceManager();
		TwitterCredentialsDataModel twitterCredentials = new TwitterCredentialsDataModel(twitter, twitter.getScreenName(), aT, aTID);
		try {
		pm.makePersistent(twitterCredentials);
		} finally {
			pm.close();
		}
		
		return twitter;
	}
}