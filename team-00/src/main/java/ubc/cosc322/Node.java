package ubc.cosc322;

import java.util.ArrayList;

public class Node implements Cloneable{
	private Board board;
	private ArrayList<Node> children;
	private Node parent;
	private int branching = 20;
	private int gameOver;
	
	public Node(Board board) {
		this.board = board;
		this.children = new ArrayList<>();
		this.parent = null;
		gameOver = -1;
	}
	public Board getBoard() {
		return board;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public Node getParent() {
		return this.parent;
	}
	public void setGameOver(int gameOver) {
		this.gameOver = gameOver;
	}
	public int getGameOver() {
		return this.gameOver;
	}
	public void addChild(Node child) {
		if(this.children.size() == this.branching) {return;}
		else {
			this.children.add(child);
		}
	}
	public Node getChild(int child) {
		return this.children.get(child);
	}
	public ArrayList<Node> getChildren() {
		return this.children;
	}
	public int getBranching() {
		return this.branching;
	}
	public Object clone()throws CloneNotSupportedException{  
	      return (Node)super.clone();
	}
}
