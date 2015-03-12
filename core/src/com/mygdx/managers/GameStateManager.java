package com.mygdx.managers;

import com.mygdx.states.GameState;
import com.mygdx.states.MenuState;
import com.mygdx.states.PlayState;

public class GameStateManager {
	
	private GameState current;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;

	public static final int HIGHSCORES = 2;
	
	public GameStateManager(){
		
		setState(MENU);
		
	}
	
	public void setState(int state){
		if (current != null) current.dispose();
		if (state == MENU) {
			current = new MenuState(this);

		}
		if (state == PLAY) {
			current = new PlayState(this);
			
		}
		if (state == HIGHSCORES) {
			//current = new ScoreState(this);
		}
		
	}
	
	public void update(float dt) {
		current.update(dt);
		
	}
	
	public void draw() { 
		current.draw();
	}
}
