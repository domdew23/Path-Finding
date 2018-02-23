package com.dom.pathfinding;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dom.pathfinding.settings.Control;
import com.dom.pathfinding.tools.PriorityQueue;

public class Pathfinding extends ApplicationAdapter {
	SpriteBatch batch;
	Texture[] textures;
	ShapeRenderer shapes;
	int width, height, cellWidth, cellHeight;
	Control c;
	
	public void create () {
		batch = new SpriteBatch();
		width = 10;
		height = 10;
		cellWidth= Gdx.graphics.getWidth() / width;
		cellHeight = Gdx.graphics.getHeight() / height;
		c = new Control(width, height);
		c.printNodes();
		textures = new Texture[11];
		generateTextures();
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		draw();
		batch.end();
		
	}
	
	private void draw() {
		for (int x = 0; x < width * cellWidth; x+=cellWidth) {
			for (int y = 0; y < height * cellHeight; y+=cellHeight) {
				batch.draw(textures[c.getNodes()[x / cellWidth][y / cellHeight]], x, y, cellWidth, cellHeight);
			}
		}
	}
	
	private void generateTextures() {
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new Texture("cell_" + i + ".png");
		}
	}
	
	public void dispose () {
		batch.dispose();
	}
}
