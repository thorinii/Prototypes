/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Properties;
import websitedownload.handler.DownloadHandler;
import websitedownload.handler.GenericDownloadHandler;
import websitedownload.output.DownloadOutput;
import websitedownload.output.GenericDownloadOutput;

/**
 *
 * @author lachlan
 */
public class WebsiteDownloader {

	public static final String PROP_BASE_URL = "base-url";
	public static final String PROP_DESTINATION = "destination";
	public static final String PROP_CONCURRENCY = "concurrency";
	private final Properties props;
	private final DownloadOutput output;
	private final DownloadHandler handler;

	public WebsiteDownloader(Properties props) throws MalformedURLException {
		this.props = props;
		output = new GenericDownloadOutput(new File(props.getProperty(
				PROP_DESTINATION)));
		handler = new GenericDownloadHandler(output, props.getProperty(
				PROP_BASE_URL), Integer.parseInt(props.getProperty(PROP_CONCURRENCY)));
	}

	public void run() {
		handler.start();
	}

}
