package ubc.cosc322;

public class Queen extends Tile{
	private int prevRow, prevCol;
	private boolean opponent;
	public Actions actions;
	
	public Queen(int row, int col, boolean opponent) {
		super(row,col);
		this.opponent = opponent;
		this.actions = new Actions();
	}
	public int getPrevRow() {return prevRow;}
	public void setPrevRow() {this.prevRow = this.getRow();}
	public int getPrevCol() {return prevCol;}
	public void setPrevCol() {this.prevCol = this.getCol();}
	public boolean getOpponent() {return opponent;}
}
