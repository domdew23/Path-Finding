package com.dom.pathfinding.grid;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;

public class Node {
	
	int x,y;
	Color color;
	ArrayList<Edge> edges;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
		this.color = Color.YELLOW;
		this.edges = new ArrayList<Edge>();
	}
	
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}
	
	public boolean hasTeleport(){
		for (Edge e : edges){
			if (e.weight == 0){
				return true;
			}
		}
		return false;
	}
	
}
