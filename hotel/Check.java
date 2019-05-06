import java.sql.*;
import java.util.*;
import exceptions.*;
/**
 * Class for storing order checking data, with methods to 
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
    
    private void getOrder() throws CheckException
    {
	Connection c = null;
	Statement stmt = null;
	
	try{
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);

	    stmt = c.createStatement();
	    
	    ResultSet rs = getSet(c, stmt);
	    if(rs == null || !rs.isBeforeFirst()){
		throw new CheckException();
	    }
	    ParseOrder.parse(rs);
	    rs.close();
	    stmt.close();
	    c.close();
	}
	catch(Exception e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }

    public static void main( String args[] ){
	Check check = new Check("asd", 1);
	String errMessage = null;
	try{
	    check.getOrder();
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}
