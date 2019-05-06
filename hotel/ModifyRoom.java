import java.sql.*;
import java.util.*;
import exceptions.*;
/**
 * Class for modifying the room amounts of the order
 */
public class ModifyRoom extends Check
{
    /**
     * @param roomList list of pairs that stores roomtype and amount ex:(ONE_ADULT, 3)
     */
    protected List<Pair<String, Integer>> roomList = new ArrayList<Pair<String, Integer>>();
    
    public ModifyRoom(String uid, int id, int one_adult, int two_adults, int four_adults)
    {
	super(uid, id);
	roomList.add(new Pair<>("ONE_ADULT", one_adult));
	roomList.add(new Pair<>("TWO_ADULTS", two_adults));
	roomList.add(new Pair<>("FOUR_ADULTS", four_adults));
    }

    /**
     * Modifies the room amounts of the order
     * @exception ModifyException exception for invalid room amounts
     */
    private void setRoom() throws ModifyException
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

	    ArrayList<Pair<String, Integer>> delList = new ArrayList<Pair<String, Integer>>();
	    for(Pair<String, Integer> roomPair : roomList){
		String r_type = roomPair.getKey();
		int amount = roomPair.getValue();
		if(amount > rs.getInt(r_type)){
		    rs.close();
		    stmt.close();
		    c.close();
		    throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_AMOUNT);
		}
		delList.add(new Pair<>(r_type, rs.getInt(r_type) - amount));
	    }

	    String modify = "UPDATE ORDERS " +
		"SET ONE_ADULT = " + roomList.get(0).getValue() +
		", TWO_ADULTS = " + roomList.get(1).getValue() +
		", FOUR_ADULTS = " + roomList.get(2).getValue() +
		" WHERE UID = \'" + uid + "\' AND ID = " + id + ";";
	    stmt.executeUpdate(modify);
	    
	    for(Pair<String, Integer> roomPair : delList){
		modify = "DELETE FROM RESV " +
		    "WHERE UID = \'" + uid + "\' AND ID = " + id +
		    " AND R_TYPE = \'" + roomPair.getKey() + "\' AND R_INDEX IN (" +
		    " SELECT R_INDEX FROM RESV WHERE UID = \'" + uid + "\' AND ID = " + id +
		    " AND R_TYPE = \'" + roomPair.getKey() + "\' ORDER BY \'R_INDEX\' " + 
		    "LIMIT " + roomPair.getValue() + ");";
		//System.out.println(modify);
		stmt.executeUpdate(modify);
	    }

	    c.commit();
	    rs.close();
	    stmt.close();
	    c.close();
	    System.out.println("修改成功，已將您的訂房數量變更為: ");
	    System.out.println("ONE_ADULT: " + roomList.get(0).getValue());
	    System.out.println("TWO_ADULTS: " + roomList.get(1).getValue());
	    System.out.println("FOUR_ADULTS: " + roomList.get(2).getValue());

	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }

    public static void main( String args[] ){
	ModifyRoom modify = new ModifyRoom("asd", 1, 8, 2, 3);
	String errMessage = null;
	try{
	    modify.setRoom();
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}

