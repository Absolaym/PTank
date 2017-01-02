package essais;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class StateGame extends StateBasedGame {

	public StateGame() {
		super("Ptank mamen");
	}

	public static void main(String[] args) throws SlickException {
		try {
			new AppGameContainer(new StateGame(), 800, 600, false).start();
		}

		catch (SlickException e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {
		addState(new MenuState());
		addState(new TankSelectState());
		addState(new TankPersState());
		addState(new InGameState());
	}
	
	
}