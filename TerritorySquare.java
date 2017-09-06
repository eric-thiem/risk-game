import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class TerritorySquare {
	
	private static final int SIZE = 30;
	
	private int x;
	private int y;
	private int troopNumber;
	private String name;
	private int player;
	private ArrayList<TerritorySquare> links;
	private boolean isSelected;
	private boolean isChecked;
	
	public TerritorySquare(int x, int y, String name){
		this.x = x;
		this.y = y;
		this.name = name;
		this.troopNumber = 0;
		this.player = -1;
		this.links = new ArrayList<TerritorySquare>();
		this.isSelected = false;
		this.isChecked = false;
	}
	
	public void draw(Graphics g){
		if(!isSelected){
			g.setColor(Color.BLACK);
			g.drawRect(x-1, y-1, SIZE+1, SIZE+1);
		}else{
			g.setColor(Color.WHITE);
			g.fillRect(x-4, y-4, SIZE+8, SIZE+8);
		}
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.BOLD, 12));
		g.drawString(name, x-2, y-10);
		
		// Set the color of the square to the color of the owner
		
		switch(player){
		case 1:
			g.setColor(Color.RED);
			break;
		case 2:
			g.setColor(Color.BLUE);
			break;
		case 3:
			g.setColor(Color.YELLOW);
			break;
		case 4:
			g.setColor(Color.GREEN);
			break;
		case 5:
			g.setColor(Color.MAGENTA);
			break;
		case 6:
			g.setColor(Color.CYAN);
			break;
		case 0:
			g.setColor(Color.WHITE);
			break;
		default:
			g.setColor(Color.GRAY);

		}
		g.fillRect(x, y, SIZE, SIZE);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Serif", Font.BOLD, 16));
		g.drawString(Integer.toString(troopNumber), x + SIZE/2-4, y + SIZE/2+6);

	}

	public int getTroopNumber() {
		return troopNumber;
	}

	public void setTroopNumber(int troopNumber) {
		this.troopNumber = troopNumber;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getSize(){
		return SIZE;
	}

	public int getPlayer() {
		return player;
	}

	public void setPlayer(int player) {
		this.player = player;
	}
	
	public void setLinks(TerritorySquare... squares){
		for(TerritorySquare square : squares){
			links.add(square);
		}
	}
	
	public ArrayList<TerritorySquare> getLinks(){
		return links;
	}
	
	public TerritorySquare getLink(int i){
		return links.get(i);
	}
	
	public String getName(){
		return name;
	}
	
	public boolean checkLinks(TerritorySquare square){
		
		// Returns true if the square passed has a link with the current square
		
		for(int i = 0; i < links.size(); i++){
			if(square.getName().equals(links.get(i).getName())){
				return true;
			}
		}return false;
	}

	public boolean getIsSelected() {
		return isSelected;
	}

	public void setIsSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public boolean getChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	
}
