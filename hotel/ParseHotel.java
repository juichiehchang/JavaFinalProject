import java.sql.*;
/**
 * Class for parsing the hotel data in the HOTEL table
 */
public class ParseHotel{
    public static void parse(ResultSet rs){
	try{
	    while ( rs.next() ) {
		String hotel_name = rs.getString("hotel_name");
		int star  = rs.getInt("star");
		int r1 = rs.getInt("one_adult");
		int r2 = rs.getInt("two_adults");
		int r4 = rs.getInt("four_adults");
		int price = rs.getInt("price");
		System.out.println( "HOTEL_NAME = " + hotel_name );
		System.out.println( "STAR = " + star );
		System.out.println( "ONE_ADULT = " + r1 );
		System.out.println( "TWO_ADULTS = " + r2 );
		System.out.println( "FOUR_ADULTS = " + r4 );
		System.out.println( "PRICE = " + price );
		System.out.println();
	    }
	}
	catch(Exception e){
	    System.out.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
    }
    public static void main(String[] args){
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM HOTEL ORDER BY PRICE;");
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
