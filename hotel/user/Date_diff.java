package hotel.user;

import java.util.*;
import java.text.*;
/**
 * Class for calculating the difference between two given dates
 */
public class Date_diff
{
    final static long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    /**
     * Calculates difference in days
     * @param date1 date 1 in string format
     * @param date2 date 2 in string format
     * @return date difference, -1 if date1 is larger than date2
     */
    public static int getDiff(String date1, String date2){
	try{
	    // parse date-time format                                                                    
	    SimpleDateFormat simple = new SimpleDateFormat();
	    simple.applyPattern("yyyy-MM-dd");
	    Date d1 = simple.parse(date1);
	    Date d2 = simple.parse(date2);
	    
	    if(d1.after(d2))
		return -1;
	
	    int diffInDays = (int) ((d2.getTime() - d1.getTime())/ DAY_IN_MILLIS );
	    return diffInDays;
	}catch(Exception e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
	}
	return -1;
    }
}
