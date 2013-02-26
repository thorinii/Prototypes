/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload.handler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import websitedownload.output.DownloadOutput;

/**
 *
 * @author lachlan
 */
public class URLFetcher implements Runnable {

	private static final Logger LOG = Logger.getLogger(
			URLFetcher.class.getName());
	private final URL url;
	private final DownloadOutput output;
	private final URLsStatus urlsStatus;
	private final ExecutorService downloadService;
	private final ExecutorService parserService;

	public URLFetcher(URL url, DownloadOutput output, URLsStatus urlsStatus,
			ExecutorService downloadService,
			ExecutorService parserService) {
		this.url = url;
		this.output = output;
		this.urlsStatus = urlsStatus;
		this.downloadService = downloadService;
		this.parserService = parserService;
	}

	@Override
	public void run() {
		if (!urlsStatus.testAndInProgress(url)) {
			LOG.log(Level.WARNING, "URL {0} already in progress", url);
			return;
		}

		boolean shouldParse;
		try {
			shouldParse = downloadURL();
			LOG.log(Level.INFO, "Success {0}", url);
		} catch (IOException ioe) {
			LOG.log(Level.WARNING, "Failed {0} - reason: {1}",
					new Object[]{url, ioe});
			urlsStatus.setStatus(url, URLsStatus.Status.FAIL);
			return;
		}

		urlsStatus.setStatus(url, URLsStatus.Status.SUCCESS);

		if (shouldParse) {
			Parser parser = new Parser(output, url, downloadService,
					parserService,
					urlsStatus);
			parserService.execute(parser);
		}
	}

	private boolean downloadURL() throws IOException {
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestProperty("User-Agent",
				"FV-Website-Download/1.0 Java/1.7");

		connection.connect();

		int code = connection.getResponseCode();
		if (code != HttpURLConnection.HTTP_OK && code
				!= HttpURLConnection.HTTP_MOVED_PERM) {
			throw new IOException("Invalid HTTP Code: " + code);
		}

		String mime = connection.getHeaderField("Content-Type");
		if (mime.indexOf(';') >= 0) {
			mime = mime.substring(0, mime.indexOf(';'));
		}

		output.saveFile(url, connection.getInputStream(), mime);

		return (mime == null) ? true : mime.contains("text/html");
	}

}
