package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dom.pathfinding.Pathfinding;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Path Finding";
		config.width = 1000;
		config.height = 1000;
		new LwjglApplication(new Pathfinding(), config);
	}
}
