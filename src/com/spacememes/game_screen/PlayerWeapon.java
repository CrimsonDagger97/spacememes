package com.spacememes.game_screen;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.spacememes.explosion.ExplosionManager;
import com.spacememes.player_bullets.Rocket;
import com.spacememes.player_bullets.PlayerWeaponType;
import com.spacememes.sound.Sound;
import com.spacememes.timer.Timer;

public class PlayerWeapon {

	private Timer timer;
	private ExplosionManager explosionManager;
	public ArrayList<PlayerWeaponType> weapons = new ArrayList<PlayerWeaponType>();
	private Sound shootSound;
	
	public PlayerWeapon(){
		explosionManager = new ExplosionManager();
		timer = new Timer();
		shootSound = new Sound("/com/spacememes/sounds/shoot.wav");
	}
	
	public void draw(Graphics2D g){
		
		explosionManager.draw(g);
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).draw(g);
		}
	}
	
	public void update(double delta, BasicBlocks blocks){
		
		explosionManager.update(delta);
		for(int i = 0; i < weapons.size(); i++){
			weapons.get(i).update(delta, blocks);
			if(weapons.get(i).destory()) {
				ExplosionManager.createPixelExplosion(weapons.get(i).getxPos(), weapons.get(i).getyPos());
				weapons.remove(i);
			}
		}
	}
	
	public void shootBullet(double xPos, double yPos, int width, int height){
		if(timer.timerEvent(250)&& weapons.size() < 5) {
			if (shootSound.isPlaying()) {
				shootSound.stop();     
			}
			shootSound.play();
			weapons.add(new Rocket(xPos + 22, yPos + 15, width, height));
		}
	}

	public void reset() {
		weapons.clear();
	}
}