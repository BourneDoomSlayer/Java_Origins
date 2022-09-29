import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

import javax.swing.JPanel;

public class GamePanelS extends JPanel implements ActionListener {
	
enum gameState {
	MENU,
	GAMEOVER,
	SINGLE,
	DOUBLE
}   

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
	Timer timer;
	Random random;
	gameState state;

	
	GamePanelS() {
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT ));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		timer = new Timer(DELAY,this);
		startGame();
	}
	
	public void startGame() {
		state = gameState.MENU;
	} 
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		revalidate();
	}
	
	public void draw(Graphics g) 
	{
		
		switch (state)
		{
			case MENU:
				drawMenu(g);
				break;
			case SINGLE:
				drawSingle(g);
				break;
			case DOUBLE: 
				drawDouble(g);
				break; 
			case GAMEOVER:
				drawGameOver(g);
				break;
		} 
		
	}

	public void drawMenu(Graphics g)
	{
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.ITALIC, 100));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("MENU", (SCREEN_WIDTH - metrics.stringWidth("MENU"))/2, g.getFont().getSize());
	}

	public void drawSingle(Graphics g)
	{
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

		//Score 
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.ITALIC, 25));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	}

	public void drawDouble(Graphics g)
	{

		// for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) //for testing purposes to see how big  items in game will be
		// {
		// 	g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT); // draws vertical lines along game window
		// 	g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE); // draws horizontal lines across window
		// }
		
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
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) * (2/3), g.getFont().getSize());

		//Second score
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.ITALIC, 25));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Score2: " + applesEaten2, (SCREEN_WIDTH - metrics2.stringWidth("Score: " + applesEaten2))/3, g.getFont().getSize());
	}

	public void drawGameOver(Graphics g) {
		// Score
		g.setColor(Color.white);
		g.setFont(new Font("Ink Free", Font.ITALIC, 25));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());
	
		//Game over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics overMetric = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - overMetric.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
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
		
		if (state == gameState.DOUBLE)
		{
			for(int i = bodyParts2; i > 0; i--)
			{
				x2[i] = x2[i-1];
				y2[i] = y2[i-1];
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
	} 
	
	public void checkApple() {
		

		if ((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++;
			applesEaten++;
			newApple();
			
		}
		
		if (state == gameState.DOUBLE)
		{
			if ((x2[0] == appleX) && (y2[0] == appleY))
			{
				bodyParts2++;
				applesEaten2++;
				newApple();
			}
		}
	}
	
	public void checkCollisions() {
		
		// checks if head collides with body
		for (int i = bodyParts; i > 0; i--)
		{
			if ((x[0] == x[i]) && (y[0] == y[i]))
			{
				state = gameState.GAMEOVER;
			}
		}
	
		//check if head touches left border
		if (x[0] < 0) { state = gameState.GAMEOVER;}
		//checks if head touches right border
		if (x[0] > SCREEN_WIDTH-UNIT_SIZE) {state = gameState.GAMEOVER;}
		//check if head touches top border
		if (y[0] < 0) {state = gameState.GAMEOVER;}
		//check if head touches bottom border
		if (y[0] > SCREEN_HEIGHT-UNIT_SIZE) {state = gameState.GAMEOVER;}
		

		if (state == gameState.DOUBLE)
		{
			for (int i = bodyParts2; i > 0; i--)
			{
				if ((x2[0] == x2[i]) && (y2[0] == y2[i]))
				{
					state = gameState.GAMEOVER;
				}
			}
			
			//check if head touches left border
			if (x2[0] < 0) { state = gameState.GAMEOVER;}
			//checks if head touches right border
			if (x2[0] > SCREEN_WIDTH-UNIT_SIZE) {state = gameState.GAMEOVER;}
			//check if head touches top border
			if (y2[0] < 0) {state = gameState.GAMEOVER;}
			//check if head touches bottom border
			if (y2[0] > SCREEN_HEIGHT-UNIT_SIZE) {state = gameState.GAMEOVER;}
		}
		
		if (state == gameState.GAMEOVER) {timer.stop();}
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if (state == gameState.SINGLE || state == gameState.DOUBLE) 
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
			case KeyEvent.VK_1:
				if(state == gameState.MENU)
				{
					state = gameState.SINGLE;
					newApple();
					timer.start();
				}
			case KeyEvent.VK_2:
				if(state == gameState.MENU)
				{
					state = gameState.DOUBLE;
					newApple();
					timer.start();
				}
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
