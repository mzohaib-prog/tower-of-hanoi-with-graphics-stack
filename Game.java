import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Stack;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.FontMetrics;

public class Game extends JPanel implements Runnable{
	Peg pegA, pegB, pegC;
	final int base = 200, dist, brickSize = 50;
	final int fps = 60;
	int cfps = 90;
	Stack<Disk> A = new Stack<Disk>(), B = new Stack<Disk>(), C = new Stack<Disk>();
	Disk disks[] = new Disk[Launcher.nofDisks];
	final int speed = 5;
	
	final int dSize = 15;
	
	final int setoff = 100;
	
	Point startP;
	Font startFont;
	String startStr = "Starting Game Autoplay...";
	boolean started = true;
	
	Cloud clouds[] = new Cloud[10];
	
	private static final long serialVersionUID = 1L;
	Game(int width, int height){
		setSize(width, height);
		setVisible(true);
		setBackground(new Color(135,206,250));
		dist = width/4;
		
		
		startFont = new Font("arial", 20, 20);
		FontMetrics fm = getFontMetrics(startFont);
		startP = new Point(getWidth()/2-fm.stringWidth(startStr)/2, getHeight());
			
		
		pegA = new Peg("A", A, dist*1, base, 15, 50+dSize*Launcher.nofDisks);
		pegB = new Peg("B", B, dist*2, base, 15, 50+dSize*Launcher.nofDisks);
		pegC = new Peg("C", C, dist*3, base, 15, 50+dSize*Launcher.nofDisks);
		
		for(int i = 0; i < clouds.length; i++)
		clouds[i] = new Cloud(10+(int)(Math.random()*1000) % 30,
				base, getWidth());
		
		for(int i = Launcher.nofDisks; i > 0; i--) {
			Disk disk = new Disk(pegA.x-(50+i*10)/2, pegA.y+pegA.h-dSize*(Launcher.nofDisks-i+1), 50+i*10, pegA.w);
			A.push(disk);
		}
		
		
		//set disks out of screen
		for(Disk disk : A) {
			disk.y -= pegA.h;
			for(int i = 0; i < setoff; i++) {
				disk.x -= speed;
				disk.y -= speed;
			}
		}
		
		new Thread(this).start();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(Cloud cloud : clouds)
			cloud.drawCloud(g);
	
		Graphics2D g2d = (Graphics2D)g;
		int ground = pegA.y+pegA.h;
		g.setColor(Peg.color);
		g.fillRect(0, ground, getWidth(), getHeight());
		g.setColor(new Color(181, 101, 29));
		for(int i = 0; i < getWidth()/brickSize; i++) {
			g.drawLine(i*brickSize, ground, i*brickSize, getHeight());
		}
		
		for(int i = 0; i < (getHeight()*1.5)/brickSize; i++) {
			g.drawLine(0, (int)(ground+i*brickSize/1.5), getWidth(), (int)(ground+i*brickSize/1.5));
		}
		
		g2d.setColor(Peg.color);
		g.drawString(pegA.name, pegA.x, pegA.y-20);
		g2d.fillRoundRect(pegA.x, pegA.y, pegA.w, pegA.h+pegA.w, pegA.w ,pegA.w);
		g.drawString(pegB.name, pegB.x, pegB.y-20);
		g2d.fillRoundRect(pegB.x, pegB.y, pegB.w, pegB.h+pegB.w, pegB.w ,pegB.w);
		g.drawString(pegC.name, pegC.x, pegC.y-20);
		g2d.fillRoundRect(pegC.x, pegC.y, pegC.w, pegC.h+pegC.w, pegC.w ,pegC.w);
		
		for(Disk disk : A)
			disk.drawDisk(g);
		for(Disk disk : B)
			disk.drawDisk(g);
		for(Disk disk : C)
			disk.drawDisk(g);
		
		if( ! started) {
			g.setFont(startFont);
			g.setColor(Color.WHITE);
			g.drawString(startStr, startP.x, startP.y-=speed);
			g.setColor(Peg.color);
		}

	}
	
	void delay(final int fps) {
		try {
			Thread.sleep(1000/fps);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void animateDisk(Disk disk, int x, Stack<Disk> stack) {
		while(disk.y > pegA.y-disk.s)  //move up
		{
			disk.y-=speed;
			repaint();
			delay(fps);
		}
		
		for(int i = 0; i < Math.abs(x)/speed; i++) {  //move x-axis
			disk.x += speed*(x/Math.abs(x));
			repaint();
			delay(fps);
		} 
		
		while(disk.y < pegA.y+pegA.h-stack.size()*dSize)  // move down
		{
			disk.y+= speed;
			repaint();
			delay(fps);
		}
	}
	
	public void move(int n, Peg a, Peg b, Peg c)
    {
        if (n == 0)
        	return;
        
        move(n-1, a, c, b);
        Disk d = a.stack.pop();
        c.stack.push(d);
        animateDisk(d, c.x-a.x, c.stack);
        move(n-1, b, a, c);
    }
	
	void throwDisks() {
		for(int i = 0; i < Launcher.nofDisks; i++) {
			Disk disk = A.get(i);
			//throw disk to top of peg
			while(((disk.x+(disk.s)/2)) < pegA.x) {
				disk.y += speed;
				disk.x += speed;
				repaint();
				delay(cfps);
			}
			//take disk to base of peg
			while(disk.y < pegA.y+pegA.h-dSize*(i+1)) {
				disk.y += speed;
				repaint();
				delay(fps);
			}
		}
	}
	
	@Override
	public void run() {
		throwDisks();
		started = false;
		while(startP.y > 0) {
			repaint();
			delay(fps);
		}
		
		started = true;
		move(Launcher.nofDisks, pegA,pegB,pegC);
	}
}

