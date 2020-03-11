package app;

import java.awt.Color;
import java.util.Random;

public class TetrominoBag {
	private final boolean[][] J = {{true,false,false},{true,true, true},{false, false, false}};
	private final boolean[][] L = {{false,false,true},{true,true, true},{false, false, false}};
	private final boolean[][] Z = {{true,true,false},{false,true, true},{false, false, false}};
	private final  boolean[][] S = {{false,true,true},{true,true, false},{false, false, false}};
	private final boolean[][] O = {{true,true},{true,true}};
	private final boolean[][] I = {{false,false,false,false}, {true,true,true,true},{false,false,false,false}, {false,false,false,false}};
	private final boolean[][] T = {{false,true,false},{true,true, true},{false, false, false}};
	private int current;
	
	private Tetromino jTetromino = new Tetromino(J);
	private Tetromino lTetromino = new Tetromino(L);
	private Tetromino zTetromino = new Tetromino(Z);
	private Tetromino sTetromino = new Tetromino(S);
	private Tetromino oTetromino = new Tetromino(O);
	private Tetromino iTetromino = new Tetromino(I);
	private Tetromino tTetromino = new Tetromino(T);
	private Tetromino[] bag = {jTetromino, lTetromino, zTetromino, sTetromino, oTetromino, iTetromino, tTetromino};
	
	public  TetrominoBag() {
		iTetromino.setColor(Color.cyan);
		oTetromino.setColor(Color.yellow);
		tTetromino.setColor(Color.pink);
		sTetromino.setColor(Color.green);
		zTetromino.setColor(Color.red);
		jTetromino.setColor(Color.blue);
		lTetromino.setColor(Color.orange);
		randomise();
	}
	public Tetromino getCurrent() {
		if(current == bag.length) {
			current = 0;
			randomise();
		}
		return bag[current];
	}
	public Tetromino showNext() {
		int next = current + 1;
		if(next == bag.length) {
			current = 0;
			next = 1;
			randomise();
		}
		return bag[next];
	}
	public Tetromino getNext() {
		current++;
		return getCurrent();

	}
	public void randomise() {
		Random random = new Random();
		for (int i = 0; i < bag.length; i++) {
			int randomSwapIndex = random.nextInt(bag.length);
			Tetromino temp = bag[randomSwapIndex];
			bag[randomSwapIndex] = bag[i];
			bag[i] = temp;
		}		
	}
	

}
