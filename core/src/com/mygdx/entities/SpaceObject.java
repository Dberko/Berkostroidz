package com.mygdx.entities;

import com.mygdx.game.Stroid;

public class SpaceObject {
	protected float x, y;
	protected float dx, dy;
	protected float rad;
	protected float speed, rotSpeed;
	protected float width, height;
	protected float[] shapex;
	protected float[] shapey;
	
	public float getX(){ return x; }
	public float getY(){ return y; }

	public float[] getShapeX(){ return shapex; }
	public float[] getShapeY(){ return shapey; }
	
	
	public void setPos(float x, float y){
		this.x = x;
		this.y = y;
	}	
	
	public boolean intersects(SpaceObject other) {
		float[] sx = other.getShapeX();
		float[] sy = other.getShapeY();
		
		for (int i = 0; i < sx.length; i++) {
			if (contains(sx[i], sy[i])) {
				return true;
			}
		}
		return false; 
	}
	
	public boolean contains(float x, float y){
		boolean b = false;
		for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
			if ( (shapey[i] > y) != (shapey[j] > y) &&
					(x < (shapex[j] - shapex[i]) * 
					(y - shapey[i]) / (shapey[j] - 
					shapey[i]) + shapex[i])) {
						
					b = !b;
			}
		}
		return b;
	}

	
	public SpaceObject(){	
		
	}
	
	public void wrap(){
		if (x > Stroid.W) {
			x = 0;
		}
		if (x < 0) x = Stroid.W;
		if (y < 0) y = Stroid.H;
		if (y > Stroid.H) {
			y = 0;
		}
	}
}
