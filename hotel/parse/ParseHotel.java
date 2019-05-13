package hotel.parse;

import java.sql.*;
/**
 * Class for parsing the hotel data in the HOTEL table
 */
public class ParseHotel{
    /**
     * Parses the hotel information from the result set
     * @param rs result set that contains the hotel data
     */
    public static void parse(ResultSet rs){
	try{
	    while ( rs.next() ) {
		int hotel_id = rs.getInt("hotel_id");
		int star  = rs.getInt("star");
		int r1 = rs.getInt("one_adult");
		int r2 = rs.getInt("two_adults");
		int r4 = rs.getInt("four_adults");
		int p1 = rs.getInt("one_price");
		int p2 = rs.getInt("two_price");
		int p4 = rs.getInt("four_price");
		System.out.println( "HOTEL_ID = " + hotel_id );
		System.out.println( "STAR = " + star );
		System.out.println( "ONE_ADULT = " + r1 );
		System.out.println( "TWO_ADULTS = " + r2 );
		System.out.println( "FOUR_ADULTS = " + r4 );
		System.out.println( "ONE_PRICE = " + p1 );
		System.out.println( "TWO_PRICE = " + p2 );
		System.out.println( "FOUR_PRICE = " + p4 );
		System.out.println();
	    }
	}
	catch(Exception e){
	    System.out.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
    }
    /** 
     * Opens the database and parses all the hotel data in it
     */
    public static void main(String[] args){
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM HOTEL;");
            ParseHotel.parse(rs);
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
