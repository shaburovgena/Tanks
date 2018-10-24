package com.gena.IO;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class Input extends JComponent{ //класс инпут наследует все члены суперкласса джейкомпонент
	
	private boolean[] map;
	
	public Input(){
		map = new boolean[256];
		for(int i=0; i < map.length; i++){
		final int KEY_CODE = i;
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke( i,0,false), Integer.valueOf(i*2));
		getActionMap().put(Integer.valueOf(i*2), new AbstractAction(){
			public void actionPerformed(ActionEvent arg0){
				map[KEY_CODE]=true;
			}
		});
		
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put( KeyStroke.getKeyStroke( i,0,true), Integer.valueOf(i*2+1));
		getActionMap().put(Integer.valueOf(i*2+1), new AbstractAction(){
			public void actionPerformed(ActionEvent arg0){
				map[KEY_CODE]=false;
			}
		});
		}
	}

	public boolean[] getMap(){
		return Arrays.copyOf(map, map.length);
	}
	
	public boolean getKey (int keyCode){
		return map[keyCode];
	}
}
