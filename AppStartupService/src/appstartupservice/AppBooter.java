/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstartupservice;

import com.sun.management.OperatingSystemMXBean;
import java.io.*;
import java.lang.management.ManagementFactory;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lachlan
 */
public class AppBooter {

	private static final double CPU_THRESHOLD = 0.2;
	private final OperatingSystemMXBean bean;
	private final Set<App> apps;

	public AppBooter() {
		bean = (OperatingSystemMXBean) ManagementFactory.
				getOperatingSystemMXBean();
		apps = new TreeSet<>();
	}

	public void loadApps() {
		Properties props = new Properties();

		try {
			try (Reader r = new FileReader(cwd() + "/apps.properties")) {
				props.load(r);
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		int currentOrder = 1000;
		for (Object o : props.keySet()) {
			String key = (String) o;

			if (key.endsWith(".cmd")) {
				currentOrder++;

				String appname = key.substring(0, key.length() - 4);
				String cmd = props.getProperty(key);
				String working = props.getProperty(appname + ".working");
				String condition = props.getProperty(appname + ".condition");

				App app = new App(
						appname, cmd, working,
						new Condition(condition));

				if (props.getProperty(appname + ".wait") != null) {
					app.setWait(props.getProperty(appname + ".wait").equals("y"));
				} else {
					app.setWait(true);
				}

				if (props.getProperty(appname + ".order") != null) {
					int order = Integer.parseInt(props.getProperty(appname
							+ ".order"));
					app.setOrder(order);
				} else {
					app.setOrder(currentOrder);
				}

				apps.add(app);
			}
		}
	}

	public void bootAll() throws Exception {
		AverageDataSet dataSet = new AverageDataSet(40);

		do {
			sleep(dataSet, 200);
		} while (dataSet.getAverage() > CPU_THRESHOLD);

		for (App app : apps) {
			System.out.print("Booting " + app.getName() + "...");
			app.boot();

			System.out.println(" done");

			if (app.isWait()) {
				sleep(dataSet, 1000);

				System.out.println("Waiting for CPU to stablise");
				double max;
				do {
					max = sleep(dataSet, 200);
				} while (max > CPU_THRESHOLD
						|| dataSet.getAverage() > CPU_THRESHOLD);
			}

			System.out.println();
		}
	}

	private double sleep(AverageDataSet dataSet, int millis) throws InterruptedException {
		double max = 0;
		for (int i = 0; i < millis; i += 200) {
			double load = bean.getSystemCpuLoad();

			if (max < load) {
				max = load;
			}

			dataSet.addSample(load);
			Thread.sleep(200);
		}

		return max;
	}

	public static String cwd() {
		try {
			File jar = new File(AppBooter.class.getProtectionDomain().
					getCodeSource().
					getLocation().toURI());

			if (jar.getName().endsWith(".jar")) {
				return jar.getParentFile().getParent();
			}

			return jar.getParentFile().getParent();
		} catch (URISyntaxException ex) {
			return ".";
		}
	}

}
