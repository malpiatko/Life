package board;

import life.Life.Colour;
/*represents a cell on the grid of the game*/
public class Cell {
	private Colour colour;
	
	Cell(Colour colour){
		this.colour = colour;
	}
	
	public Colour getColour() {
		return colour;
	}
	
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	
}
