import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Launcher{
	static int nofDisks;
	static final int max = 15, min = 1;
	public static void main(String[] args){
		boolean pass = false;
		System.out.println("Starting Game");
		do {
			try {
				String diskNumStr = JOptionPane.showInputDialog("Enter number of disks", 3);
				if(diskNumStr == null) {
					showErrorMsg("Process Cancelled!");
					System.exit(0);
				}
				nofDisks = Integer.parseInt(diskNumStr);
				if(nofDisks < min || nofDisks > max)
					throw new IndexOutOfBoundsException();
				pass = true;  //if reaches here then pass the loop
			}catch(NumberFormatException nfe) {
				showErrorMsg("Please Enter +ve integer Only!");
			}catch(IndexOutOfBoundsException iob) {
				showErrorMsg("Enter number between : "+min+ " <---> "+max);
			}
		}while(!pass);
		new Frame(1280,720);
	}
	
	static void showErrorMsg(String msg) {
		JOptionPane.showMessageDialog(new JFrame(), msg);
	}
	
}
