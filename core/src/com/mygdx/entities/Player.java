package com.mygdx.entities;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.Stroid;
import com.mygdx.managers.Boombox;

public class Player extends SpaceObject{
	private ArrayList<Bullet> b;
	private final int MAXB = 4;
	
	private float[] fx, fy;
	private boolean left, up, right;
	private float maxSpeed, acc, dec, at; // pixels per second
	
	private boolean hit, dead;
	private float hitTimer;
	private float hitTime;
	private Line2D.Float[] hitLines;
	private Point2D.Float[] hitLinesVector;
	
	private long score;
	private int extraLives;
	private long requiredScore;
	

	public boolean isHit() { return hit; }

	public boolean isDead() { return dead; }
	
	public long getScore(){ return score;}
	
	public int getLives(){ return extraLives;}
	
	public void loseLife(){ extraLives--;}
	
	public void incrementScore(int i){ score += i;}

	
	public Player(ArrayList<Bullet> bullets){
		this.b = bullets;
		
		this.x = Stroid.W / 2;
		this.y = Stroid.H / 2;
		
		maxSpeed = 300;
		acc = 200;
		dec = 20;
		at = 0;	
		
		shapex = new float[4];
		shapey = new float[4];
		
		fx = new float[3];
		fy = new float[3];
		
		rad = 3.14152f / 2;
		rotSpeed = 3;
		
		hitTimer = 0;
		hitTime = 2;
		
		score = 0;
		requiredScore = 10000;
		extraLives = 3;
	}
	
	public void setPos(float x, float y) {
		super.setPos(x, y);
		setShape();
	}
	
	private void setShape() {
		shapex[0] = x + MathUtils.cos(rad) * 8;
		shapey[0] = y + MathUtils.sin(rad) * 8;
		
		shapex[1] = x + MathUtils.cos(rad - 4 * 3.1415f / 5) * 8;
		shapey[1] = y + MathUtils.sin(rad - 4 * 3.1415f / 5) * 8;
		
		shapex[2] = x + MathUtils.cos(rad + 3.1415f) * 5;
		shapey[2] = y + MathUtils.sin(rad + 3.1415f) * 5;
		
		shapex[3] = x + MathUtils.cos(rad + 4 * 3.1415f / 5) * 8;
		shapey[3] = y + MathUtils.sin(rad + 4 * 3.1415f / 5) * 8;
	}
	
	private void flameOn(){
		fx[0] = x + MathUtils.cos(rad - 5 * 3.1415f / 6) * 5;
		fy[0] = y + MathUtils.sin(rad - 5 * 3.1415f / 6) * 5;
		
		fx[1] = x + MathUtils.cos(rad - 3.1415f) * (6 + at * 50);
		fy[1] = y + MathUtils.sin(rad - 3.1415f) * (6 + at * 50);
		
		fx[2] = x + MathUtils.cos(rad + 5 * 3.1415f / 6) * 5;
		fy[2] = y + MathUtils.sin(rad + 5 * 3.1415f / 6) * 5;

	}
	
	public void setLeft(boolean b) { left = b; }
	public void setRight(boolean b) { right = b; }
	public void setUp(boolean b) { up = b; }
	
	public void shoot(){
		if (b.size() == MAXB || hit == true) {
			return;
		}
		b.add(new Bullet(x, y, rad));
		Boombox.play("Laser");

		
	}
	
	public void hit(){
		if (hit) return;
		
		hit = true;
		dx = dy = 0;
		left = right = up = false;
		hitLines = new Line2D.Float[4];
		
		for (int i = 0, j = hitLines.length - 1; 
			i < hitLines.length; j = i++) {
				
				hitLines[i] = new Line2D.Float(
							shapex[i], shapey[i],
							shapex[j], shapey[j]);
		}
		
		hitLinesVector = new Point2D.Float[4];
		hitLinesVector[0] = new Point2D.Float(
								MathUtils.cos(rad + 1.5f), 
								MathUtils.sin(rad + 1.5f));
		hitLinesVector[1] = new Point2D.Float(
								MathUtils.cos(rad - 1.5f), 
								MathUtils.sin(rad - 1.5f));
		hitLinesVector[2] = new Point2D.Float(
								MathUtils.cos(rad - 2.8f), 
								MathUtils.sin(rad - 2.8f));
		hitLinesVector[3] = new Point2D.Float(
								MathUtils.cos(rad + 2.8f), 
								MathUtils.sin(rad + 2.8f));
		
		
	}
	
	public void reset(){
		x = Stroid.W / 2;
		y = Stroid.H / 2;
		setShape();
		hit = dead = false;
	}
	
	public void update(float dt) {
		if (hit) {
			hitTimer += dt;
			if (hitTimer > hitTime) {
				dead = true;
				hitTimer = 0;
			}
			for (int i = 0; i < hitLines.length; i++) {
				hitLines[i].setLine(
								hitLines[i].x1 + hitLinesVector[i].x * 10 * dt,
								hitLines[i].y1 + hitLinesVector[i].y * 10 * dt,
								hitLines[i].x2 + hitLinesVector[i].x * 10 * dt,
								hitLines[i].y2 + hitLinesVector[i].y * 10 * dt);
			}
			
			return;
		}
		
		// check extra lives
		if (score >= requiredScore) {
			extraLives++;
			requiredScore += 10000;
		}
		
		if (left) {
				rad += rotSpeed * dt;
		} else if (right) {	
				rad -= rotSpeed * dt;
		}
		
		if (up) {
			dx += MathUtils.cos(rad) * acc * dt;
			dy += MathUtils.sin(rad) * acc * dt;
			at += dt;
			if (at > 0.1f) {
				at = 0;	
			}
		} else {
			at = 0;
		}
		
		// decceleration
		float vec = (float) Math.sqrt(dx*dx + dy*dy);
		if (vec > 0) {
			dx -= (dx / vec) * dec * dt;
			dy -= (dy / vec) * dec * dt;
		}
		if (vec > maxSpeed) {
			dx = (dx / vec) * maxSpeed;
			dy = (dy / vec) * maxSpeed;
		}
		
		// set position
		x += dx * dt;
		y += dy * dt;
		
		
		// set shape
		setShape();
		
		flameOn();

		
		// screen wrap
		wrap();
		
		
	}
	
	public void draw(ShapeRenderer sr) {
			
			sr.setColor(1, 1, 1, 1);
			
			sr.begin(ShapeType.Line);
			
			if (hit) {
				for (int i = 0; i < hitLines.length; i++) {
					sr.line(
							hitLines[i].x1,
							hitLines[i].y1,
							hitLines[i].x2,
							hitLines[i].y2
							);
				}
				sr.end();
				return;
			}
			
			for(int i = 0, j = shapex.length - 1;
				i < shapex.length;
				j = i++) {
				
				sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
				
			}
			if (up) {
				for(int i = 0, j = fx.length - 1;
						i < fx.length;
						j = i++) {
						
						sr.line(fx[i], fy[i], fx[j], fy[j]);
						
					}
			}
			
			sr.end();
			
		}
	
	
}
