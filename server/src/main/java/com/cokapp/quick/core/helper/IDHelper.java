package com.cokapp.quick.core.helper;

import java.util.Random;
import java.util.UUID;

import org.joda.time.DateTime;

public abstract class IDHelper {
	public static String genNO() {
		DateTime dateTime = new DateTime();
		String time = dateTime.toString("yyyyMMddHHmmssSSS");
		Random random = new Random();
		return time + "" + random.nextInt(1000);
	}

	public static String genUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}