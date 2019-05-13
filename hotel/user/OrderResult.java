package hotel.user;
/**
 * Class for storing order result
 */
public class OrderResult
{
    public int hotel_id;
    public int one_adult;
    public int two_adults;
    public int four_adults;
    public String in_date;
    public String out_date;
    public String uid;
    public int id;
    public int total_nights;
    public int total_price;

    public OrderResult(int hotel_id, int one_adult, int two_adults, int four_adults, String in_date, String out_date, String uid, int id, int total_nights, int total_price){
	this.hotel_id = hotel_id;
	this.one_adult = one_adult;
	this.two_adults = two_adults;
	this.four_adults = four_adults;
	this.in_date = in_date;
	this.out_date = out_date;
	this.uid = uid;
	this.id = id;
	this.total_nights = total_nights;
	this.total_price = total_price;
    }
}
