package board;

import life.Life.Colour;

/*represents a grid consisting of cells*/
public interface GridInterface {
	
	public void setCellColour(int x, int y, Colour colour);
	public void initCells(Colour colour);
	public Colour getCellColour(int x, int y);
}
