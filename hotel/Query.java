import java.sql.*;
import java.util.*;
import exceptions.*;
/**
 * Class for storing query data, with methods to execute query
 */
public class Query
{
    /**
     * @param roomList list of pairs that stores roomtype and amount ex:(ONE_ADULT, 3)
     * @param in_date string that stores the check-in date
     * @param out_date string that stores the check-out date
     */
    protected List<Pair<RoomType, Integer>> roomList = new ArrayList<Pair<RoomType, Integer>>();

    protected String in_date;
    protected String out_date;

    /**
     * Contructor for initializing Query
     * @param one_adult amount of single
     * @param two_adults amount of double
     * @param four_adults amount of quad
     * @param in_date check-in date
     * @param out_date check-out date
     */
    public Query(int one_adult, int two_adults, int four_adults, String in_date, String out_date)
    {
	roomList.add(new Pair<>(RoomType.ONE_ADULT, one_adult));
	roomList.add(new Pair<>(RoomType.TWO_ADULTS, two_adults));
	roomList.add(new Pair<>(RoomType.FOUR_ADULTS, four_adults));
	
	this.in_date = in_date;
	this.out_date = out_date;
    }
    /**
     * Methods that returns the amount of reserved rooms with the given room_type in the given hotel
     * returns -1 when fails
     * @param hotel_name target hotel
     * @param r_type room type
     * @return the amount the reserved rooms
     */
    private int reservedRoomInHotel(String hotel_name, String r_type)
    {
	Connection c = null;
	Statement stmt = null;
	int count = -1;
	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    String query = "SELECT COUNT(DISTINCT R_INDEX) FROM RESV " +
		"WHERE R_TYPE = \'" + r_type + "\' " +
		"AND HOTEL_NAME = \'" + hotel_name + "\' " +
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

    protected Set<RoomType> validate(ResultSet rs, StringBuilder message){
	
	Set<RoomType> typeSet = new HashSet<RoomType>();
	
	try{
	    String hotel_name = rs.getString("hotel_name");
	    message.append("Hotel name: " + hotel_name + "\n"
			   + "Star: " + rs.getInt("star") + "\n");
	    
	    for(Pair<RoomType, Integer> roomPair : roomList){
		String r_type = roomPair.getKey().name();
		int amount = roomPair.getValue();
		int total_room = rs.getInt(r_type);
		//System.out.println("total_room = " + total_room);
		int reserved_room = reservedRoomInHotel(hotel_name, r_type);
		//System.out.println("reserved_room = " + reserved_room);
		int empty_room = total_room - reserved_room;
		if(reserved_room >= 0 && empty_room >= amount){
		    message.append(r_type + ": " + amount + "\n");
		}
		else{
		    typeSet.add(roomPair.getKey());
		}
	    }
	}catch(Exception e){
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	}
	return typeSet;
    }
    
    private void searchRoom()
    {
	Connection c = null;
	Statement stmt = null;

	try {
	    Class.forName("org.sqlite.JDBC");
	    c = DriverManager.getConnection("jdbc:sqlite:hotelreservation.db");
	    c.setAutoCommit(false);
	    
	    stmt = c.createStatement();
	    String query = "SELECT * FROM HOTEL " +
		"ORDER BY PRICE";

	    //System.out.println(query);
	    ResultSet rs = stmt.executeQuery(query);
	    while(rs.next()){
		StringBuilder message = new StringBuilder();
		if(validate(rs, message).size() == 0){
		    System.out.println(message);
		}
	    }
	    rs.close();
	    stmt.close();
	    c.close();
	} catch ( Exception e ) {
	    System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    System.exit(0);
	}
    }
    
    public static void main( String args[] ){
	Query query = new Query(10, 2, 3, "2019-01-01 10:20:05.123", "2019-01-10 10:20:05.123");
	query.searchRoom();
    }
}
