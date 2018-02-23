package com.dom.pathfinding.settings;

import java.util.Random;

import com.dom.pathfinding.grid.Dijkstra;
import com.dom.pathfinding.grid.Node;

public class Control {
	
	private int[] options = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0};
	private byte[][] nodes;
	private byte[] graph;
	private int width, height;
	private Node last;
	
	public Control(int width, int height){
		this.width = width;
		this.height = height;
		nodes = new byte[height][width];
		graph = new byte[width * height];
		generateMap();
		System.out.println("Map Generated.");
		Dijkstra d = new Dijkstra(nodes, width, height, 0, 0);
		d.run();
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (d.getDistances()[y][x] == 127) System.out.print("F ");
				else System.out.print(d.getDistances()[y][x] + " ");
			}
			System.out.println();
		}
	}

	private void generateMap(){
		generateNodes();
	}

	private void generateNodes(){
		Random r = new Random();
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				byte nextByte = (byte) options[r.nextInt(options.length)];
				nodes[x][y] = nextByte;
				graph[x+y*width] = nextByte;
			}
		}
	}
	
	public byte[][] getNodes(){
		return nodes;
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
	
	public void printGraph() {
		System.out.println("Graph:\n");
		for (int i = 0; i < width * height; i++) {
			System.out.println(graph[i]);
		}
	}
}
