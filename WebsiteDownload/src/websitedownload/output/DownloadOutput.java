/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websitedownload.output;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 *
 * @author lachlan
 */
public interface DownloadOutput {

	public File convertURL(URL url);
	public File convertURL(URL url, String mimeType);

	public void saveFile(URL url, InputStream inputStream);
	public void saveFile(URL url, InputStream in, String mimeType);

}
