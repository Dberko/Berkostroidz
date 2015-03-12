package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.managers.Boombox;
import com.mygdx.managers.GameStateManager;

public class Stroid extends ApplicationAdapter {
	public static int W, H;
	public static OrthographicCamera cam;
	SpriteBatch batch;
	Texture img;
	
	private GameStateManager gm;
		
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		W = Gdx.graphics.getWidth();
		H = Gdx.graphics.getHeight();
		
		cam = new OrthographicCamera(W, H);
		cam.translate(W/2, H/2);
		cam.update();
		
		Boombox.load("sounds/Hit.wav", "Hit");
		Boombox.load("sounds/Laser_Shoot.wav", "Laser");
		Boombox.load("sounds/Explosion.wav", "Explosion");

		
		gm = new GameStateManager();
		//img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gm.update(Gdx.graphics.getDeltaTime());		
		batch.begin();
		gm.draw();
		//batch.draw(img, 0, 0);
		batch.end();
	}
	
	public void resize(int width, int height) {
		
	}
	
	public void pause(){
		
		
	}
	
	public void resume(){
		
		
	}
	
	public void dispose(){
		
		
	}
}
