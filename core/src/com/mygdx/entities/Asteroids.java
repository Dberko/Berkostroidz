package com.mygdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Asteroids extends SpaceObject {

	private int type;
	public static final int S = 0;
	public static final int M = 1;
	public static final int L = 2;
	
	private int numPoints;
	private float[] dists;
	private boolean rem;
	
	private int score;
	
	public Asteroids(float x, float y, int type){
		this.x = x;
		this.y = y;
		this.type = type;
		
		if (type == S) {
			numPoints = 6;
			width = height = 30;
			speed = MathUtils.random(70,100);
			score = 100;
		} else if (type == M) {
			numPoints = 10;
			width = height = 45;
			speed = MathUtils.random(40,70);
			score = 50;
		} else if (type == L) {
			numPoints = 12;
			width = height = 65;
			speed = MathUtils.random(10,30);
			score = 20;
		}
		
		rotSpeed = MathUtils.random(-1, 1);
		rad = MathUtils.random(2 * 3.1415f);
		dx = MathUtils.cos(rad) * speed;
		dy = MathUtils.sin(rad) * speed;
		
		shapex = new float[numPoints];
		shapey = new float[numPoints];
		dists = new float[numPoints];
		int radius = (int) (width / 2);
		for (int i = 0; i < numPoints; i++) {
			dists[i] = MathUtils.random(radius / 2, radius);
		}

		setShape();
		
	}
	
	public int getScore(){
		return score;
	}
	
	private void setShape(){
		float angle = 0;
		for(int i = 0; i < numPoints; i++) {
			shapex[i] = x + MathUtils.cos(angle + rad) * dists[i];
			shapey[i] = y + MathUtils.sin(angle + rad) * dists[i];
			angle += 2 * 3.1415f / numPoints;

		}
	}
	
	public int getType() {
		return type;
	}
	
	public boolean rem() {
		return rem;
	}
	
	public void update(float dt) {
		x += dx * dt;
		y += dy * dt;
		rad += rotSpeed * dt;
		setShape();
		wrap();
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(1,1,1,1);
		sr.begin(ShapeType.Line);
		for(int i = 0, j = shapex.length - 1;
				i < shapex.length;
				j = i++) {
				
				sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
				
				}
		sr.end();
	}
	
	
	
}
