import java.sql.*;
/**
 * Class with static methods to create the tables to store hotel reservation data
 */
public class CreateTable
{
    /**
     * Create table for reservation data
     */
    public static void createRESV()
    {
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");

	    stmt = c.createStatement();
	    
	    String sql = "CREATE TABLE IF NOT EXISTS RESV " +
		"(R_TYPE         INT     NOT NULL, " +
		" HOTEL_NAME     TEXT    NOT NULL, " +
		" R_INDEX        INT     NOT NULL, " + 
		" IN_DATE        TEXT    NOT NULL, " +
		" OUT_DATE       TEXT    NOT NULL, " +
		" UID            TEXT    NOT NULL, " +  
		" ID             INT     NOT NULL)";
		
	    stmt.executeUpdate(sql);
	    stmt.close();
	    c.close();
	}
	catch(Exception e){
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
	System.out.println("Created RESV table successfully");
    }
    /**
     * Create table for hotel data
     */
    public static void createHOTEL(){
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");

	    stmt = c.createStatement();
	    
	    String sql = "CREATE TABLE IF NOT EXISTS HOTEL " +
		"(HOTEL_NAME     TEXT    NOT NULL, " +
		" STAR           INT     NOT NULL, " + 
		" ONE_ADULT      INT     NOT NULL, " +
		" TWO_ADULTS     INT     NOT NULL, " +
		" FOUR_ADULTS    INT     NOT NULL, " +  
		" PRICE          INT     NOT NULL, " + 
		" PRIMARY KEY (HOTEL_NAME))";
		
	    stmt.executeUpdate(sql);
	    stmt.close();
	    c.close();
	}
	catch(Exception e){
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
	System.out.println("Created HOTEL table successfully");
    }
    /**
     * Create table for order data
     */
    public static void createORDER(){
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    
	    stmt = c.createStatement();
	    
	    String sql = "CREATE TABLE IF NOT EXISTS ORDERS " +
		"(HOTEL_NAME     TEXT    NOT NULL, " +
		" ONE_ADULT      INT     NOT NULL, " +
		" TWO_ADULTS     INT     NOT NULL, " +
		" FOUR_ADULTS    INT     NOT NULL, " +  
		" IN_DATE        TEXT    NOT NULL, " +
		" OUT_DATE       TEXT    NOT NULL, " +
		" UID            TEXT    NOT NULL, " +
		" ID             INT     NOT NULL)";
	    
	    stmt.executeUpdate(sql);
	    stmt.close();
	    c.close();
	}
	catch(Exception e){
	    System.err.println(e.getClass().getName() + ": " + e.getMessage());
	    System.exit(0);
	}
	System.out.println("Created ORDER table successfully");
    }
    /**
     * Create reservation, hotel and order tables
     */
    public static void main(String[] args){
	createRESV();
	createHOTEL();
	createORDER();
    }
}
