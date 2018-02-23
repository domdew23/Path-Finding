public class main {
	
	private static final int width=10000, height=10000;
	
	public static void main(String[] args) {
		Control c = new Control(width, height);
		GraphicsEngine graphics = new GraphicsEngine(width, height, c.getNodeMatrix()); 
		//c.printNodes();
	}
}