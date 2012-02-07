package com.dueyfinster.dueybot.auth;

import com.dueyfinster.dueybot.data.TwitterCredentialsSystemProperties;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class DirectTwitterAccess {
	private TwitterFactory factory;
	private Twitter twitter;
	
	public Twitter getTwitterInstance(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		 cb.setOAuthConsumerKey(TwitterCredentialsSystemProperties.getConsumerKey());
		 cb.setOAuthConsumerSecret(TwitterCredentialsSystemProperties.getConsumerKeySecret());
		 cb.setOAuthAccessToken(TwitterCredentialsSystemProperties.getAccessToken());
		 cb.setOAuthAccessTokenSecret(TwitterCredentialsSystemProperties.getAccessTokenSecret());
		 cb.setGZIPEnabled(false);
		 factory = new TwitterFactory(cb.build());
		  twitter = factory.getInstance();
			
	 return twitter;
	}

}