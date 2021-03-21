package ubc.cosc322;

public class Tree {
	private Node root;
	private int depth;
	
	public Tree(Node root) {
		this.root = root;
		this.depth = 0;
	}
	public void growTree(Node curr, int toDepth) {
		if(toDepth == 0) return;
		Node child;
		for(int i=0;i<curr.getBranching();++i) {
			if(toDepth % 2 == 1) {
				child = new Node(new Board(curr.getBoard()));
				child.getBoard().randomMove(true);
			}
			else {
				child = new Node(new Board(curr.getBoard()));
				child.getBoard().randomMove(false);
			}
			if(curr.getChildren().contains(child)) {
				continue;
			}
			else {
				addChild(curr,child);
				growTree(child,toDepth-1);
			}
		}
		this.depth = this.depth + 1;
	}
	public void addChild(Node parent, Node child) {
		parent.addChild(child);
		child.setParent(parent);
	}
}
