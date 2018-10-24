package com.gena.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.gena.utils.Utils;

public class Sprite {
	
	private SpriteSheet sheet;
	private float scale;
	private BufferedImage image;
	public Sprite(SpriteSheet sheet, float scale){
		this.scale = scale;
		this.sheet = sheet;
		image = sheet.getSprite(0);
		image = Utils.resize(image, (int)(image.getWidth()*scale), (int)(image.getHeight()*scale));
		
	}

	public void render(Graphics2D g, float x, float y){
		
		
		
		g.drawImage(sheet.getSprite(0), (int)(x), (int)(y), null);
		
	}
}

