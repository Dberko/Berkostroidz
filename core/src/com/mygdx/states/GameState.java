package com.mygdx.states;

import com.mygdx.managers.GameStateManager;

public abstract class GameState {
	protected GameStateManager gm;
	
	protected GameState( GameStateManager gm) {
		
		this.gm = gm;
		init();
	}
	
	public void init(){
		
	}
	public void update(float dt){
		
	}
	public void draw(){
	
	}
	
	public void input(){
		
	}
	public void dispose(){
		
	}
	
}
