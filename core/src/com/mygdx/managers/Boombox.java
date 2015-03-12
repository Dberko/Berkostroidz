
//____________ _.----------.-._ ____________       
//|            |    __          |            |      
//|  .''.  O   | o |__| |||| |  |   O  .''.  |      
//| :    :     |________________|     :    : |      
//|  '..'   _  |oo  .,..        |  _   '..'  |      
//|        (_) | ....|...... OO | (_)        |      
//|            |________________|            |      
//|   .-''-.   |    ________  mm|   .-''-.   |      
//|  /      \  |   |O;;;  :O|   |  /      \  |      
//| (        ) | m '--------' m | (        ) |      
//|  \      /  |________________|  \      /  |      
//|   '-..-'   |____|__|__|__|__|   '-..-'   |     
//|____________|________________|____________|     
//|___|----|___|___|--------|___|___|----|___| 
//

package com.mygdx.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;


public class Boombox {
	
	private static HashMap<String, Sound> sounds;
	static {
		sounds = new HashMap<String, Sound>();
	}
	
	public static void load(String path, String name) {
		Sound s = Gdx.audio.newSound(Gdx.files.internal(path));
		sounds.put(name, s);
		
	}
	
	public static void play(String name) {
		sounds.get(name).play();
		
	}
	
	public static void loop(String name) {
		sounds.get(name).loop();
	 }
	
	public static void stop(String name) {
		sounds.get(name).stop();
	}
	
	public static void stopAll() {	
		for (Sound s : sounds.values()) {
			s.stop();
		}
	}
	
	
}
