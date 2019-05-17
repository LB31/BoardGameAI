import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


import javax.imageio.ImageIO;

import lenz.htw.sawhian.Move;
import lenz.htw.sawhian.net.NetworkClient;

public class Client extends Thread {
	private NetworkClient client;
	/*private*/ public int myPlayerNumber;
	private String myName;
	
	/*private*/ public int[][] playField = new int[7][7];

	public Client(String name) throws IOException {
		this.myName = name;
	}
	
	// for testing
	private void fillPlayFiled() {
		for (int i = 0; i < playField.length; i++) {
			for (int j = 0; j < playField.length; j++) {
				playField[i][j] = -1; //(int)(Math.random() * 50 + 1);
			}
		}
	}

	private void update() {
      
//      client.getTimeLimitInSeconds();
//      client.getExpectedNetworkLatencyInMilliseconds();
//
      while (true) {
          Move move = client.receiveMove();
          if (move == null) { // own move
              move = calculateNextMove();
              client.sendMove(move);
          } else { // Update the playing field after an enemy moved
        	  updatePlayField(move);
          }
      }
	}
	
	// TODO
	private void updatePlayField(Move move) {
        switch (move.player) {
        case 0:  move.y += 1;
        		 break;
        case 1:  move.x += 1;
                 break;
        case 2:  move.y -= 1;
                 break;
        case 3:  move.x -= 1;
        		 break;
    }
		Move pos = worldToLocalPosition(move);
		playField[pos.x][pos.y] = pos.player;

		
		// For testing
		System.out.println(Arrays.deepToString(playField).replace("], ", "]\n").replace("[[", "[").replace("]]", "]"));
		System.out.println("For player " + myPlayerNumber + " with name " + myName);
		
	}
	
	// TODO
	private Move calculateNextMove() {
		Move newMove = localToWorldPosition(new Move(myPlayerNumber, 0, 0));
		
		return newMove;
	}
	

	private Move worldToLocalPosition(Move move) { 
		int newX = move.x;
		int newY = move.y;
        switch (myPlayerNumber) {
            case 1:  newX = 6 - move.y; newY = move.x;
                     break;
            case 2:  newX = 6 - move.x; newY = 6 - move.y;
                     break;
            case 3:  newX = move.y; newY = 6 - move.x;
            		 break;
        }
        
		return new Move(move.player, newX, newY);
	}
	
	private Move localToWorldPosition(Move move) {
		int newX = move.x;
		int newY = move.y;
        switch (myPlayerNumber) {
            case 1:  newX = move.y; newY = 6 - move.x; // check
                     break;
            case 2:  newX = 6 - move.x; newY = 6 - move.y; // check
                     break;
            case 3:  newX = 6 - move.y; newY = move.x; // check
            		 break;
        }
        
		return new Move(move.player, newX, newY);

	}
	
	
	private Move Rotate(Move pos, int degrees) {
		double newX, newY;
		newX = pos.x * Math.cos(Math.toRadians(degrees)) - pos.y * Math.sin(Math.toRadians(degrees));
		newY = pos.x * Math.sin(Math.toRadians(degrees)) + pos.y * Math.cos(Math.toRadians(degrees));
		return new Move(pos.player, (int)newX, (int)newY);
	}

	public void run(){

        BufferedImage logo = null;
		try {
			logo = ImageIO.read(new File("./phoenix.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        client = new NetworkClient(null, myName, logo);
        myPlayerNumber = client.getMyPlayerNumber();
        System.out.println("I am player " + myPlayerNumber);

        fillPlayFiled();
        // Start the thinking
        update();
        
	    }
        
        public static void main(String[] args) throws IOException {
        	for (int i = 0; i < 4; i++) {
          	  Client player = new Client("Player" + i);
          	  player.start();
			}
        	  
        }
}