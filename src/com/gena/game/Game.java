package com.gena.game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import com.gena.IO.Input;
import com.gena.display.Display;
import com.gena.game.level.Level;
import com.gena.graphics.TextureAtlas;
import com.gena.utils.Time;

//int width, int height, String title, int _clearColor, int numBuffers

public class Game implements Runnable {

	public static int WIDTH = 800;
	public static int HEIGHT = 600;
	public static String TITLE = "Tanks";
	public static int CLEAR_COLOR = 0xff000000;
	public static int NUM_BUFFERS = 3;

	public static final String ATLAS_FILE_NAME = "texture_atlas.png";

	public static final float UPDATE_RATE = 30.0f;
	public static final float UPDATE_INTERVAL = Time.SECOND / UPDATE_RATE;
	public static final long IDLE_TIME = 1;

	private boolean running;
	private Thread gameThread;
	private Graphics2D graphics;
	private Input input;
	private TextureAtlas atlas;
	private Player player;
	private Level lvl;


	public Game() {
		running = false;
		Display.create(WIDTH, HEIGHT, TITLE, CLEAR_COLOR, NUM_BUFFERS);
		graphics = Display.getGraphics();
		input = new Input();
		Display.addInputListener(input);
		atlas = new TextureAtlas(ATLAS_FILE_NAME);
		player = new Player(300, 300, 3, 3, atlas);
		lvl = new Level(atlas);
		/*
		 * sheet = new SpriteSheet(atlas.cut(1*16, 9*16, 16, 16), 2, 16); sprite
		 * = new Sprite(sheet, 1);*
		 */
	}

	public synchronized void Start() {
		if (running)
			return;

		running = true;
		gameThread = new Thread(this);
		gameThread.start();
	}

	public synchronized void Stop() {
		if (!running)
			return;

		running = false;
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cleanUp();
	}

	private void update() {

		player.update(input);
		lvl.update();
		/*
		 * if(input.getKey(KeyEvent.VK_UP)){ y -= speed; }
		 * if(input.getKey(KeyEvent.VK_DOWN)){ y += speed; }
		 * if(input.getKey(KeyEvent.VK_LEFT)){ x -= speed; }
		 * if(input.getKey(KeyEvent.VK_RIGHT)){ x += speed; }*
		 */
	}

	private void render() {
		Display.clear();
		lvl.render(graphics);
		player.render(graphics);
		lvl.renderGrass(graphics);
		Display.swapBuffers();
	}

	public void run() {
		int fps = 0;
		int upd = 0;
		int updl = 0;

		long count = 0;

		float delta = 0;

		long lastTime = Time.get();
		while (running) {
			long now = Time.get();
			long elapsedTime = now - lastTime;
			lastTime = now;

			count += elapsedTime;
			boolean render = false;
			delta += (elapsedTime / UPDATE_INTERVAL);

			while (delta > 1) {
				update();
				upd++;
				delta--;
				if (render) {
					updl++;
				} else {
					render = true;
				}
			}
			if (render) {
				render();
				fps++;
			} else {
				try {
					Thread.sleep(IDLE_TIME);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (count >= Time.SECOND) {
				Display.setTitle(TITLE + " || fps:" + fps + " | upd:" + upd
						+ " | updl:" + updl);
				upd = 0;
				fps = 0;
				updl = 0;
				count = 0;
			}
		}
	}

	private void cleanUp() {

	}

}
