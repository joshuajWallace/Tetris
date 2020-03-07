package TetrisApp;

import java.util.Random;

public class TetrominoBag {
	private final static boolean[][] J = {{true,false,false},{true,true, true},{false, false, false}};
	private final static boolean[][] L = {{false,false,true},{true,true, true},{false, false, false}};
	private final static boolean[][] Z = {{true,true,false},{false,true, true},{false, false, false}};
	private final static boolean[][] S = {{false,true,true},{true,true, false},{false, false, false}};
	private final static boolean[][] O = {{true,true},{true,true}};
	private final static boolean[][] I = {{false,false,false,false}, {true,true,true,true},{false,false,false,false}, {false,false,false,false}};
	private final static boolean[][] T = {{false,true,false},{true,true, true},{false, false, false}};
	private int current;
	
	private Tetromino JTetromino = new Tetromino(J);
	private Tetromino LTetromino = new Tetromino(L);
	private Tetromino ZTetromino = new Tetromino(Z);
	private Tetromino STetromino = new Tetromino(S);
	private Tetromino OTetromino = new Tetromino(O);
	private Tetromino ITetromino = new Tetromino(I);
	private Tetromino TTetromino = new Tetromino(T);
	private Tetromino[] bag = {JTetromino, LTetromino, ZTetromino, STetromino, OTetromino, ITetromino, TTetromino};
	
	public  TetrominoBag() {
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
