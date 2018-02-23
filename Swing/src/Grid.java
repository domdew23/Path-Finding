import java.awt.GridLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.JPanel;

public class Grid extends JPanel {
	int x,y,width,height;

	public Grid(int x, int y, int width, int height){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		g.setColor(Color.RED);
		g.fillRect(x, y, width, height);

		g.setColor(Color.BLUE);
		g.fillRect(x + (x/5), y + (y/5), (width - width/10), height - (height/10));
	}
}