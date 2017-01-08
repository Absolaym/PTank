package PTank.Entities.Tanks;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

import PTank.Entities.GridShape;
import PTank.Entities.Skin;
import PTank.Entities.Transform;

import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;

public class EnnemyImmobileSkin extends Skin {
	private static final int 	NB_SEG_CAT = 6, 
								TANK = 0, TURRET = 1, 
								LCAT_BG = 2, LCAT_OV = 3, LCAT_MV = 4,
								RCAT_BG = 5, RCAT_OV = 6, RCAT_MV = 7,
								CAT_BG = 2, CAT_OV = 3;
	 
	private final float CAT_WIDTH, CAT_HEIGHT,
						TURRET_WIDTH;
	
	public EnnemyImmobileSkin(float x, float y, float width, float height) {
		nbShapes = 8;
		CAT_HEIGHT = height*1.1f;
		CAT_WIDTH = CAT_HEIGHT/NB_SEG_CAT;
		TURRET_WIDTH = width/3.5f;
		
		shapes = new Shape[nbShapes];
		colors = new Color[nbShapes-4];
		
		shapes[TANK] = new Rectangle(x - width/2, y - height/2, width, height);		
		shapes[TURRET] = new Circle(x, y, TURRET_WIDTH);
		
		shapes[LCAT_BG] = new Rectangle(x - width/2 - CAT_WIDTH*0.8f, y - height/2 - (CAT_HEIGHT-height)/2, CAT_WIDTH, CAT_HEIGHT);		
		shapes[LCAT_OV] = GridShape.get(x - width/2 - CAT_WIDTH*0.8f, y - height/2 - (CAT_HEIGHT-height)/2, NB_SEG_CAT, CAT_WIDTH);
		shapes[LCAT_MV] = GridShape.get(x - width/2 - CAT_WIDTH*0.8f, y - height/2 - (CAT_HEIGHT-height)/2, NB_SEG_CAT-1, CAT_WIDTH);
		
		shapes[RCAT_BG] = new Rectangle(x + width/2 - CAT_WIDTH*0.2f, y - height/2 - (CAT_HEIGHT-height)/2, CAT_WIDTH, CAT_HEIGHT);	
		shapes[RCAT_OV] = GridShape.get(x + width/2 - CAT_WIDTH*0.2f, y - height/2 - (CAT_HEIGHT-height)/2, NB_SEG_CAT, CAT_WIDTH);	
		shapes[RCAT_MV] = GridShape.get(x + width/2 - CAT_WIDTH*0.2f, y - height/2 - (CAT_HEIGHT-height)/2, NB_SEG_CAT-1, CAT_WIDTH);	
		
		shapes[LCAT_MV] = Transform.translate(shapes[LCAT_MV], 0, CAT_WIDTH/2);
		shapes[RCAT_MV] = Transform.translate(shapes[RCAT_MV], 0, CAT_WIDTH/2);
		
		colors[TANK] = new Color(95, 5, 30);
		colors[TURRET] = new Color(175, 35, 90);
		this.canonColor = new Color(175, 35, 90);
		colors[CAT_BG] = new Color(180, 140, 140);
		colors[CAT_OV] = new Color(65, 35, 5);
		
		for(int i = 0; i < nbShapes; i++)
			shapes[i] = Transform.rotate(shapes[i], (float) Math.toRadians(270), x, y);
	}
	
	@Override
	public void render(Graphics g) {
		g.setColor(colors[CAT_BG]);
		g.fill(shapes[LCAT_BG]);
		g.fill(shapes[RCAT_BG]);
		
		g.setColor(colors[CAT_OV]);
		g.draw(shapes[LCAT_OV]);
		g.draw(shapes[RCAT_OV]);
		
		if(Player.bouge) {
			g.draw(shapes[LCAT_MV]);
			g.draw(shapes[RCAT_MV]);
		}
		
		g.setColor(colors[TANK]);
		g.fill(shapes[TANK]);
		
		g.setColor(colors[TURRET]);
		g.fill(shapes[TURRET]);
	}
}
