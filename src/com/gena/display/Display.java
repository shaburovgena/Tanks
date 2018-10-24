package com.gena.display;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import javax.swing.JFrame;

import com.gena.IO.Input;


public abstract class Display {
	
	private static boolean created = false;
	private static JFrame window;
	private static Canvas content;
	
	private static BufferedImage buffer;
	private static int[] bufferData;
	private static Graphics bufferGraphics;
	private static int clearColor;
	
	
	private static BufferStrategy bufferStrategy;
	public static void create (int width, int height, String title, int _clearColor, int numBuffers) {
		if (created)
			return;
		
		window = new JFrame(title);//создаем окно 
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//при нажатии креста закрываться
		content = new Canvas(){};//заполняем окно
		Dimension size = new Dimension(width, height);//чтобы передать размер заполнения
		content.setPreferredSize(size);//устанавливаем размер заполнения		
		
		window.setResizable(false);//запрещаем изменять размер
		window.getContentPane().add(content);//добавляем в окно контент
		window.pack();//размер окна по размеру заполняемого контента
		
		window.setLocationRelativeTo(null);	//окно по середине экрана	
		window.setVisible(true);//окно видимо
		
				
		buffer = new BufferedImage(width, height,  BufferedImage.TYPE_INT_ARGB);//создаем буфер типа изображение 
		bufferData =((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();//сохраняем в bufferData изображение как массив простых чисел
		bufferGraphics = buffer.getGraphics();//возвращает изображение 
		((Graphics2D)bufferGraphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//отрисовываем графику со сглаживанием и кастуем сноова в Graphics2D
		clearColor = _clearColor;
		
		content.createBufferStrategy(numBuffers);//создаем буферизацию холста для более плавной отрисовки
		bufferStrategy = content.getBufferStrategy();
		
		created = true;
		
		
		
	}
	
	public static void clear(){
		Arrays.fill(bufferData,  clearColor);//метод очистки дисплея - массив заполняется пикселями из bufferData значениями hex цвета clearColor
		
	}
	
	public static void swapBuffers(){
		Graphics g = bufferStrategy.getDrawGraphics(); //для хранения изображения в буферах
		g.drawImage(buffer,  0,  0, null);//отрисовываем изображение buffer с начальными координатами 0, 0
		bufferStrategy.show(); 
	}
	public static Graphics2D getGraphics(){
		return (Graphics2D) bufferGraphics; //метод который возвращает bufferGraphics как объект Graphics2D
	}
	
	public static void destroy(){
		if(created)
			return;
		window.dispose();
	}
	
	public static void setTitle(String title){
		
		window.setTitle(title);//метод который устанавливает заголовок окна
	}
	
	public static void addInputListener(Input inputListener){
		
		window.add(inputListener);//метод добавляет в окно слушателя событий ввода
	}
}
