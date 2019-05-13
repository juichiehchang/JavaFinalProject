package hotel.user;
/**
 * Class for storing query result
 */
public class QueryResult
{
    public int hotel_id;
    public int star;
    public int one_adult;
    public int two_adults;
    public int four_adults;
    public String in_date;
    public String out_date;
    public int total_price;

    public QueryResult(int hotel_id, int star, int one_adult, int two_adults, int four_adults, String in_date, String out_date, int total_price){
	this.hotel_id = hotel_id;
	this.star = star;
	this.one_adult = one_adult;
	this.two_adults = two_adults;
	this.four_adults = four_adults;
	this.in_date = in_date;
	this.out_date = out_date;
	this.total_price = total_price;
    }
}
