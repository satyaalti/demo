package com.ccp.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ConstantParams {
	
	public static final String tokensep = "|";
	public static long tokenExipireTimeInMin = 1440;
	public static final String dateTimeInputFormat = "dd-MM-yyyy HH:mm:ss";
	public static final String dateInputFormat = "dd-MM-yyyy";
	public static final String dbDateFormat = "yyyy-MM-dd";
	public static final String dbDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
	public static final String googleMapsApiUrl = "https://maps.googleapis.com/maps/api/directions/json";
	public static final String googleMapsApiKey = "";
	
	public static final String ServerKey = "AIzaSyByCTvA4oGLNUU-kwAa2pl-ijF7gQlyvHc";
	
	public static Long getTimeDiff(Date dateOne, Date dateTwo) {
        long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
        return TimeUnit.MILLISECONDS.toMinutes(timeDiff);
	}
	
	public static String getDateAsString(Date dDate, String fromFormat, String toFormat) throws ParseException
	{
		DateFormat toDateFormat = new SimpleDateFormat(toFormat);
		SimpleDateFormat fromDateFormat = new SimpleDateFormat(fromFormat);
		try {
			Date parsedDate = fromDateFormat.parse(fromDateFormat.format(dDate));
			return toDateFormat.format(parsedDate);
		} 
		catch(ParseException e) {
			return null;
		}
	}
}
