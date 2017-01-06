package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public abstract class Skin {
	protected Shape shapes[];
	protected Color colors[];
	
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
}
