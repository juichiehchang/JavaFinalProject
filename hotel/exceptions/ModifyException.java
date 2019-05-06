package exceptions;
/**
 * Exception class for order-modifying errors
 */
public class ModifyException extends Exception
{
    public static enum ExceptionTYPE { INVALID_ID, INVALID_AMOUNT, INVALID_DATE }

    private ExceptionTYPE type;

    public ModifyException(ExceptionTYPE type)
    {
	this.type = type;
    }

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
		
