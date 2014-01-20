package board;

import life.Life.Colour;

/*represents the grid of the game*/
public class Grid implements GridInterface{
	int size;
	private Cell[][] cells;
	
	/*constructs a grid of size given as an argument and initialises all
	  colours of all cells on the grid to colour*/
	public Grid(int size){
		this.size = size;
		cells = new Cell[size][size];
	}
	
	/*sets all cell colours to the colour given as its argument*/
	public void initCells(Colour colour){
		for(int x = 0; x < size; x++){
			for(int y = 0; y < size; y++){
				cells[x][y] = new Cell(colour);
			}
		}
	}
	
	/*sets cell at (x,y) to the given colour*/
	public void setCellColour(int x, int y, Colour colour){
		cells[x][y].setColour(colour);
	}
	
	/*returns the colour of the cell at (x,y)*/
	public Colour getCellColour(int x, int y) {
		return cells[x][y].getColour();
	}
	

}
