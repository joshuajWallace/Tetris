package app;

import java.awt.Color;

public class Tetromino {
	//true false array showing position occupied
	private boolean[][] shape;
	private Color blockColor = Color.green;
	public Tetromino(boolean[][] outline) {
		shape = outline;
	}	
	/**[y][x] ---> [x][length-y] when rotated to the left,  rotation matrix.
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
				newShape[y][shape.length - 1 - x] = shape[x][y];
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
	public void setColor(Color newColor) {
		blockColor = newColor;
	}
	public Color getColor() {
		return blockColor;				
	}
	
}
