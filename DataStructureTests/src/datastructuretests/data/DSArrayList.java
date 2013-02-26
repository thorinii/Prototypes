/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructuretests.data;

import java.util.AbstractList;
import java.util.Arrays;

/**
 *
 * @author lachlan
 */
public class DSArrayList<T> extends AbstractList<T> {

	private T[] list;
	private int size;

	public DSArrayList() {
		super();
		list = (T[]) new Object[16];
	}

	@Override
	public boolean add(T e) {
		ensureSpace();
		list[size] = e;
		size++;
		return true;
	}

	private void ensureSpace() {
		if (list.length <= size) {
			list = Arrays.copyOf(list, list.length * 2);
		}
	}

	@Override
	public T get(int index) {
		if (index >= size || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		return list[index];
	}

	@Override
	public int size() {
		return size;
	}

}
