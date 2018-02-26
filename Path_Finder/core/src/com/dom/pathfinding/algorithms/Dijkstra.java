package com.dom.pathfinding.algorithms;

public class Dijkstra extends SearchAlgorithm {
	
	private int[][] distances;
	
	public Dijkstra(byte[][] graph, int[][][] teleports, int teleportCount, int width, int height) {
		super(graph, teleports, teleportCount, width, height);
		this.distances = new int[width][height];
	}
	
	protected void initSets() {
		distances[srcX][srcY] = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x != srcX || y != srcY) {
					distances[x][y] = Integer.MAX_VALUE;
					visited[x][y] = false;
				}
			}
		}
	}
	
	protected void check(int currentX, int currentY, int neighborX, int neighborY) {
		if (graph[neighborX][neighborY] == 10 || graph[currentX][currentY] == 10) return;
		if (visited[neighborX][neighborY]) return;
		
		int altDist = distances[currentX][currentY] + graph[neighborX][neighborY];
		if (altDist < distances[neighborX][neighborY]) {
			cameFrom[neighborX][neighborY][0] = currentX;
			cameFrom[neighborX][neighborY][1] = currentY;
			distances[neighborX][neighborY] = altDist;
			queue.put(neighborX, neighborY, distances[neighborX][neighborY]);
		}
	}
	
	public int[][] getDistances(){
		return distances;
	}
	
}
