package PTank.Entities.Tanks;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import PTank.Entities.Bullet;
import PTank.Entities.Entity;
import PTank.Entities.Weapon;

public abstract class Tank extends Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	protected final static float WIDTH = 32;
	protected final static float HEIGHT = 32;
	protected final static float CANON_WIDTH = 8;
	protected final static float CANON_HEIGHT = 26;
	
	protected Shape[] skinShapes;
	protected Color[] skinColors;
	protected Weapon weapon;

	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	
	// this is how each tank will be updated.
	// in Player class, it'll analyse the gc.inputs to move or fire
	// in Ennemy subclasses; it 
	public abstract void update(GameContainer gc, int dt) throws SlickException;
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		// render this
		if (isAlive && isVisible)
		{
			g.setColor(color);
			g.fill(shape);
			if(skin != null)
				skin.render(g);
		}
		
		// render his weapon
		this.weapon.render(gc, g);
	}
	
	public boolean fire()
	{
		return this.weapon.fire();
	}
	
	public Weapon getWeapon() { return this.weapon; }
	
	public abstract int getId();
}
