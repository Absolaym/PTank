package PTank.GameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.MouseOverArea;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import PTank.Entities.Tanks.Player;
import PTank.Map.Map;
import PTank.Ressources.Button;
import PTank.Ressources.Data;

public class Customization extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------

	private final int R = 0, G = 1, B = 2, TANK = 0, TURRET = 1, CATERPILLARS = 2, btnWidth = 250, btnHeight = 54;
	private int onClick, r, g, b;
	private Map map;
	private Player player;
	private Slider sliders[];
	private ImageButton buttons[], finish;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	@Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {
		map = new Map("maps/mapCustomization.txt");
		map.init();
		
		onClick = TANK;
		
		float offset = 50;
		float loopVar = -offset;
		float width = gc.getWidth();
		float height = gc.getHeight();
		
		player = new Player(map, width/4, height/4);
		
		sliders = new Slider[3];
		
		for(int i = 0; i < sliders.length; i++) {
			sliders[i] = new Slider(3*width/4,  2*height/3 + loopVar, 250, 10, new Color(128, 128, 128), new Color(179, 179, 179));
			loopVar += offset;
		}
		
		offset = 25;
		
		buttons = new ImageButton[3];
		
		buttons[TANK] = new ImageButton(gc, "ressources/image/Tankbutton.png", "ressources/image/Tankbutton2.png", (int) (3*width/4), (int) ((height/4) - btnHeight - offset), btnWidth, btnHeight);
		buttons[TURRET] = new ImageButton(gc, "ressources/image/Tourellebutton.png", "ressources/image/Tourellebutton2.png", (int) (3*width/4), (int) (height/4), btnWidth, btnHeight);
		buttons[CATERPILLARS] = new ImageButton(gc, "ressources/image/Chenillesbutton.png", "ressources/image/Chenillesbutton2.png", (int) (3*width/4), (int) ((height/4) + btnHeight + offset), btnWidth, btnHeight);
    
		finish = new ImageButton(gc, "ressources/image/Tourellebutton.png", "ressources/image/Tourellebutton2.png", (int) (width/4), (int) (3*height/4), btnWidth, btnHeight);
    
		Color c = player.getTankColor();
		
		sliders[R].setValue(c.getRed());	
		sliders[G].setValue(c.getGreen());
		sliders[B].setValue(c.getBlue());
    }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		//map.update(gc, dt);
		player.update(gc, dt);
		finish.update(gc);
		
		if(finish.isClickedOn()) {
			player.setAngle(0);
			Data.setSkin(player.getSkin());
			sbg.enterState(GameStates.IN_GAME, new FadeOutTransition(), new FadeInTransition());
		}

		for(int i = 0; i < sliders.length; i++) {
			sliders[i].update(gc);
			buttons[i].update(gc);
			
			if(onClick <= CATERPILLARS) {
				Color c = new Color(sliders[R].getValue(), sliders[G].getValue(), sliders[B].getValue());
				if(onClick == TANK)
					player.setTankColor(c);
				
				else if(onClick == TURRET)
					player.setTurretColor(c);
				
				else if(onClick == CATERPILLARS)
					player.setCatColor(c);
			}
			
			if(buttons[i].isClickedOn()) {
				Color c = Color.white;
				onClick = i;
				if(onClick == TANK)
					c = player.getTankColor();
				
				else if(onClick == TURRET)
					c = player.getTurretColor();
				
				else if(onClick == CATERPILLARS)
					c = player.getCatColor();
				
				System.out.println(""+onClick);
				sliders[R].setValue(c.getRed());	
				sliders[G].setValue(c.getGreen());
				sliders[B].setValue(c.getBlue());
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		//map.render(gc, g);
		player.render(gc, g);
		finish.render(gc, g);
		g.setColor(Color.red);
		g.draw(new Rectangle(0, gc.getHeight()/2, gc.getWidth(), 1));
		g.draw(new Rectangle(gc.getWidth()/2, 0, 1, gc.getHeight()));

		for(int i = 0; i < sliders.length; i++) {
			sliders[i].render(gc, g);
			buttons[i].render(gc, g);
		}
	}

	@Override
	public int getID() { return GameStates.CUSTOMIZATION; }
}
