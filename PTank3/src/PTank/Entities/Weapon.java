package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
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
	public Weapon(Map map, Tank tank, int nbBulletsMax)
	{
		this.map = map;
		this.tank = tank;
		this.shape = new Polygon(Ressources.getShape("Weapon"));
		this.nbBulletsMax = nbBulletsMax;
		this.bullets = new Bullet[nbBulletsMax];
		for (int i = 0; i < nbBulletsMax; i++)
		{
			bullets[i] = new Bullet(map , this, 100, 100, 0);
			bullets[i].setAlive(false);
			bullets[i].setWeapon(this);
		}
		this.color = tank.getSkin().getCanonColor();
	}
	
	public Bullet[] getBullets() { return bullets; }
	public void setBullets(Bullet[] bullets) { this.bullets = bullets; }
	
	public int getNbBulletsMax() { return nbBulletsMax; }
	public void setNbBulletsMax(int nbBulletsMax) { this.nbBulletsMax = nbBulletsMax; }
	
	public int getNbBullets() { return nbBullets; }
	public void setNbBullets(int nbBullets) { this.nbBullets = nbBullets; }

	public void setTank(Tank tank) { this.tank = tank; }
	public Tank getTank() { return this.tank; }
	
	public void setColor(Color color) { this.color = color; }
	public Color getColor() { return this.color; }

	
	public boolean fire()
	{
		boolean fired=false;
		// on cherche si le weapon lui reste une bullet à tirer
		int cpt = 0;
		while (cpt < this.nbBulletsMax - 1 && this.bullets[cpt].isAlive())
		{
			cpt++;
		}
		if (!this.bullets[cpt].isAlive())
		{	
			fired=true;
			this.bullets[cpt] = new Bullet(map, this, this.getCenterX(), this.getCenterY(), this.angle);
		}
		return fired;
	}

	@Override
	public void update(GameContainer gc, int dt) throws SlickException 
	{
		// update this (son angle en gros)
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
		
		this.setAngle(angle);
		this.setCenterX(tank.getCenterX());
		this.setCenterY(tank.getCenterY());
		this.speed = 8;
		this.move(1);

		// update ses bullets
		//System.out.println(nbBullets);
		for (int i = 0; i < this.nbBulletsMax; i++)
		{
			this.bullets[i].update(gc, dt);
		}
	}
	
	public void update(float mouseX, float mouseY, GameContainer gc, int dt) throws SlickException 
	{
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
		
		this.setAngle(angle);
		this.setCenterX(tank.getCenterX());
		this.setCenterY(tank.getCenterY());
		this.speed = 8;
		this.move(1);

		// update ses bullets
		//System.out.println(nbBullets);
		for (int i = 0; i < this.nbBulletsMax; i++)
		{
			this.bullets[i].update(gc, dt);
		}
	}
	
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		// render his bullets
		Bullet[] bullets = this.getBullets();
		int nbBulletsMax = this.getNbBulletsMax();
		
		for (int i = 0; i < nbBulletsMax; i++)
		{
			bullets[i].render(gc, g);
		}
		
		// render this
		if (isAlive && isVisible)
		{
			g.setColor(color);
			g.fill(shape);
			if(skin != null)
				skin.render(g);
		}



	}

}
