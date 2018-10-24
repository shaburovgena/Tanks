package com.gena.game;

import java.awt.Graphics2D;

import com.gena.IO.Input;

public abstract class Entity {
	
	public final EntityType type;
	
	public float x;
	public float y;
	
	public Entity (EntityType type, float x, float y){
		
		this.type = type;
		this.x = x;
		this.y = y;
		
	}
	
	public abstract void update(Input input);
	public abstract void render (Graphics2D g);

}
