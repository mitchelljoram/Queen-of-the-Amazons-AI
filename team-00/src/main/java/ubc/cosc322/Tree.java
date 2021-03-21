package ubc.cosc322;

public class Tree {
	private Node root;
	private int depth;
	
	public Tree(Node root) {
		this.root = root;
		try {
			for(int i=0;i<root.getBranching();++i) {
				Node child = (Node) root.clone();
				child.getBoard().randomMove(false);
				if(root.getChildren().contains(child)) {
					--i;
					continue;
				}
				else {
					addChild(root,child);
				}
			}
		}catch (CloneNotSupportedException e) {
			   e.printStackTrace();
		}
		this.depth = 1;
	}
	public void addChild(Node parent, Node child) {
		parent.addChild(child);
		child.setParent(parent);
	}
}
