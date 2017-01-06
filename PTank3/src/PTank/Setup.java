package PTank;

import java.io.File;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import PTank.GameStates.*;

public class Setup extends StateBasedGame
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	public static boolean APPLET = false;
	public static boolean FULLSCREEN = false;
	public static boolean SHOW_FPS = true;
	public static boolean ALWAYS_RENDER = true;
	public static int MAX_FPS = 60;
	public static int MAX_UPS = 60;
	public static boolean VSYNC = true;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Setup()
	{
		super("PTank");
	}
	
	// where the execution starts
	public static void main (String args[]) throws SlickException 
	{	
		// getting natives folder
		File f = new File("natives");
		if (f.exists())
		{
			System.setProperty("org.lwjgl.librarypath", f.getAbsolutePath());
		}
		
		try
		{
			// new game created
			AppGameContainer app = new AppGameContainer(new Setup());
			
			// game configuration
			app.setDisplayMode(Window.WIDTH, Window.HEIGHT, FULLSCREEN);
			
			// game start
			app.start();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}

	}

	// this is the first function called
	public void initStatesList(GameContainer gc) throws SlickException
	{
		// setup GameContainer gc
		gc.setTargetFrameRate(MAX_FPS);
		gc.setMaximumLogicUpdateInterval(MAX_UPS);
		gc.setAlwaysRender(ALWAYS_RENDER);
		gc.setShowFPS(SHOW_FPS);
		gc.setVSync(VSYNC);
		
		// add states
		this.addState(new InGame());
		this.addState(new TitleScreen());
		this.addState(new Menu());
		this.addState(new MenuLevelEditor());
		
	}
	
	
}

