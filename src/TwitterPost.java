import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.utils.URIBuilder;

import com.google.gson.Gson;

/**
 * Created by zxx on 8/22/2015.
 */
public class TwitterPost {

	String screenName;
	// String
	// follower_url="https://twitter.com/$sn$/followers/users?include_available_features=1&include_entities=1$m$";
	String follower_url = "https://twitter.com/i/profiles/show/$sn$/timeline";

	public TwitterPost(String screenName) {
		this.screenName = screenName;
	}

	public List<Tweet> getPost() throws Exception {

		List<Tweet> posts = new ArrayList<Tweet>();
		TwitterResponse response;
		URL url = constructURL(null);
		boolean continueSearch = true;
		String minTweet = null;
		while ((response = executeSearch(url)) != null && continueSearch) {

			List<Tweet> tweets = response.getTweets();
			if (tweets.isEmpty()) {
				break;
			}

			if (minTweet == null) {
				minTweet = tweets.get(0).getId();
			}

			for (Tweet tweet : tweets) {
				posts.add(tweet);
				// System.out.println( "[" + tweet.getCreatedAt() + "] - " +
				// tweet.getText());

			}
			String maxTweet = tweets.get(tweets.size() - 1).getId();
			if (!minTweet.equals(maxTweet)) {
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String maxPosition = maxTweet;
				url = constructURL(maxPosition);
			}
			if (!response.isHas_more_items()) {
				break;
			}
		}

		return posts;
	}

	public TwitterResponse executeSearch(final URL url) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(url
					.openConnection().getInputStream()));
			// StringBuffer sb = new StringBuffer();
			// String line = null;
			// while ((line = reader.readLine()) != null) {
			// sb.append(line);
			// }
			// String content = sb.toString();
			Gson gson = new Gson();

			TwitterResponse resp = gson.fromJson(reader, TwitterResponse.class);
			return resp;
		} catch (Exception e) {
			// If we get an IOException, sleep for 5 seconds and retry.
			System.err
					.println("Could not connect to Twitter. Retrying in 5 seconds.");
			Main.count--;
			try {
				Thread.sleep(5000);
				// return executeSearch(url);
			} catch (InterruptedException e2) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (NullPointerException | IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public URL constructURL(final String maxPosition)
			throws InvalidQueryException {

		try {
			URIBuilder uriBuilder = new URIBuilder(follower_url.replace("$sn$",
					screenName));
			uriBuilder.addParameter("include_available_features", "1");
			uriBuilder.addParameter("include_entities", "1");

			if (maxPosition != null) {
				uriBuilder.addParameter("max_position", maxPosition);
			}
			return uriBuilder.build().toURL();
		} catch (MalformedURLException | URISyntaxException e) {
			e.printStackTrace();
			return null;

		}
	}
}
