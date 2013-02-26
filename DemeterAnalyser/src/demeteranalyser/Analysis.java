/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demeteranalyser;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author lachlan
 */
public class Analysis {

	public enum Status {

		Perfect, Failed
	}

	private final String className;
	private final Map<String, Status> methodAnalysis;
	private final Map<String, String> methodDescription;

	public Analysis(String className) {
		this.className = className;
		this.methodAnalysis = new HashMap<>();
		this.methodDescription = new HashMap<>();
	}

	public void pushMethodAnalysis(String method, Status status, String reason) {
		methodAnalysis.put(method, status);
		methodDescription.put(method, reason);
	}

	@Override
	public String toString() {
		StringBuilder toString = new StringBuilder();

		toString.append(className);

		for (Map.Entry<String, Status> analysis : methodAnalysis.entrySet()) {
			toString.append("\n  ");
			toString.append(analysis.getKey());
			toString.append("\n    ");
			toString.append(analysis.getValue().name());

			if (methodDescription.get(analysis.getKey()) != null) {
				toString.append(" - ");
				toString.append(methodDescription.get(analysis.getKey()));
			}
		}

		return toString.toString();
	}

}
