package PTank;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Bullet extends Entity {
	
	private final static float WIDTH = 9.f;
	private final static float HEIGHT = 13.f;
	private final static float TOP = 4.f;
	private float _speed;
	private float _angle;
	private Shape _shape;
	private int _bouncesLeft;
	
	private float _colUp[];
	private float _colRight[];
	private float _colDown[];
	private float _colLeft[];
	
	private boolean _bouncesHorizontaly;
	private boolean _bouncesVerticaly;
	
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
		_bouncesLeft = 1;
		
		float colUp[] = {x, y + tmpB};
		float colRight[] = {x + tmpB, y};
		float colDown[] = {x, y - tmpB};
		float colLeft[] = {x - tmpB, y};
		_colUp = colUp;
		_colRight = colRight;
		_colDown = colDown;
		_colLeft = colLeft;
		
		/*
		_polygon.addPoint(x - tmpA, y - tmpB);
		_polygon.addPoint(x + tmpA, y - tmpB);
		_polygon.addPoint(x + tmpA, y + tmpB);
		_polygon.addPoint(x, y + tmpB + TOP);
		_polygon.addPoint(x - tmpA, y + tmpB);
 		*/
		
		// rotating it from the center with given angle in degrees
		_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians(270), x, y));
		_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians(_angle), x, y));
		_bouncesVerticaly = false;
		_bouncesHorizontaly = false;
	}
	
	public Shape getShape()
	{
		return _shape;
	}
	
	public void setShape(Shape shape)
	{
		_shape = shape;
	}
	
	
	
	public float[] getColUp() {
		return _colUp;
	}

	public float[] getColRight() {
		return _colRight;
	}

	public float[] getColDown() {
		return _colDown;
	}

	public float[] getColLeft() {
		return _colLeft;
	}

	public void setColUp(float[] _colUp) {
		this._colUp = _colUp;
	}

	public void setColRight(float[] _colRight) {
		this._colRight = _colRight;
	}

	public void setColDown(float[] _colDown) {
		this._colDown = _colDown;
	}

	public void setColLeft(float[] _colLeft) {
		this._colLeft = _colLeft;
	}

	public float getAngle() { return _angle; }
	public void setAngle(float deltaTeta) { _angle = deltaTeta;	}
	
	public float getSpeed() { return _speed; }
	
	public float getX()         { return _shape.getCenterX(); }
	public void  setX(float x)  { _shape.setCenterX(x); }
	
	public float getY()         { return _shape.getCenterY(); }
	public void  setY(float y)  { _shape.setCenterY(y); }

	public float getSpeedX() { return _speed * (float) Math.cos(Math.toRadians(_angle)); }
	public float getSpeedY() { return - _speed * (float) Math.cos(Math.toRadians(_angle) + (Math.PI / 2)); }
	
	
	
	public void move(int dt)
	{
		boolean angleMoves = false;
		
		if (_bouncesHorizontaly)
		{
			angleMoves = true;
			_angle =  - _angle;
			_bouncesHorizontaly = false;
		}
		if (_bouncesVerticaly)
		{
			angleMoves = true;
			_angle = 180 - _angle;
			_bouncesVerticaly = false;
		}


		
		
		if (angleMoves)
		{
			_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians( (_angle)), this.getX(), this.getY()));
		}
		
		float xMove = dt * this.getSpeedX();
		float yMove = dt * this.getSpeedY();
		
		_shape = _shape.transform(Transform.createTranslateTransform(xMove, yMove));
		
		return;
	}
	
	public void finalize () 
	{
		this.setX(0);
		this.setY(0);
	}
	
	public int getBouncesLeft() { return _bouncesLeft; }
	public void setBouncesLeft(int nb) { _bouncesLeft = nb; }
	public void setBouncesHorizontaly(boolean bool) { _bouncesHorizontaly = bool; }
	public void setBouncesVerticaly(boolean bool) { _bouncesVerticaly = bool; }
}

