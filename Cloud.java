import java.awt.Graphics;
import java.awt.Color;

public class Cloud {
	private int x,  y;
	int size, edge, sky;
	
	public Cloud(int size, int sky, int edge){
		this.size = size;
		this.edge = edge;
		this.sky = sky;
		x = (int)(Math.random()*10000) % edge;
		y = (int)(Math.random()*10000) % sky;
	}
	
	void drawCloud(Graphics g) {
		g.setColor(new Color(255, 255, 255, 250));
		
		if(x+size*2 < 0) {
			y = (int)(Math.random()*10000) % sky;
			x = edge;
		}
		g.fillOval(x, y, size, size);
		g.fillOval(x+size-5, y, size, size);
		g.fillOval(x+size/2, y-size/2, size, size);
		x--;
	}
}
