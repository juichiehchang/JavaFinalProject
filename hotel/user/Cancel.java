package hotel.user;

import java.sql.*;
import java.util.*;
import hotel.exceptions.*;
/**
 * Class for cancelling order
 */
public class Cancel extends Check
{
    public Cancel(String uid, int id)
    {
	super(uid, id);
    }

    /**
     * Checks if the order exists
     * @param c connection to the database
     * @param stmt for executing the query
     * @return true if the order exists, else false
     */
    private boolean validate(Connection c, Statement stmt)
    {
	try{
	    // order doesn't exist
	    if(!(super.getSet(c, stmt).isBeforeFirst())){
		return false;
	    }
	    return true;
	}
	catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return false;
    }

    /**
     * Deletes the order
     * @exception ModifyException exception for invalid order id
     */
    private void delOrder() throws ModifyException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    
	    // order doesn't exist
	    if(!validate(c, stmt)){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_ID);
	    }
	    
	    // delete order
	    String query = "DELETE FROM ORDERS " +
		"WHERE UID = \'" + uid + "\' " + "AND ID = " + id + ";";
	    stmt.executeUpdate(query);

	    // delete reservations
	    query = "DELETE FROM RESV " +
		"WHERE UID = \'" + uid + "\' " + "AND ID = " + id + ";";
	    stmt.executeUpdate(query);
	    
	    c.commit();
	    stmt.close();
	    c.close();
	    System.out.println("退訂成功，已取消您的訂房紀錄");
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }

    public static void main( String args[] ){
	Cancel cancel = new Cancel("asd", 1);
	String errMessage = null;
	try{
	    cancel.delOrder();
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}
