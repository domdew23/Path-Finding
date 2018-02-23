import java.util.Random;

public class Control {
	
	private int[] options = {1, 2, 3, 4, 5, 6, 7, 8, 9, Integer.MAX_VALUE, 11};
	private Node[][] nodes;
	private int width, height;
	private Node last;

	public Control(int width, int height){
		this.width = width;
		this.height = height;
		nodes = new Node[height][width];
		generateMap();
	}

	private void generateMap(){
		generateNodes();
		generateLinks();
	}

	private void generateNodes(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				nodes[y][x] = new Node(x, y);
			}
		}
	}

	private void generateLinks(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				if (y < height - 1){
					linkNodes(nodes[y][x], nodes[y+1][x]);	
				}

				if (x < width - 1){
					linkNodes(nodes[y][x], nodes[y][x+1]);
				}
			}
		}
	}

	private void linkNodes(Node one, Node two){
		Random r = new Random();
		int weight = options[r.nextInt(options.length)];
		
		while (weight == options[options.length-1]){
			if (weight % 2 == 0 && !last.hasTeleport() && !two.hasTeleport()){
				createLink(last, two, 0);
			} else {
				last = one;
			}
			options[options.length-1]++;
			weight = options[r.nextInt(options.length)];
		}

		createLink(one, two, weight);
	}

	private void createLink(Node one, Node two, int weight){
		Edge e1 = new Edge(one, two, weight);
		Edge e2 = new Edge(two, one, weight);
		one.addEdge(e1);
		two.addEdge(e2);
	}

	public Node[] getNodeArray(){
		Node[] tmp = new Node[width*height];
		int k = 0;

		for(int i = 0; i < nodes.length; i++) {
        	for(int j = 0; j < nodes[i].length; j++) {
            	tmp[k++] = nodes[i][j];
          	}
      	}
      	return tmp;
	}

	public Node[][] getNodeMatrix(){
		return nodes;
	}

	public void printNodes(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				System.out.println(nodes[y][x]);
			}
		}	
	}
}