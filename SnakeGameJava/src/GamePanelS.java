import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanelS extends JPanel implements ActionListener {
	
	static final int SCREEN_WIDTH = 800;
	static final int SCREEN_HEIGHT = 800;
	static final int UNIT_SIZE = 25; // how big we want objects to be in game
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE; // sets how many objects can fit on the screen 
	static final int DELAY = 100; // the higher the delay the slower the game will run
	
	final int x[] = new int[GAME_UNITS]; //x coordinates for snake body parts
	final int y[] = new int[GAME_UNITS]; //y coordinates for snake body
	
	final int x2[] = new int[GAME_UNITS];
	final int y2[] = new int[GAME_UNITS];

	int bodyParts = 6; // snake starts with 6 body parts
	int bodyParts2 = 6;
	
	int applesEaten; // will initially be 0
	int applesEaten2;
	// appleX and appleY will change apple location to a random location once eaten by snake
	int appleX; 
	int appleY;
	char direction = 'R';
	char direction2 = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanelS() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT ));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	} 
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running)
		{
			for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) //for testing purposes to see how big  items in game will be
			{
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); // draws vertical lines along game window
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); // draws horizontal lines across window
			}
			
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			
			//First player
			for (int i = 0; i < bodyParts; i++) 
			{
				if (i==0)
				{
					g.setColor(Color.yellow);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else 
				{
					g.setColor(Color.cyan);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//Second player
			for (int i = 0; i < bodyParts2; i++) 
			{
				if (i==0)
				{
					g.setColor(Color.yellow);
					g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
				}
				else 
				{
					g.setColor(Color.orange);
					g.fillRect(x2[i], y2[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			
			//Score 
			g.setColor(Color.white);
			g.setFont(new Font("Ink Free", Font.ITALIC, 25));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
		}
		else 
		{
			gameOver(g);
		}
		
		
	}
	
	
	public void newApple() {
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE;
		
	}
	
	public void move() {
		for(int i = bodyParts; i > 0; i--)
		{
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		
		for(int i = bodyParts2; i > 0; i--)
		{
			x2[i] = x2[i-1];
			y2[i] = y2[i-1];
		}
		
		switch (direction) 
		{
		case 'U':
			y[0] = y[0] - UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE;
			break;
		}
		
		switch (direction2) 
		{
		case 'U':
			y2[0] = y2[0] - UNIT_SIZE;
			break;
		case 'D':
			y2[0] = y2[0] + UNIT_SIZE;
			break;
		case 'L':
			x2[0] = x2[0] - UNIT_SIZE;
			break;
		case 'R':
			x2[0] = x2[0] + UNIT_SIZE;
			break;
		}
	} 
	
	public void checkApple() {
		
		if ((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
			
		}
		
		if ((x2[0] == appleX) && (y2[0] == appleY))
		{
			bodyParts2++;
			applesEaten2++;
			newApple();
			
		}
		
	}
	
	public void checkCollisions() {
		// checks if head collides with body
		for (int i = bodyParts; i > 0; i--)
		{
			if ((x[0] == x[i]) && (y[0] == y[i]))
			{
				running = false;
			}
		}
		
		//check if head touches left border
		if (x[0] < 0) { running = false;}
		//checks if head touches right border
		if (x[0] > SCREEN_WIDTH-UNIT_SIZE) {running = false;}
		//check if head touches top border
		if (y[0] < 0) {running = false;}
		//check if head touches bottom border
		if (y[0] > SCREEN_HEIGHT-UNIT_SIZE) {running = false;}
		
		for (int i = bodyParts2; i > 0; i--)
		{
			if ((x2[0] == x2[i]) && (y2[0] == y2[i]))
			{
				running = false;
			}
		}
		
		//check if head touches left border
		if (x2[0] < 0) { running = false;}
		//checks if head touches right border
		if (x2[0] > SCREEN_WIDTH-UNIT_SIZE) {running = false;}
		//check if head touches top border
		if (y2[0] < 0) {running = false;}
		//check if head touches bottom border
		if (y2[0] > SCREEN_HEIGHT-UNIT_SIZE) {running = false;}
		
		if (!running) {timer.stop();}
	}
	
	public void gameOver(Graphics g) {
		// Score
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.ITALIC, 25));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	
		//Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (running) 
		{
			move();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	
	public class MyKeyAdapter extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_LEFT:
				if (direction != 'R') // to avoid 180 degree turn 
				{
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direction != 'L') // to avoid 180 degree turn 
				{
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direction != 'D') // to avoid 180 degree turn 
				{
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direction != 'U') // to avoid 180 degree turn 
				{
					direction = 'D';
				}
				break;
			case KeyEvent.VK_A:
				if (direction2 != 'R') // to avoid 180 degree turn 
				{
					direction2 = 'L';
				}
				break;
			case KeyEvent.VK_D:
				if (direction2 != 'L') // to avoid 180 degree turn 
				{
					direction2 = 'R';
				}
				break;
			case KeyEvent.VK_W:
				if (direction2 != 'D') // to avoid 180 degree turn 
				{
					direction2 = 'U';
				}
				break;
			case KeyEvent.VK_S:
				if (direction2 != 'U') // to avoid 180 degree turn 
				{
					direction2 = 'D';
				}
				break;
				
			}
			
		}
	}

}
