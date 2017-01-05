package PTank.Ressources;

import java.util.HashMap;
import java.util.Map;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

import PTank.Window;
import PTank.Ressources.Button;



public class Ressources
{

	private static Map<String, Button> buttons;
	private static Map<String, float[]> shapes;
	private static Map<String, Image> images;
	
	public static void loadAll()
	{
		Ressources.loadButtons();
		Ressources.loadImages();
		Ressources.loadShapes();
		// Ressources.loadMusics();
	}
	
	public static void loadButtons()
	{
		buttons = new HashMap<String, Button>();
		
		// Menu buttons
		buttons.put("Play", new Button("Play", (Window.WIDTH / 2) - 150, 300f, 300f, 50f));
		buttons.put("Level Editor", new Button("Level Editor", (Window.WIDTH / 2) - 150, 400f, 300f, 50f));
		buttons.put("Exit", new Button("Exit", (Window.WIDTH / 2) - 150, 500f, 300f, 50f));	
		
		// Level Editor buttons
		buttons.put("New Map", new Button("New Level", (Window.WIDTH / 2) - 150, 300f, 300f, 50f));	
		buttons.put("Load Map", new Button("Load Level", (Window.WIDTH / 2) - 150, 400f, 300f, 50f));	
		buttons.put("Main Menu", new Button("Return to main menu", (Window.WIDTH / 2) - 150, 600f, 300f, 50f));
	}
	
	public static void loadImages()
	{
		images = new HashMap<String, Image>();
		
		
	}
	
	public static void loadShapes()
	{
		shapes = new HashMap<String, float[]>();
		
		// Player shape
		// tableau de points du player
		float player[] = {0, 0,
						  0, 32,
						  32, 32,
						  32, 0} ;
		
		// tableau de points du weapon
		float weapon[] = {0, 0,
						  16, 0,
						  16, 8,
						  0, 8} ;
		
		// tableau de points du bullet
		float bullet[] = {0, 0,
						  12, 0,
						  16, 4,
						  12, 8,
						  0, 8};
		
		// on l'ajoute dans le HASHMAP
		shapes.put("Player",  player);
		shapes.put("Weapon",  weapon);
		shapes.put("Bullet", bullet);

	}
	
	public static Button getButton(String key)
	{
		if (buttons.containsKey(key))
		{
			return buttons.get(key);
		}
		else
		{
			System.out.println("Erreur: le Button " + key + " n'existe pas dans Ressources.");
			return null;
		}
	}
	
	public static float[] getShape(String key)
	{
		if (key == null)
		{
			System.out.println("Erreur: null key");
			return null;
		}
		if (shapes.containsKey(key))
		{
			return shapes.get(key);
		}
		else
		{
			System.out.println("Erreur: la Shape " + key + " n'existe pas dans Ressources.");
			return null;
		}
	}
}
