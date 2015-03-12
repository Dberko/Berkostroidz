package com.mygdx.states;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.entities.Asteroids;
import com.mygdx.entities.Bullet;
import com.mygdx.entities.Particle;
import com.mygdx.entities.Player;
import com.mygdx.game.Stroid;
import com.mygdx.managers.Boombox;
import com.mygdx.managers.GameStateManager;

public class PlayState extends GameState{
	private Player p;
	private Player pHud; // extra lives
	private ShapeRenderer sr;
	private SpriteBatch sb;
	private ArrayList<Bullet> b;
	private ArrayList<Asteroids> a;
	private ArrayList<Particle> ps; 
	
	private BitmapFont font;
	private BitmapFont gameOverFont;
	
	private int level;
	private int totalAsteroids;
	private int numAsteroids;

	public PlayState(GameStateManager gm) {
		super(gm);
	}
	
	public void init(){
		sr = new ShapeRenderer();
		b = new ArrayList<Bullet>();
		p = new Player(b);
		a = new ArrayList<Asteroids>();
		ps = new ArrayList<Particle>();
		sb = new SpriteBatch();
		pHud = new Player(null); 
		
		//font stoof
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(
				Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = 35;
		font = gen.generateFont(parameter);
		FreeTypeFontParameter gameOverParameter = new FreeTypeFontParameter();
		gameOverParameter.size = 100;
		gameOverFont = gen.generateFont(gameOverParameter);
		
		
		level = 1;
		spawnAsteroids();
	}
	
	private void collision(){
		
			
			// player collision
			if (!p.isHit()) {
				for (int i = 0; i < a.size(); i++) {
					Asteroids ta = a.get(i);
					if (ta.intersects(p)) {
						Boombox.play("Explosion");
						p.hit();
						a.remove(i);
						i--;
						splitAsteroids(ta);
						break;
						
					}
					
				}
			}
			
			
			// bullet collision
			for (int i = 0; i < b.size(); i++) {
				Bullet tb = b.get(i);
				for (int j = 0; j < a.size(); j++) {
					Asteroids ta = a.get(j);
					// asteroid contains bullet
					if (ta.contains(tb.getX(), tb.getY())) {
						Boombox.play("Hit");
						b.remove(i);
						i--;
						a.remove(j);
						j--;
						splitAsteroids(ta);
						p.incrementScore(ta.getScore());
						break;
					}
					
				}
			}
		}
		
	
	
	private void createParticles(float x, float y) {
		for (int i = 0; i < 6; i++) {
			ps.add(new Particle(x, y));

		}
	}
	
	private void splitAsteroids(Asteroids ta) {
		createParticles(ta.getX(), ta.getY());
		numAsteroids--;
		if (ta.getType() == Asteroids.L) {
			a.add(new Asteroids(ta.getX(), ta.getY(), Asteroids.M));
			a.add(new Asteroids(ta.getX(), ta.getY(), Asteroids.M));
		}
		if (ta.getType() == Asteroids.M) {
			a.add(new Asteroids(ta.getX(), ta.getY(), Asteroids.S));
			a.add(new Asteroids(ta.getX(), ta.getY(), Asteroids.S));
		}
		if (ta.getType() == Asteroids.S) {
			return;
		}
	}
	
	private void spawnAsteroids() {
		a.clear();
		int numToSpawn = 4 + 4 * (level - 1);
		totalAsteroids = numToSpawn * 7;
		
		for (int i = 0; i < numToSpawn; i++) {
			float x = MathUtils.random(Stroid.W);
			float y = MathUtils.random(Stroid.H);
			float dx = x - p.getX();
			float dy = y - p.getY();
			float dist = (float) Math.sqrt(dx*dx + dy*dy);
			
			while (dist < 100) {
				x = MathUtils.random(Stroid.W);
				y = MathUtils.random(Stroid.H);
				dx = x - p.getX();
				dy = y - p.getY();
				dist = (float) Math.sqrt(dx*dx + dy*dy);
			}
			
			a.add(new Asteroids(x,y,Asteroids.L));
		}
	}
	
	public void update(float dt){
		input();
		
		// levels
		if (a.size() == 0) {
			level++;
			spawnAsteroids();
			return;
			
		}
		
		// player
		p.update(dt);
		if (p.isDead()) {
			if (p.getLives() == 0) {
				
				gm.setState(GameStateManager.MENU);
			}
			p.reset();
			p.loseLife();
			return;
		}
		
		
		
		// player bullets
		for (int i = 0; i < b.size(); i++) {
			b.get(i).update(dt);
			if (b.get(i).rem) {
				b.remove(i);
				i--;
			}
		}
		// asteroids
		for (int i = 0; i < a.size(); i++) {
			a.get(i).update(dt);
			if (a.get(i).rem()) {
				a.remove(i);
				i--;
			}
		}
		
		// particles
		for (int i = 0; i < ps.size(); i++) {
			ps.get(i).update(dt);
			if (ps.get(i).rem()) {
				ps.remove(i);
				i--;
			}
		}
		
		collision();

	}
	
	
	public void draw(){
		p.draw(sr);
		
		// bullet
		for (int i = 0; i < b.size(); i++) {
			b.get(i).draw(sr);
		}
		
		// asteroid
		for (int i = 0; i < a.size(); i++) {
			a.get(i).draw(sr);
		}
		
		// particle
		for (int i = 0; i < ps.size(); i++) {
			ps.get(i).draw(sr);
		}
		
		// color
		sb.setColor(1,1,1,1);
		sb.begin();
		font.draw(sb, Long.toString(p.getScore()), 760, 560);
		sb.end();
		
		// HUD
		for (int i = 0; i < p.getLives(); i++) {
			pHud.setPos(765 + i * 15, 510);
			pHud.draw(sr);
				
		}
		
		if (p.isHit() && p.getLives() == 0) {
			sb.begin();
			float fw = gameOverFont.getBounds("GAME OVER").width;
			gameOverFont.draw(sb, "GAME OVER", (Stroid.W - fw) / 2, (Stroid.H / 2) + 75);
			sb.end();
		}
	
	}
	
	public void input(){
		p.setLeft(Gdx.input.isKeyPressed(Keys.LEFT));
		p.setRight(Gdx.input.isKeyPressed(Keys.RIGHT));
		p.setUp(Gdx.input.isKeyPressed(Keys.UP));
		
		if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			p.shoot();
		}
	}
	
	
	public void dispose(){
		
	}
	
}
