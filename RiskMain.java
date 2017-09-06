import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class RiskMain {
	
    public static void main(String[] args) {

		JFrame window = new JFrame("Risk");	    
		RiskModel model = new RiskModel();
	    RiskView view = new RiskView(model);
	    
	    window.setResizable(false);  
	    window.setVisible(true);
	    window.setContentPane(view);
	    window.pack();  
	    Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	    window.setLocation( (screensize.width - window.getWidth())/2, 
	    		(screensize.height - window.getHeight())/2 );
	    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
