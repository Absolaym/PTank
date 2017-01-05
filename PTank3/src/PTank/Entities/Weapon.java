package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import PTank.Window;
import PTank.Entities.Tanks.Tank;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class Weapon extends Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private Tank tank;
	private Bullet bullets[];
	private int nbBulletsMax;
	private int  nbBullets;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Weapon(Map map, int nbBulletsMax)
	{
		this.map = map;
		this.map.addEntity(this);
		this.shape = new Polygon(Ressources.getShape("Weapon"));
		this.nbBulletsMax = nbBulletsMax;
		this.bullets = new Bullet[nbBulletsMax];
		for (int i = 0; i < nbBulletsMax; i++)
		{
			bullets[i] = new Bullet(map ,100, 100, 0);
			bullets[i].setAlive(false);
		}
		this.color = Color.green;
	}
	
	public Bullet[] getBullets() { return bullets; }
	public void setBullets(Bullet[] bullets) { this.bullets = bullets; }
	
	public int getNbBulletsMax() { return nbBulletsMax; }
	public void setNbBulletsMax(int nbBulletsMax) { this.nbBulletsMax = nbBulletsMax; }
	
	public int getNbBullets() { return nbBullets; }
	public void setNbBullets(int nbBullets) { this.nbBullets = nbBullets; }

	public void setTank(Tank tank)
	{
		this.tank = tank;
	}

	
	public void fire()
	{
		// on cherche si le weapon lui reste une bullet à tirer
		int cpt = 0;
		while (cpt < this.nbBulletsMax - 1 && this.bullets[cpt].isAlive())
		{
			cpt++;
		}
		if (!this.bullets[cpt].isAlive())
		{
			this.bullets[cpt] = new Bullet(map, this.getCenterX(), this.getCenterY(), this.angle);
			map.addEntity(this.bullets[cpt]);
		}
	}

	@Override
	public void update(float angleWeapon) throws SlickException 
	{
		// On calcule l'angle que doit avoir le weapon
		float mouseX = gc.getInput().getMouseX();
		float mouseY = gc.getInput().getMouseY();
		
    	float dx = mouseX - this.tank.getCenterX();
    	float dy = mouseY - this.tank.getCenterY(); 
		
		float angle = (float) Math.toDegrees(Math.atan(dy/dx));
		
		if (dx >= 0 && dy < 0)
    	{
    		angle += 360;
    	}
    	else if (dx < 0)
    	{
    		angle += 180;
    	}
		
		this.setAngle(angleWeapon);
		this.setCenterX(tank.getCenterX());
		this.setCenterY(tank.getCenterY());
		this.speed = 8;
		this.move(1);
	}

}
