/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload.output;

import java.io.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class GenericDownloadOutput implements DownloadOutput {

	private static final Logger LOG = Logger.getLogger(
			GenericDownloadOutput.class.getName());
	private static final int BLOCK_SIZE = 1024;
	private final File root;

	public GenericDownloadOutput(File root) {
		this.root = root;

		if (!root.exists()) {
			root.mkdirs();
		}
	}

	@Override
	public File convertURL(URL url) {
		return convertURL(url, "text/html");
	}

	@Override
	public File convertURL(URL url, String mimeType) {
		File save = new File(root, url.getHost() + "/" + url.getPath());

		if (url.getPath().equals("/")) {
			save = new File(save, "index.html");
		} else if (save.getPath().endsWith("/")) {
			save = new File(save.getPath().substring(0, save.getPath().length()
					- 1));
		} else if (url.getPath().lastIndexOf('.') < url.getPath().lastIndexOf(
				'/')) {
			String add;
			switch (mimeType) {
				case "text/html":
					add = ".html";
					break;
				case "text/plain":
					add = ".txt";
					break;
				default:
					add = null;
			}

			if (add != null) {
				save = new File(save.getPath() + add);
			}
		}
		try {
			return save.getCanonicalFile();
		} catch (IOException ex) {
			return save.getAbsoluteFile();
		}
	}

	@Override
	public void saveFile(URL url, InputStream in) {
		saveFile(url, in, "text/html");
	}

	@Override
	public void saveFile(URL url, InputStream in, String mimeType) {
		File save = convertURL(url);
		File parent = save.getParentFile();

		if (!parent.exists()) {
			parent.mkdirs();
		}

		LOG.log(Level.INFO, "Saving {0} to {1}", new Object[]{url, save});

		byte[] buf = new byte[BLOCK_SIZE];
		try (
				FileOutputStream out = new FileOutputStream(save)) {

			int length;
			while ((length = in.read(buf)) > 0) {
				out.write(buf, 0, length);
			}

			in.close();
		} catch (IOException ioe) {
			LOG.log(Level.WARNING, "Saving {0} failed {1}", new Object[]{url,
						ioe});
		}
	}

}
