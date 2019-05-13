package hotel.parse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.*;
/**
 * Class for parsing json file data to into the database
 */
public class ParseJson
{
    /**
     * Parses the json file and store the data into the datavase
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args)
    {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
         
        try (FileReader reader = new FileReader("hotel/data/hotelList.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
 
            JSONArray hotelList = (JSONArray) obj;
            //System.out.println(hotelList);
             
            //Iterate over hotelList array
            hotelList.forEach( hotel -> parseHotelObject( (JSONObject) hotel ) );
 
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    /**
     * Parses single hotel information and insert it into the database
     * @param hotel json object that contains the hotel information
     */
    @SuppressWarnings("unchecked")
    private static void parseHotelObject(JSONObject hotel)
    {
	// Get hotel id
	int hotel_id = ((Long) hotel.get("HotelID")).intValue();
	System.out.println("Hotel_id: " + hotel_id);
	
	// Get hotel star
	int star = ((Long) hotel.get("HotelStar")).intValue();
	System.out.println("Star: " + star);
	
	// Get locality
	String locality = (String) hotel.get("Locality");
	//System.out.println("Locality: " + locality);
	
	// Get street-address
	String address = (String) hotel.get("Street-Address");
	//System.out.println("Address: " + address);
	
	JSONArray roomList = (JSONArray) hotel.get("Rooms");
	
	//Iterate over roomList array
	ArrayList<Integer> priceList = new ArrayList<Integer>();
	ArrayList<Integer> amountList = new ArrayList<Integer>();
	roomList.forEach( room -> parseRoomObject( (JSONObject) room, priceList, amountList ) );

	// Insert into the hotel table
	Insert.insertHotel(hotel_id, star, amountList.get(0), amountList.get(1), amountList.get(2),
			   priceList.get(0), priceList.get(1), priceList.get(2));
    }

    /**
     * Parses the room information and store it into arrayLists
     * @param room json object that contains the room information
     * @param priceList arrayList that stores the price of each room type
     * @param amountList arrayList that stores the amount of each room type
     */
    private static void parseRoomObject(JSONObject room, ArrayList<Integer> priceList, ArrayList<Integer> amountList){
	// Get room type
	String roomType = (String) room.get("RoomType");
	System.out.println("RoomType: " + roomType);
	
	// Get room price
	int price = ((Long) room.get("RoomPrice")).intValue();
	priceList.add(price);
	System.out.println("Price: " + price);
	
	// Get room amount
	int amount = ((Long) room.get("Number")).intValue();
	amountList.add(amount);
	System.out.println("Number: " + amount);
    }
}
