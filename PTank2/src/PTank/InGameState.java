package PTank;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InGameState extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	// const class attributes
	private final static int WINDOW_WIDTH = 1200;
	private final static int WINDOW_HEIGHT = 800;

	// object attributes
	private Map _map;
	private int _levelId;
	
	private Entity _entities[];
	
	private GameIO _gameIO;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	// Constructor
	public InGameState(int choseLevel)
	{
		_levelId = choseLevel;
	}
	
	// ____________________________________| init |_____________________________________________
    public void init(GameContainer container, StateBasedGame arg1) throws SlickException 
    {
    	// new map
    	String mapPath = new String("maps/map" + _levelId + ".txt");
    	_map = new Map(mapPath, 32, 16);
    	
    	// la map parse le fichier "maps/map<_levelID>.txt" et créer les block et les entity
    	_map.init();
    	
    	// new game input / output
    	_gameIO = new GameIO();
    	
    }
    
	// ____________________________________| update |___________________________________________
	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int dt) throws SlickException 
	{
		// On récupère la référence du player créé par la map
		Tank player = _map.getPlayer();
		player.setSpeed(0.1f);
		
		// update gameIO
    	_gameIO.updateGameIO(player.getX(), player.getY(), Mouse.getX(), WINDOW_HEIGHT - Mouse.getY());
    	
        // update map
        _map.update(_gameIO, dt);
        
	}

	// ____________________________________| render |___________________________________________
	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException
	{
		Tank player = _map.getPlayer();
		
		// render info
		g.setColor(Color.white);
    	g.drawString("IN GAME", 50, 50);
    	g.drawString("canon angle = " + _gameIO.getTankCanonAngle(), 100, 650);
    	g.drawString("player x = " + _gameIO.getTankX(), 100, 680);
    	g.drawString("player y = " + _gameIO.getTankY(), 100, 700);
    	
    	g.drawString("mouse x = " + Mouse.getX(), 300, 680);
    	g.drawString("mouse y = " + (WINDOW_HEIGHT - Mouse.getY()), 300, 700);
    	

    	// render map
    	Block[][] blocks = _map.getBlocks();
    	
    	// pour chaque bloc de la map, prendre sa couleur et le remplir (le dessiner)
    	for (Block[] rawOfBlocks : blocks)
    	{
    		for (Block block : rawOfBlocks)
    		{
    			g.setColor(block.getColor());
    			g.fill(block.getShape());
    		}
    	}
    	
    	/*
    	for (int i = 0; i < _map.getMapHeight(); i++)
    	{
    		for (int j = 0; j < _map.getMapWidth(); j++)
    		{
    			if (blocks[i][j].getShape() != null)
    				g.fill(blocks[i][j].getShape());
    		}
    	}
    	*/
    	
    	// render player
    	g.setColor(Color.blue);
    	g.fill(player.getShape());

    	// render bullets
        for (Bullet bul : player.getBullets())
        {
        	if (bul != null)
        	{
        		g.fill(bul.getShape());

        	}
        }
	}

    // ____________________________________* other *____________________________________________
    // ----- keyboard/mouse listeners
	// keyPressed
    @Override
    public void keyPressed(int key, char c) 
    {
    	_gameIO.pressKey(key);
    }

	// keyReleased
    @Override
    public void keyReleased(int key, char c)
    {
        _gameIO.releaseKey(key);
    }
    
    // mousePressed
    @Override
    public void mousePressed(int button, int x, int y)
    {
    	_gameIO.pressMouse(button, x, y);
    }

    // ----- From coordonates (x, y), speed, and directional angle, you get the next pos (x', y')
    public static float moveX(float x, float speed, float angle)
    {
        return (float) (x + speed * Math.cos(Math.toRadians(angle)));
    }
    public static float moveY(float y, float speed, float angle)
    {
        return (float) (y + speed * Math.cos(Math.toRadians((Math.PI / 2) + angle)));
    }
    
    // retourne l'id de ce game state
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 2;
	}
}
