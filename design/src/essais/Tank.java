package essais;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Tank {	

	private Shape shape[];
	private Color color[];
	
	private float tankAngle, cannonAngle, speed;
	
	private final int NB_SEG_CAT;
	//contient les dimensions
	private final float TANK[];
	private final float CAT[];
	private final float CANNON[];
	private final float TURRET_WIDTH;

	
	public Tank(float tkX, float tkY, int width, int height, int nbSeg) {
		
		/* tkX, tkY : tank x, tank y
		 * rCtX, rCtY, lCtX, lCtY : rCaterpillar x y, lCaterpillar x y 
		 * cnX, xY : shape[R.CANNON] x, shape[R.CANNON] y
		 * trX, trY : shape[R.TURRET] x, shape[R.TURRET] y; */
		
		color = new Color[R.NB_COLORS_TANK];
		color[R.TANK]  	= new Color(30, 95, 5);
		color[R.CANNON] = new Color(145, 180, 145);
		color[R.TURRET] = new Color(90, 175, 35);
		color[R.CAT_BG] = new Color(140, 180, 140);
		color[R.CAT_OV] = new Color(65, 35, 5);
		
		TANK = new float[2];
		TANK[R.WIDTH]  = width;
		TANK[R.HEIGHT] = height;;
		
		NB_SEG_CAT = nbSeg;
		
		CAT = new float[2];		
		CAT[R.HEIGHT] = (float) (TANK[R.HEIGHT]*1.2);
		CAT[R.WIDTH] = CAT[R.HEIGHT]/NB_SEG_CAT;

		CANNON = new float[2];
		
		CANNON[R.WIDTH] = TANK[R.WIDTH]/6;
		CANNON[R.HEIGHT] = TANK[R.HEIGHT]/2;
		
		TURRET_WIDTH = TANK[R.WIDTH]/4;
		
		shape = new Shape[R.NB_SHAPES_TANK];
		
		shape[R.TANK] = new Rectangle(tkX, tkY, TANK[R.WIDTH], TANK[R.HEIGHT]);
		shape[R.TANK].setCenterX(tkX);
		shape[R.TANK].setCenterY(tkY);
				
		float lCtX = shape[R.TANK].getCenterX() - TANK[R.WIDTH]/2 - CAT[R.WIDTH] + CAT[R.WIDTH]*0.2f;
		float lCtY = shape[R.TANK].getCenterY() - TANK[R.HEIGHT]/2 - (CAT[R.HEIGHT] -TANK[R.HEIGHT])/2;
		
		float rCtX = shape[R.TANK].getCenterX() + TANK[R.WIDTH]/2 - CAT[R.WIDTH]*0.2f;
		float rCtY = shape[R.TANK].getCenterY() - TANK[R.HEIGHT]/2 - (CAT[R.HEIGHT] -TANK[R.HEIGHT])/2;
		
		shape[R.LCAT_BG] = new Rectangle(lCtX, lCtY, CAT[R.WIDTH], CAT[R.HEIGHT]);
		shape[R.LCAT_OV] = GridShape.get(NB_SEG_CAT, CAT[R.WIDTH], lCtX, lCtY);
		shape[R.LCAT_MV] = GridShape.get(NB_SEG_CAT-1, CAT[R.WIDTH], lCtX, lCtY);
		shape[R.LCAT_MV] = Transform.translate(shape[R.LCAT_MV], 0, CAT[R.WIDTH]/2);
		
		shape[R.RCAT_BG] = new Rectangle(rCtX, rCtY, CAT[R.WIDTH], CAT[R.HEIGHT]);
		shape[R.RCAT_OV] = GridShape.get(NB_SEG_CAT, CAT[R.WIDTH], rCtX, rCtY);
		shape[R.RCAT_MV] = GridShape.get(NB_SEG_CAT-1, CAT[R.WIDTH], rCtX, rCtY);
		shape[R.RCAT_MV] = Transform.translate(shape[R.RCAT_MV], 0, CAT[R.WIDTH]/2);
		
		//on créé le shape[R.CANNON]
		float cnX = shape[R.TANK].getCenterX() - CANNON[R.WIDTH]/2, cnY = shape[R.TANK].getCenterY();
		
		shape[R.CANNON] = new Rectangle(cnX, cnY, CANNON[R.WIDTH], CANNON[R.HEIGHT]);
		
		//on créé la tourelle
		float trX = shape[R.TANK].getCenterX(), trY = shape[R.TANK].getCenterY();
		
		shape[R.TURRET] = new Circle(trX, trY, TURRET_WIDTH);
			
		//on fait tourner le tout pour que le tank soit orienté vers le 0 du repère
		float tX, tY, rotation;
		
		tX = shape[R.TANK].getCenterX();
		tY = shape[R.TANK].getCenterY();
		rotation =  (float) Math.toRadians(270f);
		
		for(int i = 0; i < R.NB_SHAPES_TANK; i++)
			shape[i] = Transform.rotate(shape[i], rotation, tX, tY);

		speed = R.SPEED;
	}
	
	public Tank(float tkX, float tkY) {
		this(tkX, tkY, R.DEFAULT_TW, R.DEFAULT_TH, R.DEFAULT_NS);
	}
	
	public Tank(float tkX, float tkY, int width, int height) {
		this(tkX, tkY, width, height, R.DEFAULT_NS);
	}
	
	public Tank(float tkX, float tkY, int width, int height, int nbSeg, Color... color) {
		this(tkX, tkY, width, height, nbSeg);

		if(color.length <= R.NB_COLORS_TANK)
			for(int i = 0; i < color.length; i++)
				this.color[i] = color[i];
	}
	
	public boolean contains(float x, float y) {
		return (shape[R.TURRET].contains(x, y) || shape[R.CANNON].contains(x, y) || shape[R.TANK].contains(x, y) ||shape[R.RCAT_BG].contains(x, y) || shape[R.LCAT_BG].contains(x, y));
	}
	
	public void draw(Graphics g) {
		//on dessine le tank, on récupère le pinceau de la méthode render() du State appelant
		g.setColor(color[R.CAT_BG]);	
		g.fill(shape[R.RCAT_BG]);	g.draw(shape[R.RCAT_BG]);
		g.fill(shape[R.LCAT_BG]);	g.draw(shape[R.LCAT_BG]);
		
		g.setColor(color[R.CAT_OV]);	
		g.draw(shape[R.RCAT_OV]);
		g.draw(shape[R.LCAT_OV]);
		
		if(InGameState.isMoving()) {
			g.setColor(color[R.CAT_OV]);	
			g.draw(shape[R.RCAT_MV]);
			g.draw(shape[R.LCAT_MV]);
		}
		
		g.setColor(color[R.TANK]);	 g.fill(shape[R.TANK]);   g.draw(shape[R.TANK]);
		g.setColor(color[R.CANNON]); g.fill(shape[R.CANNON]); g.draw(shape[R.CANNON]);
		g.setColor(color[R.TURRET]); g.fill(shape[R.TURRET]); g.draw(shape[R.TURRET]);
	}
	
	public void rotateCannon(int mX, int mY) {
		 //mouseAngle : angle entre le point où se trouve la souris et le 0 du repère
		float tkX = shape[R.TANK].getCenterX(), tkY = shape[R.TANK].getCenterY();		
		float a = mX - tkX, b = mY - tkY;		
		float mouseAngle = (float) Math.toDegrees(Math.atan(b/a));
		
		if (a >= 0 && b < 0) {
			mouseAngle += 360;
		}
		
		else if(a < 0) {
			mouseAngle += 180;
		}
		
		shape[R.CANNON] = Transform.rotate(shape[R.CANNON], (float) (Math.toRadians(mouseAngle - cannonAngle)), shape[R.TANK].getCenterX(), shape[R.TANK].getCenterY());
		cannonAngle = mouseAngle;
	}
	
	public void update(float angle, int dt) {
			
		float xMove = speed * (float) Math.cos(Math.toRadians(tankAngle));
		float yMove = speed * (float) Math.cos(Math.toRadians(tankAngle) + Math.PI/2);
		float x = dt * xMove;
		float y = dt * yMove;
		
		for(int i = 0; i < R.NB_SHAPES_TANK; i ++)
			shape[i] = Transform.translate(shape[i], x, y);
		
		this.setAngle(angle);
	}
	
	public void setAngle(float angle) {
		float agl = (float) Math.toRadians(tankAngle - angle);
		float x = shape[R.TANK].getCenterX();
		float y = shape[R.TANK].getCenterY();
		
		shape[R.TANK] = Transform.rotate(shape[R.TANK], agl, x, y);
		
		for(int i = R.LCAT_BG; i <= R.RCAT_MV; i++)
			shape[i] = Transform.rotate(shape[i], agl, x, y);
		
		tankAngle = angle;
	}
	
	public void moveTo(float x, float y) {
		//déplace le tank aux coordonnées x y
		System.out.println("x " + x + " y " + y);
		x -= shape[R.TANK].getCenterX();
		y -= shape[R.TANK].getCenterY();
				
		System.out.println("x " + x + " y " + y);
		
		for(int i = 0; i < R.NB_SHAPES_TANK; i++)
			shape[i] = Transform.translate(shape[i], x, y);
	}


	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setTankColor(Color cTank) {
		color[R.TANK] = cTank;
	}

	public void setCannonColor(Color cCannon) {
		color[R.CANNON] = cCannon;
	}

	public void setTurretColor(Color cTurret) {
		color[R.TURRET] = cTurret;
	}

	public void setCaterpillarColor(Color cCat1, Color cCat2) {
		color[R.CAT_BG] = cCat1;
		color[R.CAT_OV] = cCat1;
	}
	
	public void setColorOnShape(float x, float y, Color c) {
		//lorsqu'on clique sur le tank, on modifie la couleur de la shape sur laquelle on a cliqué
		if(shape[R.TURRET].contains(x, y))
			color[R.TURRET] = c;
		
		else if(shape[R.CANNON].contains(x, y))
			color[R.CANNON] = c;
		
		else if(shape[R.TANK].contains(x, y))
			color[R.TANK] = c;
		
		else if(shape[R.RCAT_BG].contains(x, y) || shape[R.LCAT_BG].contains(x, y))
			color[R.CAT_BG] = c;
		
		else if(shape[R.RCAT_OV].contains(x, y) || shape[R.LCAT_OV].contains(x, y))
			color[R.CAT_OV] = c;
	}
	
	public Color getTurretColor() {
		return color[R.TURRET];
	}
	
	public float getCenterX() {
		return shape[R.TANK].getCenterX();
	}
	
	public float getCenterY() {
		return shape[R.TANK].getCenterY();
	}
	
	public Color getColorOnShape(float x, float y) {
		//lorsqu'on clique sur le tank, on renvoie la couleur de la shape sur laquelle on a cliqué
		Color c = null;
		
		if(shape[R.TURRET].contains(x, y))
			c = color[R.TURRET];
		
		else if(shape[R.CANNON].contains(x, y))
			c = color[R.CANNON];
		
		else if(shape[R.TANK].contains(x, y))
			c = color[R.TANK];
		
		else if(shape[R.RCAT_BG].contains(x, y) || shape[R.LCAT_BG].contains(x, y))
			c = color[R.CAT_BG];
		
		else if(shape[R.RCAT_OV].contains(x, y) || shape[R.LCAT_OV].contains(x, y))
			c = color[R.CAT_OV];
		
		return c;
	}
}
