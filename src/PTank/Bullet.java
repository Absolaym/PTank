package PTank;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Bullet {
	
	private final static float WIDTH = 9.f;
	private final static float HEIGHT = 13.f;
	private final static float TOP = 4.f;
	private float _speed;
	private float _angle;
	private Shape _shape;
	
	public Bullet(float x, float y, float angle, float speed)
	{
		// creating the points of the shape
		float tmpA = WIDTH / 2;
		float tmpB = HEIGHT / 2;
		float points[] = {x - tmpA, y - tmpB,
					x + tmpA, y - tmpB,
					x + tmpA, y + tmpB,
					x, y + tmpB + TOP,
					x - tmpA, y + tmpB};
		
		// set attributes 
		_shape = new Polygon(points);
		_shape.setCenterX(x);
		_shape.setCenterY(y);
		_angle = angle;
		_speed = speed;
		
		/*
		_polygon.addPoint(x - tmpA, y - tmpB);
		_polygon.addPoint(x + tmpA, y - tmpB);
		_polygon.addPoint(x + tmpA, y + tmpB);
		_polygon.addPoint(x, y + tmpB + TOP);
		_polygon.addPoint(x - tmpA, y + tmpB);
 		*/
		
		// rotating it from the center with given angle in degrees
		_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians( (_angle - 90f)), x, y));

	}
	
	public Shape getShape()
	{
		return _shape;
	}
	
	public void setShape(Shape shape)
	{
		_shape = shape;
	}
	
	public float getAngle() { return _angle; }
	public float getSpeed() { return _speed; }
	
	public void move()
	{
		float xMove = _speed * (float) Math.cos(Math.toRadians(_angle));
		float yMove = - _speed * (float) Math.cos(Math.toRadians(_angle) + (Math.PI / 2));
		_shape = _shape.transform(Transform.createTranslateTransform(xMove, yMove));
		return;
	}
}
