package com.kedacom.expenses.utils;

public class CommsTools {

	public static void sleep(long millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		};
	}
}
