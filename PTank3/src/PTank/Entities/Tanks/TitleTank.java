package PTank.Entities.Tanks;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Polygon;

import PTank.Entities.Transform;
import PTank.Entities.Weapon;
import PTank.Map.Map;
import PTank.Ressources.Ressources;

public class TitleTank extends Player
{
		// --------------------------------------------
		// ------------- Attributes -------------------
		// --------------------------------------------
		private final static float SPEED = 0.15f;
		private final static int NB_BULLETS_MAX = 20;
		
		private boolean hasFired = false;
		public static boolean bouge = false;
		
		// --------------------------------------------
		// -------------- Methods ---------------------
		// --------------------------------------------
		public TitleTank(Map map, float x, float y)
		{
			super(map,x,y);
		}

		@Override
		public void update(GameContainer gc, int dt) throws SlickException 
		{
			Input input = gc.getInput();
			
			// On calcule l'angle que doit avoir le weapon
			
			//this.weapon.update(gc.getInput().getMouseX(), gc.getInput().getMouseY());
			
			// calcul de bouge (le tank doit bouger) et angleDirection = angle du player
			bouge = true;
			float angleDirection = 0;
			int x = 0;
			int y = 0;
			
			// up 270
			if (input.isKeyDown(Input.KEY_T))
			{
				y++;
			}
			
			// down 90
			if (input.isKeyDown(Input.KEY_G))
			{
				y--;
			}
			
			// left 180
			if (input.isKeyDown(Input.KEY_F))
			{
				x--;
			}
			
			// right 0
			if (input.isKeyDown(Input.KEY_H))
			{
				x++;
			}
			
			if (x == 1)
			{
				if ( map.isBlockingBlock(shape.getCenterX() + 16, shape.getCenterY()) )
				{
					x = 0;
				}			
			}
			if (x == -1)
			{
				if ( map.isBlockingBlock(shape.getCenterX() - 16, shape.getCenterY()) )
				{
					x = 0;
				}			
			}
			if (y == 1)
			{
				if ( map.isBlockingBlock(shape.getCenterX(), shape.getCenterY() - 16) )
				{
					y = 0;
				}			
			}
			if (y == -1)
			{
				if ( map.isBlockingBlock(shape.getCenterX(), shape.getCenterY() + 16) )
				{
					y = 0;
				}			
			}
			
			if (x == 0 && y == 0) { bouge = false; }
			else if (x == 1  && y == 0) 	{ angleDirection = 0; }
			else if (x == 1  && y == -1) 	{ angleDirection = 45; }
			else if (x == 0  && y == -1) 	{ angleDirection = 90; }
			else if (x == -1 && y == -1) 	{ angleDirection = 135; }
			else if (x == -1 && y == 0) 	{ angleDirection = 180; }
			else if (x == -1 && y == 1) 	{ angleDirection = 225; }
			else if (x == 0  && y == 1) 	{ angleDirection = 270; }
			else if (x == 1  && y == 1) 	{ angleDirection = 315; }
			
			if (bouge)
			{
				this.setAngle(angleDirection);
				this.move(dt);
			}
			
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !this.hasFired)
			{
				if(this.fire()){
					Sound fire = new Sound("ressources/musique/Piou.WAV");
				    fire.play();
				}
				this.hasFired = true;
			}
			if (!input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			{
				this.hasFired = false;
			}

			// update his weapon
			float mouseX = gc.getInput().getMouseX();
			float mouseY = gc.getInput().getMouseY();

			this.weapon.update(mouseX, mouseY, gc, dt);
		}
		
		public int getId() { return Tanks.PLAYER; }
	}
