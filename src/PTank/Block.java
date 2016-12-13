package PTank;

import org.newdawn.slick.geom.Shape;

public abstract class Block {

	private final static float BLOCK_WIDTH = 32;
	private final static float BLOCK_HEIGHT = 32;
	
	public static float getWidth() {
		return BLOCK_WIDTH;
	}

	public static float getHeight() {
		return BLOCK_HEIGHT;
	}

	public Block()
	{}
	
	public abstract Shape getShape();
}
