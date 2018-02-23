import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Color;
import javax.swing.JFrame;

public class EventListener implements MouseListener {
	
	private Node[][] nodes;
	private JFrame frame;

	public EventListener(JFrame frame, Node[][] nodes){
		this.nodes = nodes;
		this.frame = frame;
	}

	public EventListener(JFrame frame){
		this.frame = frame;
	}

	public void mouseClicked(MouseEvent e){
		int x = (int) Math.ceil((e.getX() - 30) / 40);
		int y = (int) Math.ceil((e.getY() - 50) / 40);
		System.out.println("Mouse coordinates: x - " +  x + "(" + e.getX() + ") | y - " + y + "(" + e.getY() + ")");
		
		for (int i = 0; i < nodes.length; i++){
			for (int j = 0; j < nodes[i].length; j++){
				if (nodes[i][j].checkClicked(e.getX(), e.getY())) break;
			}
		}
		frame.repaint();
	}

	public void mouseEntered(MouseEvent e){

	}

	public void mouseExited(MouseEvent e){

	}

	public void mousePressed(MouseEvent e){

	}

	public void mouseReleased(MouseEvent e){
		
	}
}