package board;

import life.Life.Colour;

/*its an adapter for the grid, converts the coordinates of cells so that
  the grid is wrapped around as a torus*/
public class EdglessAdapter implements GridInterface{
	int size;
	Grid adaptee;
	
	public EdglessAdapter(Grid adaptee){
		this.adaptee = adaptee;
		this.size = adaptee.size;
	}
	
	/*returns the grid that it is wrapped around*/
	public Grid getGrid(){
		return adaptee;
	}
	
	/*private function, returns modulus x, y , note: always positive*/
	private int modulus(int x, int y){
		return (x+y)%y;
	}
	
	/*sets colour of the cell at (x mod size,y mod size) to the given colour */
	public void setCellColour(int x, int y, Colour colour){
		x = modulus(x,size);
		y = modulus(y,size);
		adaptee.setCellColour(x,y,colour);
	}
	
	/*initialises the colour of all sells in its adaptee to the given colour*/
	public void initCells(Colour colour){
		adaptee.initCells(colour);
	}
	
	/*returns the colour of the cell at (x mod size, y mod size)*/
	public Colour getCellColour(int x, int y) {
		x = modulus(x,size);
		y = modulus(y,size);
		return adaptee.getCellColour(x,y);
	}


}
