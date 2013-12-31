var Card = (function(suit, value) { //constructor
	//private variables
	var _suitNr = suit,
		_value = value,
		_suitName = suitNrToString(suit);
		
	function suitNrToString(nr){
		switch(nr){
			case 1: return 'clubs'; break;
			case 2: return 'spades'; break;
			case 3: return 'diamond'; break;
			case 4: return 'hearts'; break;
		}
	}
		
	
	return {
		
		suit : _suitNr,
		
		value : _value,
		
		isHigher: function(card){
			if(_suitNr < card.suit){
				return false;
			}else if (_suitNr > card.suit){
				return true;
			}else{
				if(_value < card.value){
					return false;
				}else{
					return true;
				}
			}
		},
		
		
		
		equals: function(card){
			if(_suitNr == card.suit && _value==card.value){
				return true;	
			}else{
				return false;
			}
		}
			
	}	
});