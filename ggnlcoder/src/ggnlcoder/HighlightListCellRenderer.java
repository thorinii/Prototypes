/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ggnlcoder;

import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

/**
 *
 * @author lachlan
 */
public class HighlightListCellRenderer extends JLabel implements ListCellRenderer<String> {

	public HighlightListCellRenderer() {
		highlightedList = new HashSet<String>();
	}

	@Override
	public Component getListCellRendererComponent(JList paramlist, String value, int index, boolean isSelected, boolean cellHasFocus) {
		setText(value.toString());

		if (ProductRequester.isCached(value)) {
			if (ProductRequester.getCached(value) == null) {
				highlightedList.add(value);
			}
		}

		if (highlightedList.contains(value)) {
			setOpaque(true);
			setBackground((isSelected) ? COLOUR_SELECTED_HIGHLIGHTED : COLOUR_HIGHLIGHTED);
		} else {
			setOpaque(isSelected);
			setBackground((isSelected) ? COLOUR_SELECTED : COLOUR_DEFAULT);
		}

		setForeground((isSelected) ? Color.WHITE : Color.BLACK);

		return this;
	}

	public void setHighlighted(String value, boolean highlighted) {
		if (highlighted) {
			highlightedList.add(value);
		} else {
			highlightedList.remove(value);
		}
	}

	private Set<String> highlightedList;
	private final Color COLOUR_DEFAULT = new Color(0, 0, 0, 0);
	private final Color COLOUR_SELECTED = new Color(255, 150, 0, 255);
	private final Color COLOUR_HIGHLIGHTED = new Color(255, 35, 0, 255);
	private final Color COLOUR_SELECTED_HIGHLIGHTED = new Color(255, 75, 75, 255);
}
