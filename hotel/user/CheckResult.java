package hotel.user;
/**
 * Class for storing check result
 */
public class CheckResult
{
    public int hotel_id;
    public int one_adult;
    public int two_adults;
    public int four_adults;
    public String in_date;
    public String out_date;
    public int total_nights;
    public int total_price;

    /**
     * Constructor which also sets the results that get returned by a check action
     * @param hotel_id hotel id
     * @param one_adult number single rooms
     * @param two_adults number of double rooms
     * @param four_adults number of quad rooms
     * @param in_date check-in date
     * @param out_date check-out date
     * @param total_nights number of nights(checkin date - checkout date)
     * @param total_price total price to pay
     */
    public CheckResult(int hotel_id, int one_adult, int two_adults, int four_adults, String in_date, String out_date, int total_nights, int total_price){
	this.hotel_id = hotel_id;
	this.one_adult = one_adult;
	this.two_adults = two_adults;
	this.four_adults = four_adults;
	this.in_date = in_date;
	this.out_date = out_date;
	this.total_nights = total_nights;
	this.total_price = total_price;
    }
}
