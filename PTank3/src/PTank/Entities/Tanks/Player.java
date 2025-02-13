package PTank.Entities.Tanks;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;

import PTank.Entities.Skin;
import PTank.Entities.Transform;
import PTank.Entities.Weapon;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class Player extends Tank
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static float SPEED = 0.12f;
	private final static int NB_BULLETS_MAX = 3;
	
	private boolean hasFired = false;
	public static boolean bouge = false;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Player(Map map, float x, float y)
	{
		this.map = map;
		this.shape = new Polygon(Ressources.getShape("Player"));
		this.setCenterX(x);
		this.setCenterY(y);
		this.shape = Transform.rotate(shape, (float) Math.toRadians(270), x, y);
		this.speed = SPEED;

		this.skin = new TankSkin(x, y, WIDTH, HEIGHT);
		this.weapon = new Weapon(map, this, NB_BULLETS_MAX);

		this.color = new Color(1, 1, 1, 0.5f);
	}

	@Override
	public void update(GameContainer gc, int dt) throws SlickException 
	{
		Input input = gc.getInput();
		
		// On calcule l'angle que doit avoir le weapon
		
		//this.weapon.update(gc.getInput().getMouseX(), gc.getInput().getMouseY());
		
		// calcul de bouge (le tank doit bouger) et angleDirection = angle du player
		bouge = true;
		float angleDirection = 0;
		int x = 0;
		int y = 0;
		
		// up 270
		if (input.isKeyDown(Input.KEY_Z))
		{
			y++;
		}
		
		// down 90
		if (input.isKeyDown(Input.KEY_S))
		{
			y--;
		}
		
		// left 180
		if (input.isKeyDown(Input.KEY_Q))
		{
			x--;
		}
		
		// right 0
		if (input.isKeyDown(Input.KEY_D))
		{
			x++;
		}
		
		if (x == 1)
		{
			if ( map.isBlockingBlock(shape.getCenterX() + 16, shape.getCenterY()) )
			{
				x = 0;
			}			
		}
		if (x == -1)
		{
			if ( map.isBlockingBlock(shape.getCenterX() - 16, shape.getCenterY()) )
			{
				x = 0;
			}			
		}
		if (y == 1)
		{
			if ( map.isBlockingBlock(shape.getCenterX(), shape.getCenterY() - 16) )
			{
				y = 0;
			}			
		}
		if (y == -1)
		{
			if ( map.isBlockingBlock(shape.getCenterX(), shape.getCenterY() + 16) )
			{
				y = 0;
			}			
		}
		
		if (x == 0 && y == 0) { bouge = false; }
		else if (x == 1  && y == 0) 	{ angleDirection = 0; }
		else if (x == 1  && y == -1) 	{ angleDirection = 45; }
		else if (x == 0  && y == -1) 	{ angleDirection = 90; }
		else if (x == -1 && y == -1) 	{ angleDirection = 135; }
		else if (x == -1 && y == 0) 	{ angleDirection = 180; }
		else if (x == -1 && y == 1) 	{ angleDirection = 225; }
		else if (x == 0  && y == 1) 	{ angleDirection = 270; }
		else if (x == 1  && y == 1) 	{ angleDirection = 315; }
		
		if (bouge)
		{
			this.setAngle(angleDirection);
			this.move(dt);
		}
		
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !this.hasFired)
		{
			if(this.fire()){
				Sound fire = new Sound("ressources/musique/Piou.wav");
			    fire.play();
			}
			this.hasFired = true;
		}
		if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			this.hasFired = false;
		}

		// update his weapon
		float mouseX = gc.getInput().getMouseX();
		float mouseY = gc.getInput().getMouseY();

		this.weapon.update(mouseX, mouseY, gc, dt);
	}
	
	public void setSkin(Skin skin) { this.skin = skin; }
	
	public Color getTankColor() { return skin.getTankColor(); }
	public void setTankColor(Color color) { skin.setTankColor(color); }
	
	public Color getCatColor() { return skin.getCatColor(); }
	public void setCatColor(Color color) { skin.setCatColor(color); }
	
	public Color getTurretColor() { return skin.getTurretColor(); }
	public void setTurretColor(Color color) { skin.setTurretColor(color); weapon.setColor(color);}
	
	public int getId() { return Tanks.PLAYER; }
}


/*
package PTank.Entities.Tanks;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import PTank.Entities.Weapon;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class Player extends Tank
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static float SPEED = 0.1f;
	private final static int NB_BULLETS_MAX = 50;
	
	private boolean hasFired = false;

	
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Player(Map map, float x, float y)
	{
		this.map = map;
		this.shape = new Polygon(Ressources.getShape("Player"));
		this.setCenterX(x);
		this.setCenterY(y);
		this.speed = SPEED;
		this.weapon = new Weapon(map, NB_BULLETS_MAX);
		this.weapon.setTank(this);
		this.color = new Color(1, 1, 1, 0.5f);
	}

	@Override
	public void update(GameContainer gc, int dt) throws SlickException 
	{
		Input input = gc.getInput();
		
		// calcul de bouge (le tank doit bouger) et angleDirection = angle du player
		boolean bouge = true;
		float angleDirection = 0;
		int x = 0;
		int y = 0;
		
		// up 270
		if (input.isKeyDown(Input.KEY_Z))
		{
			y++;
		}
		
		// down 90
		if (input.isKeyDown(Input.KEY_S))
		{
			y--;
		}
		
		// left 180
		if (input.isKeyDown(Input.KEY_Q))
		{
			x--;
		}
		
		// right 0
		if (input.isKeyDown(Input.KEY_D))
		{
			x++;
		}
		
		if (x == 1)
		{
			if ( map.isBlockingBlock(shape.getCenterX() + 16, shape.getCenterY()) )
			{
				x = 0;
			}			
		}
		if (x == -1)
		{
			if ( map.isBlockingBlock(shape.getCenterX() - 16, shape.getCenterY()) )
			{
				x = 0;
			}			
		}
		if (y == 1)
		{
			if ( map.isBlockingBlock(shape.getCenterX(), shape.getCenterY() - 16) )
			{
				y = 0;
			}			
		}
		if (y == -1)
		{
			if ( map.isBlockingBlock(shape.getCenterX(), shape.getCenterY() + 16) )
			{
				y = 0;
			}			
		}
		
		if (x == 0 && y == 0) { bouge = false; }
		else if (x == 1  && y == 0) 	{ angleDirection = 0; }
		else if (x == 1  && y == -1) 	{ angleDirection = 45; }
		else if (x == 0  && y == -1) 	{ angleDirection = 90; }
		else if (x == -1 && y == -1) 	{ angleDirection = 135; }
		else if (x == -1 && y == 0) 	{ angleDirection = 180; }
		else if (x == -1 && y == 1) 	{ angleDirection = 225; }
		else if (x == 0  && y == 1) 	{ angleDirection = 270; }
		else if (x == 1  && y == 1) 	{ angleDirection = 315; }
		

		
		if (bouge)
		{
			this.setAngle(angleDirection);
			this.move(dt);
		}
		
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !this.hasFired)
		{
			System.out.println("Fire !");
			this.fire();
			this.hasFired = true;
		}
		if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
		{
			this.hasFired = false;
		}
		
		
	}
}
*/
