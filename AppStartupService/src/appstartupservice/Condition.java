/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package appstartupservice;

import java.util.Calendar;

/**
 *
 * @author lachlan
 */
public class Condition {

	private final boolean[] days;

	public Condition(String parse) {
		days = new boolean[7];

		if (parse.length() == 1) {
			for (int i = 0; i < 7; i++) {
				days[i] = (parse.charAt(0) == 'y');
			}
		} else if (parse.length() > 1) {
			for (int i = 0; i < 7; i++) {
				days[i] = (parse.charAt(i) == 'y');
			}
		}
	}

	public boolean shouldBoot() {
		Calendar cal = Calendar.getInstance();

		switch (cal.get(Calendar.DAY_OF_WEEK)) {
			case Calendar.SUNDAY:
				return days[0];
			case Calendar.MONDAY:
				return days[1];
			case Calendar.TUESDAY:
				return days[2];
			case Calendar.WEDNESDAY:
				return days[3];
			case Calendar.THURSDAY:
				return days[4];
			case Calendar.FRIDAY:
				return days[5];
			case Calendar.SATURDAY:
				return days[6];
		}

		return false;
	}

}
