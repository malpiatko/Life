package life;
import java.applet.Applet;
import java.awt.*;

import javax.swing.*;

import controler.Controler;


public class Life extends JApplet {
	Controler controler;
	
	public void init(){
		controler = new Controler();
		add(controler.getView());
		controler.start();
		
		
	}
	
	
   //enum for managing the basic three colours of the cells
   //RED and GREEN cells are alive, GREY cells are dead
   public enum Colour {
     RED, GREEN, GREY;
   }
   
   /*
   The autotest will assume the following interfaces on the class life.Life
   You are free to implement these methods in any way you wish
   Note: we are assuming a standard coordinate system, that is with (0,0)
         referring to the bottom left cell, x being col no. and y being row no.
   */        
   
   //read the colour of the cell at coord (x,y)
   public Colour readCell(int x, int y)
   {
      return null;
   }   
   
   //read the turn count
   public int readTurn()
   {
      return 0;
   }
   
   //kill the cell at coord (x,y)
   public void kill (int x, int y)
   {
   
   }
   
   //create a live sell with the specified colour at coord (x,y)
   public void resurrect (int x, int y, Colour c)
   {
   
   }
   
   //clear the board and reset the turn count to 0
   public void clear()
   {
   
   }
   
   //step the simulation through one application of the rules and increment 
   //the turn counter
   public void step()
   {
   
   }

}