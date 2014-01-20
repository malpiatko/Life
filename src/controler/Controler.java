package controler;

import view.View;

import life.Game;
import life.Life.Colour;

/*controls the Model and the View of the Life game*/
public class Controler {
	Game game;
	View view;
	
	/*creates a new game and new view*/
	public Controler(){
		game = new Game();
		view = new View(game, this);
	}
	
	/*returns the view*/
	public View getView(){
		return view;
	}
	
	/*initialises the game and the view*/
	public void start(){
		view.drawApplet();
		game.clear();
	}
	/*represents one move in the game, updates the state of the
	  Model as well as the View*/
	public void step(){
		game.step();
		view.redrawGrid();
		view.redrawTurns();
	}
	
	/*changes the colour of the cell at (x,y) in the Model*/
	public void setCellColour(int x, int y, Colour c){
		game.setCellColour(x,y,c);
	}

}
