package PTank.GameStates;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.gui.MouseOverArea;

public class ImageButton {

	private MouseOverArea button;
	private Image rButton;
	private boolean imgOvered, clicked;
	private float width, height;
	private int x, y;
	
	public ImageButton(GameContainer gc, String normalColorsPath, String reversedColorsPath, int x, int y, int width, int height) throws SlickException {
		this.imgOvered = false;
		this.clicked = false;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		button = new MouseOverArea(gc, new Image(normalColorsPath), x- width/2, y - height/2, width, height);
		rButton = new Image(reversedColorsPath);
	}
	
	public void update(GameContainer gc) {
		imgOvered = button.isMouseOver();
		clicked = imgOvered && gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);
	}
	
	public void render(GameContainer gc, Graphics g) {
		button.render(gc, g);
		
		if(imgOvered) 
			g.drawImage(rButton, x-width/2, y-height/2);			
	}
	
	public boolean isClickedOn() {
		return clicked;
	}
}
