package hotel.exceptions;

import java.util.*;
/**
 * Exception class for ordering errors
 */
public class OrderException extends Exception
{
    private Set<RoomType> typeSet;

    /**
     * Constructor which also sets the room types that caused the error
     */
    public OrderException(Set<RoomType> typeSet)
    {
	this.typeSet = typeSet;
    }

    /**
     * Constructor for invalid hotel id
     */
    public OrderException()
    {
	this.typeSet = null;
    }
    
    /**
     * Returns the error message
     * @return String that contains the error message, according to the typeSet
     */
    public String getMessage()
    {
	String message = "Fails";
	if(typeSet == null)
	    return "Invalid hotel id";
	
	for(RoomType type : typeSet)
	    message += type.name() + " insufficient\n";

	return message;
    }
}
		
