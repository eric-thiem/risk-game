import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class RiskModel {
	
	//Countries
	private TerritorySquare[] territories;
	private TerritorySquare[] NorthAmerica;
	private TerritorySquare[] SouthAmerica;
	private TerritorySquare[] Europe;
	private TerritorySquare[] Asia;
	private TerritorySquare[] Africa;
	private TerritorySquare[] Australia;	
	
	//Player troop info
	private int players;
	private int currentPlayer;
	private int startingTroops;
	private int[] playerTroops;
	
	private TerritorySquare attackingSelectedSquare;
	private TerritorySquare defendingSelectedSquare;
	
	private ArrayList<Integer> attackingNumbers;
	private ArrayList<Integer> defendingNumbers;
	
	//Lots of booleans
	private boolean playersSelected;
	private boolean isChoosing;
	private boolean isDistributing;
	private boolean attackMoving;
	private boolean isTurn;
	private boolean isReinforcing;
	private boolean isAttackPhase;
	private boolean territorySelected;
	private boolean isAttacking;
	private boolean isMoving;
	private boolean isSelectingWhereToMove;
	private boolean paintSliderBoard;
	
	private int printNum;
		
	public RiskModel(){
		
		//Phases of the game
		playersSelected = false;
		isChoosing = false;
		isDistributing = false;
		attackMoving = false;
		isTurn = false;
		isReinforcing = false;
		isAttackPhase = false;
		territorySelected = false;
		isAttacking = false;
		isMoving = false;
		isSelectingWhereToMove = false;
		paintSliderBoard = false;
		
		//ArrayLists for combat
		attackingNumbers = new ArrayList<Integer>();
		defendingNumbers = new ArrayList<Integer>();
		
		//Set up territory squares
		territories = new TerritorySquare[42];
		territories[0] = new TerritorySquare(61, 102, "Alaska");
		territories[1] = new TerritorySquare(185, 107, "NW Territory");
		territories[2] = new TerritorySquare(395, 63, "Greenland");
		territories[3] = new TerritorySquare(168, 168, "Alberta"); 
		territories[4] = new TerritorySquare(257, 181, "Ontario"); 
		territories[5] = new TerritorySquare(334, 184, "Quebec"); 
		territories[6] = new TerritorySquare(171, 250, "Western US"); 
		territories[7] = new TerritorySquare(264, 271, "Eastern US"); 
		territories[8] = new TerritorySquare(176, 337, "Cent. America"); //end of NA
		territories[9] = new TerritorySquare(266, 425, "Venezuela"); 
		territories[10] = new TerritorySquare(240, 496, "Peru"); 
		territories[11] = new TerritorySquare(333, 477, "Brazil"); 
		territories[12] = new TerritorySquare(295, 596, "Argentina"); // end of SA
		territories[13] = new TerritorySquare(490, 144, "Iceland"); 
		territories[14] = new TerritorySquare(594, 138, "Scandinavia"); 
		territories[15] = new TerritorySquare(689, 181, "Ukraine"); 
		territories[16] = new TerritorySquare(472, 226, "Great Britain"); 
		territories[17] = new TerritorySquare(586, 250, "N. Europe"); 
		territories[18] = new TerritorySquare(497, 332, "W. Europe"); 
		territories[19] = new TerritorySquare(588, 315, "S. Europe"); // end of Europe
		territories[20] = new TerritorySquare(522, 451, "N. Africa"); 
		territories[21] = new TerritorySquare(626, 430, "Egypt"); 
		territories[22] = new TerritorySquare(682, 496, "E. Africa"); 
		territories[23] = new TerritorySquare(634, 557, "Congo"); 
		territories[24] = new TerritorySquare(635, 649, "S. Africa"); 
		territories[25] = new TerritorySquare(743, 656, "Madagascar"); // end of Africa
		territories[26] = new TerritorySquare(802, 155, "Ural"); 
		territories[27] = new TerritorySquare(853, 108, "Siberia"); 
		territories[28] = new TerritorySquare(957, 95, "Yakutsk"); 
		territories[29] = new TerritorySquare(1046, 96, "Kamchatka"); 
		territories[30] = new TerritorySquare(947, 181, "Irkutsk"); 
		territories[31] = new TerritorySquare(781, 263, "Afghanistan"); 
		territories[32] = new TerritorySquare(885, 304, "China"); 
		territories[33] = new TerritorySquare(948, 257, "Mongolia"); 
		territories[34] = new TerritorySquare(1081, 266, "Japan"); 
		territories[35] = new TerritorySquare(714, 375, "Middle East"); 
		territories[36] = new TerritorySquare(834, 371, "India"); 
		territories[37] = new TerritorySquare(941, 417, "Siam"); // end of Asia
		territories[38] = new TerritorySquare(954, 546, "Indonesia"); 
		territories[39] = new TerritorySquare(1071, 519, "New Guinea"); 
		territories[40] = new TerritorySquare(1015, 655, "W. Australia"); 
		territories[41] = new TerritorySquare(1117, 625, "E. Australia"); //end of Australia
		
		/////////////////////// NOW WE SET THE LINKS! ////////////////////////////////////////////////////////////////////////
		
		territories[0].setLinks(territories[1], territories[3], territories[29]);
		territories[1].setLinks(territories[0], territories[2], territories[3], territories[4]);
		territories[2].setLinks(territories[1], territories[4], territories[5], territories[13]);
		territories[3].setLinks(territories[0], territories[1], territories[4], territories[6]);
		territories[4].setLinks(territories[1], territories[2], territories[3], territories[5], territories[6], territories[7]);
		territories[5].setLinks(territories[2], territories[4], territories[7]);
		territories[6].setLinks(territories[3], territories[4], territories[7], territories[8]);
		territories[7].setLinks(territories[4], territories[5], territories[6], territories[8]);
		territories[8].setLinks(territories[6], territories[7], territories[9]);
		territories[9].setLinks(territories[8], territories[10], territories[11]);
		territories[10].setLinks(territories[9], territories[11], territories[12]);
		territories[11].setLinks(territories[9], territories[10], territories[12], territories[20]);
		territories[12].setLinks(territories[10], territories[11]);
		territories[13].setLinks(territories[2], territories[14], territories[16]);
		territories[14].setLinks(territories[14], territories[15], territories[16], territories[17]);
		territories[15].setLinks(territories[14], territories[17], territories[19], territories[26], territories[31], territories[35]);
		territories[16].setLinks(territories[13], territories[14], territories[17], territories[18]);
		territories[17].setLinks(territories[14], territories[15], territories[16], territories[18], territories[19]);
		territories[18].setLinks(territories[16], territories[17], territories[19], territories[20]);
		territories[19].setLinks(territories[15], territories[17], territories[18], territories[20], territories[21], territories[35]);
		territories[20].setLinks(territories[11], territories[18], territories[19], territories[21], territories[22], territories[23]);
		territories[21].setLinks(territories[19], territories[20], territories[22], territories[35]);
		territories[22].setLinks(territories[20], territories[21], territories[23], territories[25], territories[35]);
		territories[23].setLinks(territories[20], territories[22], territories[24]);
		territories[24].setLinks(territories[22], territories[23], territories[25]);
		territories[25].setLinks(territories[22], territories[24]);
		territories[26].setLinks(territories[15], territories[27], territories[31], territories[32]);
		territories[27].setLinks(territories[26], territories[28], territories[30], territories[32], territories[33]);
		territories[28].setLinks(territories[27], territories[29], territories[30]);
		territories[29].setLinks(territories[0], territories[28], territories[30], territories[33], territories[34]);
		territories[30].setLinks(territories[27], territories[28], territories[29], territories[33]);
		territories[31].setLinks(territories[15], territories[26], territories[32], territories[35], territories[36]);
		territories[32].setLinks(territories[26], territories[27], territories[31], territories[33], territories[36], territories[37]);
		territories[33].setLinks(territories[27], territories[29], territories[30], territories[32], territories[34]);
		territories[34].setLinks(territories[29], territories[33]);
		territories[35].setLinks(territories[15], territories[19], territories[21], territories[22], territories[31], territories[36]);
		territories[36].setLinks(territories[31], territories[32], territories[35], territories[37]);
		territories[37].setLinks(territories[32], territories[36], territories[38]);
		territories[38].setLinks(territories[37], territories[39], territories[40]);
		territories[39].setLinks(territories[38], territories[40], territories[41]);
		territories[40].setLinks(territories[38], territories[39], territories[41]);
		territories[41].setLinks(territories[39], territories[40]);
		
		//////////////////////////// THAT WAS A TREK! /////////////////////////////////////////////////////////////////////////////
		
		
		//Set up the country array lists so we can check if a player controls the whole country later on
		int index = 0;
		
		NorthAmerica = new TerritorySquare[9];
		for(int i = 0; i < NorthAmerica.length; i++){
			NorthAmerica[index++] = territories[i];
		}
		index = 0;
		
		SouthAmerica = new TerritorySquare[4];
		for(int i = 9; i < 9 + SouthAmerica.length; i++){
			SouthAmerica[index++] = territories[i];
		}
		index = 0;
		
		Europe = new TerritorySquare[7];
		for(int i = 13; i < 13 + Europe.length; i++){
			Europe[index++] = territories[i];
		}
		index = 0;

		Africa = new TerritorySquare[6];
		for(int i = 20; i < 20 + Africa.length; i++){
			Africa[index++] = territories[i];
		}
		index = 0;

		Asia = new TerritorySquare[12];
		for(int i = 26; i < 26 + Asia.length; i++){
			Asia[index++] = territories[i];
		}
		index = 0;

		Australia = new TerritorySquare[4];
		for(int i = 38; i < 38 + Australia.length; i++){
			Australia[index++] = territories[i];
		}
		index = 0;
		
	}
	
	public int getTerritoriesLen(){
		return territories.length;
	}
	
	public TerritorySquare[] getTerritories(){
		return territories;
	}
	
	public TerritorySquare getTerritories(int terr){
		return territories[terr];
	}
	
	public TerritorySquare isInTerritoryArea(int x, int y){
		
		//Check if they clicked on a territory
		for(int i = 0; i < territories.length; i++){
			if((x >= territories[i].getX() && x <= territories[i].getX() + territories[i].getSize()) &&
					(y >= territories[i].getY() && y <= territories[i].getY() + territories[i].getSize())){
				return territories[i];
			}
		}
		return null;
	}
	
	public boolean allTerritoriesChosen(){
		for(int i = 0; i < territories.length; i++){
			if(territories[i].getTroopNumber() == 0){
				return false;
			}
		}
		return true;
	}
	
	public int checkCountries(int player){
		
		//Check if they control the whole country, and if they do it adds it to the running count
		int count = 0;
		for(int i = 0; i < NorthAmerica.length; i++){
			if(NorthAmerica[i].getPlayer() != player){
				break;
			}else if(i == NorthAmerica.length-1){
				count += 5;
			}
		}
		
		for(int i = 0; i < SouthAmerica.length; i++){
			if(SouthAmerica[i].getPlayer() != player){
				break;
			}else if(i == SouthAmerica.length-1){
				count += 2;
			}
		}
		
		for(int i = 0; i < Europe.length; i++){
			if(Europe[i].getPlayer() != player){
				break;
			}else if(i == Europe.length-1){
				count += 5;
			}
		}
		
		for(int i = 0; i < Asia.length; i++){
			if(Asia[i].getPlayer() != player){
				break;
			}else if(i == Asia.length-1){
				count += 7;
			}
		}
		
		for(int i = 0; i < Africa.length; i++){
			if(Africa[i].getPlayer() != player){
				break;
			}else if(i == Africa.length-1){
				count += 3;
			}
		}
		
		for(int i = 0; i < Australia.length; i++){
			if(Australia[i].getPlayer() != player){
				break;
			}else if(i == Australia.length-1){
				count += 2;
			}
		}
		return count;
	}

	public int getPlayers() {
		return players;
	}

	public void setPlayers(int players) {
		this.players = players;
		playerTroops = new int[this.players];
		setStartingTroops(this.players);
	}
	
	public boolean getPlayersSelected(){
		return playersSelected;
	}
	
	public void setPlayersSelected(boolean playersSelected){
		this.playersSelected = playersSelected;
	}

	public int getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(int currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	
	public Color getPlayerColor(int playerNum){
		
		//Gets the color of the player based on the player's number
		if(playerNum == 1){
			return Color.RED;
		}else if(playerNum == 2){
			return Color.BLUE;
		}else if(playerNum == 3){
			return Color.YELLOW;
		}else if(playerNum == 4){
			return Color.GREEN;
		}else if(playerNum == 5){
			return Color.MAGENTA;
		}else if(playerNum == 6){
			return Color.CYAN;
		}
		return Color.WHITE;
	}

	public boolean getIsAttacking() {
		return isAttacking;
	}
	
	public void setIsAttacking(boolean att){
		isAttacking = att;
	}

	public boolean getIsChoosing() {
		return isChoosing;
	}

	public void setIsChoosing(boolean isChoosing) {
		this.isChoosing = isChoosing;
	}
	
	public void setStartingTroops(int players){
		
		//Gets the starting troop number based on the number of players
		switch(players){
		case 2:
			startingTroops = 26;
			break;
		case 3:
			startingTroops = 35;
			break;
		case 4:
			startingTroops = 30;
			break;
		case 5:
			startingTroops = 25;
			break;
		case 6:
			startingTroops = 20;
			break;
		}
		for(int i = 0; i < playerTroops.length; i++){
			playerTroops[i] = startingTroops;
		}
	}
	
	public int getStartingTroops(){
		return startingTroops;
	}
	
	public int getPlayerCurTroops(int player){
		return playerTroops[player-1];
	}
	
	public void setPlayerCurTroops(int player, int troops){
		playerTroops[player-1] = troops;
	}
	
	public void decrementPlayerCurTroops(int player){
		playerTroops[player-1] -= 1;
	}
	
	public void setAllTroopsToZero(){
		for(int i = 0; i < playerTroops.length; i++){
			playerTroops[i] = 0;
		}
	}
	
	public boolean allTroopsDistributed(){
		
		//Have all troops been distributed?
		for(int troops : playerTroops){
			if(troops != 0){
				return false;
			}
		}
		return true;
	}

	public boolean getIsDistributing() {
		return isDistributing;
	}

	public void setIsDistributing(boolean isDistributing) {
		this.isDistributing = isDistributing;
	}

	public boolean getIsAttackPhase() {
		return isAttackPhase;
	}

	public void setIsAttackPhase(boolean isAttackPhase) {
		this.isAttackPhase = isAttackPhase;
	}
	
	public void testTerritories(){
		
		//Test function
		for(int i = 0; i < territories.length; i++){
			if(i % 2 == 0){
				territories[i].setPlayer(1);
				territories[i].setTroopNumber(5);
			}else{
				territories[i].setPlayer(1);
				territories[i].setTroopNumber(5);
			}
			
			territories[1].setPlayer(2);
			territories[2].setPlayer(2);
			territories[38].setPlayer(2);
		}
	}
	
	public int countOwnedTerritories(int player){
		
		//Counts the number of territories a player owns for later reinforcement counts
		int count = 0;
		for(TerritorySquare square : territories){
			if(square.getPlayer() == player){
				count++;
			}
		}
		return count;
	}
	
	public int calculateReinforcements(int player){
		
		//Calculates the total reinforcements a player gets
		int troops = countOwnedTerritories(player) / 3;
		if(troops < 3){
			return 3 + checkCountries(player);
		}
		else return troops + checkCountries(player);
	}
	
	public int[] compareAttackingAndDefendingNumbers(){
		//returns int[] with attacking defeats then defending defeats
		if(defendingNumbers.size() == 0 || attackingNumbers.size() == 0){
			return null;
		}
		
		int[] defeats = new int[2];
		for(int i = 0; i < defeats.length; i++){
			defeats[i] = 0;
		}
		
		attackingNumbers.sort(null);
		Collections.reverse(attackingNumbers);
		defendingNumbers.sort(null);
		Collections.reverse(defendingNumbers);
		
		int arrayLength;
		if(attackingNumbers.size() < defendingNumbers.size()){
			arrayLength = attackingNumbers.size();
		}else{
			arrayLength = defendingNumbers.size();
		}
		
		for(int i = 0; i < arrayLength; i++){
			if(attackingNumbers.get(i) > defendingNumbers.get(i)){
				defeats[1] += 1;
			}else{
				defeats[0] += 1;
			}
		}
		
		return defeats;
	}
	
	public boolean checkGameOver(){
		int checkPlayer = getCurrentPlayer();
		for(int i = 0; i < territories.length; i++){
			if(checkPlayer != territories[i].getPlayer()){
				return false;
			}
		}
		return true;
	}
	
	public void twoPlayerSetup(){
		
		//Randomly assigns territories in a two player game with a third neutral faction
		int random;
		int playerOne = 14;
		int playerTwo = 14;
		int neutral = 14;
		for(int i = 0; i < territories.length; i++){
			territories[i].setTroopNumber(1);
			random = (int)(Math.random()*3)+1;
			switch(random){
			case 1:
				if(playerOne != 0){
					territories[i].setPlayer(1);
					playerOne--;
					break;
				}
			case 2:
				if(playerTwo != 0){
					territories[i].setPlayer(2);
					playerTwo--;
					break;
				}
			case 3:
				if(neutral != 0){
					territories[i].setPlayer(0);
					territories[i].setTroopNumber(4);
					neutral--;
					break;
				}else{
					if(playerOne != 0){
						territories[i].setPlayer(1);
						playerOne--;
						break;
					}else if(playerTwo != 0){
						territories[i].setPlayer(2);
						playerTwo--;
						break;
					}
				}
			}
		}
	}
	
	/////////////////////////////////////////////////// LOTS OF GETTERS AND SETTERS! ////////////////////////////////////
	
	public boolean getIsTurn() {
		return isTurn;
	}

	public void setIsTurn(boolean isTurn) {
		this.isTurn = isTurn;
	}

	public boolean getIsReinforcing() {
		return isReinforcing;
	}

	public void setIsReinforcing(boolean isReinforcing) {
		this.isReinforcing = isReinforcing;
	}

	public boolean getIsMoving() {
		return isMoving;
	}

	public void setIsMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean getTerritorySelected() {
		return territorySelected;
	}

	public void setTerritorySelected(boolean territorySelected) {
		this.territorySelected = territorySelected;
	}

	public TerritorySquare getAttackingSelectedSquare() {
		return attackingSelectedSquare;
	}

	public void setAttackingSelectedSquare(TerritorySquare attackingSelectedSquare) {
		this.attackingSelectedSquare = attackingSelectedSquare;
	}

	public TerritorySquare getDefendingSelectedSquare() {
		return defendingSelectedSquare;
	}

	public void setDefendingSelectedSquare(TerritorySquare defendingSelectedSquare) {
		this.defendingSelectedSquare = defendingSelectedSquare;
	}

	public void addAttackingNumber(int num){
		attackingNumbers.add(num);
	}
	
	public void printAttackingNumbers(){
		for(int i = 0; i < attackingNumbers.size(); i++){
			System.out.println(attackingNumbers.get(i));
		}
	}
	
	public void addDefendingNumber(int num){
		defendingNumbers.add(num);
	}
	
	public void printDefendingNumbers(){
		for(int i = 0; i < defendingNumbers.size(); i++){
			System.out.println(defendingNumbers.get(i));
		}
	}
	
	public ArrayList<Integer> getAttackingNumbers(){
		return attackingNumbers;
	}
	
	public ArrayList<Integer> getDefendingNumbers(){
		return defendingNumbers;
	}
	
	public void clearRollNumbers(){
		attackingNumbers = new ArrayList<Integer>();
		defendingNumbers = new ArrayList<Integer>();
	}

	public boolean getisSelectingWhereToMove() {
		return isSelectingWhereToMove;
	}

	public void setisSelectingWhereToMove(boolean isSelectingWhereToMove) {
		this.isSelectingWhereToMove = isSelectingWhereToMove;
	}

	public boolean getPaintSliderBoard() {
		return paintSliderBoard;
	}

	public void setPaintSliderBoard(boolean paintSliderBoard) {
		this.paintSliderBoard = paintSliderBoard;
	}

	public int getPrintNum() {
		return printNum;
	}

	public void setPrintNum(int printNum) {
		this.printNum = printNum;
	}

	public boolean getAttackMoving() {
		return attackMoving;
	}

	public void setAttackMoving(boolean attackMoving) {
		this.attackMoving = attackMoving;
	}
	
	public void resetIsChecked(){
		for(int i = 0; i < territories.length; i++){
			territories[i].setChecked(false);
		}
	}
	
}
