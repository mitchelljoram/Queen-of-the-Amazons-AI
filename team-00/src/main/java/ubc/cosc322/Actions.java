package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;

public class Actions {
	List<byte[]> actions;
	List<byte[]> arrowThrows;
	
	public Actions() {
		actions = new ArrayList<>();
		arrowThrows = new ArrayList<>();
		byte[] action;
		for(int i=1; i<=9; ++i) {
			action = new byte[] {(byte) -i,0}; // left
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {(byte) i,0}; // right
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {0,(byte) -i}; // up
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {0,(byte) i}; // down
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {(byte) -i,(byte) -i}; // up-left
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {(byte) i,(byte) -i}; // up-right
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {(byte) i,(byte) -i}; // down-left
			actions.add(action);
			arrowThrows.add(action);
			action = new byte[] {(byte) i,(byte) i}; // down-right
			actions.add(action);
			arrowThrows.add(action);
		}
	}
	
	public List<byte[]> getActions(){return actions;}
	public List<byte[]> getArrowThrows(){return arrowThrows;}
}
