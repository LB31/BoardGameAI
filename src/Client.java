import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


import javax.imageio.ImageIO;

import lenz.htw.sawhian.Move;
import lenz.htw.sawhian.net.NetworkClient;

public class Client {
	private NetworkClient client;
	private int myPlayerNumber;
	
	private int[][] playField = new int[7][7];

	public Client(String name) throws IOException {
        // for testing
        fillPlayFiled();
        System.out.println(Arrays.deepToString(playField).replace("], ", "]\n"));
		
        BufferedImage logo = ImageIO.read(new File("./phoenix.png"));
        client = new NetworkClient(null, name, logo);
        myPlayerNumber = client.getMyPlayerNumber();
        System.out.println("Player " + myPlayerNumber);
        
        Move testPos = worldToLocalPosition(new Move(myPlayerNumber, 2,4));
        System.out.println(testPos.x + " " + testPos.y);
        
        Move testPosBack = localToWorldPosition(testPos);
        System.out.println(testPosBack.x + " " + testPosBack.y);
        // Start the thinking
        //update();
	}
	
	// for testing
	private void fillPlayFiled() {
		for (int i = 0; i < playField.length; i++) {
			for (int j = 0; j < playField.length; j++) {
				playField[i][j] = (int)(Math.random() * 50 + 1);
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
		
	}
	
	// TODO
	private Move calculateNextMove() {
		
		return new Move(client.getMyPlayerNumber(),0,0);
	}
	
	// When getting real coordinates to transform them in absolute ones
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
        
		return new Move(client.getMyPlayerNumber(),newX,newY);
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
        
		return new Move(client.getMyPlayerNumber(),newX,newY);
	}


        
        public static void main(String[] args) throws IOException {
        	new Client("1");
        }
}