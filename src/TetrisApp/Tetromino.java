package TetrisApp;

import java.awt.Color;

public class Tetromino {
	//true false array showing position occupied
	private boolean[][] shape;
	public Tetromino(boolean[][] outline) {
		shape = outline;
	}	
	/*[y][x] ---> [x][length-y] when rotated to the left,  rotation matrix.
	 * [y][x] ---> [length-x][y] for right.
	 * right rotation =
	 * {cos(θ), -sin(θ)
	 * 	sin(θ), cos(θ)}
	 * positive(right)=
	 * {0, -1 * {x    {-y	
	 *  1, 0} 	y}  =   x}
	 *  negative rotation(left)=
	 * {0, 1 * {x    {-x	
	 *  -1, 0} 	y}  =   y}
	 */
	public void rotate() {
		boolean[][] newShape = new boolean[shape.length][shape.length];
		for(int x =0; x<shape.length;x++) {
			for(int y =0; y<shape.length;y++) {
				newShape[x][shape.length - (y+1)] = shape[y][x];
			}
		}
		shape = newShape;
	}
	public boolean[][] getShape() {
		return shape;
		}
	public int length() {
		return shape.length;
	}
	public boolean value(int x, int y) {
		return shape[x][y];
	}
	
}
