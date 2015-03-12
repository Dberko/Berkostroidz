package com.mygdx.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {

	private float lt, ltr; // life time(r)
	public boolean rem;
	
	public Bullet(float x, float y, float radians){
		this.x = x;
		this.y = y;
		this.rad = radians;
		
		float speed = 350;
		dx = MathUtils.cos(rad) * speed;
		dy = MathUtils.sin(rad) * speed;
		
		width = height = 2;
		
		ltr = 0;
		lt = 2;

	}
	
	public void update(float dt) {
		x += dx * dt;
		y += dy * dt;
		wrap();
		ltr += dt;
		if (ltr > lt) {
			rem = true;
		}
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(Color.CYAN);
		sr.begin(ShapeType.Filled);
		sr.circle(x - width / 2, y - height / 2, width / 2);
		sr.end();
	}
	
	
}
