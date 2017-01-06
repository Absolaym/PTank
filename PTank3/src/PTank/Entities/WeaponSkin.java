package PTank.Entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;

public class WeaponSkin extends Skin {
	private final float WIDTH, HEIGHT;
	
	
	public WeaponSkin(float x, float y, float width, float height) {
		nbShapes = 1;
		WIDTH = width;
		HEIGHT = height;
		
		shapes = new Shape[nbShapes];
		colors = new Color[nbShapes];
		
		shapes[0] = new Rectangle(x - WIDTH/2, y - WIDTH/2, WIDTH, HEIGHT);
		colors[0] = new Color(90, 175, 35);
		
		shapes[0] = Transform.rotate(shapes[0], (float) Math.toRadians(270), x, y);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(colors[0]);
		g.fill(shapes[0]);
	}

}
