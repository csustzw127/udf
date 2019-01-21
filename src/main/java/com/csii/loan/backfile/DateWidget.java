/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.csii.loan.backfile;

import java.util.Calendar;
import java.util.Date;

public class DateWidget {
	public static Date calcDateByCalPeriod(Date startDate, String calPeriod, int directionMultiplier) {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		if (CalPeriodEnum.DAY.getCode().equals(calPeriod)) {
			c.add(5, directionMultiplier * 1);
		} else if (CalPeriodEnum.WEEK.getCode().equals(calPeriod)) {
			c.add(5, directionMultiplier * 7);
		} else if ("BIWK".equals(calPeriod)) {
			c.add(5, directionMultiplier * 14);
		} else if ("FRWK".equals(calPeriod)) {
			c.add(5, directionMultiplier * 28);
		} else if ("30D".equals(calPeriod)) {
			c.add(5, directionMultiplier * 30);
		} else if (!"SEMM".equals(calPeriod)) {
			if (CalPeriodEnum.MNTH.getCode().equals(calPeriod)) {
				c.add(2, directionMultiplier * 1);
			} else if ("BMTH".equals(calPeriod)) {
				c.add(2, directionMultiplier * 2);
			} else if (CalPeriodEnum.QUAT.getCode().equals(calPeriod)) {
				c.add(2, directionMultiplier * 3);
			} else if (CalPeriodEnum.SEMA.getCode().equals(calPeriod)) {
				c.add(2, directionMultiplier * 6);
			} else if (CalPeriodEnum.ANNU.getCode().equals(calPeriod)) {
				c.add(2, directionMultiplier * 12);
			} else if ("2YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 24);
			} else if ("3YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 36);
			} else if ("4YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 48);
			} else if ("5YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 60);
			} else if ("6YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 72);
			} else if ("7YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 84);
			} else if ("8YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 96);
			} else if ("9YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 108);
			} else if ("10YR".equals(calPeriod)) {
				c.add(2, directionMultiplier * 120);
			}
		}

		Date result = c.getTime();
		return result;
	}

	public static Date adjustDateByDay(Date startDate, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(startDate);
		Calendar lastDayOfMonth = (Calendar) c.clone();
		lastDayOfMonth.set(5, lastDayOfMonth.getActualMaximum(5));
		int lastDay = lastDayOfMonth.getTime().getDate();
		new Date();
		Date adjustDate;
		if (day > lastDay) {
			adjustDate = new Date(startDate.getYear(), startDate.getMonth(), lastDay);
		} else {
			adjustDate = new Date(startDate.getYear(), startDate.getMonth(), day);
		}

		return adjustDate;
	}

	public static Date addDays(Date date, long nbrOfDays) {
		if (date == null) {
			throw new IllegalArgumentException("date cannot be null");
		} else {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(6, (int) nbrOfDays);
			return c.getTime();
		}
	}

	public static int calcTermDays(Date startDate, Date endDate) {
		Calendar start = getDateCalendar(startDate);
		Calendar end = getDateCalendar(endDate);
		int termDays = (int) ((end.getTimeInMillis() - start.getTimeInMillis()) / 86400000L);
		termDays = termDays < 0 ? 0 : termDays;
		return termDays;
	}

	public static Calendar getDateCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
		return calendar;
	}

	public static boolean areInSameNatrualMonth(Date d1, Date d2) {
		return d1.getYear() == d2.getYear() && d1.getMonth() == d2.getMonth();
	}

	public static boolean isLeapYear(Date dueDate) {
		Calendar c =  Calendar.getInstance();
		c.setTime(dueDate);
		int  year = c.get(Calendar.YEAR);
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	}
}