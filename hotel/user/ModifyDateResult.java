package hotel.user;
/**
 * Class for storing modifydate result
 */
public class ModifyDateResult
{
    public String in_date;
    public String out_date;

    /**
     * Constructor which also sets the results that get returned by a date-modifying action
     * @param in_date check-in date
     * @param out_date check-out date
     */
    public ModifyDateResult(String in_date, String out_date){
	this.in_date = in_date;
	this.out_date = out_date;
    }
}
