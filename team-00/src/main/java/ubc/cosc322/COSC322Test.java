
package ubc.cosc322;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sfs2x.client.entities.Room;
import ygraph.ai.smartfox.games.BaseGameGUI;
import ygraph.ai.smartfox.games.GameClient;
import ygraph.ai.smartfox.games.GameMessage;
import ygraph.ai.smartfox.games.GamePlayer;
import ygraph.ai.smartfox.games.amazons.AmazonsGameMessage;
import ygraph.ai.smartfox.games.amazons.HumanPlayer;

/**
 * An example illustrating how to implement a GamePlayer
 * @author Yong Gao (yong.gao@ubc.ca)
 * Jan 5, 2021
 *
 */
public class COSC322Test extends GamePlayer{

    private GameClient gameClient = null; 
    private BaseGameGUI gamegui = null;
	
    private String userName = null;
    private String passwd = null;
    private Tree tree;
    private Node node;
    private int toDepth = 4;
 
	
    /**
     * The main method
     * @param args for name and passwd (current, any string would work)
     */
    public static void main(String[] args) {				 
    	COSC322Test player = new COSC322Test(args[0], args[1]);
    	//HumanPlayer player = new HumanPlayer();
    	
    	if(player.getGameGUI() == null) {
    		player.Go();
    	}
    	else {
    		BaseGameGUI.sys_setup();
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                	player.Go();
                }
            });
    	}
    }
	
    /**
     * Any name and passwd 
     * @param userName
     * @param passwd
     */
    public COSC322Test(String userName, String passwd) {
    	this.userName = userName;
    	this.passwd = passwd;
    	
    	//To make a GUI-based player, create an instance of BaseGameGUI
    	//and implement the method getGameGUI() accordingly
    	this.gamegui = new BaseGameGUI(this);
    }
 


    @Override
    public void onLogin() {
    	System.out.println("Congratualations!!! "
    			+ "I am called because the server indicated that the login is successfully");
    	System.out.println("The next step is to find a room and join it: "
    			+ "the gameClient instance created in my constructor knows how!");
    	
    	/*
    	List<Room> rooms = gameClient.getRoomList();
    	for(Room room: rooms) {System.out.print(room.getName() + " ");}
    	System.out.println();
    	int roomIndex = (int) Math.random(rooms.size());
    	gameClient.joinRoom(rooms.get(roomIndex).getName());
    	System.out.print("You have successfully joined room " + roomIndex + ".");
    	*/
    	
    	userName = gameClient.getUserName();
    	if(gamegui != null) {
    		gamegui.setRoomInformation(gameClient.getRoomList());
    	}
    }

    @Override
    public boolean handleGameMessage(String messageType, Map<String, Object> msgDetails) {
    	//This method will be called by the GameClient when it receives a game-related message
    	//from the server.
    	//For a detailed description of the message types and format, 
    	//see the method GamePlayer.handleGameMessage() in the game-client-api document.
    	
    	System.out.println(msgDetails.get(messageType));
    	switch(messageType) {
    	case GameMessage.GAME_STATE_BOARD:
    		gamegui.setGameState((ArrayList<Integer>) msgDetails.get("game-state"));
    		break;
    	case GameMessage.GAME_ACTION_START:
    		if(msgDetails.get(AmazonsGameMessage.PLAYER_BLACK).equals(this.userName)) {
    			System.out.println("White: " + msgDetails.get(AmazonsGameMessage.PLAYER_WHITE));
    			System.out.println("Black: " + this.userName);
    			node = new Node(new Board(true));
    			tree = new Tree(node);
    			handleGameActionStart();
    		}
    		else {
    			System.out.println("White: " + this.userName);
    			System.out.println("Black: " + msgDetails.get(AmazonsGameMessage.PLAYER_BLACK));
    			node = new Node(new Board(false));
    			tree = new Tree(node);
    		}
    		break;
    	case GameMessage.GAME_ACTION_MOVE:
    		try {
    			gamegui.updateGameState(msgDetails);
				handleGameActionMove(msgDetails);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		break;
    	}
    	
    	return true;
    }
    
    public void handleGameActionStart() {
    	Node child = new Node(new Board(node.getBoard()));
    	child.getBoard().randomMove(false);
        tree.addChild(node, child);
        node = child;
        ArrayList<Integer> queenPrevPos = new ArrayList<Integer>();
        queenPrevPos.add(node.getBoard().chosen.getPrevRow()+1);queenPrevPos.add(node.getBoard().chosen.getPrevCol()+1);
        ArrayList<Integer> queenNewPos = new ArrayList<Integer>();
        queenNewPos.add(node.getBoard().chosen.getRow()+1);queenPrevPos.add(node.getBoard().chosen.getCol()+1);
        ArrayList<Integer> arrPos = new ArrayList<Integer>();
        arrPos.add(node.getBoard().chosen.getArrRow()+1);arrPos.add(node.getBoard().chosen.getArrCol()+1);
        gameClient.sendMoveMessage(queenPrevPos,queenNewPos,arrPos);
        gamegui.updateGameState(queenPrevPos, queenNewPos, arrPos);
    }
    
    public void handleGameActionMove(Map<String, Object> msgDetails) {
    	// opponent's move
    	ArrayList<Integer> queenPrevPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.QUEEN_POS_CURR);
        ArrayList<Integer> queenNewPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.Queen_POS_NEXT);
        ArrayList<Integer> arrPos = (ArrayList<Integer>) msgDetails.get(AmazonsGameMessage.ARROW_POS);
        Node child = new Node(new Board(node.getBoard()));
        child.getBoard().updateBoard(queenPrevPos, queenNewPos, arrPos);
        if(node.getChildren().contains(child)) {
        	for(int i=0;i<node.getChildren().size();++i) {
        		if(node.getChildren().get(i).equals(child)) {
        			node = node.getChildren().get(i);
        			break;
        		}
        	}
        }
        else {
        	tree.addChild(node, child);
        	node=child;
        }
        // game over check
        int gameOver = node.getBoard().gameOverCheck(false);
        if(gameOver == 0) return;
        // our move
        if(node.getChildren().get(0) == null) {
        	tree.growTree(node, toDepth);
        }
        child = node.getChildren().get(0);
        for(Node children : node.getChildren()) {
        	if(child.getBoard().evaluateBoard() < children.getBoard().evaluateBoard()) {
        		child = children;
        	}
        }
        node = child;
        queenPrevPos = new ArrayList<Integer>();
        queenPrevPos.add(node.getBoard().chosen.getPrevRow()+1);queenPrevPos.add(node.getBoard().chosen.getPrevCol()+1);
        queenNewPos = new ArrayList<Integer>();
        queenNewPos.add(node.getBoard().chosen.getRow()+1);queenPrevPos.add(node.getBoard().chosen.getCol()+1);
        arrPos = new ArrayList<Integer>();
        arrPos.add(node.getBoard().chosen.getArrRow()+1);arrPos.add(node.getBoard().chosen.getArrCol()+1);
        gameClient.sendMoveMessage(queenPrevPos,queenNewPos,arrPos);
        gamegui.updateGameState(queenPrevPos, queenNewPos, arrPos);
        // game over check
        gameOver = node.getBoard().gameOverCheck(true);
        if(gameOver == 1) return;
    }
    
    
    @Override
    public String userName() {
    	return userName;
    }

	@Override
	public GameClient getGameClient() {
		// TODO Auto-generated method stub
		return this.gameClient;
	}

	@Override
	public BaseGameGUI getGameGUI() {
		// TODO Auto-generated method stub
		return  this.gamegui;
	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub
    	gameClient = new GameClient(userName, passwd, this);			
	}

 
}//end of class
