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
	return "您輸入的身份識別碼/定位代號有誤，請重新輸入";
    }
}
		
