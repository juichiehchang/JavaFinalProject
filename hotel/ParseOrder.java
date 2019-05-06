import java.sql.*;

public class ParseOrder{
    public static void parse(ResultSet rs){
	try{
	    while ( rs.next() ) {
		String hotel_name = rs.getString("hotel_name");
		int one_adult = rs.getInt("one_adult");
		int two_adults = rs.getInt("two_adults");
		int four_adults = rs.getInt("four_adults");
		String in_date = rs.getString("in_date");
		String out_date = rs.getString("out_date");
		String uid = rs.getString("uid");
		int id = rs.getInt("id");
		System.out.println( "HOTEL_NAME: " + hotel_name );
		System.out.println( "ONE_ADULT: " + one_adult );
		System.out.println( "TWO_ADULTS: " + two_adults );
		System.out.println( "FOUR_ADULTS: " + four_adults );
		System.out.println( "IN_DATE: " + in_date );
		System.out.println( "OUT_DATE: " + out_date );
		System.out.println( "UID: " + uid );
		System.out.println( "ID: " + id );
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
