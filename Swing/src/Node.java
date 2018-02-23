import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Node extends JPanel {
	public int x,y,xLo,xHi,yLo,yHi;
	public Color color;
	private ArrayList<Edge> edges;
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
		this.color = Color.YELLOW;
		this.edges = new ArrayList<Edge>();
	}

	public void addEdge(Edge edge){
		edges.add(edge);
	}

	public void setCoordinates(int xLo, int xHi, int yLo, int yHi){
		this.xLo = xLo;
		this.xHi = xHi;
		this.yLo = yLo;
		this.yHi = yHi;
	}

	public boolean checkClicked(int mouseX, int mouseY){
		if (mouseX >= xLo && mouseX <= xHi && mouseY >= yLo && mouseY <= yHi){
			//System.out.println("You clicked: " + this);
			this.color = Color.RED;
			return true;
		}
		return false;
	}

	public boolean hasTeleport(){
		for (Edge e : edges){
			if (e.weight == 0){
				return true;
			}
		}
		return false;
	}

	public boolean check(){
		boolean hasOne = false, hasTwo = false;
		for (Edge e : edges){
			if (e.weight == 0 && !hasOne){
				hasOne = true;
			} else if (e.weight == 0 && hasOne){
				hasTwo = true;
			}
		}
		return hasTwo;
	}

	public String toString() {
		String retVal = "";
		for (int i = 0; i < edges.size(); i++){
			Edge e = edges.get(i);
			retVal += "\t-[" + i + "] - " + "(" + e.dest.x + " " + e.dest.y + ") weight: " + e.weight + "\n";
		}
		String coords = "xLow = " + xLo + " | xHigh = " + xHi + " | yLow = " + yLo + " | yHigh = " + yHi;
		return "(" + x + ", " + y + "): Connected to:\n" + retVal + "\t" + coords + "\n";
	}
}