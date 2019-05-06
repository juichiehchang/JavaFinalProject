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
     * @param price price of the room
     */
    public static void insertHotel(String hotel_name, int star, int one_adult, int two_adults, int four_adults, int price)
    {
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    
	    c.setAutoCommit(false);
	    System.out.println("Opened database successfully");
	    
	    stmt = c.createStatement();
	    String sql = "INSERT INTO HOTEL (HOTEL_NAME,STAR,ONE_ADULT,TWO_ADULTS,FOUR_ADULTS,PRICE) " +
		"VALUES ('" + hotel_name + "', " + star + ", " +
		one_adult + ", " + two_adults + ", " + four_adults + ", " + price + ");";
	    
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
    public static void main(String[] args){
	insertHotel("Johnny Walker", 5, 10, 20, 30, 10000);
    }
}
	
