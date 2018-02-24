package com.dom.pathfinding;

import com.badlogic.gdx.Game;
import com.dom.pathfinding.screens.GridScreen;

public class Pathfinding extends Game {
	public void create () {
		setScreen(new GridScreen());
	}

	public void render () {
		super.render();
	}
	
	public void dispose () {
		super.dispose();
	}
}
