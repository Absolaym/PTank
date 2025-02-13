
package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import PTank.Map.Map;


public abstract class Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	protected boolean isVisible = true;
	protected boolean isAlive = true;
	
	protected Map map;
	protected Shape shape;
	protected float angle; // in degrees. 0 is EAST, 90 is SOUTH, 180 is WEST, 270 is NORTH
	protected float speed;
	protected Color color = Color.white;
	protected Skin skin;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public boolean isAlive() { return this.isAlive; }
	public void setAlive(boolean isAlive) { this.isAlive = isAlive; }
	
	public boolean isVisible() { return this.isVisible; }
	public void setVisible(boolean isVisible) { this.isVisible = isVisible; }
	
	public float getX() { return shape.getX(); }
	public void setX(float x) { shape.setX(x); }
	
	public float getY() { return shape.getY(); }
	public void setY(float y) { shape.setX(y); }
	
	public float getCenterX() { return shape.getCenterX(); }
	public void setCenterX(float x) { shape.setCenterX(x); }
	
	public float getCenterY() { return shape.getCenterY(); }
	public void setCenterY(float y) { shape.setCenterY(y); }
	
	public float getSpeed() { return speed; }
	public void setSpeed(float speed) { this.speed = speed; }
	
	public Shape getShape() { return shape; }
	public Skin getSkin() { return skin; }
	
	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }

	public float getAngle() { return this.angle; }
	public void setAngle(float angle)
	{
		float deltaAngle = angle - this.angle;
		shape = shape.transform(Transform.createRotateTransform((float) Math.toRadians(deltaAngle), this.getCenterX(), this.getCenterY()));
		if(skin != null) {
			skin.rotate((float) Math.toRadians(deltaAngle), shape.getCenterX(), shape.getCenterY());
		}
		this.angle = angle;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		if (isAlive && isVisible)
		{
			g.setColor(color);
			g.fill(shape);
			if(skin != null)
				skin.render(g);
		}
	}
	
	public void move(int dt)
	{
		float xMove = dt * this.speed * (float) Math.cos(Math.toRadians(angle));
		float yMove = dt * this.speed * (float) Math.sin(Math.toRadians(angle));
	
		this.shape = shape.transform(Transform.createTranslateTransform(xMove, yMove));
		if(skin != null)
			this.skin.move(xMove, yMove);
	}
	
	public void destroy()
	{
		this.isAlive = false;
	}
	
	public abstract void update(GameContainer gc, int dt) throws SlickException;
}

/*
package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

import PTank.Map.Map;
import PTank.Entities.Skin;


public abstract class Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	protected boolean isVisible = true;
	protected boolean isAlive = true;
	
	protected Map map;
	protected Shape shape;
	protected Skin skin;
	protected float angle; // in degrees. 0 is EAST, 90 is SOUTH, 180 is WEST, 270 is NORTH
	protected float speed;
	protected Color color = Color.white;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public boolean isAlive() { return this.isAlive; }
	public void setAlive(boolean isAlive) { this.isAlive = isAlive; }
	
	public boolean isVisible() { return this.isVisible; }
	public void setVisible(boolean isVisible) { this.isVisible = isVisible; }
	
	public float getX() { return shape.getX(); }
	public void setX(float x) { shape.setX(x); }
	
	public float getY() { return shape.getY(); }
	public void setY(float y) { shape.setX(y); }

	public float getCenterX() { return shape.getCenterX(); }
	public void setCenterX(float x) { shape.setCenterX(x); }

	public float getCenterY() { return shape.getCenterY(); }
	public void setCenterY(float y) { shape.setCenterY(y); }

	public Color getColor() { return color; }
	public void setColor(Color color) { this.color = color; }

	public float getAngle() { return this.angle; }
	public void setAngle(float angle)
	{
		float deltaAngle = angle - this.angle;
		shape = shape.transform(Transform.createRotateTransform((float) Math.toRadians(deltaAngle), this.getCenterX(), this.getCenterY()));
		this.angle = angle;
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		if (isAlive && isVisible)
		{
			g.setColor(color);
			g.fill(shape);
		}
	}
	
	public void move(int dt)
	{
		float xMove = dt * this.speed * (float) Math.cos(Math.toRadians(angle));
		float yMove = dt * this.speed * (float) Math.sin(Math.toRadians(angle));
	
		this.shape = shape.transform(Transform.createTranslateTransform(xMove, yMove));
		if(skin != null)
			this.skin.move(xMove, yMove);
	}
	
	public void destroy()
	{
		this.isAlive = false;
	}
	
	public abstract void update(GameContainer gc, int dt) throws SlickException;
}
*/
