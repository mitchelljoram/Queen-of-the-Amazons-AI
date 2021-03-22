package ubc.cosc322;

public class Arrow extends Tile{
	public Arrow(int row, int col) {
		super(row,col);
	}
	public int[] move() {return new int[] {this.getRow(), this.getCol()};}
}
