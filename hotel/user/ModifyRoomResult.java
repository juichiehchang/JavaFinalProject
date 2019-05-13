package hotel.user;
/**
 * Class for storing modifyroom result
 */
public class ModifyRoomResult
{
    public int one_adult;
    public int two_adults;
    public int four_adults;

    /**
     * Construtor which also sets the results that get returned by a room-modifying action
     * @param one_adult number of single rooms
     * @param two_adults number of double rooms
     * @param four_adults number of quad rooms
     */
    public ModifyRoomResult(int one_adult, int two_adults, int four_adults){
	this.one_adult = one_adult;
	this.two_adults = two_adults;
	this.four_adults = four_adults;
    }
}
	  
