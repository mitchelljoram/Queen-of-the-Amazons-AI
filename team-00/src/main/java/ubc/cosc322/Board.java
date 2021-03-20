package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class Board {
	Tile[][] board;
	Queen[] allies;
	Queen[] enemies;
	
	public Board(boolean white) {
		if(white) {
			board = new Tile[][] 
			{
				{null,null,null,new Queen(0,3,true),null,null,new Queen(0,6,true),null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(3,0,true),null,null,null,null,null,null,null,null,new Queen(3,9,true)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(6,0,false),null,null,null,null,null,null,null,null,new Queen(6,9,false)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,new Queen(9,3,false),null,null,new Queen(9,6,false),null,null,null}
			};
			allies = new Queen[] {(Queen) board[6][0],(Queen) board[6][9],(Queen) board[9][3],(Queen) board[9][6]};
			enemies = new Queen[] {(Queen) board[0][3],(Queen) board[0][6],(Queen) board[3][0],(Queen) board[3][9]};
		}
		else {
			board = new Tile[][] 
			{
				{null,null,null,new Queen(0,3,false),null,null,new Queen(0,6,false),null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(3,0,false),null,null,null,null,null,null,null,null,new Queen(3,9,false)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{new Queen(6,0,true),null,null,null,null,null,null,null,null,new Queen(6,9,true)},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,null,null,null,null,null,null,null},
				{null,null,null,new Queen(9,3,true),null,null,new Queen(9,6,true),null,null,null}
			};
			allies = new Queen[] {(Queen) board[0][3],(Queen) board[0][6],(Queen) board[3][0],(Queen) board[3][9]};
			enemies = new Queen[] {(Queen) board[6][0],(Queen) board[6][9],(Queen) board[9][3],(Queen) board[9][6]};
		}
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos, ArrayList<Integer> arrPos) {
		updateBoard(queenPrevPos, queenNewPos);
		updateBoard(arrPos);
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos) {
		int prevRow = queenPrevPos.get(0)-1, prevCol = queenPrevPos.get(1)-1, 
			newRow = queenNewPos.get(0)-1, newCol = queenNewPos.get(1)-1;
		Queen queen = (Queen) board[prevRow][prevCol];
		queen.setPrevRow();
		queen.setPrevCol();
		queen.setRow(newRow);
		queen.setCol(newCol);
		board[newRow][newCol] = board[prevRow][prevCol];
		board[prevRow][prevCol] = null;
	}
	public void updateBoard(ArrayList<Integer> arrPos) {
		int arrRow = arrPos.get(0)-1, arrCol = arrPos.get(1)-1;
		board[arrRow][arrCol] = new Arrow(arrRow,arrCol);
	}
	public void printBoard() {
		for(int row=0;row<10;++row) {
			for(int col=0;col<10;++col) {
				if(board[row][col] instanceof Queen) {
					System.out.print(" Q ,");
				}
				else if(board[row][col] instanceof Arrow) {
					System.out.print(" A ,");
				}
				else {
					System.out.print(" null ,");
				}
			}
			System.out.println();
		}
	}
}
