package PTank.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

public class Ground extends Block
{
	//constructor
	// crée un wall avec le coin supérieur gauche en (x,y) (pixels)
	public Ground(int x, int y)
	{
		isBouncingBullets = false;
		isBlockingTanks = false;
		shape = new Rectangle(x, y, Block.getWidth(), Block.getHeight());
		color = Color.gray;
	}
	

	@Override
	public void update(GameContainer gc, int dt) throws SlickException
	{
		// does nothing when updated :)
	}

}