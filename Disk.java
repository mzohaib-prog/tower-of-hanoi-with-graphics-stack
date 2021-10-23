
import java.awt.Color;
import java.awt.Graphics;

public class Disk {
	static Color colors[] = {Color.RED, Color.BLUE, Color.BLACK,
			Color.GREEN, Color.YELLOW, Color.ORANGE, Color.CYAN, Color.MAGENTA };
	int x, y, s, pegW;
	Color c;
	public Disk(int x, int y, int s, int ins)
	{
		this.x = x;
		this.y = y;
		this.s = s;
		this.pegW = ins+10;
		c = colors[(int)(Math.random()*10000) % colors.length];
	}
	
	public void drawDisk(Graphics g) {
		g.setColor(c);
		g.fillOval(x+pegW/3,y,s,s/3);
		g.setColor(new Color(1f, 1f,1f, .9f));
		g.fillOval(x+pegW/3+(s/2)-(pegW/2),y+(s/5)-(pegW/2),pegW,pegW/2);
		g.setColor(Color.BLACK);
	}
}
