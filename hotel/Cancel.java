import java.sql.*;
import java.util.*;
import exceptions.*;

public class Cancel extends Check
{
    public Cancel(String uid, int id)
    {
	super(uid, id);
    }

    protected boolean validate(Connection c, Statement stmt)
    {
	try{
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
    
    private void delOrder() throws ModifyException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();

	    if(!validate(c, stmt)){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_ID);
	    }
		
	    String query = "SELECT * FROM ORDERS " +
		"WHERE UID = \'" + uid + "\' " + "AND ID = " + id + ";";
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    if(!rs.isBeforeFirst()){
		System.out.println("No such record");
		System.exit(0);
	    }
	    query = "DELETE FROM ORDERS " +
		"WHERE UID = \'" + uid + "\' " + "AND ID = " + id + ";";
	    stmt.executeUpdate(query);
	    query = "DELETE FROM RESV " +
		"WHERE UID = \'" + uid + "\' " + "AND ID = " + id + ";";
	    stmt.executeUpdate(query);
	    c.commit();
	    rs.close();
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

