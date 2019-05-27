package hotel.user;

import java.sql.*;
import java.util.*;
import java.text.*;
import hotel.exceptions.*;
/**
 * Class for modifying the check-in and check-out dates of the specified order
 */
public class ModifyDate extends Check
{
    /**
     * @param in_date check-in date
     * @param out_date chek-out date
     */
    private String in_date;
    private String out_date;

    /**
     * Constructor for initializing date-modifying action
     * @param uid user id
     * @param id order id
     * @param in_date check-in date
     * @param out_date check-out date
     */
    public ModifyDate(String uid, int id, String in_date, String out_date)
    {
	super(uid, id);
	this.in_date = in_date;
	this.out_date = out_date;
    }

    /**
     * Modifies the check-in and check-out dates of the order
     * @return ModifyDateResult information about the modified in_date and out_date
     * @exception ModifyException exception for invalid check-in or check-out date
     */
    public ModifyDateResult setDate() throws ModifyException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    //Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();

	    ResultSet rs = getSet(c, stmt);
	    // order doesn't exist
	    if(!rs.isBeforeFirst()){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_ID);
	    }

	    // parse date-time format
	    SimpleDateFormat simple = new java.text.SimpleDateFormat();
	    simple.applyPattern("yyyy-MM-dd");
	    
	    // previous in_dat and out_date
	    String pre_in = rs.getString("in_date");
	    String pre_out = rs.getString("out_date");
	    
	    // tries to extend dates
	    if(simple.parse(in_date).after(simple.parse(out_date)) ||
	       simple.parse(in_date).before(simple.parse(pre_in)) ||
	       simple.parse(out_date).after(simple.parse(pre_out))){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_DATE);
	    }
	    
	    // get new total price
	    int total_price = rs.getInt("total_price") *
		Date_diff.getDiff(in_date, out_date) / Date_diff.getDiff(pre_in, pre_out);
	    
	    String modify = "UPDATE ORDERS " +
		"SET IN_DATE = \'" + in_date + "\', " + "OUT_DATE = \'" + out_date + "\' , " +
		"TOTAL_PRICE = " + total_price + " " + 
		"WHERE UID = \'" + uid + "\' AND ID = " + id + ";";
	    //System.out.println(modify);
	    stmt.executeUpdate(modify);

	    modify = "UPDATE RESV " +
		"SET IN_DATE = \'" + in_date + "\', " + "OUT_DATE = \'" + out_date + "\' " +
		"WHERE UID = \'" + uid + "\' AND ID = " + id + ";";
	    //System.out.println(modify);
	    stmt.executeUpdate(modify);

	    c.commit();

	    ModifyDateResult result = new ModifyDateResult(in_date, out_date);
	    return result;

	} catch ( SQLException e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	} catch ( ParseException e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	} finally {
	    rs.close();
	    stmt.close();
	    c.close();
	}
	return null;
    }

    public static void main( String args[] ){
	ModifyDate modify = new ModifyDate("asd", 1, "2019-01-03", "2019-01-08");
	String errMessage = null;
	try{
	    ModifyDateResult r = modify.setDate();
	    System.out.println("Modification succeed. Your booking date has been changed to " +
			       r.in_date + " - " + r.out_date);
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}
