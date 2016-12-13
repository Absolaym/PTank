package PTank;

import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;

public class Tank {
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static float WIDTH = 32;
	private final static float HEIGHT = 32;
	
	private final static float CANON_WIDTH = 8;
	private final static float CANON_HEIGHT = 32;
	
	private final static int BULLET_MAX = 100;
	private int _nbBulletsMax;
	private Bullet _bullets[];
	private int  _nbBullets;
	
	private float _speed;
	private float _angle;
	private float _canonAngle;
	
	private Shape _shape;
	private Shape _canon;
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------

	public Tank(float x, float y)
	{
		_bullets = new Bullet[BULLET_MAX];
		
		// Creation du shape de la carrosserie du tank
		float tmpA = WIDTH / 2;
		float tmpB = HEIGHT / 2;
		float points[] = {x - tmpA, y - tmpB,
						  x + tmpA, y - tmpB,
						  x + tmpA, y + tmpB,
						  x - tmpA, y + tmpB};

		_shape = new Polygon(points);
		_shape.setCenterX(x);
		_shape.setCenterY(y);
		
		// Creation du shape du canon
		float tmpC = CANON_WIDTH / 2;
		float tmpD = CANON_HEIGHT / 2;
		float points2[] = {x - tmpC, y - tmpC,
						  x + tmpC, y - tmpC,
						  x + tmpC, y + tmpD,
						  x - tmpC, y + tmpD};

		_canon = new Polygon(points2);
		_canon.setCenterX(x);
		_canon.setCenterY(y);
		
		// rotating it from the center with given angle in degrees
		_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians(- (_angle + 270f)), x, y));
		_canon = _canon.transform(Transform.createRotateTransform((float) Math.toRadians(- (_canonAngle + 270f)), x, y));
		
	}
	public Tank(float x, float y, int nbBulletsMax)
	{
		this(x,y);
		_nbBulletsMax = nbBulletsMax;
	}

	public Shape getShape()
	{
		return _shape;
	}
		
	public void setShape(Shape shape)
	{
		_shape = shape;
	}
	
	public Shape getCanon()
	{
		return _canon;
	}
		

	
	public float getSpeed() {
		return _speed;
	}
	public void setSpeed(float speed) {
		_speed = speed;
	}
	public float getAngle() {
		return _angle;
	}
	public void setAngle(float angle) {
		_angle = angle;
	}
	public float getCanonAngle() {
		return _canonAngle;
	}
	public void setCanonAngle(float angle) {

		if (angle != _canonAngle)
		{
			_canon = _canon.transform(Transform.createRotateTransform((float) Math.toRadians(- (_canonAngle + angle  + 270f)), _canon.getCenterX(), _canon.getCenterY()));
		}
		_canonAngle = angle;
	}
	
	// Utile
	public Bullet[] getBullets()
	{
		return _bullets;
	}

	public void setBullets(Bullet[] _bullets)
	{
		this._bullets = _bullets;
	}
	

	public void move()
	{
		float xMove = _speed * (float) Math.cos(Math.toRadians(_angle));
		float yMove = _speed * (float) Math.cos(Math.toRadians(_angle) + (Math.PI / 2));
		_shape = _shape.transform(Transform.createTranslateTransform(xMove, yMove));
		_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians(- (_angle + 270f)), _shape.getCenterX(), _shape.getCenterY()));

		return;
	}
	
	public void fire(float speed, float angle) 
	{
		if (_nbBullets >= _nbBulletsMax)
		{
			_nbBullets = 0;
		}
		_bullets[_nbBullets] = new Bullet(_shape.getCenterX(), _shape.getCenterY(), angle, speed);

		_nbBullets++;
	}
	
	public void update(GameIO gameIO)
	{
		float angleMove = -_angle;
		
		this.setAngle(gameIO.getTankAngle());
		this.setCanonAngle(gameIO.getTankCanonAngle());
		
		angleMove += _angle;
		
		if (gameIO.isMoving())
		{
			float xMove = _speed * (float) Math.cos(Math.toRadians(_angle));
			float yMove = _speed * (float) Math.cos(Math.toRadians(_angle) + (Math.PI / 2));
			_shape = _shape.transform(Transform.createTranslateTransform(xMove, yMove));
			_shape = _shape.transform(Transform.createRotateTransform((float) Math.toRadians(- (angleMove + 270f)), _shape.getCenterX(), _shape.getCenterY()));
		}
		
        if (gameIO.mouseLMB())
        {
        	gameIO.setMouseLMB(false);
        	this.fire(0.2f, this.getCanonAngle());
        }
	}
}
