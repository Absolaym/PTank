package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public abstract class Skin {
	protected Shape shapes[];
	protected Color colors[];
	protected Color canonColor = Color.white;
	
	protected int nbShapes;
	
	public abstract void render(Graphics g);
	
	public void move(float x, float y) {
		for(int i = 0; i < nbShapes; i ++)
			shapes[i] = Transform.translate(shapes[i], x, y);
	}
	
	public void rotate(float angle, float x, float y) {		
		for(int i = 0; i < nbShapes; i++) 
			shapes[i] = Transform.rotate(shapes[i], angle, x, y);
	}
	
	public void setCenter(float x, float y) {
		for(int i = 0; i < nbShapes; i++) {
			shapes[i].setCenterX(x);
			shapes[i].setCenterY(y);
		}
	}
	
	public void setCenterX(float x) {
		for(int i = 0; i < nbShapes; i++)
			shapes[i].setCenterX(x);
	}
	
	public void setCenterY(float y) {
		for(int i = 0; i < nbShapes; i++)
			shapes[i].setCenterX(y);
	}
	
	public Color getCanonColor() { return this.canonColor; }
	
	public Color getTankColor() { return Color.white; }
	public void setTankColor(Color color) {}
	
	public Color getCatColor() { return Color.white; }
	public void setCatColor(Color color) {}
	
	public Color getTurretColor() { return Color.white; }
	public void setTurretColor(Color color) {}
}
