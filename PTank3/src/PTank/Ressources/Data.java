package PTank.Ressources;

import PTank.Entities.Skin;
import PTank.Entities.Tanks.Player;
import PTank.Entities.Tanks.TankSkin;
import PTank.Map.Map;

public class Data {
	// classe qui sert à la communication entre States, faute de mieux
	
	private static Skin skin;
	
	public static Skin getSkin(int x, int y, int width, int height) {
		
		if(skin == null) {
			skin = new TankSkin(x, y, width, height);
		}
		else {
			skin.setCenter(x, y);
		}
		/*player.setCenterX(x);
		player.setCenterY(y);
		player.setMap(map);*/
		
		return skin;
	}
	
	public static void setSkin(Skin s) {
		skin = s;
	}
	
	private Data() {
	}
}
