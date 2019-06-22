package hotel.parse;

import java.sql.*;
/**
 * Class for parsing the orders in the ORDERS table
 */
public class ParseOrder{
    /**
     * Parses the order from the result set
     * @param rs result set that contains the order information
     */
    public static void parse(ResultSet rs){
	try{
	    while ( rs.next() ) {
		int hotel_id = rs.getInt("hotel_id");
		int one_adult = rs.getInt("one_adult");
		int two_adults = rs.getInt("two_adults");
		int four_adults = rs.getInt("four_adults");
		String in_date = rs.getString("in_date");
		String out_date = rs.getString("out_date");
		String uid = rs.getString("uid");
		int id = rs.getInt("id");
		int total_price = rs.getInt("total_price");
		System.out.println( "HOTEL_ID: " + hotel_id );
		System.out.println( "ONE_ADULT: " + one_adult );
		System.out.println( "TWO_ADULTS: " + two_adults );
		System.out.println( "FOUR_ADULTS: " + four_adults );
		System.out.println( "IN_DATE: " + in_date );
		System.out.println( "OUT_DATE: " + out_date );
		System.out.println( "UID: " + uid );
		System.out.println( "ID: " + id );
		System.out.println( "TOTAL_PRICE: " + total_price );
	    }
	}
	catch(Exception e){
	    System.out.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
    }
    /**
     * Opens the database and parses all the orders in it
     */
    public static void main(String[] args){
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ORDERS;");
            ParseOrder.parse(rs);
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
