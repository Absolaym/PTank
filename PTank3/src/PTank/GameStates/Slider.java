package PTank.GameStates;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Rectangle;;

public class Slider {
	private int value;
	private float range, min;
	private Shape line;
	private Circle button;
	private Color color, colorMouseOvered;
	private Boolean sliderOvered, sliderClicked;
	
	public Slider(float centerX, float centerY, float width, float radius, Color color, Color colorOnMouseOver) {
		this.line = new Rectangle(centerX - width/2, centerY, width, 1);
		this.button = new Circle(centerX, centerY, radius);
		this.color = color;
		this.colorMouseOvered = colorOnMouseOver;
		this.sliderOvered = false;
		this.sliderClicked = false;
		this.range = line.getMaxX() - line.getMinX();
		this.min = line.getMinX();
	}
	
	public void update(GameContainer gc) {
		float mX =  gc.getInput().getMouseX();
		float mY =  gc.getInput().getMouseY();
		
		sliderOvered = button.contains(mX, mY);
		
		if(gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			if(button.contains(mX, mY))
				sliderClicked = true;
			
			if(sliderClicked && mX >= line.getMinX() && mX <= line.getMaxX()) {
				button.setCenterX(mX);
				value = (int) (button.getCenterX() - min);
				value *= (int) 255/range;
			}
		}
	
		else
			sliderClicked = false;
	}
	
	public void render(GameContainer gc, Graphics g) {
		g.setColor(color);
		g.draw(line);
		
		g.setColor(sliderClicked || sliderOvered ? colorMouseOvered : color);
		g.fill(button);
	}
	
	public int getValue() {
		return value;
	}
	
	public void setValue(int value) {
		button.setCenterX((int) (line.getMinX() + value * range/255));
		this.value = value;
	}
}