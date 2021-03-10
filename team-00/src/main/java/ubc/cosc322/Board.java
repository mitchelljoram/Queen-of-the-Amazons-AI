package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class Board {
	Tile[][] board;
	Queen[] allies;
	
	public Board(boolean white) {
		if(white) {
			board = new Tile[][] 
			{
				{null,null,null,new Queen(0,3,true),null,null,new Queen(0,6,true),null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(2,0,true),null,null,null,null,null,null,null,null,new Queen(2,9,true)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(7,0,false),null,null,null,null,null,null,null,null,new Queen(7,9,false)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,new Queen(9,3,false),null,null,new Queen(9,6,false),null,null,null}
			};
			allies = new Queen[] {(Queen) board[7][0],(Queen) board[7][9],(Queen) board[9][3],(Queen) board[9][6]};
		}
		else {
			board = new Tile[][] 
			{
				{null,null,null,new Queen(0,3,false),null,null,new Queen(0,6,false),null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(2,0,false),null,null,null,null,null,null,null,null,new Queen(2,9,false)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(7,0,true),null,null,null,null,null,null,null,null,new Queen(7,9,true)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,new Queen(9,3,true),null,null,new Queen(9,6,true),null,null,null}
			};
			allies = new Queen[] {(Queen) board[0][3],(Queen) board[0][6],(Queen) board[2][0],(Queen) board[2][9]};
		}
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos, ArrayList<Integer> arrPos) {
		updateBoard(queenPrevPos, queenNewPos);
		updateBoard(arrPos);
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos) {
		int prevRow = queenPrevPos.get(0), prevCol = queenPrevPos.get(1), 
			newRow = queenNewPos.get(0), newCol = queenNewPos.get(1);
		Queen queen = (Queen) board[prevRow][prevCol];
		queen.setPrevRow();
		queen.setPrevCol();
		queen.setRow(newRow);
		queen.setCol(newCol);
		board[newRow][newCol] = board[prevRow][prevCol];
		board[prevRow][prevCol] = null;
	}
	public void updateBoard(ArrayList<Integer> arrPos) {
		int arrRow = arrPos.get(0), arrCol = arrPos.get(1);
		board[arrRow][arrCol] = new Arrow(arrRow,arrCol);
	}
}
