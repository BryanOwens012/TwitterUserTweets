import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends TwitterSearch {

	private final AtomicInteger counter = new AtomicInteger();
	public static int count = 0;
	public static int numLines = 0;
	public static int numUsers = 0;

	@Override
	public boolean saveTweets(List<Tweet> tweets) {
		if (tweets != null) {
			for (Tweet tweet : tweets) {
				System.out.println(counter.getAndIncrement() + 1 + "["
						+ tweet.getCreatedAt() + "] - " + tweet.getText());
				if (counter.get() >= 100) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {

		// TwitterSearch twitterSearch = new TwitterSearchImpl();
		// twitterSearch.search("BarackObama", 2000);

		System.out
				.println("This program takes a file full of Twitter screen names (e.g., @BarackObama), and for each screen name, creates a txt of all the person's tweets.");
		System.out.println("Enter the name of the input file now:");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();
		ReadFromFileObj inputFile = new ReadFromFileObj(input);
		ReadNumLinesObj readLinesFile = new ReadNumLinesObj(input);
		numLines = readLinesFile.getNumLines();
		numUsers = numLines;
		count = 0;
		System.out.println("Accepted file \"" + input + "\"." + "\n");

		while (!input.isEmpty()) {
			String output = inputFile.readLine();
			if (output == null || output.equals("")) {
				numUsers--;
				double progress = (count + 0.0) / numUsers * 100; // this is
																	// %progress
				if (progress >= 100.0) {
					System.out
							.println("File \""
									+ input
									+ "\" has been completely processed. Program terminated.");
					break;
				}
				continue;
			}
			output = output.substring(1);
			WriteToFileObj outputFile = new WriteToFileObj(output + ".txt");
			TwitterPost tp = new TwitterPost(output);
			List<Tweet> posts = tp.getPost();
			for (int i = 0; i < posts.size(); i++) {
				Tweet item = posts.get(i);
				if (item.getCreatedAt() == null) {
					continue;
				}
				outputFile.write((i + 1) + "[" + item.getCreatedAt() + "] --  "
						+ item.getText());
				outputFile.write("\n");
				System.out.println("@" + item.getUserScreenName() + ": "
						+ item.getCreatedAt() + " --  " + item.getText());
			}
			outputFile.close();
			count++;
			System.out.println("[@" + output + " had " + posts.size()
					+ " posts]");
			double progress = (count + 0.0) / numUsers * 100;
			String.format("%.2f", progress);
			System.out.println(count + " users done with " + (numUsers - count)
					+ " users to go (" + progress + "% done)");
			System.out.println();
		}
	}
}