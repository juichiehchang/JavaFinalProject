package hotel.exceptions;
/**
 * Exception class for order-modifying errors
 */
public class ModifyException extends Exception
{
    /**
     * Exception types for modifyException: 
     */
    public static enum ExceptionTYPE {
	/** Invalid order id */
	INVALID_ID,
	/** Invalid room amount */
	INVALID_AMOUNT,
	/** Invalid check-in, check-out date */
	INVALID_DATE }

    private ExceptionTYPE type;

    /**
     * Constructor which also sets the exceptionType
     */
    public ModifyException(ExceptionTYPE type)
    {
	this.type = type;
    }

    /**
     * Returns the error message
     * @return String that contains the error message, according to the exceptionType
     */
    public String getMessage()
    {
	switch(this.type){
	case INVALID_ID:
	    return "Cancel/Modification fails: id doesn't exist.";
	case INVALID_AMOUNT:
	    return "Modification fails: attempt to increase number of rooms.";
	case INVALID_DATE:
	    return "Modification fails: cannot extend reservation date.";
	default:
	    return "TYPE_ERROR";
	}
    }
}
		
