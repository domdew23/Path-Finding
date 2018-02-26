package com.dom.pathfinding.screens;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.dom.pathfinding.algorithms.AStar;
import com.dom.pathfinding.algorithms.Dijkstra;
import com.dom.pathfinding.tools.Control;

public class GridScreen implements Screen {

	private final OrthographicCamera camera;
	private final Viewport viewPort;
	
	private final SpriteBatch batch;
	private final Texture[] textures;
	private final int width, height, cellWidth, cellHeight;
	private final Control c;
	private final boolean[][] clicked;
	private final Dijkstra d;
	private ArrayList<int[]> shortestPath;
	private int srcX, srcY, goalX, goalY;
	private boolean srcSet, goalSet, done, doDijkstra, doAStar;
	private final AStar a;
	
	public GridScreen() {
		this.camera = new OrthographicCamera();
		this.viewPort = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.camera.position.set(viewPort.getWorldWidth(), viewPort.getWorldHeight(), 0);
		this.batch = new SpriteBatch();
		this.width = 10;
		this.height = 10;
		this.cellWidth= Gdx.graphics.getWidth() / width;
		this.cellHeight = Gdx.graphics.getHeight() / height;
		this.c = new Control(width, height);
		this.textures = new Texture[14];
		this.clicked = new boolean[width][height];
		this.generateTextures();
		this.srcX = -1;
		this.srcY = -1;
		this.goalX = -1;
		this.goalY = -1;
		this.srcSet = false;
		this.goalSet = false;
		this.doDijkstra = false;
		this.doAStar = true;
		this.done = false;
		this.d = new Dijkstra(c.getNodes(), c.getTeleports(), c.getTeleportCount(), width, height);
		this.a = new AStar(c.getNodes(), c.getTeleports(), c.getTeleportCount(), width, height);
		this.shortestPath = new ArrayList<int[]>();
	}
	
	public void show() {
		
	}
	
	private void handleInput(float delta) {
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && !srcSet && !done) {
			srcX = Gdx.input.getX()/cellWidth;
			srcY = Gdx.input.getY()/cellHeight;
			clicked[srcX][srcY] = true;
			srcSet = true;
			System.out.println("Src Set.");
			try { Thread.sleep(250);}catch (InterruptedException e) {}
		} else if (Gdx.input.isButtonPressed(Input.Buttons.LEFT) && srcSet && !done) {
			goalX = Gdx.input.getX()/cellWidth;
			goalY = Gdx.input.getY()/cellHeight;
			if (goalX == srcX && goalY == srcY) return;
			clicked[goalX][goalY] = true;
			goalSet = true;
			System.out.println("Goal Set.");
			try {Thread.sleep(250);}catch (InterruptedException e) {}
		}
	}

	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput(delta);
		
		if (srcSet && goalSet) {
			done = true;
			srcSet = false;
			goalSet = false;
			if (doDijkstra) {
				d.run(srcX, srcY, goalX, goalY);
				shortestPath = d.getShortestPath();
			} else if (doAStar) {
				a.run(srcX, srcY, goalX, goalY);
				shortestPath = a.getShortestPath();
			}
		}
		batch.begin();
		draw();
		batch.end();
		camera.update();
	}
	
	private void draw() {
		for (int y=0, i=height-1; y < height * cellHeight && i >= 0; y+=cellHeight, i--) {
			for (int x=0, j=0; x < width * cellWidth && j < width; x+=cellWidth, j++) {
				byte node = c.getNodes()[j][i];
				Texture t = null;
				if (shortestPath.size() != 0) {
					t = drawShortest(i, j, node);
				}
				else if (clicked[j][i] && node != 10) t = textures[11];
				else t = textures[node];
				batch.draw(t, x, y, cellWidth, cellHeight);
			}
		}
	}
	
	private Texture drawShortest(int i, int j, int node) {
		Texture t = null;
		for (int[] path : shortestPath) {
			if (path[0] == j && path[1] == i) {
				t = textures[13];
				break;
			} else if (clicked[j][i] && node != 10) {
				t = textures[11];
			} else if (doAStar && a.getVisited()[j][i]){
				t = textures[12];
			} else if (doDijkstra && d.getVisited()[j][i]) {
				t = textures[12];
			} else {
				t = textures[node];
			}
		}
		return t;
	}
	
	private void generateTextures() {
		for (int i = 0; i < textures.length; i++) {
			textures[i] = new Texture("cell_" + i + ".png");
		}
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				clicked[x][y] = false;
			}
		}
	}

	public void resize(int width, int height) {
		viewPort.update(width, height);
	}

	public void pause() {
	}

	public void resume() {
	}

	public void hide() {
	}

	public void dispose() {
	}

}
