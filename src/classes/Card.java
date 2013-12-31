package classes;

public class Card implements Comparable<Card> {

	private int suit;
	private int value;
	
	public Card(int suit, int value){
		this.setSuit(suit);
		this.setValue(value);
	}

	public int getSuit() {
		return suit;
	}

	public void setSuit(int suit) {
		this.suit = suit;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	

	@Override
	public int compareTo(Card arg0) {
		Card otherCard = (Card) arg0;
		if(this.suit< otherCard.getSuit()){
			return -1;
		}else if(this.suit>otherCard.getSuit()){
			return 1;
		}else{
			if(this.value<otherCard.getValue()){
				return -1;
			}else if(this.value>otherCard.getValue()){
				return 1;
			}else{
				return 0;
			}
		}
	}
	
}
