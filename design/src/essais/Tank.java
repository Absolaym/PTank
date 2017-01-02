package essais;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Tank {	
	/*
	 * rCaterpillar[0] : couleur de la chenille
	 * rCaterpillar[1] : forme de la chenille
	 * rCaterpillar[2] : forme de la chenille affichée en déplacement
	 * 
	 * cCaterpillar1 : couleur de fond de la chenille
	 * cCaterpillar2 : couleur des segments de la chenille
	 */
	
	//un tank est composé d'un canon, d'une tourelle, d'un corps (tank) et de 6 chenilles : 3 gauches, 3 droites
	//la première est la shape pour la couleur de la chenille
	//la deuxième est la shape qui matérialise les segments de la chenille
	//la troisième est comme la deuxième (moins un segment) et n'est afichée qu'en mouvement
	private Shape tank, cannon, turret, rCaterpillar[], lCaterpillar[];
	//une couleur par shape (cCaterpillar1 pour les chenilles de fond, cCaterpillar2 pour les chenilles de forme
	private Color cTank, cCannon, cTurret, cCaterpillar1, cCaterpillar2;
	
	//angle du tank et du canon, vitesse du tank
	private float tankAngle, cannonAngle, speed;
	//taille du tank
	private int tankWidth, tankHeight;
	
	//nombre de segments des chenilles, par défaut à 6
	private final int NB_SEG_CAT;
	//taille des chenilles
	private final float CAT_HEIGHT, CAT_WIDTH;
	//taille du canon
	private final float CANNON_WIDTH, CANNON_HEIGHT;
	//taille de la tourelle
	private final float TURRET_WIDTH;

	
	public Tank(float tkX, float tkY, int width, int height, int nbSeg) {
		
		/* tkX, tkY : tank x, tank y
		 * rCtX, rCtY, lCtX, lCtY : rCaterpillar x y, lCaterpillar x y 
		 * cnX, xY : cannon x, cannon y
		 * trX, trY : turret x, turret y; */
		
		//on initialise les variables/constantes
		cTank = new Color(30, 95, 5);
		cCannon = new Color(145, 180, 145);
		cTurret = new Color(90, 175, 35);
		cCaterpillar1 = new Color(140, 180, 140);
		cCaterpillar2 = new Color(65, 35, 5);
		
		tankWidth = width;
		tankHeight = height;
		
		NB_SEG_CAT = nbSeg;
		CAT_HEIGHT = (float) (tankHeight*1.2);
		CAT_WIDTH = CAT_HEIGHT/NB_SEG_CAT;
		CANNON_WIDTH = tankWidth/6;
		CANNON_HEIGHT = tankHeight/2;
		TURRET_WIDTH = tankWidth/4;
		
		//on créé le tank
		tank = new Rectangle(tkX, tkY, tankWidth, tankHeight);
		tank.setCenterX(tkX);
		tank.setCenterY(tkY);
				
		float lCtX = tank.getCenterX() - tankWidth/2 - CAT_WIDTH + CAT_WIDTH*0.2f;
		float lCtY = tank.getCenterY() - tankHeight/2 - (CAT_HEIGHT -tankHeight)/2;
		
		float rCtX = tank.getCenterX() + tankWidth/2 - CAT_WIDTH*0.2f;
		float rCtY = tank.getCenterY() - tankHeight/2 - (CAT_HEIGHT -tankHeight)/2;
		
		//on créé les chenilles
		lCaterpillar = new Shape[3];
		rCaterpillar = new Shape[3];
		
		lCaterpillar[0] = new Rectangle(lCtX, lCtY, CAT_WIDTH, CAT_HEIGHT);
		lCaterpillar[1] = GridShape.get(NB_SEG_CAT, CAT_WIDTH, lCtX, lCtY);
		lCaterpillar[2] = GridShape.get(NB_SEG_CAT-1, CAT_WIDTH, lCtX, lCtY);
		lCaterpillar[2] = Transform.translate(lCaterpillar[2], 0, CAT_WIDTH/2);
		
		rCaterpillar[0] = new Rectangle(rCtX, rCtY, CAT_WIDTH, CAT_HEIGHT);
		rCaterpillar[1] = GridShape.get(NB_SEG_CAT, CAT_WIDTH, rCtX, rCtY);
		rCaterpillar[2] = GridShape.get(NB_SEG_CAT-1, CAT_WIDTH, rCtX, rCtY);
		rCaterpillar[2] = Transform.translate(rCaterpillar[2], 0, CAT_WIDTH/2);
		
		//on créé le canon
		float cnX = tank.getCenterX() - CANNON_WIDTH/2, cnY = tank.getCenterY();
		
		cannon = new Rectangle(cnX, cnY, CANNON_WIDTH, CANNON_HEIGHT);
		
		//on créé la tourelle
		float trX = tank.getCenterX(), trY = tank.getCenterY();
		
		turret = new Circle(trX, trY, TURRET_WIDTH);
			
		//on fait tourner le tout pour que le tank soit orienté vers le 0 du repère
		float tX, tY, rotation;
		
		tX = tank.getCenterX();
		tY = tank.getCenterY();
		rotation =  (float) Math.toRadians(270f);
		
		tank = Transform.rotate(tank, rotation, tX, tY);
		cannon = Transform.rotate(cannon, rotation, tX, tY);
		
		for(int i = 0; i < 3; i++) {
			rCaterpillar[i] = Transform.rotate(rCaterpillar[i], rotation, tX, tY);
			lCaterpillar[i] = Transform.rotate(lCaterpillar[i], rotation, tX, tY);
		}
		
		speed = 0.1f;
	}
	
	public Tank(float tkX, float tkY) {
		this(tkX, tkY, 32, 48, 6);
	}
	
	public Tank(float tkX, float tkY, int width, int height) {
		this(tkX, tkY, width, height, 6);
	}
	
	public Tank(float tkX, float tkY, int width, int height, int nbSeg, Color cTank, Color cCannon, Color cTurret, Color cCaterpillar1, Color cCaterpillar2) {
		this(tkX, tkY, width, height, nbSeg);
		this.cTank = cTank;
		this.cCannon = cCannon;
		this.cTurret = cTurret;
		this.cCaterpillar1 = cCaterpillar1;
		this.cCaterpillar2 = cCaterpillar2;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setAngle(float angle) {
		float agl = (float) Math.toRadians(tankAngle - angle);
		float x = tank.getCenterX();
		float y = tank.getCenterY();
		
		tank = Transform.rotate(tank, agl, x, y);

		for(int i = 0; i < 3; i++) {
			rCaterpillar[i] = Transform.rotate(rCaterpillar[i], agl, x, y);
			lCaterpillar[i] = Transform.rotate(lCaterpillar[i], agl, x, y);
		}
		
		tankAngle = angle;
	}

	public void setTankColor(Color cTank) {
		this.cTank = cTank;
	}

	public void setCannonColor(Color cCannon) {
		this.cCannon = cCannon;
	}

	public void setTurretColor(Color cTurret) {
		this.cTurret = cTurret;
	}

	public void setCaterpillarColor(Color cCaterpillar1, Color cCaterpillar2) {
		this.cCaterpillar1 = cCaterpillar1;
		this.cCaterpillar2 = cCaterpillar2;
	}
	
	public void setColorOnShape(float x, float y, Color c) {
		//lorsqu'on clique sur le tank, on modifie la couleur de la shape sur laquelle on a cliqué
		if(turret.contains(x, y))
			cTurret = c;
		
		else if(cannon.contains(x, y))
			cCannon = c;
		
		else if(tank.contains(x, y))
			cTank = c;
		
		else if(rCaterpillar[0].contains(x, y) || lCaterpillar[0].contains(x, y))
			cCaterpillar1 = c;
		
		else if(rCaterpillar[1].contains(x, y) || lCaterpillar[1].contains(x, y))
			cCaterpillar2 = c;
	}
	
	public Color getTurretColor() {
		return cTurret;
	}
	
	public float getCenterX() {
		return tank.getCenterX();
	}
	
	public float getCenterY() {
		return tank.getCenterY();
	}
	
	public Color getColorOnShape(float x, float y) {
		//lorsqu'on clique sur le tank, on renvoie la couleur de la shape sur laquelle on a cliqué
		Color c = null;
		
		if(turret.contains(x, y))
			c = cTurret;
		
		else if(cannon.contains(x, y))
			c = cCannon;
		
		else if(tank.contains(x, y))
			c = cTank;
		
		else if(rCaterpillar[0].contains(x, y) || lCaterpillar[0].contains(x, y))
			c = cCaterpillar1;
		
		else if(rCaterpillar[1].contains(x, y) || lCaterpillar[1].contains(x, y))
			c = cCaterpillar2;
		
		return c;
	}
	
	public boolean contains(float x, float y) {
		return (turret.contains(x, y) || cannon.contains(x, y) || tank.contains(x, y) ||rCaterpillar[0].contains(x, y) || lCaterpillar[0].contains(x, y));
	}
	
	public void draw(Graphics g) {
		//on dessine le tank, on récupère le pinceau de la méthode render() du State appelant
		g.setColor(cCaterpillar1);	
		g.fill(rCaterpillar[0]);	g.draw(rCaterpillar[0]);
		g.fill(lCaterpillar[0]);	g.draw(lCaterpillar[0]);
		
		g.setColor(cCaterpillar2);	
		g.draw(rCaterpillar[1]);
		g.draw(lCaterpillar[1]);
		
		if(InGameState.isMoving()) {
			g.setColor(cCaterpillar2);	
			g.draw(rCaterpillar[2]);
			g.draw(lCaterpillar[2]);
		}
		
		g.setColor(cTank);	 g.fill(tank); 	 g.draw(tank);
		g.setColor(cCannon); g.fill(cannon); g.draw(cannon);
		g.setColor(cTurret); g.fill(turret); g.draw(turret);
	}
	
	public void rotateCannon(int mX, int mY) {
		 //mouseAngle : angle entre le point où se trouve la souris et le 0 du repère
		float tkX = tank.getCenterX(), tkY = tank.getCenterY();		
		float a = mX - tkX, b = mY - tkY;		
		float mouseAngle = (float) Math.toDegrees(Math.atan(b/a));
		
		if (a >= 0 && b < 0) {
			mouseAngle += 360;
		}
		
		else if(a < 0) {
			mouseAngle += 180;
		}
		
		cannon = Transform.rotate(cannon, (float) (Math.toRadians(mouseAngle - cannonAngle)), tank.getCenterX(), tank.getCenterY());
		cannonAngle = mouseAngle;
	}
	
	public void update(float angle, int dt) {
			
		float xMove = speed * (float) Math.cos(Math.toRadians(tankAngle));
		float yMove = speed * (float) Math.cos(Math.toRadians(tankAngle) + Math.PI/2);
		float x = dt * xMove;
		float y = dt * yMove;
		
		tank = Transform.translate(tank, x, y);
		cannon = Transform.translate(cannon, x, y);
		turret = Transform.translate(turret, x, y);
		
		for(int i = 0; i < 3; i++) {
			rCaterpillar[i] = Transform.translate(rCaterpillar[i], x, y);
			lCaterpillar[i] = Transform.translate(lCaterpillar[i], x, y);
		}
		
		this.setAngle(angle);
	}
	
	public void moveTo(float x, float y) {
		//déplace le tank aux coordonnées x y
		System.out.println("x " + x + " y " + y);
		x -= tank.getCenterX();
		y -= tank.getCenterY();
				
		System.out.println("x " + x + " y " + y);
		
		tank = Transform.translate(tank, x, y);
		cannon = Transform.translate(cannon, x, y);
		turret = Transform.translate(turret, x, y);
		
		for(int i = 0; i < 3; i++) {
			rCaterpillar[i] = Transform.translate(rCaterpillar[i], x, y);
			lCaterpillar[i] = Transform.translate(lCaterpillar[i], x, y);
		}
	}
}
