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
