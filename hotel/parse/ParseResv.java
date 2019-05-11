package hotel.parse;

import java.sql.*;
/**
 * Class for parsing the reservations in the RESV table
 */
public class ParseResv{
    public static void parse(ResultSet rs){
	try{
	    while ( rs.next() ) {
		String r_type = rs.getString("r_type");
		int hotel_id = rs.getInt("hotel_id");
		int r_index = rs.getInt("r_index");
		String in_date = rs.getString("in_date");
		String out_date = rs.getString("out_date");
		String uid = rs.getString("uid");
		int id = rs.getInt("id");
		System.out.println( "R_TYPE = " + r_type );
		System.out.println( "HOTEL_ID = " + hotel_id );
		System.out.println( "R_INDEX = " + r_index );
		System.out.println( "IN_DATE = " + in_date );
		System.out.println( "OUT_DATE = " + out_date );
		System.out.println( "UID = " + uid );
		System.out.println( "ID = " + id );
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
            c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
            c.setAutoCommit(false);

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM RESV;");
            ParseResv.parse(rs);
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
