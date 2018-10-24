package com.gena.graphics;

import java.awt.image.BufferedImage;

import com.gena.utils.ResourceLoader;

public class TextureAtlas {
	
	BufferedImage image;
	
	public TextureAtlas(String imageName){
		
		image = ResourceLoader.loadImage(imageName);//загружает файл texture_atlas.png в image
	}
	
	public BufferedImage cut(int x, int y, int w, int h){
		
		return image.getSubimage(x, y, w, h);// вырезает в image фрагмент с указанными характиристиками
	}

}
