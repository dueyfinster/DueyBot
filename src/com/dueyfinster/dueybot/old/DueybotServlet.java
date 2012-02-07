package com.dueyfinster.dueybot.old;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.*;

import com.dueyfinster.dueybot.auth.DirectTwitterAccess;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
//import com.google.appengine.api.datastore.Entity;
//import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;
//import com.google.cloud.sql.jdbc.internal.Util;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class DueybotServlet extends HttpServlet {
	private Twitter twitter;
	//private static AccessToken aT;
	//private static DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	



	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		 
		DirectTwitterAccess dTA = new DirectTwitterAccess();
		twitter = dTA.getTwitterInstance();
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		try {
			viewTimeline(twitter, resp);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	 
	 private static void viewTimeline(Twitter twitter, HttpServletResponse resp) throws TwitterException, IOException {
		 List<Status> statuses = twitter.getHomeTimeline();
		    System.out.println("Showing friends timeline.");
		    for (Status status : statuses) {
		    	resp.getWriter().println(status.getUser().getName() + ":" +
		                           status.getText());
		    }
		
	}
}
