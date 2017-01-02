package essais;

import java.util.Random;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class TankSelectState extends BasicGameState {
	
	private static int WINDOW_WIDTH, WINDOW_HEIGHT;
	private static final int ID = 2;
	
	//nombre de tanks à générer, taille des tanks, taille des boites
	private static final int NB_TANK = 8, TANK_WIDTH = 32, TANK_HEIGHT = 48,
							 BOX_WIDTH = 100, BOX_HEIGHT = 100;
	
	//boites qui vont entourer les tanks
	private static Rectangle[] boxes;
	//bouton pour accéder au menu de personnalisation
	private static Rectangle personalization;
	private static Tank[] tanks;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {	
		WINDOW_WIDTH = gc.getWidth();
		WINDOW_HEIGHT = gc.getHeight();
		
		boxes = new Rectangle[NB_TANK];
		tanks = new Tank[NB_TANK];
		
		//pour que les tanks s'affiche dans des directions aléatoires		
		float tankAngles[] = {0, 45, 90, 135, 180, 225, 270};	
		Random rand = new Random();
		
		//on génère le bouton personnaliser
		personalization = new Rectangle(0, 0, 300, 100);
		personalization.setCenterX(WINDOW_WIDTH/2);
		personalization.setCenterY(WINDOW_HEIGHT/6);
		
		//on génère les boites
		for(int i = 0; i < NB_TANK; i++) {
			boxes[i] = new Rectangle(0, 0, BOX_WIDTH, BOX_HEIGHT);
			
			if(i < NB_TANK/2) {
				boxes[i].setCenterX((i+1)*WINDOW_WIDTH/(NB_TANK/2+1));
				boxes[i].setCenterY((WINDOW_HEIGHT + WINDOW_HEIGHT/4)/2 - BOX_HEIGHT);
			}
			
			else {
				boxes[i].setCenterX((i-3)*WINDOW_WIDTH/(NB_TANK/2+1));
				boxes[i].setCenterY((WINDOW_HEIGHT + WINDOW_HEIGHT/4)/2 + BOX_HEIGHT);
			}
		}
		
		//on génère les tanks, ayant tous des couleurs aléatoires
		for(int i = 0; i < NB_TANK; i++) {
			tanks[i] = new Tank(boxes[i].getCenterX(),
								boxes[i].getCenterY(), 
								TANK_WIDTH, TANK_HEIGHT, 6, 
								new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)), 
								new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)), 
								new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)), 
								new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)), 
								new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
	
			//on les tourne dans une position aléatoire
			tanks[i].setAngle(tankAngles[rand.nextInt(7)]);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {		
				
		//si on passe sur le bouton on le surligne en jaune
		if(personalization.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(Color.yellow);
		}
		//on dessine le bouton
		g.fill(personalization);
		g.draw(personalization);
		
		//on dessine le texte sur le bouton
		String perso = "Personnaliser";
		g.setColor(Color.black);
		g.drawString(perso, personalization.getCenterX() - g.getFont().getWidth(perso)/2, personalization.getCenterY() - g.getFont().getHeight(perso)/2);
		
		
		//on surligne la boite si on passe dessus
		for(Rectangle box : boxes) {
			if(box.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				g.setColor(Color.yellow);
				g.fill(box);
				g.draw(box);
			}
			
			//on dessine la boite
			g.setColor(Color.white);
			g.draw(box);
		}
		
		//on dessine les tanks
		for(Tank tank : tanks)
			tank.draw(g);
		
		g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int g) throws SlickException {
		int mX =  gc.getInput().getMouseX();
		int mY =  gc.getInput().getMouseY();
		
		//on met à jour l'angle du canon
		for(Tank tank : tanks) {
			tank.rotateCannon(mX, mY);
		}
		
		//si on clique 
		if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			for(int i = 0; i < NB_TANK; i++) {
				//sur un tank, on le sauvegarde dans Data et on passe en jeu
				if(boxes[i].contains(mX, mY)) {
					Data.setTank(tanks[i]);
					game.enterState(3, new FadeOutTransition(), new FadeInTransition());
				}
				
				//sur le bouton, on rentre dans le menu nde personnalisation
				else if(personalization.contains(mX, mY))
					game.enterState(4, new FadeOutTransition(), new FadeInTransition());
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

}
