package PTank;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;

public abstract class Block {

	private final static float BLOCK_WIDTH = 32;
	private final static float BLOCK_HEIGHT = 32;
	protected boolean _bouncesBullets = false;
	
	public static float getWidth() {
		return BLOCK_WIDTH;
	}

	public static float getHeight() {
		return BLOCK_HEIGHT;
	}

	public Block()
	{}
	
	public abstract Shape getShape();
	public abstract Color getColor();
	public abstract void bounceBullets(Bullet[] bullets);
	
	public void setBoucesBullets(boolean bool) { _bouncesBullets = bool; }
	public boolean isBouncingBullets() { return _bouncesBullets; }


		
}
