package essais;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class InGameState extends BasicGameState {
		
	private static final int ID = 3;
	private static int WINDOW_WIDTH, WINDOW_HEIGHT;
	
	private GameContainer container;
	private Tank player;

	//angle utilisé pour mettre à jour la direction du tank
	private int moveAngle;
	
	//pour savoir si le canon a déja été loadé
	private boolean tankLoaded;
	
	private static boolean moving, up, down, left, right; 

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
    	this.container = container;
    	WINDOW_WIDTH = container.getWidth();
    	WINDOW_HEIGHT = container.getHeight();
    	player = new Tank(WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
    }
    
    @Override
    public void keyPressed(int key, char c) {   	
      	
    	switch(key) {
    		case Input.KEY_UP : up = true; break;
    		case Input.KEY_DOWN : down = true; break;
    		case Input.KEY_LEFT: left = true; break;
    		case Input.KEY_RIGHT : right = true; break;
    	}
    }
    
    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            container.exit();
        }
        
        else {
        	switch(key) {
	    		case Input.KEY_UP : up = false; break;
	    		case Input.KEY_DOWN : down = false; break;
	    		case Input.KEY_LEFT: left = false; break;
	    		case Input.KEY_RIGHT : right = false; break;
        	}
        }
    }
    

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {	
    	if(tankLoaded)
    		player.draw(g);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    	//on load le tank ici et pas dans init parce que sinon on a un tank par défaut, 
    	//les méthodes Init de chaque Sate étant appelées au lancement du programme
    	//(le joueur a pas pu faire son choix)    	
    	if(!tankLoaded) {
    		player = Data.getTank(WINDOW_WIDTH/2, WINDOW_HEIGHT/2);
    		tankLoaded = true;
    	}
    		
    	//on met à jour la direction du tank avec l'angle de la souris
    	player.rotateCannon(Mouse.getX(), WINDOW_HEIGHT - Mouse.getY());
    	
    	moving = false;
    	
    	if(up) {moving = true; moveAngle = 90;}
    	if(down) {moving = true; moveAngle = 270;}
    	if(left) {moving = true; moveAngle = 180;}
    	if(right) {moving = true; moveAngle = 0;}
    	
    	if(down && left) {moving = true; moveAngle = 225;}
    	if(down && right) {moving = true; moveAngle = 315;}
    	if(up && left) {moving = true; moveAngle = 135;}
    	if(up && right) {moving = true; moveAngle = 45;}
    		
    	if(moving)
    		player.update(moveAngle, delta); 	
    }
    
    public static boolean isMoving() {
    	return moving;
    }
    
    @Override
    public int getID() {
    	return ID;
    }
}
