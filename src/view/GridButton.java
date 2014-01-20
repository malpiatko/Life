package view;

import javax.swing.JButton;
import javax.swing.JPanel;

public class GridButton extends JPanel {
	
	GridButton(int x, int y){
		super();
		this.x = x;
		this.y = y;
	}
	
	final int x, y;
	
	public int getXCoordinate(){
		return x;
	}
	
	public int getYCoordinate(){
		return y;
	}

	
}
