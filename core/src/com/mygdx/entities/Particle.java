package com.mygdx.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;

public class Particle extends SpaceObject{
	
	// Explosions
	
	public float timer, time;
	private boolean remove;
	
	public float r, g, b;

	public boolean rem(){ return remove; }

	public Particle(float x, float y){
		this.x = x;
		this.y = y;
		width = height = 2;
		speed = 50;
		rad = MathUtils.random(-2*3.1415f, 2*3.1415f);
		dx = MathUtils.cos(rad) * speed;
		dy = MathUtils.sin(rad) * speed;
		timer = 0;
		time = MathUtils.random(0.2f, .5f);
		remove = false;
		g = MathUtils.random(0, .6f);
		b = MathUtils.random(0, .2f);
		r = MathUtils.random(.8f, 1f);

	}
	
	public void update(float dt) {
		x += dx * dt;
		y += dy * dt;
		timer += dt;
		if (timer > time) {
			remove = true;
			timer = 0;
		}

		
	}
	
	public void draw(ShapeRenderer sr) {
		sr.setColor(r,g,b,b);
		sr.begin(ShapeType.Filled);
		sr.circle(x - width / 2, y - width / 2, width / 2);
		sr.end();
	}
	
	
	
}
