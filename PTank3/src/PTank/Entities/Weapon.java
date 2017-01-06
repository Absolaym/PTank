package PTank.Entities;

import javax.swing.plaf.synth.SynthPainter;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;
import org.omg.CosNaming._BindingIteratorImplBase;

import PTank.Entities.Tanks.Player;
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
	private float x, y;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public Weapon(Map map, int nbBulletsMax, float x, float y)
	{
		this.x = x;
		this.y = y;
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
		this.skin = new WeaponSkin(shape.getCenterX(), shape.getCenterY(), 32/6f, 32/1.5f);
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
		this.shape.setCenterX(tank.getCenterX() + tank.getCanonWidth());
		this.shape.setCenterY(tank.getCenterY());
		this.skin.setCenter(tank.getCenterX() + tank.getCanonWidth(), tank.getCenterY());
	}

	@Override
	public void setAngle(float angle)
	{
		float deltaAngle = angle - this.angle;
		shape = shape.transform(Transform.createRotateTransform((float) Math.toRadians(deltaAngle), x, y));
		skin.rotate((float) Math.toRadians(deltaAngle), x, y);

		this.angle = angle;
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
	
	public void update(float x, float y) throws SlickException {
    	float dx = x - this.tank.getCenterX();
    	float dy = y - this.tank.getCenterY(); 
		
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
	}

	
	@Override
	public void update(GameContainer gc, int dt) throws SlickException {
		//does nothing
	}

	/*@Override
	public void update(GameContainer gc, int dt) throws SlickException 
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
		
		this.setAngle(angle);
		this.setCenterX(tank.getCenterX());
		this.setCenterY(tank.getCenterY());
		this.speed = 8;
		this.move(1);
	}*/

}
