package PTank;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Wall extends Block 
{
	private Shape _shape;
	
	
	public Wall(int x, int y)
	{
		super();
		_shape = new Rectangle(x, y, Block.getWidth(), Block.getHeight());
		
	}
	
	public Shape getShape() { return _shape; }
	
}
