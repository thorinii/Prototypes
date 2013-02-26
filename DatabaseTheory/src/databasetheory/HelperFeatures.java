package databasetheory;

import java.util.*;

/**
 *
 * @author lachlan
 */
public final class HelperFeatures {

	private HelperFeatures() {
	}

	public static <T> List<T> list(T... stuff) {
		return Arrays.asList(stuff);
	}

	public static <T> Set<T> set(T... stuff) {
		Set<T> set = new TreeSet<>();
		set.addAll(Arrays.asList(stuff));
		return set;
	}

	public static <T> Set<T> setWithout(Set<T> in, T without) {
		Set<T> set = new TreeSet<>(in);
		set.remove(without);
		return set;
	}

	public static <T> Set<T> setReplace(Set<T> in, T oldT, T newT) {
		Set<T> set = new TreeSet<>(in);
		set.remove(oldT);
		set.add(newT);
		return set;
	}

	public static <T> Set<T> setWithout(Set<T> in, T... without) {
		Set<T> set = new TreeSet<>(in);

		set.removeAll(set(without));

		return set;
	}

	public static <T> List<T> listWithout(List<T> in, T without) {
		List<T> list = new ArrayList<>(in);
		list.remove(without);
		return list;
	}

	public static <T> List<T> listWithout(List<T> in, T... without) {
		List<T> list = new ArrayList<>(in);

		list.removeAll(set(without));

		return list;
	}


	public static <T> List<T> listReplace(List<T> in, T oldT, T newT) {
		List<T> list = new ArrayList<>(in);
		list.set(list.indexOf(oldT), newT);
		return list;
	}
}
