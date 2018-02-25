package com.dom.pathfinding.algorithms;

import java.util.ArrayList;

import com.dom.pathfinding.tools.PriorityQueue;

public class AStar {
	private byte[][] graph;
	private int srcX, srcY, goalX, goalY, width, height, teleportCount;
	private boolean[][] closed;
	private int[][] gScores;
	private double[][] fScores;
	private int[][][] cameFrom;
	private int closedCount;
	private PriorityQueue open;
	private int[][][] teleports;
	ArrayList<int[]> shortestPath;

	public AStar(byte[][] graph, int[][][] teleports, int teleportCount, int width, int height) {
		this.graph = graph;
		this.width = width;
		this.height = height;
		this.gScores = new int[width][height];
		this.fScores = new double[width][height];
		this.open = new PriorityQueue();
		this.closed = new boolean[width][height];
		this.cameFrom = new int[width][height][2];
		this.teleports = teleports;
		this.teleportCount = 0;
		this.closedCount = 0;
	}
	
	public void run(int srcX, int srcY, int goalX, int goalY) {
		this.srcX = srcX; this.srcY=srcY;
		this.goalX = goalX; this.goalY = goalY;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x != srcX || x != srcY) {
					gScores[x][y] = Integer.MAX_VALUE;
					fScores[x][y] = Double.MAX_VALUE;
					//int[] tmp = {-1, -1};
					//cameFrom[x][y] = tmp;
				}
			}
		}
		
		
		open.put(srcX, srcY, 0);
		gScores[srcX][srcY] = 0;
		fScores[srcX][srcY] = heuristic(srcX, srcY, goalX, goalY);
		
		while (!open.isEmpty()) {
			int[] node = open.pull();
			int x=node[0], y=node[1];
			closed[x][y] = true;

			if (x == goalX && y == goalY) {
				System.out.println("Found Solution");
				shortestPath(x, y);
				return;
			}
			
			if (graph[x][y] == 0) {
				handleTeleport(x, y);
				continue;
			}
			
			if (x+1 < width) check(x, y, x+1, y);
			if (x-1 >= 0) check(x, y, x-1,y);
			if (y+1 < height) check(x, y, x, y+1);
			if (y-1 >= 0) check(x, y, x, y-1);
			if (x-1 >= 0 && y-1 >= 0) check(x, y, x-1, y-1);
			if (x-1 >= 0 && y+1 < height) check(x, y, x-1, y+1);
			if (x+1 < width && y-1 >= 0) check(x, y, x+1, y-1);
			if (x+1 < width && y+1 < height) check(x, y, x+1, y+1);
		}
	}
	
	private void check(int currentX, int currentY, int neighborX, int neighborY) {
		if (closed[neighborX][neighborY] || graph[neighborX][neighborY] == 10 || graph[currentX][currentY] == 10) {
			return;
		}
		int tentativeGScore = gScores[currentX][currentY] + Math.abs(graph[currentX][currentY] - graph[neighborX][neighborY]);
		//System.out.print("Current: " + currentX + " " + currentY + " Neighbor: " + neighborX + " " + neighborY);
		//System.out.print(" Tentative: " + tentativeGScore + " | current: " + graph[neighborX][neighborY] + "\n");
		if (tentativeGScore < gScores[neighborX][neighborY]) {
			cameFrom[neighborX][neighborY][0] = currentX;
			cameFrom[neighborX][neighborY][1] = currentY;
			gScores[neighborX][neighborY] = tentativeGScore;
			fScores[neighborX][neighborY] = gScores[neighborX][neighborY] + heuristic(neighborX, neighborY, goalX, goalY);
			//open.put(neighborX, neighborY, fScores[neighborX][neighborY]);
			//if (!open.contains(neighborX, neighborY)) {
				open.put(neighborX, neighborY, fScores[neighborX][neighborY]);
			//}
		}
	}
	
	private double heuristic(int aX, int aY, int bX, int bY) {
		//return Math.abs(aX - bX) + Math.abs(aY - bY);
		return Math.sqrt(Math.pow(aX - bX, 2) + Math.pow(aY - aY, 2));
	}
	
	private void handleTeleport(int x, int y) {
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
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.print(cameFrom[x][y][0] + "-" + cameFrom[x][y][1] + " ");
			}
			System.out.println();
		}
		while (currentX != srcX || currentY != srcY) {
			int[] coords = {cameFrom[currentX][currentY][0], cameFrom[currentX][currentY][1]};
			currentX = coords[0];
			currentY = coords[1];
			int[] tmp = {currentX, currentY};
			shortestPath.add(tmp);
		}
		return shortestPath;
	}
	
	public ArrayList<int[]> getShortestPath(){
		return shortestPath;
	}
	
	public boolean[][] getVisited(){
		return closed;
	}
}
