package hotel.parse;

import java.sql.*;
/**
 * Class with static method to insert hotel data into HOTEL table
 */
public class Insert
{
    /**
     * Insert hotel data into HOTEL table
     * @param hotel_name name of the hotel
     * @param star hotel rating
     * @param one_adult amount of single
     * @param two_adults amount of double
     * @param four_adults amount of quadruple
     * @param price price of one_adult room
     * @param price price of two_adults room
     * @param price price of four_adults room
     */
    public static void insertHotel(int hotel_id, int star, int one_adult, int two_adults, int four_adults, int one_price, int two_price, int four_price)
    {
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    String sql = "INSERT INTO HOTEL " + 
		"(HOTEL_ID,STAR,ONE_ADULT,TWO_ADULTS,FOUR_ADULTS,ONE_PRICE,TWO_PRICE,FOUR_PRICE) " +
		"VALUES ('" + hotel_id + "', " + star + ", " +
		one_adult + ", " + two_adults + ", " + four_adults + ", " +
		one_price + ", " + two_price + ", " + four_price + ");";
	    
	    stmt.executeUpdate(sql);

	    stmt.close();
	    c.commit();
	    c.close();
	}
	catch(Exception e){
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
	System.out.println("Records created successfully");
    }
}
