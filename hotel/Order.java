import java.sql.*;
import java.util.*;
import exceptions.RoomType;
import exceptions.Pair;
import exceptions.*;

public class Order extends Query
{
    private String hotel_name;
    private String uid;
    
    public Order(int one_adult, int two_adults, int four_adults, String in_date, String out_date, String hotel_name, String uid)
    {
	super(one_adult, two_adults, four_adults, in_date, out_date);

	this.hotel_name = hotel_name;
	this.uid = uid;
    }
    
    private Set<Integer> reservedRoomInHotel(String r_type)
    {
	Connection c = null;
	Statement stmt = null;
	Set<Integer> rooms = new HashSet<Integer>();
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    String query = "SELECT DISTINCT R_INDEX FROM RESV " +
		"WHERE R_TYPE = \'" + r_type + "\' " +
		"AND HOTEL_NAME = \'" + hotel_name + "\' " +
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

    private int getId(){
	try{
	    Class.forName("org.sqlite.JDBC");
	    Connection c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
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
    
    private void orderRoom() throws OrderException
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    String query = "SELECT * FROM HOTEL " +
		"WHERE HOTEL_NAME = \'" + hotel_name + "\'";

	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    if(!rs.isBeforeFirst()){
		System.out.println("No such hotel");
		System.exit(0);
	    }
	    
	    StringBuilder message = new StringBuilder();
	    Set<RoomType> typeSet = validate(rs, message);
	    if(typeSet.size() == 0){
		System.out.println(message);
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
			insertList.add("INSERT INTO RESV " +
			    "(R_TYPE, HOTEL_NAME, R_INDEX, IN_DATE, OUT_DATE, UID, ID) " + 
			    "VALUES (\'" + r_type + "\', \'" + hotel_name + "\', " +
				       r_index + ", \'" + in_date + "\', \'" + out_date +
				       "\', \'" + uid + "\', " + id + ");");
		    }
		}
		System.out.println("ID: " + id);
		System.out.println("UID: " + uid);
		System.out.print(message);
		System.out.println("IN_DATE: " + in_date);
		System.out.println("OUT_DATE: " + out_date);
		
		rs.close();
		stmt.close();
		c.close();

		c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
		c.setAutoCommit(false);
		System.out.println("YEAH BOI");
		stmt = c.createStatement();
		for(String insert : insertList){
		    System.out.println(insert);
		    stmt.executeUpdate(insert);
		}
		String order = "INSERT INTO ORDERS " +
		    "(HOTEL_NAME, ONE_ADULT, TWO_ADULTS, FOUR_ADULTS, IN_DATE, OUT_DATE, UID, ID) " +
		    "VALUES (\'" + hotel_name + "\', " + roomList.get(0).getValue() + ", " +
		    roomList.get(1).getValue() + ", " + 
		    roomList.get(2).getValue() + ", \'" + in_date + "\', \'" + out_date +
		    "\', \'" + uid + "\', " + id + ");";
		System.out.println(order);
		stmt.executeUpdate(order);
		stmt.close();
		c.commit();
		c.close();
	    }
	    else{
		throw new OrderException(typeSet);
	    }
	}
	catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }
    
    public static void main( String args[] ){
	Order order = new Order(10, 2, 3, "2019-01-01 10:20:05.123", "2019-01-10 10:20:05.123", "Johnny Walker", "asd");
	String errMessage = null;
	try{
	    order.orderRoom();
	}
	catch(Exception e){
	    errMessage = e.getMessage();
	}
	if(errMessage != null)
	    System.out.println(errMessage);
    }
}
