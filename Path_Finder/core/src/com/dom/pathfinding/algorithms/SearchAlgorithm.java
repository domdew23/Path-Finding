package com.dom.pathfinding.algorithms;

import java.util.ArrayList;

import com.dom.pathfinding.tools.PriorityQueue;

public abstract class SearchAlgorithm {
	protected byte[][] graph;
	protected int srcX, srcY, goalX, goalY, width, height, teleportCount;
	protected int[][][] teleports;
	protected ArrayList<int[]> shortestPath;
	protected boolean[][] visited;
	protected PriorityQueue queue;
	protected int[][][] cameFrom;

	public SearchAlgorithm (byte[][] graph, int[][][] teleports, int teleportCount, int width, int height){
		this.graph = graph;
		this.width = width;
		this.height = height;
		this.teleports = teleports;
		this.teleportCount = teleportCount;
		this.visited = new boolean[width][height];
		this.queue = new PriorityQueue();
		this.cameFrom = new int[width][height][2];
	}
	
	protected void mainLoop() {
		while (!queue.isEmpty()) {
			int[] node = queue.pull();
			int x=node[0], y=node[1];
			visited[x][y] = true;

			if (x == goalX && y == goalY) {
				shortestPath(x, y);
				return;
			}
			
			if (graph[x][y] == 0) {
				handleTeleport(x, y);
			}
			
			loopNeighbors(x, y);
		}
	}
	
	protected void loopNeighbors(int x, int y) {
		if (x+1 < width) check(x, y, x+1, y);
		if (x-1 >= 0) check(x, y, x-1,y);
		if (y+1 < height) check(x, y, x, y+1);
		if (y-1 >= 0) check(x, y, x, y-1);
		if (x-1 >= 0 && y-1 >= 0) check(x, y, x-1, y-1);
		if (x-1 >= 0 && y+1 < height) check(x, y, x-1, y+1);
		if (x+1 < width && y-1 >= 0) check(x, y, x+1, y-1);
		if (x+1 < width && y+1 < height) check(x, y, x+1, y+1);
	}
	
	protected void handleTeleport(int x, int y) {
		for (int i = 0; i < teleportCount; i++) {
			if ((teleports[i][0][0] == x && teleports[i][0][1] == y)) {
				check(x, y, teleports[i][1][0], teleports[i][1][1]);
			} else if (teleports[i][1][0] == x && teleports[i][1][1] == y) {
				check(x, y, teleports[i][0][0], teleports[i][0][1]);
			}
		}
	}
	
	public ArrayList<int[]> shortestPath(int currentX, int currentY) {
		this.shortestPath = new ArrayList<int[]>();
		
		while (currentX != srcX || currentY != srcY) {
			int[] coords = {cameFrom[currentX][currentY][0], cameFrom[currentX][currentY][1]};
			currentX = coords[0];
			currentY = coords[1];
			int[] tmp = {currentX, currentY};
			shortestPath.add(tmp);
		}
		return shortestPath;
	}
	
	protected void setSrcAndDest(int srcX, int srcY, int goalX, int goalY) {
		this.srcX = srcX; this.srcY=srcY;
		this.goalX = goalX; this.goalY = goalY;
		visited[srcX][srcY] = true;
		queue.put(srcX, srcY, 0);
	}
	
	public ArrayList<int[]> getShortestPath(){
		return shortestPath;
	}
	
	public boolean[][] getVisited(){
		return visited;
	}
	
	public int[][][] getPath(){
		return cameFrom;
	}
	
	public void run(int srcX, int srcY, int goalX, int goalY) {
		setSrcAndDest(srcX, srcY, goalX, goalY);
		initSets();
		mainLoop();
	}
	
	protected abstract void initSets();
	protected abstract void check(int currentX, int currentY, int neighborX, int neighborY);
}
