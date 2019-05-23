package hotel.exceptions;
/**
 * Exception class for order-checking error
 */
public class CheckException extends Exception
{
    /**
     * Constructor
     */
    public CheckException()
    {
    }
    /**
     * Returns the error message, invalid id or uid
     * @return String that contains the error message
     */
    public String getMessage()
    {
	return "Your uid or id is incorret. Please input again.";
    }
}
		
