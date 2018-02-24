package com.dom.pathfinding.settings;

import java.util.Random;

import com.dom.pathfinding.grid.Dijkstra;

public class Control {
	
	private int[] options = {1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 10, 11};
	private byte[][] nodes;
	private int width, height;
	private int[][][] teleports;
	private int[] last;
	private int count;
	private boolean handlesTeleports;
	
	public Control(int width, int height){
		this.width = width;
		this.height = height;
		this.nodes = new byte[height][width];
		this.teleports = new int[width*height][2][2];
		this.last = new int[2];
		this.count = 0;
		this.handlesTeleports = false;
		generateMap();
	}

	private void generateMap(){
		Random r = new Random();
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				byte nextByte = (byte) options[r.nextInt(options.length)];
				if (nextByte == options[options.length - 1] && handlesTeleports) {
					if (nextByte % 2 == 0 && !hasTeleport(x, y) && !hasTeleport(last[0], last[1])) {
						int[][] tmp = {last, {x,y}};
						teleports[count++] = tmp;
						nodes[x][y] = 0;
						nodes[last[0]][last[1]] = 0;
						options[options.length - 1]--;
					} else {
						int[] tmp = {x, y};
						last = tmp;
						while (nextByte == options[options.length-1]) nextByte = (byte) options[r.nextInt(options.length)];
						nodes[x][y] = nextByte;
						options[options.length - 1]++;
					}
				} else {
					while (nextByte == options[options.length-1]) nextByte = (byte) options[r.nextInt(options.length)];
					nodes[x][y] = nextByte;
				}
			}
		}
	}
	
	private boolean hasTeleport(int x, int y) {
		for (int i = 0; i < count; i++) {
			if (teleports[i][0][0] == x && teleports[i][0][1] == y) {
				return true;
			} else if (teleports[i][1][0] == x && teleports[i][1][1] == y) {
				return true;
			}
		}
		return false;
	}
	
	public byte[][] getNodes(){
		return nodes;
	}
	
	public int[][][] getTeleports(){
		return teleports;
	}
	
	public int getTeleportCount() {
		return count;
	}
	
	public void printTeleports() {
		for (int i = 0; i < count; i++) {
			int x1 = teleports[i][0][0];
			int y1 = teleports[i][0][1];
			int x2 = teleports[i][1][0];
			int y2 = teleports[i][1][1];
			System.out.println("Node: " + x1 + " " + y1 + " - Teleports to " + x2 + " " + y2);
		}
	}

	public void printNodes(){
		System.out.println("Nodes:\n");
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				if (nodes[y][x] == 10) System.out.print("F ");
				else System.out.print(nodes[y][x] + " ");
			}
			System.out.println();
		}	
	}
}
