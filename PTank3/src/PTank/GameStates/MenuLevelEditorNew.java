package PTank.GameStates;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.gui.TextField;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import PTank.Window;
import PTank.Ressources.Button;
import PTank.Ressources.Ressources;

public class MenuLevelEditorNew extends BasicGameState
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	private ArrayList<Button> buttons;

	// TEXT FIELD
    //Le font du TextField zoneDeSaisie.
    private UnicodeFont font;
     
    //Le TextField, là où le joueur écrira.
    private TextField zoneDeSaisie;

	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
    {

        
    	buttons = new ArrayList<Button>();
		
    	buttons.add(Ressources.getButton("Go"));
    	buttons.add(Ressources.getButton("Main Menu"));

    	
    	// TEXT FIELD
        //Le font voulu
        font = new UnicodeFont(new java.awt.Font("DejaVu Serif", java.awt.Font.PLAIN, 20));
         
    	//Si je ne les mets pas, le texte ne s'affiche pas.
        font.addAsciiGlyphs();
        font.addGlyphs(400,600);
        font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));//Ca sert a quoi ?
        font.loadGlyphs();
         
        //L'initialisation du TextField
        zoneDeSaisie = new TextField(gc, font, 110, 30, 480, 40);
        zoneDeSaisie.setTextColor(Color.white);
        zoneDeSaisie.setText("mapName");
    }

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int dt) throws SlickException
	{
		// update buttons
		for (int i = 0; i < buttons.size(); i++)
		{
			buttons.get(i).update(gc, sbg, dt);
		}

		// si New Map clické, go LEVEL_EDITOR
		if (Ressources.getButton("Go").isMouseClicked())
		{
			sbg.enterState(GameStates.LEVEL_EDITOR, new FadeOutTransition(), new FadeInTransition());
			LevelEditor.isNewMap = true;
		}
		
		// si Main Manu clické, go MENU
		if (Ressources.getButton("Main Menu").isMouseClicked())
		{
			sbg.enterState(GameStates.MENU, new FadeOutTransition(), new FadeInTransition());
		}

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		// render title
    	g.drawString("PTank", (Window.HEIGHT / 2) - 40,  100);
    		
		// render buttons
		for (int i = 0; i < buttons.size(); i++)
		{
			//buttons.get(i).render(gc, sbg, g);
		}
		
    	// TEXTFIELD
		 zoneDeSaisie.render(gc, g);
		//mapNameField.render(gc, g);
	}

	@Override
	public int getID() { return GameStates.MENU_LEVEL_EDITOR_NEW; }
}
