package classes;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private ArrayList<Card> deck;
	
	public Deck(){
		deck=new ArrayList<Card>();	
		init();
		shuffle();
	}
	
	private void init(){
		for(int i=1;i<5;i++){
			for(int j=2;j<15;j++){
				deck.add(new Card(i,j));
			}
		}
	}
	
	private void shuffle(){
		Collections.shuffle(deck);
	}
	
	public Card pop(){
		Card k = deck.get(0);
		deck.remove(k);
		return k;
	}
}
