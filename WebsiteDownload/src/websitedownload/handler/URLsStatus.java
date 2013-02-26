/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload.handler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class URLsStatus {

	public enum Status {

		NOT_DOWNLOADED, INITIALIZED, IN_PROGRESS, SUCCESS, FAIL
	}

	private final Map<URL, Status> statuses;

	public URLsStatus() {
		statuses = new HashMap<>();
	}

	public synchronized Status getStatus(URL url) {
		Status s = statuses.get(canonicalURL(url));
		return (s == null) ? Status.NOT_DOWNLOADED : s;
	}

	public synchronized boolean consideredURL(URL url) {
		return getStatus(url) == Status.NOT_DOWNLOADED;
	}

	public synchronized boolean testAndInitialize(URL url) {
		boolean notDown = consideredURL(url);
		if (notDown) {
			setStatus(url, Status.INITIALIZED);
		}
		return notDown;
	}

	public synchronized boolean testAndInProgress(URL url) {
		boolean init = (getStatus(url) == Status.INITIALIZED);
		if (init) {
			setStatus(url, Status.IN_PROGRESS);
		}
		return init;
	}

	public synchronized void setStatus(URL url, Status status) {
		statuses.put(canonicalURL(url), status);
	}

	private URL canonicalURL(URL in) {
		try {
			return new URL(in.getProtocol(), in.getHost(), (in.getPort() == -1) ?
					in.getDefaultPort() : in.getPort(), in.getPath());
		} catch (MalformedURLException ex) {
			return in;
		}
	}

}
