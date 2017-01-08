package PTank.Entities.Tanks;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Polygon;

import PTank.Entities.Transform;
import PTank.Entities.Weapon;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class EnnemyImmobile extends Ennemy
{
	// attributes
	private final static float SPEED = 0.1f;
	private final static int NB_BULLETS_MAX = 50;
	
	private boolean hasFired = false;
	public static boolean bouge = false;
	
	public EnnemyImmobile(Map map, float x, float y)
	{
		this.map = map;
		this.shape = new Polygon(Ressources.getShape("EnnemyImmobile"));
		this.setCenterX(x);
		this.setCenterY(y);
		this.speed = SPEED;

		this.skin = new EnnemyImmobileSkin(x, y, WIDTH, HEIGHT);
		this.weapon = new Weapon(map, this, NB_BULLETS_MAX);


		this.color = new Color(1, 1, 1, 0.5f);
	}
	
	@Override
	public void update(GameContainer gc, int dt) throws SlickException 
	{
		this.weapon.setAngle((float) dt);
		
		
		// update his weapon
		Player player = map.getPlayer();
		float x = player.getCenterX();
		float y = player.getCenterY();
		this.weapon.update(x, y, gc, dt);
		
		if (this.isAlive())
		{
		if (dt % 6 == 0)
			if(this.fire());
		}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		if (isAlive && isVisible)
		{
			g.setColor(color);
			g.fill(shape);
			if(skin != null)
				skin.render(g);
		}
		
		this.weapon.render(gc, g);
	}
	
	public int getId() { return Tanks.ENNEMY_IMMOBILE; }
}
