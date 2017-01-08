package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import PTank.Entities.Tanks.Ennemy;
import PTank.Entities.Tanks.Player;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class Bullet extends Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static float SPEED = 0.18f;
	private final static int NB_BOUNCES = 1;
	
	private int bouncesLeft = 1;
	
	private int xPos;
	private int yPos;
	private int prevXPos;
	private int prevYPos;
	
	private Weapon weapon;
	


	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Bullet(Map map, Weapon weapon, float x, float y, float angle)
	{
		this.map = map;
		this.shape = new Polygon(Ressources.getShape("Bullet"));
		this.speed = SPEED;
		this.bouncesLeft = NB_BOUNCES;
		this.setCenterX(x);
		this.setCenterY(y);
		this.setAngle(angle);
		this.weapon = weapon;
		this.color = this.weapon.getColor();
		
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
		
		// update des hitbox pour détruire les Ennemy
		if (weapon.getTank().getId() == 0)
		{
			Ennemy[] ennemies = this.map.getEnnemies();
			int nbEnnemies = this.map.getNbEnnemies();
			for (int i = 0; i < nbEnnemies; i++)
			{
				if (this.shape.intersects(ennemies[i].getShape()) && ennemies[i].isAlive())
				{
					ennemies[i].setAlive(false);
					ennemies[i].getWeapon().setAlive(false);
					this.setAlive(false);
					//map.decrementNbEnnemies();
				}
			}
		}
		
		// update des hitbox pour détruire Player
		if (weapon.getTank().getId() > 0)
		{
			Player player = this.map.getPlayer();
			if (this.shape.intersects(player.getShape()) && player.isAlive() && this.isAlive())
			{
				player.setAlive(false);
				player.getWeapon().setAlive(false);
				this.setAlive(false);
			}
		}
	}

	public void destroy()
	{
		this.isAlive = false;
		this.isVisible = false;
	}
	
	public int getBouncesLeft() { return bouncesLeft; }
	public void setBouncesLeft(int bouncesLeft) { this.bouncesLeft = bouncesLeft; }
	public void setWeapon(Weapon weapon) { this.weapon = weapon; }
	
}
