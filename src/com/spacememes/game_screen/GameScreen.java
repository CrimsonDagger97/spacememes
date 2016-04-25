package com.spacememes.game_screen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.spacememes.display.Display;
import com.spacememes.handler.EnemyBulletHandler;
import com.spacememes.levels.Level;
import com.spacememes.state.StateMachine;
import com.spacememes.state.SuperStateMachine;
import com.spacememes.timer.TickTimer;
/*
 * displays game
 */
public class GameScreen extends SuperStateMachine {
	
	private Player player;
	private BasicBlocks blocks;
	private Level level;
	private EnemyBulletHandler bulletHandler;
	
	public static int SCORE = 0;
	
	private Font gameScreen = new Font("Arial", Font.PLAIN, 48);
	private TickTimer gameOverTimer = new TickTimer(180);
	private TickTimer completeTimer = new TickTimer(180);
	
	public GameScreen(StateMachine stateMachine){
		super(stateMachine);
		blocks = new BasicBlocks();
		bulletHandler = new EnemyBulletHandler();
		player = new Player(Display.WIDTH/2-50, Display.HEIGHT-75, 50, 50, blocks);
		level = new Level(player, bulletHandler);
	}
	
	@Override
	public void update(double delta) {
		player.update(delta);
		level.update(delta, blocks);
		
		if (level.isGameOver()) {
			gameOverTimer.tick(delta);
			if (gameOverTimer.isEventReady()) {
				level.reset();
				blocks.reset();
				getStateMachine().setState((byte) 0);
				SCORE = 0;
			}
		}
		
		if (level.isComplete()) {
			completeTimer.tick(delta);
			if (completeTimer.isEventReady()) {
				level.reset();
			}
		}
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.drawString("Score: " + SCORE, 5, 15);
		
		g.setColor(Color.red);
		g.drawString("Health: " + player.getHealth(), 5, 35);
		
		blocks.draw(g);
		player.draw(g);
		level.draw(g);
		
		if (level.isGameOver()) {
			g.setColor(Color.orange);
			g.setFont(gameScreen);
			String gameOver = "THEY STOLE YOUR MEMES!";
			int gameOverWidth = g.getFontMetrics().stringWidth(gameOver);
			g.drawString(gameOver, (Display.WIDTH/2)-(gameOverWidth/2), Display.HEIGHT/2);
		}
		
		if (level.isComplete()) {
			g.setColor(Color.blue);
			g.setFont(gameScreen);
			String complete = "YOUR MEMES ARE SAFE!";
			int completeWidth = g.getFontMetrics().stringWidth(complete);
			g.drawString(complete, (Display.WIDTH/2)-(completeWidth/2), Display.HEIGHT/2);
		}
	}

	@Override
	public void init(Canvas canvas) {
		canvas.addKeyListener(player);
	}

}