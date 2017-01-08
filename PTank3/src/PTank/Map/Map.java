package PTank.Map;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import PTank.Entities.Bullet;
import PTank.Entities.Entity;
import PTank.Entities.Tanks.Ennemy;
import PTank.Entities.Tanks.EnnemyImmobile;
import PTank.Entities.Tanks.Player;
import PTank.Entities.Tanks.Tank;
import PTank.Entities.Tanks.TitleTank;

public class Map 
{
	// --------------------------------------------
	// ------------- Attributes -------------------
	// --------------------------------------------
	// taille fixe pour TOUTES les maps
	private final static int _mapWidth = 80;
	private final static int _mapHeight = 80;
	private final static int _maxEnnemies = 1000;

	// parser attributes
	private char[][] _blocksChar;
	private String _filePath;

	// une map, c'est des blocks, et des entités (tanks, missiles, etc..)
	private Block[][] _blocks;
	
	private Player _player;
	private Ennemy[] _ennemies;
	private int _nbEnnemies;
	
	
	// --------------------------------------------
	// -------------- Methods ---------------------
	// --------------------------------------------
	// constructor
	public Map(String filePath)
	{

		_blocksChar = new char[_mapHeight][_mapWidth];
		_blocks = new Block[_mapHeight][_mapWidth];
		_filePath = filePath;
		_ennemies = new Ennemy[_maxEnnemies];
		_nbEnnemies = 0;
	}
	
	// init
	public void init()
	{
		this.parse();
		this.charsToBlocks();
	}
	
	// update
	public void update(GameContainer gc, int dt) throws SlickException
	{
		// update de chaque Bloc
		for (Block[] rawOfBlocks : _blocks)
		{
			for (Block block : rawOfBlocks)
			{
				if (block != null && block.isVisible())
				{
					block.update(gc, dt);
				}
			}
		}
		// update de player
		_player.update(gc, dt);
		
		// update des ennemies
		for (int i = 0; i < _nbEnnemies; i++)
		{
			if (_ennemies[i] != null)
			{
				_ennemies[i].update(gc, dt);
			}
		}
		// update de chaque Entity
		/*
		for (int i = 0; i < _nbEntities; i++)
		{
			if (_entities[i] != null)
			{
				_entities[i].update(gc, dt);
			}
		}
		*/
	}
	
	//render
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		// rendu de chaque Bloc sous les entity
		for (Block[] rawOfBlocks : _blocks)
		{
			for (Block block : rawOfBlocks)
			{
				if (block != null && !block.isBlockingTanks())
				{
					block.render(gc, g);
				}
			}
		}
		// update de player
		_player.render(gc, g);
		
