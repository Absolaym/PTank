package essais;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class TankPersState extends BasicGameState {

	private static int WINDOW_WIDTH, WINDOW_HEIGHT;
	private static final int ID = 4;	
	
	private static final int BOX_WIDTH = 100, BOX_HEIGHT = 100, 
							 SLIDER_RADIUS = 10,
							 FINISHED_WIDTH = 150, FINISHED_HEIGHT = 50;
	
	//coordonnées du tank utiliséés dans render() pour colorier la shape du tank sur laquelle on clique
	//max correspond à la taille d'un slider, 
	//on s'en sert dans init et update pour placer les boutons des sliders en fonction de la couleur de la shape
	private static float tX, tY, max;
	
	//box entoure le tank, lines correspond aux lignes des trois sliders RGB, finished pour le bouton terminer
	private static Rectangle box, lines[], finished;
	
	//sliders correspond aux trois boutons des lines
	private static Circle sliders[];
	
	//on génère un tank par défaut
	private static Tank tank;
	 
	//pour pouvoir cliquer sur le slider et déplacer la souris où on veut (sinon faut rester dans la shape du slider, c'est chiant)
	private static int sliderId;
	private static boolean sliderOnClick;
	
	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {	
		WINDOW_WIDTH = gc.getWidth();
		WINDOW_HEIGHT = gc.getHeight();	
		
		//on créé la boite autour du tank
		box = new Rectangle(0, 0, BOX_WIDTH, BOX_HEIGHT);
		box.setCenterX(WINDOW_WIDTH/4);
		box.setCenterY(WINDOW_HEIGHT/2);
		
		int offset = 50, tmp = -offset;
		
		//on créé le bouton terminer
		finished = new Rectangle(0, 0, FINISHED_WIDTH, FINISHED_HEIGHT);
		finished.setCenterX(3*WINDOW_WIDTH/4);
		finished.setCenterY(WINDOW_HEIGHT/2 + 3*offset);
		
		lines = new Rectangle[3];
		
		//on créé les lignes
		for(int i = 0; i < 3; i++) {
			lines[i] = new Rectangle(0, 0, WINDOW_WIDTH/3, 1);
			lines[i].setCenterX(3*WINDOW_WIDTH/4);
			lines[i].setCenterY(WINDOW_HEIGHT/2 + tmp);
			
			tmp += offset;
		}
		
		//on créé le tank et on récupère ses coordonnées, pour pouvoir directement changer sa couleur 
		//(vu que c'est les coordonnées du centre, ce sera la tourelle qui changera de couleur)
		tank =  new Tank(box.getCenterX(), box.getCenterY());
		tX = tank.getCenterX();
		tY = tank.getCenterY();
		
		//on créé les boutons des sliders, on les positionne aux valeurs de la couleur de la tourelle
		sliders = new Circle[3];
		max = lines[0].getMaxX() - lines[0].getMinX();
		
		sliders[0] = new Circle(lines[0].getMinX() + (tank.getTurretColor().getRed() * max/255), lines[0].getCenterY(), SLIDER_RADIUS);
		sliders[1] = new Circle(lines[1].getMinX() + (tank.getTurretColor().getGreen() * max/255), lines[1].getCenterY(), SLIDER_RADIUS);
		sliders[2] = new Circle(lines[2].getMinX() + (tank.getTurretColor().getBlue() * max/255), lines[2].getCenterY(), SLIDER_RADIUS);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		
		//on surligne en jaune le bouton si la souris est dedans
		if(finished.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(Color.yellow);
		}
		
		//on dessine le bouton
		g.fill(finished);
		g.draw(finished);
		
		//on surligne les boutons des sliders si la souris est dedans
		for(int i = 0; i < 3; i++) {
			if(sliders[i].contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
				g.setColor(Color.yellow);
			}
			
			//on les dessine
			g.fill(sliders[i]);
			g.draw(sliders[i]);
			g.setColor(Color.white);
			
			g.fill(lines[i]);
			g.draw(lines[i]);
		}
		
		//on place le texte sur l'écran, automatiquement centré
		String terminate = "Terminer";
		g.setColor(Color.black);
		g.drawString(terminate,  finished.getCenterX() - g.getFont().getWidth(terminate)/2, finished.getCenterY() - g.getFont().getHeight(terminate)/2);
		
		String header = "Cliquez sur une partie du tank pour en changer la couleur";
		g.setColor(Color.white);
		g.drawString(header, WINDOW_WIDTH/2 - g.getFont().getWidth(header)/2, WINDOW_HEIGHT/6);
		
		String subHeader = "Maintenez ctrl pour que le canon ne bouge plus";
		g.drawString(subHeader, WINDOW_WIDTH/2 - g.getFont().getWidth(subHeader)/2, WINDOW_HEIGHT/6 + 40);
		
		g.setColor(Color.white);
		g.drawString("R", lines[0].getMinX() - 30, lines[0].getCenterY() - 10);
		g.drawString("G", lines[1].getMinX() - 30, lines[1].getCenterY() - 10);
		g.drawString("B", lines[2].getMinX() - 30, lines[2].getCenterY() - 10);

		g.draw(box);
		
		tank.draw(g);
		g.setColor(Color.white);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame game, int g) throws SlickException {
		int mX =  gc.getInput().getMouseX();
		int mY =  gc.getInput().getMouseY();
		int i;
						
		//si CTRL est pressés on arrête de faire tourner le canon
		if(!Keyboard.isKeyDown(Input.KEY_LCONTROL))
			tank.rotateCannon(mX, mY);
		
		
		//on récupère les valeurs des sliders, c'est à dire la position horizontale du centre des boutons ramenée entre 0 et 255
		float range = lines[0].getMaxX() - lines[0].getMinX();
		float min = lines[0].getMinX();
		
		int rgb[] = new int[3];
		
		for(i = 0; i < 3; i++) {
			rgb[i] = (int) (sliders[i].getCenterX() - min);
			rgb[i] *= 255/range;
		}
		
		//si on clique
		if(Mouse.isButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			//si on est sur le bouton terminer sans avoir cliqué sur un slider
			if(!sliderOnClick && finished.contains(mX, mY)) {
				Data.setTank(tank);
				game.enterState(3, new FadeOutTransition(), new FadeInTransition());
			}
			
			else {
				//si on est sur le tank sans avoir cliqué sur un slider
				if(!sliderOnClick && tank.contains(mX, mY)) {
					tX = mX;
					tY = mY;
					
					//on met à jour les sliders avec les valeurs de la shape sélectionnée
					sliders[0].setCenterX(lines[0].getMinX() + tank.getColorOnShape(tX, tY).getRed() * max/255);
					sliders[1].setCenterX(lines[1].getMinX() + tank.getColorOnShape(tX, tY).getGreen() * max/255);
					sliders[2].setCenterX(lines[2].getMinX() + tank.getColorOnShape(tX, tY).getBlue() * max/255);
				}
				
				//on vérifie si on a cliqué sur un slider
				for(i = 0; i < 3; i++) {	
					if(sliders[i].contains(mX, mY)) {
						sliderOnClick = true;
						sliderId = i;
					}
				}
				//si on a cliqué sur un slider, on met à jour la couleur du tank
				if(sliderOnClick && mX >= lines[sliderId].getMinX() && mX <= lines[sliderId].getMaxX()) {
					sliders[sliderId].setCenterX(mX);
					tank.setColorOnShape(tX, tY, new Color(rgb[0], rgb[1], rgb[2]));
				}
				
			}
		}
		
		else
			sliderOnClick = false;
	}

	@Override
	public int getID() {
		return ID;
	}
}
