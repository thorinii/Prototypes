/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fvtimetracker.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lachlan
 */
public class JDBCHelper {

	public static List<String> singleColDataSetToList(ResultSet set) {
		List<String> data = new ArrayList<>();

		try {
			while (set.next()) {
				data.add(set.getString(1));
			}
		} catch (SQLException sqle) {
		}

		return data;
	}

}
