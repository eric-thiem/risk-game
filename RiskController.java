import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RiskController implements ActionListener, MouseListener, ChangeListener{
	
	private RiskModel model;
	private RiskView view;
	
	public RiskController(RiskModel model, RiskView view){
		this.model = model;
		this.view = view;
	}
	
	public void actionPerformed(ActionEvent act){
		
		//If they clicked on a button...
		
		if(act.getActionCommand().equals("Two") || act.getActionCommand().equals("Three") || 
				act.getActionCommand().equals("Four") || act.getActionCommand().equals("Five") || 
				act.getActionCommand().equals("Six")){
			
			//... do this:
			
			model.setPlayersSelected(true);
			view.removeButtons();
			
			//Take button input and start new game based on button pressed
			
			if(act.getActionCommand().equals("Two")){
				model.setPlayers(2);
				startTwoPlayerGame();
			    
				//TEST CODE PLEASE IGNORE
				/*model.setIsAttacking(true);
			    model.setIsTurn(true);
			    model.setCurrentPlayer(1);
			    model.setAttackingSelectedSquare(model.getTerritories(0));
			    model.getAttackingSelectedSquare().setTroopNumber(20);
			    model.setDefendingSelectedSquare(model.getTerritories(1));*/
			    
				view.repaint();
				
			}else if(act.getActionCommand().equals("Three")){
				model.setPlayers(3);
				startNewGame(3);
				
			}else if(act.getActionCommand().equals("Four")){
				model.setPlayers(4);
				startNewGame(4);
				
			}else if(act.getActionCommand().equals("Five")){
				model.setPlayers(5);
				startNewGame(5);
				
			}else if(act.getActionCommand().equals("Six")){
				model.setPlayers(6);
				startNewGame(6);
			}
		}
	}
	
	public void startTwoPlayerGame(){
		
		//Starts new two player game
		
		model.setCurrentPlayer(1);
		model.setPlayers(2);
		model.twoPlayerSetup();
		model.setIsDistributing(true);
		view.setLabel("Player " + model.getCurrentPlayer() + ": Reinforce one of your territories. " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " left to distribute.");
		view.repaint();
	}
	
	public void startNewGame(int players){
		
		//Starts new non-two player game
		
		model.setCurrentPlayer(1);
		model.setPlayers(players);
		model.setIsChoosing(true);
		view.setLabel("Player " + model.getCurrentPlayer() + ": Choose your territory. " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " left to distribute.");
		view.repaint();
	}
	
	public void turnSetUp(){
		
		//Sets up each player's first turn
		
		model.setPlayerCurTroops(model.getCurrentPlayer(), model.calculateReinforcements(model.getCurrentPlayer()));
		view.setLabelColor(model.getPlayerColor(model.getCurrentPlayer()));
		view.setLabel("Player " + model.getCurrentPlayer() + " is distributing their reinforcements per turn: " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " reinforcements to distribute.");
		model.setIsReinforcing(true);
	}
	
	////////////////////////////////// PRE-GAME TERRITORY SELECTION AND DISTRIBUTION /////////////////////////////////////////////////////
	
	public void chooseTerritory(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){ //If clicked on a square
			if(tempSquare.getPlayer() == -1){ //if square is unclaimed
				tempSquare.setPlayer(model.getCurrentPlayer());
				tempSquare.setTroopNumber(1);
				
				decrementTroops();				
				nextPlayer();
				
				if(model.getCurrentPlayer() > model.getPlayers()){
					model.setCurrentPlayer(1);
				}
					
				view.setLabelColor(model.getPlayerColor(model.getCurrentPlayer()));
				view.setLabel("Player " + model.getCurrentPlayer() + ": Choose your territory. " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " left to distribute.");

			}else{
				view.setLabel("That territory has already been claimed. Player " + model.getCurrentPlayer() + ", choose one that is unclaimed.");
			}
			
			if(model.allTerritoriesChosen()){
				model.setIsChoosing(false);
				model.setIsDistributing(true);
				view.setLabelColor(model.getPlayerColor(model.getCurrentPlayer()));
				view.setLabel("Player " + model.getCurrentPlayer() + ": Reinforce one of your territories. " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " left to distribute.");
			}
		}
	}
	
	public void firstDistributeToTerritory(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){
			if(tempSquare.getPlayer() == model.getCurrentPlayer()){
				tempSquare.setTroopNumber(tempSquare.getTroopNumber() + 1);
				decrementTroops();
				nextPlayer();
				
				if(model.getCurrentPlayer() > model.getPlayers()){
					model.setCurrentPlayer(1);
				}
				
				view.setLabelColor(model.getPlayerColor(model.getCurrentPlayer()));
				view.setLabel("Player " + model.getCurrentPlayer() + ": Reinforce one of your territories. " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " left to distribute.");
			}else{
				view.setLabelColor(model.getPlayerColor(model.getCurrentPlayer()));
				view.setLabel("Player " + model.getCurrentPlayer() + " does not own that territory. Choose a territory you own.");
			}
		}
		
		if(model.allTroopsDistributed()){
			model.setIsDistributing(false);
			model.setIsTurn(true);
			model.setCurrentPlayer(1);
			turnSetUp();
		}
	}
	
	////////////////////////////////// END OF PRE-GAME TERRITORY SELECTION AND DISTRIBUTION /////////////////////////////////////////////////////
	
	////////////////////////////////// START OF PLAYER TURN PHASES  //////////////////////////////////////////////////////////////////////////////////////

	public void distributeReinforcements(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){ //If clicked on a square
			if(tempSquare.getPlayer() == model.getCurrentPlayer()){
				model.decrementPlayerCurTroops(model.getCurrentPlayer());
				tempSquare.setTroopNumber(tempSquare.getTroopNumber() + 1);
				if(model.getPlayerCurTroops(model.getCurrentPlayer()) == 0){
					model.setIsReinforcing(false);
					model.setIsAttackPhase(true);
					view.setLabel("Player " + model.getCurrentPlayer() + " is attacking. Choose where to attack from or end combat.");
				}else{
					view.setLabel("Player " + model.getCurrentPlayer() + " is distributing their reinforcements per turn: " + model.getPlayerCurTroops(model.getCurrentPlayer()) + " reinforcements to distribute.");
				}
			}else{
				view.setLabel("Player " + model.getCurrentPlayer() + " does not own that territory. Reinforce a territory you own.");
			}
		}
	}
	
	//This code is a goddamn mess Eric
	
	public void attackPhase(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){ //If clicked on a square
			if(tempSquare.getPlayer() == model.getCurrentPlayer()){
				if(tempSquare.getTroopNumber() > 1){
					tempSquare.setIsSelected(true);
					model.setAttackingSelectedSquare(tempSquare);
					model.setIsAttackPhase(false);
					model.setTerritorySelected(true);
					view.setLabel("Player " + model.getCurrentPlayer() + ": Choose where to attack.");
				}else{
					view.setLabel("That square does not have enough troops to attack! Choose again.");
				}
			}else{
				view.setLabel("Player " + model.getCurrentPlayer() + " does not own that territory. Attack from a territory you own.");
			}
		}
	}
	
	public void selectTerritoryToAttack(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){ //If clicked on a square
			if(tempSquare.getPlayer() != model.getCurrentPlayer()){
				if(model.getAttackingSelectedSquare().checkLinks(tempSquare)){
					tempSquare.setIsSelected(true);
					model.setDefendingSelectedSquare(tempSquare);
					model.setTerritorySelected(false);
					model.setIsAttacking(true);
				}else{
					view.setLabel("Player " + model.getCurrentPlayer() + ": Please select an adjacent territory.");
				}
			}else{
				view.setLabel("Player " + model.getCurrentPlayer() + " owns that territory. Attack a territory you don't own.");
			}
		}else{
			model.setTerritorySelected(false);
			model.setIsAttackPhase(true);
			resetSquares();
			view.setLabel("Player " + model.getCurrentPlayer() + " is attacking. Choose where to attack from.");
		}
	}
	
	public void makeRoll(){
		int attackingNumber = 0;
		int defendingNumber = 0;
		
		//Determine how many die will be rolled on each side
		if(model.getAttackingSelectedSquare().getTroopNumber() >= 4){
			attackingNumber = 3;
		}else if(model.getAttackingSelectedSquare().getTroopNumber() == 3){
			attackingNumber = 2;
		}else if(model.getAttackingSelectedSquare().getTroopNumber() == 2){
			attackingNumber = 1;
		}else{
			System.out.println("You shouldn't be able to attack from here.");
		}
		
		if(model.getDefendingSelectedSquare().getTroopNumber() > 1){
			defendingNumber = 2;
		}else{
			defendingNumber = 1;
		}
		
		// Die rolls are added
		for(int i = 0; i < attackingNumber; i++){
			model.addAttackingNumber((int)(Math.random()*6)+1);
		}
		
		for(int i = 0; i < defendingNumber; i++){
			model.addDefendingNumber((int)(Math.random()*6)+1);
		}
		
		//Get the array for the rolls
		int[] defeats = model.compareAttackingAndDefendingNumbers();
		
		//Subtract the losses
		model.getAttackingSelectedSquare().setTroopNumber(model.getAttackingSelectedSquare().getTroopNumber() - defeats[0]);
		model.getDefendingSelectedSquare().setTroopNumber(model.getDefendingSelectedSquare().getTroopNumber() - defeats[1]);
		
		//Setup the string for the label to print the roll numbers
		ArrayList<Integer> attackingNumbers = model.getAttackingNumbers();
		ArrayList<Integer> defendingNumbers = model.getDefendingNumbers();
		
		String a = "";
		for(int i = 0; i < attackingNumbers.size(); i++){
			a = a + attackingNumbers.get(i) + ", ";
		}
		a=a.substring(0,a.length()-2);
		
		String d = "";
		for(int i = 0; i < defendingNumbers.size(); i++){
			d = d + defendingNumbers.get(i) + ", ";
		}
		d=d.substring(0,d.length()-2);
		
		view.setLabel("Player " + model.getCurrentPlayer() + " rolls " + a + ". Player " + model.getDefendingSelectedSquare().getPlayer() + " rolls " + d + ".");
		
		model.clearRollNumbers();
		
		//If the attacker won and the defender has no troops left
		if(model.getDefendingSelectedSquare().getTroopNumber() <= 0){
			model.getDefendingSelectedSquare().setPlayer(model.getAttackingSelectedSquare().getPlayer());
			model.setPrintNum(model.getAttackingSelectedSquare().getTroopNumber()-1);
			model.setIsAttacking(false);
			model.setAttackMoving(true);
			model.setPaintSliderBoard(true);
			view.setLabel("Player " + model.getCurrentPlayer() + " captured " + model.getDefendingSelectedSquare().getName() + ". Choose how many troops to move to the captured territory.");
		}
		
		//If the attackers are down to one troop
		else if(model.getAttackingSelectedSquare().getTroopNumber() == 1){
			model.setIsAttacking(false);
			model.setIsAttackPhase(true);
			view.setLabel("Player " + model.getCurrentPlayer() + "'s attack on " + model.getDefendingSelectedSquare().getName() + " failed. You may now attack again or end combat.");
			resetSquares();
		}
		
		//You shouldn't be able to get here but it doesn't hurt to leave this here
		else if(model.getAttackingSelectedSquare().getTroopNumber() == 0){
			System.out.println("Attacking Troop number equals 0. This shouldnt happen.");
		}

	}
	
	public void selectTroopsToMove(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){ //If clicked on a square
			if(tempSquare.getPlayer() == model.getCurrentPlayer()){
				if(tempSquare.getTroopNumber() > 1){
					tempSquare.setIsSelected(true);
					model.setAttackingSelectedSquare(tempSquare);
					model.setIsMoving(false);
					model.setisSelectingWhereToMove(true);
					view.setLabel("Player " + model.getCurrentPlayer() + ": Choose where to move your troops.");
				}else{
					view.setLabel("That square does not have enough troops to move! Choose again.");
				}
			}else{
				view.setLabel("Player " + model.getCurrentPlayer() + " does not own that territory. Move from a territory you own.");
			}
		}
	}
	
	public void whereToMove(MouseEvent m){
		TerritorySquare tempSquare = model.isInTerritoryArea(m.getX(), m.getY());
		if(tempSquare != null){ //If clicked on a square
			if(tempSquare.getPlayer() == model.getCurrentPlayer()){
				model.setDefendingSelectedSquare(tempSquare);
				if(checkPath(model.getAttackingSelectedSquare())){
					tempSquare.setIsSelected(true);
					model.setPrintNum(model.getAttackingSelectedSquare().getTroopNumber()-1);
					model.setisSelectingWhereToMove(false);
					model.setPaintSliderBoard(true);
					model.resetIsChecked();
					view.setLabel("Player " + model.getCurrentPlayer() + ": Choose how many troops to move.");
				}else{
					model.resetIsChecked();
					model.setDefendingSelectedSquare(null);
					view.setLabel("Player " + model.getCurrentPlayer() + " cannot not move to that territory. Move to a territory with a direct path to it.");
				}
			}else{
				view.setLabel("Player " + model.getCurrentPlayer() + " does not own that territory. Move to a territory you own.");
			}
		}else{
			model.setTerritorySelected(false);
			model.setIsMoving(true);
			resetSquares();
			view.setLabel("Player " + model.getCurrentPlayer() + " is moving. Choose where to move from or skip moving.");
		}
	}
	
	public void confirmMoveTroops(MouseEvent m){
				
		if(isConfirm(m.getX(), m.getY())){
			model.getAttackingSelectedSquare().setTroopNumber(model.getAttackingSelectedSquare().getTroopNumber() - model.getPrintNum());
			model.getDefendingSelectedSquare().setTroopNumber(model.getDefendingSelectedSquare().getTroopNumber() + model.getPrintNum());
			
			if(model.getAttackMoving()){
				model.setPaintSliderBoard(false);
				model.setIsAttackPhase(true);
				model.setAttackMoving(false);
				view.setLabel("Player " + model.getCurrentPlayer() + " moved troops to " + model.getDefendingSelectedSquare().getName() + ". Attack again or end combat.");
				resetSquares();
				return;
			}
			
		}if(isCancel(m.getX(), m.getY())){
			
			if(model.getAttackMoving()){
				model.getAttackingSelectedSquare().setTroopNumber(model.getAttackingSelectedSquare().getTroopNumber() - 1);
				model.getDefendingSelectedSquare().setTroopNumber(1);
				model.setIsAttackPhase(true);
				view.setLabel("Player " + model.getCurrentPlayer() + " moved only 1 troop to " + model.getDefendingSelectedSquare().getName() + ". Attack again or end combat.");
				model.setisSelectingWhereToMove(false);
				model.setPaintSliderBoard(false);
				resetSquares();
				return;
				
			}else{
				model.setIsMoving(true);
				model.setisSelectingWhereToMove(false);
				model.setPaintSliderBoard(false);
				resetSquares();
				view.setLabel("Player " + model.getCurrentPlayer() + " cancelled their move. Move again or skip moving.");
				return;
			}
			
		}
		
		if(isConfirm(m.getX(), m.getY()) && !model.getAttackMoving()){
			model.setisSelectingWhereToMove(false);
			model.setPaintSliderBoard(false);
			nextTurn();
		}
	}
	
	////////////////////////////////// END OF PLAYER TURN PHASES  //////////////////////////////////////////////////////////////////////////////////////

	public void mouseClicked(MouseEvent m) {
		// First check to see if the game is over
		if(model.checkGameOver()){
			view.setLabel("Player " + model.getCurrentPlayer() + " has achieved world domination!");
			model.setPaintSliderBoard(false);
			model.setAttackMoving(false);
			model.setIsMoving(false);
			try{
				model.getDefendingSelectedSquare().setTroopNumber(1);
				model.getAttackingSelectedSquare().setTroopNumber(model.getAttackingSelectedSquare().getTroopNumber()-1);
			}catch(NullPointerException e){};
			resetSquares();
		
		//Now check if they are still choosing territories
		}else if(model.getIsChoosing()){
			chooseTerritory(m);
		
		//Check if they are distributing
		}else if(model.getIsDistributing()){
			firstDistributeToTerritory(m);
		
		//Check if its a turn
		}else if(model.getIsTurn()){
			
			//Check if they are reinforcing
			if(model.getIsReinforcing()){
				distributeReinforcements(m);
			
			//If they aren't reinforcing check to see if it is now the attack phase
			}else if(model.getIsAttackPhase()){
				
				//Check if they clicked the skip button
				if(isSkipButton(m.getX(), m.getY())){
					resetSquares();
					model.setIsAttacking(false);
					model.setIsAttackPhase(false);
					model.setIsMoving(true);
					view.setLabel("Player " + model.getCurrentPlayer() + ": Choose a country to move from or skip moving.");
				}
				attackPhase(m);
				
			//If the first territory is selected to attack from
			}else if(model.getTerritorySelected()){
				selectTerritoryToAttack(m);
			
			//If both the attacking territory and the territory being attacked are selected
			}else if(model.getIsAttacking()){
				//If they are rolling
				if(isRoll(m.getX(), m.getY())){
					makeRoll();
				//If they are retreating	
				}else if(isRetreat(m.getX(), m.getY())){
					resetSquares();
					model.clearRollNumbers();
					model.setIsAttacking(false);
					model.setIsAttackPhase(true);
					view.setLabel("Player " + model.getCurrentPlayer() + " retreats. You may now attack again or end combat.");
				}
			
			//If it is the moving phase
			}else if(model.getIsMoving()){
				
				//If they skip moving
				if(isSkipButton(m.getX(), m.getY())){
					model.setIsMoving(false);
					nextTurn();
				
				//If they are selecting what territory to move from
				}else{
					selectTroopsToMove(m);
				}
			
			//If they are selecting where to move to
			}else if(model.getisSelectingWhereToMove()){
				
				//If they skip moving
				if(isSkipButton(m.getX(), m.getY())){
					resetSquares();
					model.setIsMoving(false);
					nextTurn();
				//select where to move
				}else{
					whereToMove(m);
				}
			
			//Paint the slider board for either post-combat movement or end of turn troop movement
			}else if(model.getPaintSliderBoard()){
				confirmMoveTroops(m);
			}
		}
		
		//Repaint the screen
		view.repaint();
	}
	
	public void stateChanged(ChangeEvent meow) {
		
		//If the slider is changed
		
		model.setPrintNum(view.getSlider().getValue());
		view.repaint();
	}
	
	public void resetSquares(){
		
		// Resets attacking and defending squares back to null so none are selected
		
		try{
			model.getAttackingSelectedSquare().setIsSelected(false);
			model.getDefendingSelectedSquare().setIsSelected(false);
		}
		catch(NullPointerException e){}
		model.setAttackingSelectedSquare(null);
		model.setDefendingSelectedSquare(null);
	}
	
	public void nextTurn(){
		
		//Sets up the next turn
		
		resetSquares();
		model.setCurrentPlayer(model.getCurrentPlayer() + 1);
		if(model.getCurrentPlayer() > model.getPlayers()){
			model.setCurrentPlayer(1);
		}
		turnSetUp();
	}
	
	public boolean checkPath(TerritorySquare square){
		
		//Recursively checks to see if there is one player can move troops from one territory to another
		//There has to be a direct path between the two countries
		
		square.setChecked(true);
		
		if(model.getDefendingSelectedSquare().getName().equals(square.getName())){
			return true;
		}
		
		boolean localCheck = false;
		int iterator = 0;
		
		for(TerritorySquare s : square.getLinks()){
			if(s.getPlayer() == model.getCurrentPlayer() && (!s.getChecked())){
				iterator++;
				localCheck = checkPath(s);
				if(localCheck){
					return true;
				}
			}
			if(iterator == square.getLinks().size()){
				return false;
			}
		}	
		return localCheck;
	}
	
	///////////////////////////////////// AND NOW FOR SOMETHING COMPLETELY DIFFERENT ////////////////////////////////
	
	// Dealing with buttons and sub routines Java makes me include
	
	public boolean isRoll(int x, int y){
		return((x >= 480) && (x <= 580) && (y >= 500) && (y <= 550));
	}
	
	public boolean isRetreat(int x, int y){
		return((x >= 610) && (x <= 710) && (y >= 500) && (y <= 550));
	}
	
	public boolean isSkipButton(int x, int y){
		return((x >= 8) && (x <= 108) && (y >= 740) && (y <= 790));
	}
	
	public boolean isConfirm(int x, int y){
		return((x >= 479) && (x <= 580) && (y >= 509) && (y <= 560));
	}
	
	public boolean isCancel(int x, int y){
		return((x >= 609) && (x <= 710) && (y >= 509) && (y <= 560));
	}
	
	public void nextPlayer(){
		model.setCurrentPlayer(model.getCurrentPlayer()+1);
	}
	
	public void decrementTroops(){
		model.setPlayerCurTroops(model.getCurrentPlayer(), model.getPlayerCurTroops(model.getCurrentPlayer()) - 1);
	}
	
	public void mouseEntered(MouseEvent m) {}
	public void mouseExited(MouseEvent m) {}
	public void mousePressed(MouseEvent m) {}
	public void mouseReleased(MouseEvent m) {}
	
}
