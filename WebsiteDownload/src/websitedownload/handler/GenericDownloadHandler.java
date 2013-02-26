/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload.handler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.*;
import websitedownload.output.DownloadOutput;

/**
 *
 * @author lachlan
 */
public class GenericDownloadHandler implements DownloadHandler {

	private final DownloadOutput output;
	private final URL baseURL;
	private final ExecutorService downloadService;
	private final ExecutorService parserService;
	private final URLsStatus urlsStatus;
	private volatile int taskCount;

	public GenericDownloadHandler(DownloadOutput output, String baseURL) throws MalformedURLException {
		this(output, baseURL, Runtime.getRuntime().availableProcessors());
	}

	public GenericDownloadHandler(DownloadOutput output, String baseURL, int concurrency) throws MalformedURLException {
		this.output = output;
		this.baseURL = new URL(baseURL);

		this.downloadService = new CustomThreadPoolExecutor(concurrency);
		this.parserService = new CustomThreadPoolExecutor(concurrency);

		this.urlsStatus = new URLsStatus();

		this.taskCount = 0;
	}

	@Override
	public void start() {
		System.out.println("Downloading " + baseURL);
		
		URLFetcher fetcher = new URLFetcher(baseURL, output, urlsStatus,
				downloadService, parserService);

		urlsStatus.testAndInitialize(baseURL);
		downloadService.execute(fetcher);
	}

	private synchronized void beginTask() {
		taskCount++;
	}

	private synchronized void finishTask() {
		taskCount--;

		if (isFinished()) {
			downloadService.shutdown();
			parserService.shutdown();
		}
	}

	public synchronized boolean isFinished() {
		return taskCount == 0;
	}

	private class CustomThreadPoolExecutor extends ThreadPoolExecutor {

		public CustomThreadPoolExecutor(int threads) {
			super(threads, threads,
					0L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
		}

		@Override
		public void execute(Runnable command) {
			beginTask();
			super.execute(command);
		}

		@Override
		protected void afterExecute(Runnable r, Throwable t) {
			finishTask();
			super.afterExecute(r, t);
		}

	}

}
