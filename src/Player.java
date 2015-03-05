import java.util.*;

public class Player {

	private int order;
	private int money;
	private int turn; 
	private Card holdingCard;
	private ArrayList<Commodity> shareHolds;
	
	public Player(int order, int money)
	{
		this.order = order;
		this.money = money;
	}
	
	public void takeCard(Card card)
	{
		holdingCard = card;
	}
	
	public Card exposeCard()
	{
		return holdingCard;
	}
	
	public void buyShares(ArrayList<Commodity> shares)
	{
		shareHolds = shares;
	}
	
	public void getTurn()
	{
		this.turn = turn;
	}
	
	public void setTurn(int turn)
	{
		 this.turn = turn;
	}
	/*A method to sell share(s) from the shares array list
	 */
}
