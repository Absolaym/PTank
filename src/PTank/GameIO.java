package PTank;

import org.newdawn.slick.Input;

public class GameIO {
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	// Inputs / Outputs
	boolean _up;
	boolean _down;
	boolean _left;
	boolean _right;
	
	float _mouseX;
	float _mouseY;
	private boolean _mouseLMB;
	private boolean _mouseRMB;
	
	private float _tankX;
	private float _tankY;
	private float _tankAngle;
	private float _tankCanonAngle;
	private boolean _moving;

	
	// --------------------------------------------
	// ------------- Methods ----------------------
	// --------------------------------------------
	// constructor
	public GameIO()
	{}

	// getters
	public boolean up() { return _up; }
	public boolean down() {	return _down; }
	public boolean left() {	return _left; }
	public boolean right() { return _right;	}
	
	public boolean mouseLMB() {	return _mouseLMB; }
	public void setMouseLMB(boolean lmb) { _mouseLMB = lmb; }
	public boolean mouseRMB() {	return _mouseRMB; }
	public float mouseX() { return _mouseX; }
	public float mouseY() { return _mouseY; }
	
	public float getTankX() { return _tankX; }
	public float getTankY() { return _tankY; }
	public float getTankAngle() { return _tankAngle; }
	public float getTankCanonAngle() {	return _tankCanonAngle; }
	public boolean isMoving() { return _moving; }
	
	// setters
	public void pressKey(int key)
	{
        switch (key) 
        {
        	case Input.KEY_UP:    _up = true; break;
            case Input.KEY_LEFT:  _left = true; break;
            case Input.KEY_DOWN:  _down = true; break;
            case Input.KEY_RIGHT: _right = true; break;
        }
	}

	public void releaseKey(int key)
	{
	    switch (key) 
	    {
    	case Input.KEY_UP:    _up = false; break;
        case Input.KEY_LEFT:  _left = false; break;
        case Input.KEY_DOWN:  _down = false; break;
        case Input.KEY_RIGHT: _right = false; break;
	    }
	}
	
    public void pressMouse(int button, int x, int y)
    {
    	switch (button)
    	{
    		case Input.MOUSE_LEFT_BUTTON: _mouseLMB = true; break;
    		case Input.MOUSE_RIGHT_BUTTON: _mouseRMB = true; break;
    	}
    }

    // update
	public void updateGameIO(float tankX, float tankY, float mouseX, float mouseY)
	{
	    {
	    	// update tank position
	    	_tankX = tankX;
	    	_tankY = tankY;
	    	
	    	// update mouse position
	    	_mouseX = mouseX;
	    	_mouseY = mouseY;
	    	
	    	// update tank angle
	    	_moving = false;
	    	if (_up) { _tankAngle = 90; _moving = true; }
	    	if (_left) { _tankAngle = 180; _moving = true; }
	    	if (_down) { _tankAngle = 270; _moving = true; }
	    	if (_right) { _tankAngle = 0; _moving = true; }
	    	
	    	if (_up && !_down) { _moving = true; _tankAngle = 90; }
	    	if (_left && !_right) { _moving = true; _tankAngle = 180; }
	    	if (_down && !_up) { _moving = true; _tankAngle = 270; }
	    	if (_right && !_left) { _moving = true; _tankAngle = 0; }
	    	
	    	if (_up && _left) { _moving = true; _tankAngle = 135; }
	    	if (_left && _down) { _moving = true; _tankAngle = 225; }
	    	if (_down && _right) { _moving = true; _tankAngle = 315; }
	    	if (_right && _up) { _moving = true; _tankAngle = 45; }
	    	
	    	// update tank canon angle
	    	float a = _mouseX - _tankX;
	    	float b = _mouseY - _tankY;
	    	float c = (float) Math.sqrt(a * a + b * b);

	    	_tankCanonAngle = (float) Math.toDegrees(Math.atan(b / a));
	    	if (a < 0)
	    	{
	    		_tankCanonAngle = 180 + _tankCanonAngle;
	    	}
	    	
	    	return;
	    }
	}	
}
