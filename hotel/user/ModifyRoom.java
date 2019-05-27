package hotel.user;

import java.sql.*;
import java.util.*;
import hotel.exceptions.*;
/**
 * Class for modifying the room amounts of the order
 */
public class ModifyRoom extends Check
{
    /**
     * @param roomList list of pairs that stores roomtype and amount ex:(ONE_ADULT, 3)
     */
    protected List<Pair<String, Integer>> roomList = new ArrayList<Pair<String, Integer>>();

    /**
     * Constructor for initializing room-modifying action
     * @param uid user id
     * @param id order id
     * @param one_adult number of single rooms
     * @param two_adults number of double rooms
     * @param four_adults number of quad rooms
     */
    public ModifyRoom(String uid, int id, int one_adult, int two_adults, int four_adults)
    {
	super(uid, id);
	roomList.add(new Pair<>("ONE_ADULT", one_adult));
	roomList.add(new Pair<>("TWO_ADULTS", two_adults));
	roomList.add(new Pair<>("FOUR_ADULTS", four_adults));
    }

    /**
     * Modifies the room amounts of the order
     * @return ModifyRoomResult information about the modified number of rooms
     * @exception ModifyException exception for invalid nuumber of rooms
     */
    public ModifyRoomResult setRoom() throws ModifyException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    //Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    
	    // check if order exists
	    ResultSet rs = getSet(c, stmt);
	    if(!rs.isBeforeFirst()){
		throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_ID);
	    }
	    // get hotel id
	    int hotel_id = rs.getInt("hotel_id");
	    rs.close();

	    // get hotel information
	    String query = "SELECT * FROM HOTEL WHERE HOTEL_ID = " + hotel_id + ";";
	    rs = stmt.executeQuery(query);
	    // price dictionary for storing the price of each room type
	    Map<String, Integer> price_dict = new HashMap<String, Integer>();
	    price_dict.put("ONE_ADULT", rs.getInt("ONE_PRICE"));
	    price_dict.put("TWO_ADULTS", rs.getInt("TWO_PRICE"));
	    price_dict.put("FOUR_ADULTS", rs.getInt("FOUR_PRICE"));
	    rs.close();

	    rs = getSet(c, stmt);
	    int total_price = 0;
	    // check room amount
	    ArrayList<Pair<String, Integer>> delList = new ArrayList<Pair<String, Integer>>();
	    for(Pair<String, Integer> roomPair : roomList){
		String r_type = roomPair.getKey();
		int amount = roomPair.getValue();
		// tries to add room
		if(amount > rs.getInt(r_type)){
		    throw new ModifyException(ModifyException.ExceptionTYPE.INVALID_AMOUNT);
		}
		delList.add(new Pair<>(r_type, rs.getInt(r_type) - amount));
		// calculate new total_price ex: "ONE_ADULT" -> "ONE_PRICE"
		total_price += amount * price_dict.get(r_type);
	    }
	    total_price *= Date_diff.getDiff(rs.getString("in_date"), rs.getString("out_date"));
	    // update order
	    String modify = "UPDATE ORDERS " +
		"SET ONE_ADULT = " + roomList.get(0).getValue() +
		", TWO_ADULTS = " + roomList.get(1).getValue() +
		", FOUR_ADULTS = " + roomList.get(2).getValue() +
		", TOTAL_PRICE = " + total_price + 
		" WHERE UID = \'" + uid + "\' AND ID = " + id + ";";
	    stmt.executeUpdate(modify);

	    // delete abundant room reservations
	    for(Pair<String, Integer> roomPair : delList){
		modify = "DELETE FROM RESV " +
		    "WHERE UID = \'" + uid + "\' AND ID = " + id +
		    " AND R_TYPE = \'" + roomPair.getKey() + "\' AND R_INDEX IN (" +
		    " SELECT R_INDEX FROM RESV WHERE UID = \'" + uid + "\' AND ID = " + id +
		    " AND R_TYPE = \'" + roomPair.getKey() + "\' ORDER BY R_INDEX DESC" + 
		    " LIMIT " + roomPair.getValue() + ");";
		//System.out.println(modify);
		stmt.executeUpdate(modify);
	    }

	    c.commit();
	    
	    ModifyRoomResult result = new ModifyRoomResult(roomList.get(0).getValue(),
							   roomList.get(1).getValue(), 
							   roomList.get(2).getValue());
	    return result;
	} catch ( SQLException e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	} finally {
	    rs.close();
	    stmt.close();
	    c.close();
	}
	return null;
    }

    public static void main( String args[] ){
	ModifyRoom modify = new ModifyRoom("asd", 1, 0, 2, 3);
	String errMessage = null;
	try{
	    ModifyRoomResult r = modify.setRoom();
	    System.out.println("Modification succeed. Your room type has been changed to: ");
	    System.out.println("ONE_ADULT: " + r.one_adult);
	    System.out.println("TWO_ADULTS: " + r.two_adults);
	    System.out.println("FOUR_ADULTS: " + r.four_adults);
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null){
	    System.out.println(errMessage);
	}
    }
}
