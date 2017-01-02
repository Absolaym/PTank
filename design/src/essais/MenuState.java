package essais;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuState extends BasicGameState{
	
	private int WINDOW_WIDTH, WINDOW_HEIGHT;
	private static final int ID = 1;
	
	private final static int NB_BUTTONS = 2, BUTTON_HEIGHT = 100, BUTTON_WIDTH = 300;
	
	private Rectangle[] buttons;

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {		
		WINDOW_WIDTH = gc.getWidth();
		WINDOW_HEIGHT = gc.getHeight();
		
		buttons = new Rectangle[NB_BUTTONS];
		
		for(int i = 0; i < NB_BUTTONS; i++) {
			buttons[i] = new Rectangle(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
			buttons[i].setCenterX(WINDOW_WIDTH/2);
			buttons[i].setCenterY((i+1)*WINDOW_HEIGHT/(NB_BUTTONS+1));
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		
		for(int i = 0; i < NB_BUTTONS; i++) {
			if(buttons[i].contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				g.setColor(Color.yellow);
			}
			
			g.fill(buttons[i]);
			g.draw(buttons[i]);
			g.setColor(Color.white);
		}
		
		String play = "Jouer", selection = "Selection du skin";
    	g.setColor(Color.black);
    	g.drawString(play, buttons[0].getCenterX() - g.getFont().getWidth(play)/2, buttons[0].getCenterY() - g.getFont().getHeight(play)/2);
    	g.drawString(selection, buttons[1].getCenterX() - g.getFont().getWidth(selection)/2, buttons[1].getCenterY() - g.getFont().getHeight(selection)/2);
    	g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int g) throws SlickException {
		float mX =  gc.getInput().getMouseX();
		float mY =  gc.getInput().getMouseY();
		
		if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(buttons[0].contains(mX, mY))
				game.enterState(3, new FadeOutTransition(), new FadeInTransition());
			
			else if(buttons[1].contains(mX, mY))
				game.enterState(2, new FadeOutTransition(), new FadeInTransition());
		}
	}

	@Override
	public int getID() {
		return ID;
	}
}
