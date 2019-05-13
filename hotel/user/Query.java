package hotel.user;

import java.sql.*;
import java.util.*;
import hotel.exceptions.*;
/**
 * Class for storing query data, with methods to execute query
 */
public class Query
{
    /**
     * @param roomList list of pairs that stores roomtype and amount ex:(ONE_ADULT, 3)
     * @param in_date string that stores the check-in date
     * @param out_date string that stores the check-out date
     * @param date_diff date difference of in_date and out_date
     */
    protected List<Pair<RoomType, Integer>> roomList = new ArrayList<Pair<RoomType, Integer>>();

    protected String in_date;
    protected String out_date;
    protected int date_diff;
    /**
     * Contructor for initializing Query
     * @param one_adult amount of single
     * @param two_adults amount of double
     * @param four_adults amount of quad
     * @param in_date check-in date
     * @param out_date check-out date
     * @param date_diff difference between two dates
     */
    public Query(int one_adult, int two_adults, int four_adults, String in_date, String out_date)
    {
	roomList.add(new Pair<>(RoomType.ONE_ADULT, one_adult));
	roomList.add(new Pair<>(RoomType.TWO_ADULTS, two_adults));
	roomList.add(new Pair<>(RoomType.FOUR_ADULTS, four_adults));
	
	this.in_date = in_date;
	this.out_date = out_date;
	this.date_diff = Date_diff.getDiff(in_date, out_date);
    }
    /**
     * Methods that returns the amount of reserved rooms with the given room_type in the given hotel
     * returns -1 when fails
     * @param hotel_id target hotel
     * @param r_type room type
     * @return the amount the reserved rooms
     */
    private int reservedRoomInHotel(int hotel_id, String r_type)
    {
	Connection c = null;
	Statement stmt = null;
	int count = -1;
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    // get number of rooms that are occuiped during in-date and out-date
	    String query = "SELECT COUNT(DISTINCT R_INDEX) FROM RESV " +
		"WHERE R_TYPE = \'" + r_type + "\' " +
		"AND HOTEL_ID = \'" + hotel_id + "\' " +
		"AND (IN_DATE BETWEEN \'" + in_date + "\' AND \'" + out_date + "\' " +
		"OR OUT_DATE BETWEEN \'" + in_date + "\' AND \'" + out_date + "\')";
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    count = rs.getInt("COUNT(DISTINCT R_INDEX)");
	    rs.close();
	    stmt.close();
	    c.close();
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return count;
    }

    /**
     * Validates the hotel data with the query
     * @param rs resultSet containing the hotel data
     * @param message message containing the room types that match the query
     * @param typeSet a set of room types that fail to match the query
     * @return total price of the order
     */
    protected int validate(ResultSet rs, StringBuilder message, Set<RoomType> typeSet){
	int price = 0;
	try{
	    int hotel_id = rs.getInt("hotel_id");
	    message.append("Hotel_id: " + hotel_id + "\n"
			   + "Star: " + rs.getInt("star") + "\n");
	    for(Pair<RoomType, Integer> roomPair : roomList){
		String r_type = roomPair.getKey().name();
		// get price column ex: "ONE_ADULT" -> "ONE_PRICE"
		String r_ptype = r_type.split("_")[0] + "_PRICE";
		int amount = roomPair.getValue();
		int total_room = rs.getInt(r_type);
		//System.out.println("total_room = " + total_room);
		int reserved_room = reservedRoomInHotel(hotel_id, r_type);
		//System.out.println("reserved_room = " + reserved_room);
		int empty_room = total_room - reserved_room;
		if(reserved_room >= 0 && empty_room >= amount){
		    message.append(r_type + ": " + amount + "\n");
		    price += rs.getInt(r_ptype) * amount;
		}
		else{
		    typeSet.add(roomPair.getKey());
		}
	    }
	    price *= date_diff;
	    message.append("Total price: " + price + "\n");
	}catch(Exception e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	}
	return price;
    }

    /**
     * Prints out the hotels that match the query
     */
    private ArrayList<QueryResult> searchRoom(int offset)
    {
	Connection c = null;
	Statement stmt = null;
	ArrayList<QueryResult> resultList = new ArrayList<QueryResult>();
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotel/data/hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    // get hotel info, ordered by total price
	    String query = "SELECT * FROM HOTEL " +
		"ORDER BY ( " +
		"ONE_PRICE * " + roomList.get(0).getValue() +
		" + TWO_PRICE * " + roomList.get(1).getValue() + 
		" + FOUR_PRICE * " + roomList.get(2).getValue() + ")" +
		" LIMIT " + offset + ", 20;";

	    int one_adult = roomList.get(0).getValue();
	    int two_adults = roomList.get(1).getValue();
	    int four_adults = roomList.get(2).getValue();
	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    
	    while(rs.next()){
		StringBuilder message = new StringBuilder();
		Set<RoomType> typeSet = new HashSet<RoomType>();
		int price = validate(rs, message, typeSet);
		if(typeSet.size() == 0){
		    QueryResult result = new QueryResult(rs.getInt("hotel_id"), rs.getInt("star"),
							 one_adult, two_adults, four_adults,
							 in_date, out_date, price);
		    resultList.add(result);
		    //System.out.println(message);
		}
	    }
	    rs.close();
	    stmt.close();
	    c.close();
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
	return resultList;
    }
    
    public static void main( String args[] ){
	Query query = new Query(10, 2, 3, "2019-01-01", "2019-01-10");
	ArrayList<QueryResult> resultList = query.searchRoom(0);
	for(QueryResult r : resultList){
	    System.out.println("HOTEL_ID: " + r.hotel_id);
	    System.out.println("STAR: " + r.star);
	    System.out.println("ONE_ADULT: " + r.one_adult);
	    System.out.println("TWO_ADULTS: " + r.two_adults);
	    System.out.println("FOUR_ADULTS: " + r.four_adults);
	    System.out.println("TOTAL_PRICE: " + r.total_price);
	}
    }
}
