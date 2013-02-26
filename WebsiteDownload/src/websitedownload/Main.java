/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload;

import java.io.*;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		System.out.println("Website Downloader");

		Logger.getLogger(Main.class.getPackage().getName()).setLevel(
				Level.WARNING);

		if (args.length == 1) {
			if(args[0].endsWith("debug")) {
				Logger.getLogger(Main.class.getPackage().getName()).setLevel(
						Level.INFO);
			} else if(args[0].equals("-help")) {
				System.out.println("Usage: java WebsiteDownload [config file]");
				System.out.println();
				System.out.println("Properties Options:");
				System.out.println("\t" + WebsiteDownloader.PROP_BASE_URL
						+ "= the website to download");
				System.out.println("\t" + WebsiteDownloader.PROP_DESTINATION
						+ "= where to save it");
				return;
			}
		}

		// Load Properties
		Properties props = new Properties(getDefaults());

		File propsFile = new File((args.length > 1) ? args[args.length-1] :
				"config.properties");
		if (propsFile.exists()) {
			props.load(new FileReader(propsFile));
		}

		WebsiteDownloader downloader = new WebsiteDownloader(props);
		downloader.run();
	}

	private static Properties getDefaults() {
		Properties def = new Properties();

		def.setProperty(WebsiteDownloader.PROP_BASE_URL,
				"http://www.tvlac.org.au/");
		def.setProperty(WebsiteDownloader.PROP_DESTINATION, "./destination/");

		def.setProperty(WebsiteDownloader.PROP_CONCURRENCY, "2");

		return def;
	}

}
