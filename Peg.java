
import java.awt.Color;
import java.util.Stack;

public class Peg {
	Stack<Disk> stack;
	int x, y, w, h;
	static Color color = new Color(101, 67, 33);  //brown
	String name;
	public Peg(String name, Stack<Disk> stack, int x, int y, int w, int h){
		this.name = name;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.stack = stack;
	}
}
