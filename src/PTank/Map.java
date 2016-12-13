package PTank;

import java.io.*;

public class Map {
	// attributes
	private int _mapWidth = 32;
	private int _mapHeight = 16;


	private Block[][] _blocksBlock;
	private char[][] _blocksChar;
	private String _filePath;
	private Tank _player;
	private Tank[] _ennemies;
	
	
	// methods
	public Map(String filePath, int width, int height)
	{
		_mapWidth = width;
		_mapHeight = height;
		_blocksChar = new char[_mapHeight][_mapWidth];
		_blocksBlock = new Block[_mapHeight][_mapWidth];
		_filePath = filePath;

	}
	
	public void init()
	{
		this.parse();
		this.charsToBlocks();
	}
	
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
	
	public void charsToBlocks() 
	{
    	for (int i = 0; i < _mapHeight; i++)
    	{
    		for (int j = 0; j < _mapWidth; j++)
    		{
    			if (_blocksChar[i][j] == '#')
    			{
    				_blocksBlock[i][j] = new Wall((int) (j * Block.getWidth()),
    						                      (int) (i * Block.getHeight()) );
    			}
    			else if (_blocksChar[i][j] == 'S')
    			{
    				_blocksBlock[i][j] = new Wall(0, 0);
    				_player = new Tank(j * Block.getWidth(), i * Block.getHeight(), 5);
    			}
    			else
    			{
    				_blocksBlock[i][j] = new Wall(0, 0);
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
	
	// getters
	public int getMapWidth() { return _mapWidth; }
	public int getMapHeight() { return _mapHeight; }
	public Block[][] getBlocks() { return _blocksBlock; }
	public Tank getPlayer() { return _player; };
	
	/*
	public static void main(String args[])
	{
		Map map = new Map("maps/map1.txt");
		map.parse();
		map.print();
	}
	*/
}
