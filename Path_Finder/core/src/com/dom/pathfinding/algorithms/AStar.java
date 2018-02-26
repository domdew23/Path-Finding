package com.dom.pathfinding.algorithms;

public class AStar extends SearchAlgorithm {
	private double[][] fScores;
	private int[][] gScores;

	public AStar(byte[][] graph, int[][][] teleports, int teleportCount, int width, int height) {
		super(graph, teleports, teleportCount, width, height);
		this.gScores = new int[width][height];
		this.fScores = new double[width][height];
	}
	
	protected void initSets() {
		gScores[srcX][srcY] = 0;
		fScores[srcX][srcY] = heuristic(srcX, srcY, goalX, goalY);
		
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (x != srcX || x != srcY) {
					gScores[x][y] = Integer.MAX_VALUE;
					fScores[x][y] = Double.MAX_VALUE;
					visited[x][y] = false;
				}
			}
		}
	}
	
	protected void check(int currentX, int currentY, int neighborX, int neighborY) {
		if (visited[neighborX][neighborY] || graph[neighborX][neighborY] == 10 || graph[currentX][currentY] == 10) {
			return;
		}
		int tentativeGScore = gScores[currentX][currentY] + Math.abs(graph[currentX][currentY] - graph[neighborX][neighborY]);
		if (tentativeGScore < gScores[neighborX][neighborY]) {
			cameFrom[neighborX][neighborY][0] = currentX;
			cameFrom[neighborX][neighborY][1] = currentY;
			gScores[neighborX][neighborY] = tentativeGScore;
			fScores[neighborX][neighborY] = gScores[neighborX][neighborY] + heuristic(neighborX, neighborY, goalX, goalY);
			queue.put(neighborX, neighborY, fScores[neighborX][neighborY]);
		}
	}
	
	private double heuristic(int aX, int aY, int bX, int bY) {
		return Math.sqrt(Math.pow(aX - bX, 2) + Math.pow(aY - aY, 2));
	}
}
