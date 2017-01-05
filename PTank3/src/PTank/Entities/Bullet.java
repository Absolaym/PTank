package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class Bullet extends Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static float SPEED = 0.2f;
	private final static int NB_BOUNCES = 5;
	
	private int bouncesLeft = 1;
	
	private int xPos;
	private int yPos;
	private int prevXPos;
	private int prevYPos;
	


	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Bullet(Map map, float x, float y, float angle)
	{
		this.map = map;
		this.shape = new Polygon(Ressources.getShape("Bullet"));
		this.speed = SPEED;
		this.bouncesLeft = NB_BOUNCES;
		this.setCenterX(x);
		this.setCenterY(y);
		this.setAngle(angle);
		this.color = Color.red;
		
		xPos = map.getXPos(this);
		yPos = map.getYPos(this);
		prevXPos = xPos;
		prevYPos = yPos;
	}
	
	@Override
	public void update(GameContainer gc, int dt) throws SlickException
	{
		
		if (this.isAlive)
		{
			this.move(dt);
			xPos = map.getXPos(this);
			yPos = map.getYPos(this);
			
			// si la xPos est passée d'un Ground à un Wall
			if ( xPos != prevXPos &&    (map.isBouncingBlock(xPos, yPos) && !map.isBouncingBlock(prevXPos,  prevYPos))
								     || (map.isBouncingBlock(xPos, yPos) && map.isBouncingBlock(prevXPos,  prevYPos)))
			{
				// le détruire s'il n'a plus de rebond
				if (bouncesLeft == 0)
				{
					this.destroy();
				}
				
				// ou le faire rebondir verticalement
				else
				{
					this.bouncesLeft--;
					this.setAngle(180 - this.getAngle());
				}
			}
			// si la yPos est passée d'un Ground à un Wall
			if (yPos != prevYPos &&    (map.isBouncingBlock(xPos, yPos) && !map.isBouncingBlock(prevXPos,  prevYPos))
					                || (map.isBouncingBlock(xPos, yPos) && map.isBouncingBlock(prevXPos,  prevYPos)))
			{
				// le détruire s'il n'a plus de rebond
				if (bouncesLeft == 0)
				{
					this.destroy();
				}
							
				// ou le faire rebondir horizontalement
				else
				{
					this.bouncesLeft--;
					this.setAngle(- this.getAngle());
				}
			}
		}
		
		prevXPos = xPos;
		prevYPos = yPos;
	}

	public void destroy()
	{
		this.isAlive = false;
		this.isVisible = false;
	}
	
	public int getBouncesLeft() { return bouncesLeft; }
	public void setBouncesLeft(int bouncesLeft) { this.bouncesLeft = bouncesLeft; }
	
}
