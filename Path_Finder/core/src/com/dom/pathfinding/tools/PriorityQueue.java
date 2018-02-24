package com.dom.pathfinding.tools;

public class PriorityQueue {
	private int DEFAULT_INIT_SIZE = 50;
	private int[][] nodes;
	private int[] dists;
	private int size;

	public PriorityQueue(int initSize){
		nodes = new int[initSize + 1][2];
		dists = new int[initSize + 1];
		size = 0;
	}
	
	public PriorityQueue() {
		nodes = new int[DEFAULT_INIT_SIZE + 1][2];
		dists = new int[DEFAULT_INIT_SIZE + 1];
		size = 0;
	}

	public void put(int x, int y, int dist){
		if (size == nodes.length - 1){
			reSize();
		}
		size++;
		nodes[size] = new int[]{x, y};
		dists[size] = dist;
		check(size);
	}

	private void reSize(){
		int[][] oldNodes = nodes;
		int[] oldDists = dists;
		nodes = new int[nodes.length * 2][2];
		dists = new int[dists.length * 2];
		System.arraycopy(oldNodes, 1, nodes, 1, size);
		System.arraycopy(oldDists, 1, dists, 1, size);
	}

	private boolean greater(int i, int j){
		return dists[i] > dists[j];
	}

	private void siftUp(int i, int j){
		int[] tmpNode = nodes[i];
		int tmpDist = dists[i];

		nodes[i] = nodes[j];
		nodes[j] = tmpNode;
		dists[i] = dists[j];
		dists[j] = tmpDist;
	}

	private void siftDown(int k){
		/* check it has a child - if so sift to the bottom of the heap */
		while(2 * k <= size){
			int j = 2 * k;
			if (j < size && greater(j, j + 1)){
				j++;
			} 

			if (!(greater(k, j))){
				break;
			}

			siftUp(k, j);
			k = j;
		}
	}

	private void check(int k){
		/* check if root or greater than parent - if so swap with parent*/
		while (k > 1 && greater(k/2, k)){
			siftUp(k, k/2);
			k /= 2;
		}
	}

	public int[] peek(){
		/* return the root element */
		if (isEmpty()){
			throw new NullPointerException("Cannot peek an empty list.");
		}
		return nodes[1];
	}

	public int[] pull(){
		if (isEmpty()){
			throw new NullPointerException("Cannot poll an empty queue.");
		}

		/* swap root and bottom node - store root - sift root back to the bottom */
		siftUp(1, size);
		int[] node = nodes[size--];
		nodes[size + 1] = null;
		dists[size + 1] = -1;
		siftDown(1);
		return node;
	}

	public int findNodeIndex(int x, int y){
		for (int i = 1; i < size + 1; i++) {
			if (nodes[i][0] == x && nodes[i][1] == y) {
				return i;
			}
		}
		return -1;
	}
	
	public void remove(int index) {
		siftUp(index, size);
		size--;
		nodes[size + 1] = null;
		dists[size + 1] = -1;
		siftDown(index);
	}
	
	public void update(int x, int y, int dist) {
		int index = 0;
		if ((index = findNodeIndex(x,y)) != -1) {
			remove(index);
			put(x, y, dist);
		}
	}

	public int size(){
		return size;
	}

	public boolean isEmpty(){
		return size == 0;
	}

	public int[][] getHeap(){
		return nodes;
	}
	
	public String toString() {
		String retVal = "";
		for (int i = 1; i < size + 1; i++) {
			retVal += i + ": [x: " + nodes[i][0] + " y: " + nodes[i][1] + "][dist: " + dists[i] +"]\n";
		}
		return retVal;
	}
}
