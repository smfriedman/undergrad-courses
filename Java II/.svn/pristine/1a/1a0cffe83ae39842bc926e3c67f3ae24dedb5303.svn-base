package battleship.game;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import battleship.client.util.Wrappers;

/**
 * 
 * @author steven.friedman Steven Friedman
 * @author rgsmith Ryan Smith
 * Server class that interacts with the provided clients.
 * Handles messages
 *
 */
public class Server {

	private int port;
	private String name;
	private String password;
	private int numPlayers;
	private byte turn;
	private byte[] shipLength;
	private short rows;
	private short cols;
	private Board[] boards;
	private DataInputStream[] ins = new DataInputStream[2];
	private DataOutputStream[] outs = new DataOutputStream[2];

	public Server(int port){
		this.port = port;
		this.name = "Stevo";
		this.password = "xyzzy";
		this.numPlayers = 0;
		this.turn = 1;
		this.shipLength = new byte[] {3, 3, 4, 4, 5};
		this.rows = 15;
		this.cols = 15;
		this.boards = new Board[] {new Board(rows, cols), new Board(rows, cols)};
	}

	public void run() throws IOException{
		ServerSocket ss = new ServerSocket(port);
		while(true){
			Socket s = ss.accept();
			
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			DataInputStream dis = new DataInputStream(s.getInputStream());

			ins[numPlayers] = dis;
			outs[numPlayers] = dos;	
			
			String clientName = ins[numPlayers].readUTF();
			String clientPassword = ins[numPlayers].readUTF();
			byte clientFunction = ins[numPlayers].readByte();

			Thread t1 = new Thread(){
				public void run(){
					try {
					
						outs[numPlayers].writeUTF(name);
						outs[numPlayers].writeByte(numPlayers + 1);
						
						outs[numPlayers].writeUTF("config");
						outs[numPlayers].writeShort(rows); //num rows
						outs[numPlayers].writeShort(cols); //num cols
						outs[numPlayers].writeByte(shipLength.length); //num ships
						//for each ship s, length is:
						for(int i = 0; i < shipLength.length; i++){
							outs[numPlayers].writeByte(shipLength[i]);
						}

						boolean allOK = true;
						for(int i = 0; i < shipLength.length; i++){
							Byte b = dis.readByte();
							if(b == 0){
								Ship ship1 = Ship.genHorizontalShip(new Coordinate(ins[numPlayers].readShort(), ins[numPlayers].readShort()), shipLength[i]);
								if(!boards[numPlayers].placeShip(ship1)) allOK = false;
							} else if(b == 1){
								Ship ship1 = Ship.genVerticalShip(new Coordinate(ins[numPlayers].readShort(), ins[numPlayers].readShort()), shipLength[i]);
								if(!boards[numPlayers].placeShip(ship1)) allOK = false;
							} else{
								allOK = false;
							}
						}
						if(allOK) outs[numPlayers].writeUTF("ok");
						else outs[numPlayers].writeUTF("bye");
						
						numPlayers++;
					} catch (IOException e) {
						// FIXME Auto-generated catch block
						e.printStackTrace();
					}
				}
			};

			Thread t2 = new Thread(){
				public void run(){
					while(true){
						try {
							//open a  players turn
							for(int i = 0; i < boards.length; i++){
								outs[i].writeUTF("PlayerTurn");
								outs[i].writeByte(turn);
							}

							//receive players turn
							String in = ins[turn - 1].readUTF();
							while(in.equals("message")){
								String content = ins[turn - 1].readUTF();
								for(int i = 0; i < boards.length; i++){
									outs[i].writeUTF("broadcast");
									outs[i].writeUTF("Player " + turn + ": ");
									outs[i].writeUTF(content);
								}
								in = ins[turn-1].readUTF();
							} 
							if(in.equals("move")){



								short fireRow = ins[turn - 1].readShort();
								short fireCol = ins[turn - 1].readShort();


								//create hit result object
								int other = 3 - turn - 1;
								HitResult hr = new HitResult(boards[other].getPCS());
								boards[other].processHit(new Coordinate(fireRow, fireCol));

								//log a players fire/send out
								for(int i = 0; i < boards.length; i++){
									outs[i].writeUTF("PlayerFires");
									outs[i].writeByte(turn);
									outs[i].writeShort(fireRow);
									outs[i].writeShort(fireCol);
									outs[i].writeUTF(hr.isHit());
									outs[i].writeUTF(hr.isSunk());
									outs[i].writeUTF(hr.isWin());
								}


								//update who's going
								turn = (byte) (3 - turn);
							}
							else throw new Error("move expcted but got " + in);
						} catch (IOException e) {
							// FIXME Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			};

			if(clientFunction == 1 && numPlayers < 2){
				t1.run();
			}
			
			if(numPlayers == 2){
				t2.start();
			}		
		}
	}
	
	public static void main(String[] args) throws IOException{
		Server service = new Server(3001);
		service.run();
	}
}
