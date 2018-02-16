package com.qtpselenium.core.hybrid;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
// to select the date from the calander
public class Temp {

	public static void main(String[] args) throws ParseException {
		Date d = new Date();
		System.out.println(d.toString().replace(":", "_").replace(" ", "_"));
//this one is for name of the report so that report created each time should varies by this name with date
		System.out.println(d);
	  String date ="11/09/2018";
	  
	  SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d1 = sdf.parse(date);

	  System.out.println(d.compareTo(d1));
	  
	  sdf = new SimpleDateFormat("yyyy");
	  System.out.println(sdf.format(d1));
	}


}
