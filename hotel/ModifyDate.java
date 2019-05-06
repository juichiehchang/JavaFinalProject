import java.sql.*;
import java.util.*;
import java.text.*;
import exceptions.*;

public class ModifyDate extends Check
{
    private String in_date;
    private String out_date;
    
    public ModifyDate(String uid, int id, String in_date, String out_date)
    {
	super(uid, id);
	this.in_date = in_date;
	this.out_date = out_date;
    }

    private void setDate() throws ModifyException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();

	    ResultSet rs = getSet(c, stmt);
	    if(!rs.isBeforeFirst()){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_ID);
	    }
	    
	    SimpleDateFormat simple = new java.text.SimpleDateFormat();
	    simple.applyPattern("yyyy-MM-dd HH:mm:ss");
	    if(simple.parse(in_date).before(simple.parse(rs.getString("in_date"))) ||
	       simple.parse(out_date).after(simple.parse(rs.getString("out_date")))){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_DATE);
	    }

	    String modify = "UPDATE ORDERS " +
		"SET IN_DATE = \'" + in_date + "\', " + "OUT_DATE = \'" + out_date + "\' " +
		"WHERE UID = \'" + uid + "\' AND ID = " + id + ";";
	    //System.out.println(modify);
	    stmt.executeUpdate(modify);

	    modify = "UPDATE RESV " +
		"SET IN_DATE = \'" + in_date + "\', " + "OUT_DATE = \'" + out_date + "\' " +
		"WHERE UID = \'" + uid + "\' AND ID = " + id + ";";
	    //System.out.println(modify);
	    stmt.executeUpdate(modify);

	    c.commit();
	    rs.close();
	    stmt.close();
	    c.close();
	    System.out.println("修改成功，已將您的住宿日期變更為" + in_date + " - " + out_date);
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }

    public static void main( String args[] ){
	ModifyDate modify = new ModifyDate("asd", 1, "2019-01-03 10:20:05.123", "2019-01-08 10:20:05.123");
	String errMessage = null;
	try{
	    modify.setDate();
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}
