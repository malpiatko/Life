package life;

import life.Life.Colour;
import board.EdglessAdapter;
import board.Grid;

/*represents the grid together with the possible moves for Game of Life*/
public class Game {
	private static final int MAXSIZE = 30;
	private static final int SURROUND = 8;
	private Grid grid;
	private EdglessAdapter adapter;
	private int turns;
	
	/*creates a grid of size MAXSIZE together with its wrapper, sets the
	 number of turns to zero*/
	public Game(){
		grid = new Grid(MAXSIZE);
		adapter = new EdglessAdapter(grid);
	}
	
	/*returns the number of the turns that have already passed*/
	public int getTurns() {
		return turns;
	}
	
	
	/*clears the state of the game to the initial state*/
	public void clear(){
		adapter.initCells(Colour.GREY);
		turns = 0;
	}
	
	/*updates the state of the grid as required after step by the rules
	  stated in the specification of Game of Life*/
	public void step(){
		EdglessAdapter updateAd = new EdglessAdapter(new Grid(MAXSIZE));
		updateAd.initCells(Colour.GREY);
		for(int x = 0; x < MAXSIZE; x++){
			for(int y = 0; y < MAXSIZE; y++){
				Colour[] sur = getSurColours(x,y);
				int aliveNo = aliveNo(sur);
				Colour itself  = adapter.getCellColour(x, y);
				Colour maj = majorityCol(sur);
				Colour newCol = findColour(aliveNo, itself, maj);
				updateAd.setCellColour(x,y, newCol);
			}
		}
		turns++;
		adapter = updateAd;
	}
	
	/*sets the colour of the cell at (x,y) to the given colour*/
	public void setCellColour(int x, int y, Colour c){
		adapter.setCellColour(x, y, c);
	}
	
	/*returns the colour of the cell at (x,y)*/
	public Colour getCellColour(int x, int y){
		return adapter.getCellColour(x, y);
	}
	
	/*returns an array of colours of cells surrounding cell at
	 * (x,y)*/
	private Colour[] getSurColours(int x, int y){
		Colour[] sur = new Colour[SURROUND];
		for(int i = -1, index = 0; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(i!=0 || j!=0){
					sur[index] = adapter.getCellColour(x + i, y +j);
					index++;
				}
			}
		}
		return sur;
	}
	
	/*returns the number of colours that are not grey in the given
	  array of colours*/
	private int aliveNo(Colour[] c){
		int alive = 0;
		for(int i = 0; i < SURROUND; i ++){
			if(alive(c[i])){
				alive++;
			}
		}	
		return alive;
	}
	
	/*returns true if the given colour is not grey*/
	private boolean alive(Colour c){
		return !(c == Colour.GREY);
	}
	
	/*returns the colour that occurs most frequent  and is not grey
	  in the given array of colours*/
	private Colour majorityCol(Colour[] c){
		int green = 0;
		int red = 0;
		for(int i = 0; i < SURROUND; i ++){
			if (c[i] == Colour.GREEN){
				green++;
			}
			if (c[i] == Colour.RED){
				red++;
			}
		}
		if(green > red){
			return Colour.GREEN;
		}
		return Colour.RED;
	}
	
	/*returns the new colour of the cell given the number of alive cells
	  around the cell(aliveNo), its previous colour (itself) and the 
	  "alive colour" that occurs most often in the surrounding cells*/
	private Colour findColour(int aliveNo, Colour itself, Colour maj){
		boolean alive = alive(itself);
		if(alive){
			if(aliveNo < 2 || aliveNo > 3){
				return Colour.GREY;
			}
			return itself;
		}
		if( aliveNo == 3) return maj;
		return Colour.GREY;
	}
}
