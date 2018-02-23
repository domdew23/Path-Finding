import java.awt.Color;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.util.ArrayList;

public class GraphicsEngine extends JFrame {
	private JFrame frame;
	private JPanel mousepanel;
	private final int width, height;
	private final int screenWidth=800,screenHeight=800;
	private ArrayList<Node> components;

	public GraphicsEngine(int w, int h, Node[][] nodes){
		this.width = w;
		this.height = h;
		setPreferredSize(new Dimension(screenWidth, screenHeight));
        setResizable(false);
        //setLocationRelativeTo(null);
        setLayout(new GridLayout(width, height));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addMouseListener(new EventListener(this, nodes));

	    Container container = getContentPane();
       	components = new ArrayList<Node>();
        Node temp = null;

        int squareWidth = screenWidth/width-1;
        int squareHeight = screenHeight/height-1;

        for (int i=0,y=30; i < height; i++,y+=squareHeight){
        	for (int j=0,x=10; j < width; j++,x+=squareWidth){
        		temp = nodes[i][j];
        		temp.setCoordinates(x, x + squareWidth, y, y + squareHeight);
        		components.add(temp);
        		container.add(temp);
        	}
        }

        pack();
        setVisible(true);
		
		for (int i = 0; i < components.size(); i++){
        	components.get(i).setBackground(components.get(i).color);
        	components.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
	}

	public void paint(Graphics g){
		super.paint(g);
	}

	public void repaint(){
		for (int i = 0; i < components.size(); i++){
			if (components.get(i).color == Color.YELLOW) continue;
        	components.get(i).setBackground(components.get(i).color);
        	components.get(i).setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
	}
}