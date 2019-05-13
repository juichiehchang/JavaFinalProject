package hotel.user;

import java.sql.*;
import java.util.*;
import hotel.exceptions.*;
import hotel.parse.*;
/**
 * Class for checking order
 */
public class Check
{
    /**
     * @param uid user id
     * @param id order id
     */
    protected String uid;
    protected int id;

    public Check(String uid, int id)
    {
	this.uid = uid;
	this.id = id;
    }
    
    /**
     * Returns the order with the specified uid and id, returns null if fails
     * @param c connection to the database
     * @param stmt statement for executing the query
     * @return resultSet containing the order
     */
    protected ResultSet getSet(Connection c, Statement stmt)
    {
	try {
	    String query = "SELECT * FROM ORDERS " +
		"WHERE UID = \'" + uid + "\' " + "AND ID = " + id + ";";
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    
	    return rs;
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return null;
    }

    /**
     * Prints out the order
     * @exception CheckException exception for invalid order id
     */
    private CheckResult getOrder() throws CheckException
    {
	Connection c = null;
	Statement stmt = null;
	
	try{
	    //Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);

	    stmt = c.createStatement();
	    
	    ResultSet rs = getSet(c, stmt);
	    // order doesn't exist
	    if(rs == null || !rs.isBeforeFirst()){
		throw new CheckException();
	    }
	    // prepare check result
	    String in_date = rs.getString("in_date");
	    String out_date = rs.getString("out_date");
	    CheckResult result = new CheckResult(rs.getInt("hotel_id"), rs.getInt("one_adult"),
						 rs.getInt("two_adults"), rs.getInt("four_adults"),
						 in_date, out_date,
						 Date_diff.getDiff(in_date, out_date),
						 rs.getInt("total_price"));
	    // print out the order
	    //ParseOrder.parse(rs);
	    rs.close();
	    stmt.close();
	    c.close();
	    return result;
	}
	catch(SQLException e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return null;
    }

    public static void main( String args[] ){
	Check check = new Check("asd", 1);
	String errMessage = null;
	try{
	    CheckResult r = check.getOrder();
	    System.out.println( "HOTEL_ID: " + r.hotel_id );
	    System.out.println( "ONE_ADULT: " + r.one_adult );
	    System.out.println( "TWO_ADULTS: " + r.two_adults );
	    System.out.println( "FOUR_ADULTS: " + r.four_adults );
	    System.out.println( "IN_DATE: " + r.in_date );
	    System.out.println( "OUT_DATE: " + r.out_date );
	    System.out.println( "TOTAL_NIGHTS: " + r.total_nights );
	    System.out.println( "TOTAL_PRICE: " + r.total_price );
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}
