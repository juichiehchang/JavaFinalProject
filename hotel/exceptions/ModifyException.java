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
	    return "退訂/修改失敗，此訂位代號不存在";
	case INVALID_AMOUNT:
	    return "修改失敗，修改數量超過訂房數量";
	case INVALID_DATE:
	    return "修改失敗，無法延長訂房時間";
	default:
	    return "TYPE_ERROR";
	}
    }
}
		
