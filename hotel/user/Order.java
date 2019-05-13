package hotel.user;

import java.sql.*;
import java.util.*;
import hotel.exceptions.*;
/**
 * Class for ordering hotel
 */
public class Order extends Query
{
    /**
     * @param hotel_id hotel id
     * @param uid user id
     */
    private int hotel_id;
    private String uid;

    /**
     * Constructor for initializing a order action
     * @param one_adult number of single rooms
     * @param two_adults number of double rooms
     * @param four_adults number of quad rooms
     * @param in_date check-in date
     * @param out_date check-out date
     * @param hotel_id hotel id
     * @param uid user id
     */
    public Order(int one_adult, int two_adults, int four_adults, String in_date, String out_date, int hotel_id, String uid)
    {
	super(one_adult, two_adults, four_adults, in_date, out_date);

	this.hotel_id = hotel_id;
	this.uid = uid;
    }

    /**
     * Gets a set of the reserved rooms in the specified hotel with the specified room type
     * @param r_type room type
     * @return set containing the room indexes of the reserved rooms
     */
    private Set<Integer> reservedRoomInHotel(String r_type)
    {
	Connection c = null;
	Statement stmt = null;
	Set<Integer> rooms = new HashSet<Integer>();
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    String query = "SELECT DISTINCT R_INDEX FROM RESV " +
		"WHERE R_TYPE = \'" + r_type + "\' " +
		"AND HOTEL_ID = \'" + hotel_id + "\' " +
		"AND (IN_DATE BETWEEN \'" + in_date + "\' AND \'" + out_date + "\' " +
		"OR OUT_DATE BETWEEN \'" + in_date + "\' AND \'" + out_date + "\') " +
		"ORDER BY R_INDEX";
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next())
		rooms.add(rs.getInt("R_INDEX"));

	    rs.close();
	    stmt.close();
	    c.close();
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return rooms;
    }

    /**
     * Gets the id of the order
     * @return id of the order
     */
    private int getId(){
	try{
	    Class.forName("org.sqlite.JDBC");
	    Connection c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    Statement stmt = c.createStatement();
	    String query = "SELECT ID FROM RESV ORDER BY ID DESC LIMIT 1;";
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    int id;
	    if(!rs.isBeforeFirst())
		id = 1;
	    else
		id = rs.getInt("ID") + 1;
	    rs.close();
	    stmt.close();
	    c.close();
	    return id;
	}
	catch(Exception e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return -1;
    }

    /**
     * Orders the hotel
     * @return OrderResult containing the result of the order action
     * @exception OrderException exception for invalid order
     */
    private OrderResult orderRoom() throws OrderException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    //Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    // get hotel info
	    String query = "SELECT * FROM HOTEL " +
		"WHERE HOTEL_ID = \'" + hotel_id + "\'";
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    // hotel doesn't exist
	    if(!rs.isBeforeFirst()){
		System.out.println("No such hotel");
		System.exit(0);
	    }
	    
	    StringBuilder message = new StringBuilder();
	    // check if there is enough room
	    Set<RoomType> typeSet = new HashSet<RoomType>();
	    int price = validate(rs, message, typeSet);
	    if(typeSet.size() == 0){
		//System.out.println(message);
		ArrayList<String> insertList = new ArrayList<String>();
		int id = getId();
		for(Pair<RoomType, Integer> roomPair : roomList){
		    String r_type = roomPair.getKey().name();
		    int amount = roomPair.getValue();
		    Set<Integer> reserved_rooms = reservedRoomInHotel(r_type);
		    Set<Integer> total_rooms = new HashSet<Integer>();
		    for(int i = 1; i <= rs.getInt(r_type); i++)
			total_rooms.add(i);
		    total_rooms.removeAll(reserved_rooms);
		    for(int r_index : total_rooms){
			if(amount <= 0)
			    break;
			amount--;
			// insert reservations
			insertList.add("INSERT INTO RESV " +
			    "(R_TYPE, HOTEL_ID, R_INDEX, IN_DATE, OUT_DATE, UID, ID) " + 
			    "VALUES (\'" + r_type + "\', \'" + hotel_id + "\', " +
				       r_index + ", \'" + in_date + "\', \'" + out_date +
				       "\', \'" + uid + "\', " + id + ");");
		    }
		}
		/*
		System.out.println("ID: " + id);
		System.out.println("UID: " + uid);
		System.out.print(message);
		System.out.println("IN_DATE: " + in_date);
		System.out.println("OUT_DATE: " + out_date);
		System.out.println("total nights: " + date_diff);
		*/

		rs.close();
		stmt.close();
		c.close();

		c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
		c.setAutoCommit(false);

		stmt = c.createStatement();
		for(String insert : insertList){
		    //System.out.println(insert);
		    stmt.executeUpdate(insert);
		}
		// insert order
		String order = "INSERT INTO ORDERS " +
		    "(HOTEL_ID, ONE_ADULT, TWO_ADULTS, FOUR_ADULTS, " +
		    "IN_DATE, OUT_DATE, UID, ID, TOTAL_PRICE)"+
		    " VALUES (\'" + hotel_id + "\', " +
		    roomList.get(0).getValue() + ", " +
		    roomList.get(1).getValue() + ", " + 
		    roomList.get(2).getValue() + ", \'" + in_date + "\', \'" + out_date +
		    "\', \'" + uid + "\', " + id + ", " + price + ");";
		//System.out.println(order);
		stmt.executeUpdate(order);
		stmt.close();
		c.commit();
		c.close();
		OrderResult result = new OrderResult(hotel_id, roomList.get(0).getValue(),
						     roomList.get(1).getValue(),
						     roomList.get(2).getValue(),
						     in_date, out_date, uid, id, date_diff, price);
		return result;
	    }
	    else{
		throw new OrderException(typeSet);
	    }
	}
	catch ( SQLException e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return null;
    }
    
    public static void main( String args[] ){
	Order order = new Order(1, 2, 3, "2019-01-01", "2019-01-10", 100, "asd");
	String errMessage = null;
	try{
	    OrderResult r = order.orderRoom();
	    System.out.println("UID: " + r.uid);
	    System.out.println("ID: " + r.id);
	    System.out.println("ONE_ADULT: " + r.one_adult);
	    System.out.println("TWO_ADULTS: " + r.two_adults);
	    System.out.println("FOUR_ADULTS: " + r.four_adults);
	    System.out.println("IN_DATE: " + r.in_date);
	    System.out.println("OUT_DATE: " + r.out_date);
	    System.out.println("TOTAL_NIGHTS: " + r.total_nights);
	    System.out.println("TOTAL_PRICE: " + r.total_price);
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null)
	    System.out.println(errMessage);
    }
}
