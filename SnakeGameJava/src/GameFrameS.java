import javax.swing.JFrame;

public class GameFrameS extends JFrame {
	
	GameFrameS() {
		
		GamePanelS panel = new GamePanelS();
		
		this.add(panel);  //can also be written this.add(new GamePanel()); as "shortcut" 
		this.setTitle("Snake"); // window title
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null); // null parameter makes window pop at the center of screen 
		
		
	}

}
