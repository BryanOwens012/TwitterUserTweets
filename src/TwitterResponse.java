import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TwitterResponse {

	private boolean has_more_items;
	private String items_html;
	private String min_position;
	private String refresh_cursor;
	private long focused_refresh_interval;

	public TwitterResponse() {
	}

	public TwitterResponse(boolean has_more_items, String items_html,
			String min_position, String refresh_cursor,
			long focused_refresh_interval) {
		this.has_more_items = has_more_items;
		this.items_html = items_html;
		this.refresh_cursor = refresh_cursor;
		this.focused_refresh_interval = focused_refresh_interval;
	}

	public boolean isHas_more_items() {
		return has_more_items;
	}

	public void setHas_more_items(boolean has_more_items) {
		this.has_more_items = has_more_items;
	}

	public String getItems_html() {
		return items_html;
	}

	public void setItems_html(String items_html) {
		this.items_html = items_html;
	}

	public String getMin_position() {
		return min_position;
	}

	public void setMin_position(String min_position) {
		this.min_position = min_position;
	}

	public String getRefresh_cursor() {
		return refresh_cursor;
	}

	public void setRefresh_cursor(String refresh_cursor) {
		this.refresh_cursor = refresh_cursor;
	}

	public long getFocused_refresh_interval() {
		return focused_refresh_interval;
	}

	public void setFocused_refresh_interval(long focused_refresh_interval) {
		this.focused_refresh_interval = focused_refresh_interval;
	}

	public List<Tweet> getTweets() {
		final List<Tweet> tweets = new ArrayList<>();
		Document doc = Jsoup.parse(items_html);
		for (Element el : doc.select("li.js-stream-item")) {
			String id = el.attr("data-item-id");
			String text = null;
			String userId = null;
			String userScreenName = null;
			String userName = null;
			Date createdAt = null;
			int retweets = 0;
			int favourites = 0;
			try {
				text = el.select("p.tweet-text").text();
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				userId = el.select("div.tweet").attr("data-user-id");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				userName = el.select("div.tweet").attr("data-name");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				userScreenName = el.select("div.tweet")
						.attr("data-screen-name");
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
			try {
				final String date = el.select("span._timestamp").attr(
						"data-time-ms");
				if (date != null && !date.isEmpty()) {
					createdAt = new Date(Long.parseLong(date));
				}
			} catch (NullPointerException | NumberFormatException e) {
				e.printStackTrace();
			}
			// try {
			// retweets =
			// Integer.parseInt(el.select("span.ProfileTweet-action--retweet > span.ProfileTweet-actionCount")
			// .attr("data-tweet-stat-count"));
			// } catch(NumberFormatException | NullPointerException e) {
			// e.printStackTrace();
			// }
			// try {
			// favourites =
			// Integer.parseInt(el.select("span.ProfileTweet-action--favorite > span.ProfileTweet-actionCount")
			// .attr("data-tweet-stat-count"));
			// } catch (NumberFormatException | NullPointerException e) {
			// e.printStackTrace();
			// }
			Tweet tweet = new Tweet(id, text, userId, userName, userScreenName,
					createdAt, retweets, favourites);
			if (tweet.getId() != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
}