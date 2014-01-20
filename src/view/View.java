package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import controler.Controler;

import life.Game;
import life.Life.Colour;


public class View extends JPanel{
	/*the speed of updates in the run is indicated by "SLIDERVALUE/value"*/
	private static final int SLIDERVALUE = 2000;
	private static final int MAXSIZE = 30;
	int value;
	/*the other components of the MVC*/
	Game game;
	Controler c;
	/*components of the View*/
	JLabel turns;
	JPanel buttons;
	JPanel grid;
	JButton clear, pause, run, step;
	/*listener for the grid and the timer for the run*/
	GridListener gridListener;
	javax.swing.Timer t;
	
	public View(Game game, Controler c){
		this.c = c;
		this.game = game;
		value = 1;
		t = new Timer(SLIDERVALUE/value, new StepListener());
	}
	
	/*adds the components to the View*/
	public void drawApplet(){
		setLayout(new BorderLayout());
		turns = drawTurns();
		grid = drawGrid();
		buttons = drawButtons();
		add(turns, BorderLayout.NORTH);
		add(drawSlider(), BorderLayout.EAST);
		add(grid);
		add(buttons, BorderLayout.SOUTH);
		setBackgroundToKids(Color.PINK);
	}
	
	/*sets background to all the children of the View to the given colour*/
	private void setBackgroundToKids(Color c){
		int max = getComponentCount();
		setBackground(c);
		for(int i = 0; i < max; i ++){
			getComponent(i).setBackground(c);
		}
	}
	
	/*sets the background of the given button to the given colour*/
	public void setButtonBackground(GridButton b, Color c){
		GridButton b1 = b;
		b1.setBackground(c);
	}
	
	/*draws and returns the slider for controlling the speed of the run*/
	public JSlider drawSlider(){
		JSlider slider = new JSlider(JSlider.VERTICAL, 1, 10, value);
		slider.setMajorTickSpacing(1);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBorder(BorderFactory.createEmptyBorder());
		slider.addChangeListener(new ChangeValue());
		return slider;
	}
	
	/*draws and returns the grid of the game */
	public JPanel drawGrid(){
		gridListener = new GridListener();
		JPanel grid = new JPanel(new GridLayout(MAXSIZE,MAXSIZE));
		for(int x = 0; x < MAXSIZE; x ++){
			for(int y = MAXSIZE - 1; y >= 0; y--){
				GridButton button = new GridButton(x,y);
				button.setBorder(new LineBorder(Color.PINK, 1));
				button.setBackground(Color.GRAY);
				button.addMouseListener(gridListener);
				grid.add(button);
			}
		}
		return grid;
	}
	
	/*refreshes the turns component*/
	public void redrawTurns(){
		int turn = game.getTurns();
		turns.setText("turns: " + turn);
	}
	
	/*updates the state of the View to what is specified in the
	  specification for the Game of Life when the "Run" button is clicked*/
	public void drawRun(){
		JPanel buttonPanel = (JPanel) run.getParent();
		clear.setEnabled(false);
		step.setEnabled(false);
		run.setVisible(false);
		buttonPanel.remove(run);
		revalidate();
		pause.setVisible(true);
		buttonPanel.add(pause);
		revalidate();
	}
	
	/*updates the state of the View to what is specified in the
	  specification for the Game of Life when the "Pause" button is clicked*/
	public void drawPause(){
		JPanel buttonPanel = (JPanel) pause.getParent();
		clear.setEnabled(true);
		step.setEnabled(true);
		pause.setVisible(false);
		buttonPanel.remove(pause);
		revalidate();
		run.setVisible(true);
		buttonPanel.add(run);
		revalidate();
	}
	
	/*refreshes the grid after changes where made to its model*/
	public void redrawGrid(){
		for(int x = 0, i = 0; x < MAXSIZE; x ++){
			for(int y = MAXSIZE-1; y >= 0; y--, i++){
				JPanel button = (GridButton) grid.getComponent(i);
				Colour c = game.getCellColour(x, y);
				switch(c){
				case RED: 	{button.setBackground(Color.red);
									break;}
				case GREY: 	{button.setBackground(Color.gray);
									break;}
				case GREEN: {button.setBackground(Color.green);}
				}
				button.repaint();
			}
		}
	}
	
