package com.example.MyTweetter.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {
	
	public static boolean checkDate (String sDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		boolean ret;
		try {
			sdf.parse(sDate);
			ret = true;
		} catch (ParseException e) {
			ret = false;
		}
		return ret;
	}
	
	public static boolean checkEmail (String email) {
		if (email == null || email.equals("") ) {
			return false;
		}
		Pattern pattern = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		boolean matchFound = matcher.find();
		return matchFound;
	}
}
