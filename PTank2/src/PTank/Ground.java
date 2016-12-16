package PTank;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Ground extends Block 
{
	private static Color _color;
	private Shape _shape;
	
	public Ground(int x, int y)
	{
		super();
		_shape = new Rectangle(x, y, Block.getWidth(), Block.getHeight());
		_color = new Color(Color.darkGray);
		_bouncesBullets = true;
	}
	
	public Shape getShape() { return _shape; }
	public Color getColor() { return _color; }
	public void bounceBullets(Bullet[] bullets) {}
}
