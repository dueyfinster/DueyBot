package com.dueyfinster.dueybot.old;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.*;

import com.dueyfinster.dueybot.auth.DirectTwitterAccess;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
//import com.google.appengine.api.datastore.Entity;
//import com.google.appengine.api.datastore.Key;
//import com.google.appengine.api.datastore.KeyFactory;
//import com.google.cloud.sql.jdbc.internal.Util;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class DeleteTweetsServlet extends HttpServlet {
	private Twitter twitter;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		 
		DirectTwitterAccess dTA = new DirectTwitterAccess();
		twitter = dTA.getTwitterInstance();
		resp.setContentType("text/plain");
		 int deletedCount = 0;
		 int safeCount = 0;
		
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -24);
		Date date = calendar.getTime();
		List<Status> statuses = null;
		try {
			statuses = twitter.getUserTimeline(twitter.getId(), new Paging(1, 200));
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			resp.getWriter().println("Exception getting user timeline");
			e.printStackTrace();
		}
		String cannotDestroyStatus = "";
			for (Status status : statuses) {
				if(date.before(status.getCreatedAt())){
					resp.getWriter().println("Safe status: " +  status.getText());
					safeCount++;
			    }else if(date.after(status.getCreatedAt())){
			    	resp.getWriter().println("Deleting status: " +  status.getText());
			    	 long statusId = status.getId();
					try {
						twitter.destroyStatus(statusId);
					} catch (TwitterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						cannotDestroyStatus = "(stack trace error deleting)";
					}
					resp.getWriter().println(status.getText() + " Deleted!"); deletedCount++;
			    }
		
			}
			
			String statusUpdate = "@dueyfinster " + deletedCount + " statuses deleted " + cannotDestroyStatus + "and " + safeCount + " statuses safe.";
			StatusUpdate su = new StatusUpdate(statusUpdate);
		    Status status;
			try {
				twitter.sendDirectMessage("dueyfinster", statusUpdate);
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		   

	}
		
}
