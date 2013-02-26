/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload.handler;

import java.net.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import websitedownload.output.DownloadOutput;

/**
 *
 * @author lachlan
 */
public class Parser implements Runnable {

	private static final Logger LOG = Logger.getLogger(
			Parser.class.getName());
	private final DownloadOutput output;
	private final URL url;
	private final ExecutorService downloadService;
	private final ExecutorService parserService;
	private final URLsStatus urlsStatus;

	public Parser(DownloadOutput output, URL url,
			ExecutorService downloadService,
			ExecutorService parserService, URLsStatus urlsStatus) {
		this.output = output;
		this.url = url;
		this.downloadService = downloadService;
		this.parserService = parserService;
		this.urlsStatus = urlsStatus;
	}

	@Override
	public void run() {
		if (urlsStatus.getStatus(url) != URLsStatus.Status.SUCCESS) {
			return;
		}

		LOG.log(Level.INFO, "Parsing {0}", url);

		try {
			Document doc = Jsoup.parse(output.convertURL(url), "utf-8");

			Elements aElements = doc.getElementsByTag("a");

			for (Element e : aElements) {
				handleATag(e);
			}
		} catch (IOException ex) {
			LOG.log(Level.SEVERE, null, ex);
		}
	}

	private void handleATag(Element aTag) {
		String href = aTag.attr("href");

		if (href == null) {
			return;
		}

		if (href.startsWith("javascript")) {
			return;
		}

		URL newURL;
		try {
			newURL = new URL(url, href);
		} catch (MalformedURLException murle) {
			LOG.log(Level.INFO, "URL {0} is invalid {1}", new Object[]{href,
						murle});
			return;
		}

		handleNewURL(newURL);
	}

	private void handleNewURL(URL url) {
		if (urlsStatus.testAndInitialize(url)) {
			if (shouldDownload(url)) {
				URLFetcher fetcher = new URLFetcher(url, output, urlsStatus,
						downloadService, parserService);
				downloadService.execute(fetcher);
			}
		}
	}

	private boolean shouldDownload(URL url) {
		if (url.getPath().endsWith(".html") || url.getPath().endsWith("/")
				|| url.getPath().lastIndexOf('.') < url.getPath().lastIndexOf(
				'/')) {
			return url.getHost().equals(this.url.getHost());
		} else {
			return false;
		}
	}

}
