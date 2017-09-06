import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class RiskView extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private RiskModel model;
	private RiskController controller;
	private Dimension dimension;
	private JLabel label;
	private Color labelColor;
	private JButton two, three, four, five, six;
	private Map map;
	
	public RiskView(RiskModel model){
		
		//Set up the dimensions
		
		this.model = model;
		controller = new RiskController(model, this);
		
		setLayout(null);
		dimension = new Dimension(1200, 850);
		setPreferredSize(dimension);
		setBackground(Color.BLACK);
		
		label = new JLabel("", JLabel.CENTER);
		labelColor = Color.RED;
		label.setBounds(0, 10, 1200, 30);
		label.setForeground(labelColor);
        label.setFont(new Font("Serif", Font.BOLD, 20));
        
        //Create the map and the buttons
		
		map = new Map();
		map.addMouseListener(controller);
				
		two = new JButton("Two");
		two.addActionListener(controller);
		
		three = new JButton("Three");
		three.addActionListener(controller);
		
		four = new JButton("Four");
		four.addActionListener(controller);
		
		five = new JButton("Five");
		five.addActionListener(controller);
		
		six = new JButton("Six");
		six.addActionListener(controller);
		
		map.setBounds(0, 50, 1200, 800);
		two.setBounds(300, 450, 100, 50);
		three.setBounds(425, 450, 100, 50);
		four.setBounds(550, 450, 100, 50);
		five.setBounds(675, 450, 100, 50);
		six.setBounds(800, 450, 100, 50);
		
		this.add(six);
		this.add(five);
		this.add(four);
		this.add(three);
		this.add(two);
		this.add(map);
	}
	
	private class Map extends JPanel{
		private static final long serialVersionUID = 1L;
		private String skipLabel;
		private JSlider slider;

		Map(){
			setBackground(Color.GREEN);
		}
		
		public void paintComponent(Graphics g){
			
			Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            //Set the color of the label to the color of the player
            label.setForeground(labelColor);
			
            //Get the map and print it
            
            try{
    			URL u = this.getClass().getResource("/resources/homemap.png");
    			Image img = ImageIO.read(u);
    			g.drawImage(img, 0, 0, null);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
            
            //Draw all the territories
            for(int terrs = 0; terrs < model.getTerritoriesLen(); terrs++){
            	model.getTerritories(terrs).draw(g);
            }
			
            //If the game has been started
			if(!model.getPlayersSelected()){
				
				g.setColor(Color.WHITE);
				g.fillRect((int)dimension.getWidth()/6-8, (int)dimension.getHeight()/6-8,
						(int)dimension.getWidth()/6*4+16, (int)dimension.getHeight()/6*4+16);
				
				g.setColor(Color.BLACK);
				g.fillRect((int)dimension.getWidth()/6, (int)dimension.getHeight()/6, //Set the size of the black box to
						(int)dimension.getWidth()/6*4, (int)dimension.getHeight()/6*4);   //a smaller section of the screen
				
				g.setColor(Color.WHITE);
		        g.setFont(new Font("Serif", Font.BOLD, 50));
				g.drawString("How many players?", (int)dimension.getWidth()/4 + 100, (int)dimension.getWidth()/4);
			}
			
			//Change the skip label depending on the phase
			if(model.getIsAttackPhase()){
				skipLabel = "End Combat";
			}else if(model.getIsMoving()){
				skipLabel = "Skip Moving";
			}
			
			//If the skip button should be there, draw it
			if(model.getIsAttackPhase() || model.getIsMoving()){
				g.setColor(Color.RED);
				g.fillRect(8, 740, 100, 50);
				g.setColor(Color.BLACK);
				g.drawRect(7, 739, 101, 51);
				g.drawString(skipLabel, 16, 770);
			}
			
			//If the paintBoard should be printed
			if(model.getIsAttacking() && (!model.getPaintSliderBoard())){
				paintAttackingBoard(g, model.getAttackingSelectedSquare().getPlayer(), model.getDefendingSelectedSquare().getPlayer(), model.getAttackingSelectedSquare().getTroopNumber()-1, model.getDefendingSelectedSquare().getTroopNumber());
			}else if(model.getPaintSliderBoard()){
				paintSliderBoard(g, model.getAttackingSelectedSquare().getTroopNumber()-1);
			}
			
			//If the paint board shouldn't be printed, try to remove the slider if it is there
			if(!model.getPaintSliderBoard()){
				try{
					this.remove(slider);
					slider = null;
				}catch(NullPointerException e){}
			}
			
		}
		
		public void paintAttackingBoard(Graphics g, int attackingPlayer, int defendingPlayer, int attackingTroops, int defendingTroops){
			
			//This function paints the slider board for choosing how many troops to move into a new territory
			
			g.setColor(Color.WHITE);
			g.fillRect((int)dimension.getWidth()/6-8, (int)dimension.getHeight()/6-8,
					(int)dimension.getWidth()/6*4+16, (int)dimension.getHeight()/6*4+16);
			
			g.setColor(Color.BLACK);
			g.fillRect((int)dimension.getWidth()/6, (int)dimension.getHeight()/6,
					(int)dimension.getWidth()/6*4, (int)dimension.getHeight()/6*4);
			
			//Set up the label for the attack board
			
	        g.setFont(new Font("Serif", Font.BOLD, 50));
	        g.setColor(model.getPlayerColor(attackingPlayer));
			g.drawString("Player " + attackingPlayer, 280, 250);
			g.drawString(Integer.toString(attackingTroops), 485, 340);
			
			g.setColor(Color.WHITE);
			g.drawString(" is attacking ", 460, 250);
			g.drawString("vs", 573, 340);
			
			g.setColor(model.getPlayerColor(defendingPlayer));
			g.drawString("Player " + defendingPlayer, 740, 250);
			g.drawString(Integer.toString(defendingTroops), 680, 340);
			
			g.setColor(Color.WHITE);
			g.drawRect(479, 499, 101, 51);
			g.drawRect(609, 499, 101, 51);
			
			g.setColor(Color.RED);
			g.fillRect(480, 500, 100, 50);
			g.fillRect(610, 500, 100, 50);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Roll", 515, 530);
			g.drawString("Retreat", 628, 530);

		}
		
		public void paintSliderBoard(Graphics g, int max){
			
			//This function is called after a successful attack or during the move phase
			
			if(slider == null){
				setSlider(max);
			}
			
			g.setColor(Color.WHITE);
			g.fillRect((int)dimension.getWidth()/6-8, (int)dimension.getHeight()/6-8,
					(int)dimension.getWidth()/6*4+16, (int)dimension.getHeight()/6*4+16);

			
			g.setColor(Color.BLACK);
			g.fillRect((int)dimension.getWidth()/6, (int)dimension.getHeight()/6,
					(int)dimension.getWidth()/6*4, (int)dimension.getHeight()/6*4);
			
			g.setColor(Color.RED);
			g.fillRect(480, 510, 100, 50);
			g.fillRect(610, 510, 100, 50);
			
			g.setColor(Color.WHITE);
			g.drawRect(479, 509, 101, 51);
			g.drawRect(609, 509, 101, 51);
			
			g.setColor(Color.BLACK);
			g.setFont(new Font("Serif", Font.BOLD, 20));
			g.drawString("Confirm", 495, 540);
			g.drawString("Cancel", 630, 540);
			
			//If the slider isn't already added, add it
			if(!checkSlider()){
				addSlider();
			}
			
			g.setFont(new Font("Serif", Font.BOLD, 125));
			g.setColor(model.getPlayerColor(model.getCurrentPlayer()));
			g.drawString(Integer.toString(model.getPrintNum()), 560, 315);
			
		}
		
		public void addSlider(){
			
			//Adds the slider to the paint board
			
			slider.addChangeListener(controller);
			slider.setBounds(450, 390, 300, 50);
			slider.setMinorTickSpacing(1);
			slider.setMajorTickSpacing(2);
			slider.setPaintTicks(true);
			this.add(slider);
		}
		
		public boolean checkSlider(){
			
			//Checks to see if the board has the slider in order to see if I need to remove it
			
			Component[] c = this.getComponents();
			for(Component e : c){
				if(e == slider){
					return true;
				}
			}return false;
		}
		
		public JSlider mapGetSlider(){
			return slider;
		}
		
		public void setSlider(int max){
			slider = new JSlider(1, max, max);
		}
		
	}
	
	public JSlider getSlider(){
		//Gets the slider from the map so I can check from the controller
		return map.mapGetSlider();
	}
	
	public void removeButtons(){
		this.remove(two);
		this.remove(three);
		this.remove(four);
		this.remove(five);
		this.remove(six);
		this.add(label);
	}
	
	public void setLabel(String text){
		label.setText(text);
	}
	
	public Dimension getDimension(){
		return dimension;
	}
	
	public void setLabelColor(Color c){
		labelColor = c;
	}
	
}
