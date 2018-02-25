package com.dom.pathfinding.algorithms;

import java.util.ArrayList;

import java.util.ArrayList;

import com.dom.pathfinding.tools.PriorityQueue;

public class Dijkstra {
	
	private byte[][] graph;
	private int srcX, srcY, goalX, goalY, width, height, teleportCount;
	private PriorityQueue queue;
	int[][] distances;
	int[][][] path;
	boolean[][] visited;
	int[][][] teleports;
	int count;
	
	public Dijkstra(byte[][] graph, int[][][] teleports, int teleportCount, int width, int height) {
		this.graph = graph;
		this.width = width;
		this.height = height;
		this.queue = new PriorityQueue();
		this.distances = new int[width][height];
		this.path = new int[width][height][2];
		this.visited = new boolean[width][height];
		this.teleports = teleports;
		this.teleportCount = teleportCount;
		this.count = 0;
	}
	
	public void run(int srcX, int srcY, int goalX, int goalY) {
		this.srcX = srcX;
		this.srcY = srcY;
		this.goalX = goalX;
		this.goalY = goalY;
		distances[srcX][srcY] = 0;
		visited[srcX][srcY] = true;
		queue.put(srcX, srcY, 0);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x != srcX || y != srcY) {
					distances[x][y] = Integer.MAX_VALUE;
					visited[x][y] = false;
				}
			}
		}
		
		while (!queue.isEmpty()) {
			int[] node = queue.pull();
			int x=node[0], y=node[1];
			if (x == goalX && y == goalY) break;
			
			if (graph[x][y] == 0) {
				handleTeleport(x, y);
			}
			
			/* check all neighbors */
			if (x+1 < width) check(x, y, x+1, y);
			if (x-1 >= 0) check(x, y, x-1,y);
			if (y+1 < height) check(x, y, x, y+1);
			if (y-1 >= 0) check(x, y, x, y-1);
			if (x-1 >= 0 && y-1 >= 0) check(x, y, x-1, y-1);
			if (x-1 >= 0 && y+1 < height) check(x, y, x-1, y+1);
			if (x+1 < width && y-1 >= 0) check(x, y, x+1, y-1);
			if (x+1 < width && y+1 < height) check(x, y, x+1, y+1);
			
			visited[x][y] = true;
		}
	}
	
	private void check(int currentX, int currentY, int x, int y) {
		if (graph[x][y] == 10 || graph[currentX][currentY] == 10) return;
		if (visited[x][y]) return;
		
		int altDist = distances[currentX][currentY] + graph[x][y];
		if (altDist < distances[x][y]) {
			distances[x][y] = altDist;
			int[] coords = {currentX, currentY};
			path[x][y] = coords;
			queue.put(x, y, distances[x][y]);
		}
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
	
	public void printDistances() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (distances[y][x] >= 10000) {
					System.out.print("F ");
				} else 
					{
					System.out.print(distances[y][x] + " ");
				}
			}
			System.out.println();
		}
	}
	
	public void printPath() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				System.out.print(path[y][x][0] + "-" + path[y][x][1] + " ");
			}
			System.out.println();
		}
	}
	
	public ArrayList<int[]> getShortestPath() {
		ArrayList<int[]> pathSteps = new ArrayList<int[]>();
		int[] nextHop = {path[goalX][goalY][0], path[goalX][goalY][1]};
		pathSteps.add(nextHop);
		
		while (nextHop[0] != srcX || nextHop[1] != srcY) {
			int[] tmp = {path[nextHop[0]][nextHop[1]][0], path[nextHop[0]][nextHop[1]][1]};
			nextHop = tmp;
			pathSteps.add(nextHop);
		}
		pathSteps.remove(pathSteps.size()-1);
		return pathSteps;
	}
	
	public int[][] getDistances(){
		return distances;
	}
	
	public int[][][] getPath(){
		return path;
	}
	
	public boolean[][] getVisited(){
		return visited;
	}
}
