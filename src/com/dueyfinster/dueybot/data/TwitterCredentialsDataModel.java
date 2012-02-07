package com.dueyfinster.dueybot.data;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@PersistenceCapable
public class TwitterCredentialsDataModel {

	public TwitterCredentialsDataModel(Twitter twitter, String userName,
			AccessToken accessToken, Long accessTokenSecret) {
		this.twitter = twitter;
		this.userName = userName;
		this.accessToken = accessToken;
		this.accessTokenSecret = accessTokenSecret;
	}

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String userName;

	@Persistent
	private Twitter twitter;

	@Persistent
	private AccessToken accessToken;

	@Persistent
	private Long accessTokenSecret;

}
