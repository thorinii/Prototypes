/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.util.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;

/**
 *
 * @author Lachlan
 */
public class GeneratorWorker {

	private volatile int progress;
	private volatile GenerationStage stage;
	private ProductRequester productRequester;
	private Processor processor;
	private String templateFolder;
	private String newsletter;
	private Runnable updateRunnable;
	private Thread thread;
	public GeneratorWorker(ProductRequester productRequester, Processor processor, String templateFolder) {
		progress = 0;
		stage = GenerationStage.NoWork;
		this.productRequester = productRequester;
		this.templateFolder = templateFolder;
		this.processor = processor;
	}

	public int getProgress() {
		return progress;
	}

	public GenerationStage getCurrentStage() {
		return stage;
	}

	public void setUpdateRunnable(Runnable updateRunnable) {
		this.updateRunnable = updateRunnable;
	}

	private void updateProgressListener() {
		if (updateRunnable != null) {
			SwingUtilities.invokeLater(updateRunnable);
		}
	}

	public void runGeneration(final Map<String, List<String>> products) {
		if (stage != GenerationStage.NoWork) {
			return;
		}

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				generate(products);
			}

		});
		thread.setName("GeneratorWorker - " + thread.getName());

		thread.start();
	}

	private void generate(Map<String, List<String>> products) {
		stage = GenerationStage.Downloading;
		progress = 0;
		updateProgressListener();

		Map<String, List<Product>> downloadedProducts =
				new LinkedHashMap<String, List<Product>>();

		try {
			for (String category : products.keySet()) {
				List<String> productsInCategory = products.get(category);
				downloadedProducts.put(category,
						downloadProducts(productsInCategory, products.size(), productRequester));
			}
		} catch (IOException ioe) {
			stage = GenerationStage.Failure;

			Logger.getLogger(this.getClass().getName()).
					log(Level.WARNING, "IOException when downloading Products", ioe);

			return;
		} catch (ProductNotFoundException pnfe) {
			stage = GenerationStage.NoWork;

			Logger.getLogger(this.getClass().getName()).
					log(Level.INFO, "Product not found", pnfe);

			progress = 100;
			updateProgressListener();
			return;
		}

		stage = GenerationStage.Coding;
		progress = 0;
		updateProgressListener();

		if (processor != null) {
			for (List<Product> productList : downloadedProducts.values()) {
				for (Product product : productList) {
					processor.processProduct(product);
				}
			}
		}

		newsletter = codeProducts(downloadedProducts);

		stage = GenerationStage.NoWork;
		progress = 100;
		updateProgressListener();
	}

	private List<Product> downloadProducts(List<String> productCodes,
			int totalLength, ProductRequester productRequester) throws IOException, ProductNotFoundException {
		List<Product> products = new ArrayList<Product>();

		int max = productCodes.size();

		for (String code : productCodes) {
			Product product = null;
			try {
				product = productRequester.getProduct(code);
			} catch (ProductNotFoundException pnfe) {
				Logger.getLogger(this.getClass().getName()).
						log(Level.WARNING, "Could not find Product", pnfe);

				productNotFound(pnfe);
			}

			if (product != null) {
				products.add(product);
			}

			progress += 100 / max / totalLength;
			updateProgressListener();
		}

		return products;
	}

	private void productNotFound(final ProductNotFoundException pnfe) throws
			ProductNotFoundException {
		final java.util.concurrent.atomic.AtomicBoolean die =
				new java.util.concurrent.atomic.AtomicBoolean();

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				public void run() {
					int result = JOptionPane.showConfirmDialog(java.awt.Window.getOwnerlessWindows()[0], pnfe.getMessage()
							+ " - continue downloading?", "GG Newsletter Coder",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE);

					if (result == JOptionPane.YES_OPTION) {
						return; // Continue with whatever was before
					} else {
						die.set(true);
					}
				}

			});
		} catch (InterruptedException ie) {
			throw pnfe;
		} catch (java.lang.reflect.InvocationTargetException ite) {
			throw pnfe;
		}

		if (die.get()) {
			throw pnfe;
		}
	}

	private String codeProducts(Map<String, List<Product>> products) {
		Template template = new Template(templateFolder);

		List<String> categoryGroups = new ArrayList<String>();

		for (String categoryName : products.keySet()) {
			categoryGroups.addAll(codeCategory(template, categoryName, products.get(categoryName)));
		}

		return template.templateNewsletter(categoryGroups);
	}

	private List<String> codeCategory(Template template, String categoryName,
			List<Product> products) {
		List<String> groups = new ArrayList<String>();
		int length = products.size();

		for (int i = 0; i < length; i += 4) {
			List<Product> productGroup = new ArrayList<Product>();

			for (int j = i; j < length && j < (i + 4); j++) {
				productGroup.add(products.get(j));
			}

			groups.add(template.templateCategoryGroup((i == 0) ? categoryName
					: null, productGroup));
		}

		return groups;
	}

	public String getNewsletter() {
		if (stage != GenerationStage.NoWork) {
			throw new IllegalStateException(
					"Cannot get Newsletter while generating");
		}

		return newsletter;
	}

	public enum GenerationStage {

		NoWork, Downloading, Coding, Failure;
	}

}