	/*draws a panel which shows the number of turns that passed in the game*/
	public JLabel drawTurns(){
		int turn = game.getTurns();
		JLabel label = new JLabel("turns:" + turn);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}
	
	/*draws the panel with buttons to control the game*/
	public JPanel drawButtons(){
		JPanel panel = new JPanel();
		clear = new JButton("Clear");
		clear.addActionListener(new ClearListener());
		panel.add(clear);
		step = new JButton("Step");
		step.addActionListener(new StepListener());
		panel.add(step);
		run = new JButton("Run");
		run.addActionListener(new RunListener());
		pause = new JButton("Pause");
		pause.addActionListener(new PauseListener());
		panel.add(run);
		return panel;
	}

	/*returns the x coordinate on the grid of the given button*/
	private int getButtonX(JPanel button) {
		GridButton b1 = (GridButton) button;
		return b1.getXCoordinate();
	}
	
	/*returns the y coordinate on the grid of the given button*/
	private int getButtonY(JPanel button) {
		GridButton b1 = (GridButton) button;
		return b1.getYCoordinate();
	}
	
	/*listens to the mouse clicks on the cells on the grid*/
	private class GridListener extends MouseAdapter {
		/*boolean representing the state that the listener is,
		  if true the listener will cause changes, otherwise its
		  disabled*/
		private boolean isEnabled;
		
		public GridListener(){
			super();
			isEnabled = true;
		}
		
		/*changes the state of the listener*/
		public void activate(boolean active){
			this.isEnabled = active;
		}
		
		/*changes the state of the cell pressed in both the model and the view 
		  according to what mouse button it was pressed with */
		public void mouseClicked (MouseEvent event){
			if(isEnabled){
				GridButton button = (GridButton)event.getSource();
				int x, y;
				x = getButtonX(button);
				y = getButtonY(button);
				if(SwingUtilities.isLeftMouseButton(event)){
					setButtonBackground(button,Color.red);
					c.setCellColour(x, y, Colour.RED);
				} else if(SwingUtilities.isRightMouseButton(event)){
						setButtonBackground(button,Color.green);
						c.setCellColour(x, y, Colour.GREEN);
				} else {
					setButtonBackground(button,Color.gray);
					c.setCellColour(x, y, Colour.GREY);
				}
			}
		}
	}
	
	/*listens to the "Pause" button*/
	private class PauseListener implements ActionListener{
		
		/*stops the timer for the "run" and refreshes the view accordingly*/
		@Override
		public void actionPerformed(ActionEvent event) {
			t.stop();
			drawPause();
			gridListener.activate(true);
		}
	}
	
	/*listens to the "Step" button*/
	private class StepListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			c.step();
		}
	}
	
	/*listens to the "Clear" button*/
	private class ClearListener implements ActionListener{

		/*clears the state of the game together with updating the view accordingly*/
		@Override
		public void actionPerformed(ActionEvent event) {
			game.clear();
			redrawGrid();
			redrawTurns();
		}
	}
	
	/*listens to the "Run" button*/
	private class RunListener implements ActionListener{
		
		/*simulates the run of the game*/
		@Override
		public void actionPerformed(ActionEvent event) {
			gridListener.activate(false);
			drawRun();
			t.setDelay(SLIDERVALUE/value);
			t.start();
		}
	}
	
	/*listens for the changes in the sliders value*/
	private class ChangeValue implements ChangeListener{
		
		/*updates only when mouse is released, if the simulation
		  of the run is taking place the timers interval are changed*/
		@Override
		public void stateChanged(final ChangeEvent change){
			final JSlider source = (JSlider) change.getSource();
			if(source.getValueIsAdjusting() == false){
				value = (int) source.getValue();
				if(t.isRunning()){
					t.setDelay(SLIDERVALUE/value);
				}
			}
		}
	}

}
