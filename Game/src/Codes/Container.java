package Codes;

import javax.swing.JFrame;

public class Container{
	
	JFrame frame = new JFrame();
	
	public Container() {		
		frame.add(new Fase());
		frame.setTitle("Game");
		frame.setSize(900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		new Container();
	}
}
	