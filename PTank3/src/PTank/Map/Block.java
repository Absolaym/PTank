package PTank.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Shape;

import PTank.Entities.*;

public abstract class Block extends Entity
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private final static float BLOCK_WIDTH = 32;
	private final static float BLOCK_HEIGHT = 32;
	protected boolean isBouncingBullets;
	protected boolean isBlockingTanks;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	public static float getWidth() { return BLOCK_WIDTH; }
	public static float getHeight() { return BLOCK_HEIGHT; }
	public boolean isBouncingBullets() { return this.isBouncingBullets; }
	public boolean isBlockingTanks() { return this.isBouncingBullets; }
	
	public abstract void update(GameContainer gc, int dt) throws SlickException;
}

