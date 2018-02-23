package com.dom.pathfinding.grid;

import com.dom.pathfinding.tools.PriorityQueue;

public class Dijkstra {
	
	private byte[][] graph;
	private int srcX, srcY, width, height;
	private PriorityQueue queue;
	byte[][] distances;
	byte[][] path;
	byte[][] visited;
	
	public Dijkstra(byte[][] graph, int width, int height, int srcX, int srcY) {
		this.graph = graph;
		this.srcX = srcX;
		this.srcY = srcY;
		this.width = width;
		this.height = height;
		this.queue = new PriorityQueue();
		this.distances = new byte[width][height];
		this.path = new byte[width][height];
		this.visited = new byte[width][height];
	}
	
	public void run() {
		System.out.println("Running Dijskra...");
		distances[srcX][srcY] = 0;
		visited[srcX][srcY] = 1;
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x != srcX || y != srcY) {
					distances[x][y] = Byte.MAX_VALUE;
					path[x][y] = -1;
					visited[x][y] = 0;
				}
				queue.put(x, y, distances[x][y]);
			}
		}
		
		while (!queue.isEmpty()) {
			int[] node = queue.pull();
			int x=node[0], y=node[1];
			
			if (x+1 < width) check(x, y, x+1, y);
			if (x-1 > 0) check(x, y, x-1,y);
			if (y+1 < height) check(x, y, x, y+1);
			if (y-1 > 0) check(x, y, x, y-1);
			visited[x][y] = 1;
		}
	}
	
	private void check(int currentX, int currentY, int x, int y) {
		//if (graph[x][y] == 10 || graph[currentX][currentY] == 10) return;
		if (visited[x][y] == 1) return;
		
		byte altDist = (byte) (distances[currentX][currentY] + graph[x][y]);
		System.out.println("current: " + currentX + " " + currentY + " | neighbor: " + x + " " + y + " alt: " + altDist + " = " + distances[currentX][currentY] + " + " + graph[x][y]);
		//System.out.println("\n" + queue + "\n");
		if (altDist < distances[x][y]) {
			distances[x][y] = altDist;
			path[x][y] = graph[x][y];
			queue.update(x, y, altDist);
		}
	}
	
	public byte[][] getDistances(){
		return distances;
	}
	
	public byte[][] getPath(){
		return path;
	}
}