		// update des ennemies
		for (int i = 0; i < _nbEnnemies; i++)
		{
			if (_ennemies[i] != null)
			{
				_ennemies[i].render(gc, g);
			}
		}
		/*
		// rendu de chaque Entity
		for (int i = 0; i < _nbEntities; i++)
		{
			if (_entities[i] != null)
			{
				_entities[i].render(gc, g);
			}
		}
		*/
		
		
		// rendu de chaque Bloc sur les entity
		for (Block[] rawOfBlocks : _blocks)
		{
			for (Block block : rawOfBlocks)
			{
				if (block != null && block.isBlockingTanks())
				{
					block.render(gc, g);
				}
			}
		}
	}
	
	public int getXPos(Entity ent) { return (int) (ent.getCenterX() / Block.getWidth()); }
	public int getYPos(Entity ent) { return (int) (ent.getCenterY() / Block.getHeight()); }
	
	public boolean isBouncingBlock(int xPos, int yPos) { return _blocks[yPos][xPos].isBouncingBullets(); }
	public boolean isBlockingBlock(int xPos, int yPos) { return _blocks[yPos][yPos].isBlockingTanks(); }
	
	public boolean isBlockingBlock(float x, float y) 
	{ 
		return _blocks[(int) (y / Block.getHeight())][(int) (x / Block.getWidth())].isBlockingTanks(); 
	}
	
	public void setPlayer(Player player) 
	{ 
		// need boucle for pour add l'entity quand on en trouve une nulle dans l'array
		_player = player;
	}
	
	public void addEnnemy(Ennemy ennemy)
	{
		_ennemies[_nbEnnemies] = ennemy;  _nbEnnemies++;
	}
	/*
	public void addEntity(Entity ent) 
	{ 
		// need boucle for pour add l'entity quand on en trouve une nulle dans l'array
		_entities[_nbEntities] = ent;  _nbEntities++;
	}
	*/
	
	// lit le _filePath pour remplir le tableau 2D de char
	public void parse()
	{
		try 
		{
			FileReader fileReader = new FileReader(_filePath);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line = null;
			
			int idLine = 0;
					;
			while ((line = bufferedReader.readLine()) != null)
			{
				char arr[] = line.toCharArray();
				int idChar = 0;
				for (char block: arr)
				{
					_blocksChar[idLine][idChar] = block;
					idChar++;
				}
				idLine++;
			}
			bufferedReader.close();
		}
		catch(FileNotFoundException ex) 
		{
            System.out.println("Unable to open file '" + _filePath + "'");                
        }
        catch(IOException ex) 
		{
            System.out.println("Error reading file '" + _filePath + "'");
        }	
	}
	
	// lit le tableau 2D de char pour remplir le tableeau 2D de Block et des Entity (tanks)
	public void charsToBlocks() 
	{
    	for (int i = 0; i < _mapHeight; i++)
    	{
    		for (int j = 0; j < _mapWidth; j++)
    		{
    			char c = _blocksChar[i][j];
    			
    			int x = (int) (j * Block.getWidth());
    			int y = (int) (i * Block.getHeight());
    			
    			if (c == '#')
    			{
    				_blocks[i][j] = new Wall(x, y);
    			}
    			else if (c == '-')
    			{
    				_blocks[i][j] = new Ground(x, y);
    			}
    			else if (c == 'S')
    			{
    				_blocks[i][j] = new Ground(x, y);
    				this.setPlayer(new Player(this, x , y));
    			}
    			else if (c == 'T')
    			{
    				_blocks[i][j] = new Ground(x, y);
    				this.setPlayer(new TitleTank(this, x , y));
    			}
    			else if (c == '1')
    			{
    				_blocks[i][j] = new Ground(x ,y);
    				this.addEnnemy(new EnnemyImmobile(this, x , y));
    			}
    			else
    			{
    				_blocks[i][j] = new Ground(x ,y);
    			}
 
    		}
    	}
	}
	
	public void print()
	{
		for (int i = 0; i < _mapHeight; i++)
		{
			for (int j = 0; j < _mapWidth; j++)
			{
				System.out.print(_blocksChar[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public void clear()
	{
		for (int i = 0; i < _nbEnnemies; i++)
		{
			Bullet[] ennemyBullets = _ennemies[i].getWeapon().getBullets();
			int nbEnnemyBullets = _ennemies[i].getWeapon().getNbBullets();
			for (int j = 0; j < nbEnnemyBullets; j++)
			{
				ennemyBullets[i].destroy();
			}
			
		}
	}
	
	// getters
	public int getMapWidth() { return _mapWidth; }
	public int getMapHeight() { return _mapHeight; }
	public Block[][] getBlocks() { return _blocks; }
	public Ennemy[] getEnnemies() { return _ennemies; }
	public int getNbEnnemies() { return _nbEnnemies; }
	public void decrementNbEnnemies() { _nbEnnemies--; }
	public Player getPlayer() { return _player; }
	
	/*
	public static void main(String args[])
	{
		Map map = new Map("maps/map1.txt");
		map.parse();
		map.print();
	}
	*/
}
