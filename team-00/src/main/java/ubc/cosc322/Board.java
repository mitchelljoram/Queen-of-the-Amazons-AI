package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;

public class Board {
	Tile[][] board;
	Queen[] allies;
	Queen[] enemies;
	Queen chosen = null;
	
	public Board(boolean black) {
		if(!black) {
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
	public Board(Board oldBoard) {
        this.board = new Tile[10][10];
        this.enemies = new Queen[4];
        this.allies = new Queen[4];
        for(int row = 0; row < 10; row++) {
            for(int col = 0; col < 10; col++) {
                if(oldBoard.board[row][col] instanceof Queen) {
                    Queen oldQueen = (Queen) oldBoard.board[row][col];
                    Queen newQueen = new Queen(row, col, oldQueen.getOpponent());
                    this.board[row][col] = newQueen;
                    if(oldQueen.getOpponent()) {
                        for(int i = 0; i < enemies.length; i++) {
                            if(enemies[i] == null) {
                                enemies[i] = newQueen;
                                break;
                            }
                        }
                    } else {
                        for(int i = 0; i < enemies.length; i++) {
                            if(enemies[i] == null) {
                                enemies[i] = newQueen;
                                break;
                            }
                        }
                    }
                } else if(oldBoard.board[row][col] instanceof Arrow) {
                    this.board[row][col] = new Arrow(row, col);
                }
            }
        }
    }
	public Queen[] getAllies() {
		return this.allies;
	}
	public Queen[] getEnemies() {
		return this.enemies;
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos, ArrayList<Integer> arrPos) {
		updateBoard(queenPrevPos, queenNewPos);
		updateBoard(arrPos);
	}
	public void updateBoard(ArrayList<Integer> queenPrevPos, ArrayList<Integer> queenNewPos) {
		int prevRow = queenPrevPos.get(0)-1, prevCol = queenPrevPos.get(1)-1, 
			newRow = queenNewPos.get(0)-1, newCol = queenNewPos.get(1)-1;
		chosen.setPrevRow();
		chosen.setPrevCol();
		chosen.setRow(newRow);
		chosen.setCol(newCol);
		board[newRow][newCol] = board[prevRow][prevCol];
		board[prevRow][prevCol] = null;
	}
	public void updateBoard(ArrayList<Integer> arrPos) {
		int arrRow = arrPos.get(0)-1, arrCol = arrPos.get(1)-1;
		chosen.setArrRow(arrRow);
		chosen.setArrCol(arrCol);
		board[arrRow][arrCol] = new Arrow(arrRow,arrCol);
	}
	public void randomMove(boolean enemy) {
		ArrayList<Integer> queenPrevPos;
        ArrayList<Integer> queenNewPos;
        ArrayList<Integer> arrPos;
		if(enemy) {
			chosen = this.enemies[(int) (Math.random()*4)];
	        ArrayList<Integer> action = chosen.actions.actions.get((int) (Math.random()*chosen.actions.actions.size()));
	        queenPrevPos = new ArrayList<Integer>();
	        queenPrevPos.add(chosen.getRow()+1); queenPrevPos.add(chosen.getCol()+1);
	        queenNewPos = new ArrayList<Integer>();
	        queenNewPos.add(chosen.getRow()+action.get(0)+1); queenNewPos.add(chosen.getCol()+action.get(1)+1);
	        updateBoard(queenPrevPos, queenNewPos);
	        chosen.actions.getArrowThrows(this, chosen);
	        ArrayList<Integer> arrowThrow = chosen.actions.arrowThrows.get((int) (Math.random()*chosen.actions.arrowThrows.size()));
	        arrPos = new ArrayList<Integer>();
	        arrPos.add(chosen.getRow()+arrowThrow.get(0)+1); arrPos.add(chosen.getCol()+arrowThrow.get(1)+1);
	        updateBoard(arrPos);
		}
		else {
			chosen = this.allies[(int) (Math.random()*4)];
	        ArrayList<Integer> action = chosen.actions.actions.get((int) (Math.random()*chosen.actions.actions.size()));
	        queenPrevPos = new ArrayList<Integer>();
	        queenPrevPos.add(chosen.getRow()+1); queenPrevPos.add(chosen.getCol()+1);
	        queenNewPos = new ArrayList<Integer>();
	        queenNewPos.add(chosen.getRow()+action.get(0)+1); queenNewPos.add(chosen.getCol()+action.get(1)+1);
	        updateBoard(queenPrevPos, queenNewPos);
	        chosen.actions.getArrowThrows(this, chosen);
	        ArrayList<Integer> arrowThrow = chosen.bestArrowThrow(this);
	        arrPos = new ArrayList<Integer>();
	        arrPos.add(chosen.getRow()+arrowThrow.get(0)+1); arrPos.add(chosen.getCol()+arrowThrow.get(1)+1);
	        updateBoard(arrPos);
		}
	}
	public int gameOverCheck(boolean enemy) {
        if(enemy) {
        	for(Queen queen: this.enemies) {
            	queen.actions.getActions(this,queen);
            }
            if(this.enemies[0].actions.actions.size() == 0 && this.enemies[1].actions.actions.size() == 0 
            && this.enemies[2].actions.actions.size() == 0 && this.enemies[3].actions.actions.size() == 0) {
            	System.out.println("Enemies are out of moves. You win!");
            	return 1;
            }
        }
        else {
        	for(Queen queen: this.allies) {
            	queen.actions.getActions(this,queen);
            }
            if(this.allies[0].actions.actions.size() == 0 && this.allies[1].actions.actions.size() == 0 
            && this.allies[2].actions.actions.size() == 0 && this.allies[3].actions.actions.size() == 0) {
            	System.out.println("Allies are out of moves. You lose!");
            	return 0;
            }
        }
        return -1;
	}
	public int evaluateBoard() {
        int score = 0;
        for(int i = 0; i < allies.length; i ++) {
            allies[i].actions.getActions(this, allies[i]);
            score += allies[i].actions.actions.size();
        }
        for(int i = 0; i < enemies.length; i ++) {
            enemies[i].actions.getActions(this, enemies[i]);
            score -= enemies[i].actions.actions.size();
        }
        return score;
    }
}
