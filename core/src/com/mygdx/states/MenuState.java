package com.mygdx.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.mygdx.game.Stroid;
import com.mygdx.managers.GameStateManager;

public class MenuState extends GameState{
	
	private SpriteBatch sb;
	private BitmapFont title;
	private BitmapFont menu;
	private final String TITLE = "Berkostroids!";
	private int currentOption;
	private String[] menuItems;

	public MenuState(GameStateManager gm) {
		super(gm);
	}
	
	public void init(){
		FreeTypeFontGenerator fg = new FreeTypeFontGenerator(
									Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		FreeTypeFontParameter fp = new FreeTypeFontParameter();
		sb = new SpriteBatch();
		fp.size = 80;
		fp.color = Color.WHITE;
		title = fg.generateFont(fp);
		
		fp.size = 55;
		menu = fg.generateFont(fp);
		currentOption = 0;
		menuItems = new String[] {"Play", "Quit"};
	}
	public void update(float dt){
		input();
		
	}
	public void draw(){
		sb.setProjectionMatrix(Stroid.cam.combined);
		
		sb.begin();
		float width = title.getBounds(TITLE).width;
		title.draw(sb, TITLE, (Stroid.W - width) / 2, 500);
		for (int i = 0; i < menuItems.length; i++) {
			if (currentOption == i) {
				menu.setColor(Color.MAGENTA);
			} else {
				menu.setColor(Color.WHITE);
			}
			width = menu.getBounds(menuItems[i]).width;
			menu.draw(sb, menuItems[i], (Stroid.W - width) / 2, 350 - 95 * i);

		}
		sb.end();
	}
	public void input(){
		if (Gdx.input.isKeyJustPressed(Keys.UP)) {
			if (currentOption > 0) currentOption--;
			else {
				currentOption = menuItems.length - 1;
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if (currentOption < menuItems.length - 1) currentOption++;
			else {
				currentOption = 0;
			}
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			select();
		}
	}
	
	private void select(){
		switch (currentOption) {
			case 0:
				gm.setState(GameStateManager.PLAY);
				break;
			case 1:
				Gdx.app.exit();
				break;
		}
	}
	
	public void dispose(){
		
	}
	
}
