package PTank;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Wall extends Block 
{
	private static Color _color = new Color(Color.gray);
	private Shape _shape;
	
	private Shape _collisionUp;
	private Shape _collisionLeft;
	private Shape _collisionDown;
	private Shape _collisionRight;
	
	public Wall(int x, int y)
	{
		super();
		_shape = new Rectangle(x, y, Block.getWidth(), Block.getHeight());
		_bouncesBullets = true;
		
		float center[] = { this.getX(), 
				           this.getY() };
		
		float colUp[] = { this.getX(), this.getY(),
					      this.getTopLeftX(), this.getTopLeftY(),
					      this.getTopRightX(), this.getTopRightY() };
		
		float colRight[] = { this.getX(), this.getY(),
			                 this.getTopRightX(), this.getTopRightY(),
			                 this.getBottomRightX(), this.getBottomRightY() };
		
		float colDown[] = { this.getX(), this.getY(),
				             this.getBottomRightX(), this.getBottomRightY(),
                             this.getBottomLeftX(), this.getBottomLeftY() };
		
		float colLeft[] = { this.getX(), this.getY(),
				            this.getBottomLeftX(), this.getBottomLeftY(),
				            this.getTopLeftX(), this.getTopLeftY() };
		
		_collisionUp = new Polygon(colUp);
		_collisionRight = new Polygon(colRight);
		_collisionDown = new Polygon(colDown);
		_collisionLeft = new Polygon(colLeft); 

	}

	public Shape get_collisionUp() { return _collisionUp; }
	public Shape get_collisionLeft() { return _collisionLeft; }
	public Shape get_collisionDown() { return _collisionDown; }
	public Shape get_collisionRight() { return _collisionRight; }
	
	public Shape getShape() { return _shape; }
	public Color getColor() { return _color; }
	public float getX() { return _shape.getCenterX(); }
	public float getY() { return _shape.getCenterY(); }
	
	public float getTopLeftX() { return _shape.getMinX(); }
	public float getTopLeftY() { return _shape.getMinY(); }
	
	public float getBottomLeftX() { return _shape.getMinX(); }
	public float getBottomLeftY() { return _shape.getMaxY(); }
	
	public float getBottomRightX() { return _shape.getMaxX(); }
	public float getBottomRightY() { return _shape.getMaxY(); }
	
	public float getTopRightX() { return _shape.getMaxX(); }
	public float getTopRightY() { return _shape.getMinY(); }
	
	
	
	public void bounceBullets(Bullet[] bullets)
	{
		for (Bullet bul : bullets)
		{
			if (bul != null)
			{
				System.out.println("bullet bounce : " + bul.getBouncesLeft());


				Circle hitBox = new Circle(bul.getX(), bul.getY(), bul.getShape().getBoundingCircleRadius());
				if ( _collisionUp.intersects(hitBox) || _collisionDown.intersects(hitBox) )
				{
					if (bul.getBouncesLeft() > 0)
					{
						System.out.println("Bounce !!!");
						bul.setBouncesHorizontaly(true);
						bul.setBouncesLeft(bul.getBouncesLeft() - 1);
					}
					else
					{
						bul.finalize();
					}
				}
				if ( _collisionLeft.intersects(hitBox) || _collisionRight.intersects(hitBox) )
				{
					if (bul.getBouncesLeft() > 0)
					{
						bul.setBouncesVerticaly(true);
						bul.setBouncesLeft(bul.getBouncesLeft() - 1);
					}
					else
					{
						bul.finalize();
					}
				}

			}
		}
	}
}
