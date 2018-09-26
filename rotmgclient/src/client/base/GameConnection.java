package client.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import client.GameConsts;

import net.clarenceho.crypto.RC4;

import packets.ByteArrayDataInput;
import packets.ByteArrayDataOutput;
import packets.Packets;
import packets.RWable;
import packets.client.CREATE_Packet;
import packets.client.HELLO_Packet;
import packets.client.LOAD_Packet;
import packets.server.GOTO_Packet;
import packets.server.RECONNECT_Packet;

public class GameConnection {
	private static final int LOOP_SLEEP_TIME = 100;

	public ArrayList<GameListener> listeners = new ArrayList<GameListener>();
	public MoveClass moveClazz;
	private boolean running = false;
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	public ArrayList<RWable> sendQueue = new ArrayList<RWable>();
	private RC4 cipher_toServer;
	private RC4 cipher_fromServer;
	public World world;
	public long start;
	public int tempin=0;
	
	RECONNECT_Packet rcpdetails = new RECONNECT_Packet();
	
	private static final List<Integer> dontPrint = Arrays.asList(new Integer[] {
		
	    
		Packets.MOVE,
		Packets.ALLYSHOOT,
		Packets.MOVE,
		Packets.UPDATE,
		Packets.UPDATEACK,
		Packets.NEW_TICK,
		Packets.PING,
		Packets.PONG,

		Packets.SHOOT,
		Packets.SHOOTACK,
		Packets.SHOOTMULTI,
		Packets.SHOW_EFFECT,
		Packets.NOTIFICATION,
		
		Packets.TEXT,
		Packets.GOTO,
		Packets.GOTOACK,
		
		Packets.ALLYSHOOT,
		Packets.PLAYERSHOOT
		
		
	});

	public GameConnection() {

	}

	public void connect(HELLO_Packet hp, CREATE_Packet p) {
		connect(hp, (RWable) p);
	}

	public void connect(String server, HELLO_Packet hp, CREATE_Packet p) {
		connect(server, hp, (RWable) p);
	}

	public void connect(HELLO_Packet hp, LOAD_Packet p) {
		connect(hp, (RWable) p);
	}

	public void connect(String server, HELLO_Packet hp, LOAD_Packet p) {
		connect(server, hp, (RWable) p);
	}

	private void connect(HELLO_Packet hp, RWable r) {
		connect(GameConsts.NEXUS_USEAST3, hp, r);
	}

	private void connect(String server, HELLO_Packet hp, final RWable r) {
		//System.out.println("testing 01");
		//System.out.println(hp.toString());
		running = true;
		sendQueue.clear();
		sendQueue.add(hp);
		//if (r instanceof LOAD_Packet)
		{

		}
		//else
		{
		sendQueue.add(r); // r is assumed to be LOAD_Packet or CREATE_Packet
		}
		initCiphers();
		//server="107.22.218.252";//us mid west
		//server="50.18.113.133";//uswest main 
		server="ec2-176-34-195-206.eu-west-1.compute.amazonaws.com"; //euwest 2
		//server="107.22.231.55";//us south
		//server="184.72.218.199";//us east
		//server="ec2-174-129-56-145.compute-1.amazonaws.com"; //us mid west 2
		//server="46.137.247.5";///AsiaSouthEast</Name><DNS>
		//server="50.18.176.194";//USSouthWest
		System.out.println("Connecting to " + server + ".");
		if (this.socket != null) {
			try {
				this.socket.close();
			} catch (IOException e) {
			}
		}
		try {
			this.socket = new Socket(server, 2050);
			System.out.println(socket);
			this.in = new DataInputStream(this.socket.getInputStream());
			this.out = new DataOutputStream(this.socket.getOutputStream());
			new Thread() {
				public void run() {
					Socket sock = socket;
					try {
						while (sock.isConnected() && running) {
							start = System.currentTimeMillis();
                            //System.out.println("send quque size "+sendQueue.size());
							while (sendQueue.size() != 0) {
								RWable rw = sendQueue.remove(0);
								writePacket(rw);
							}
							//System.out.println(sock.isConnected());
							
							for (GameListener gl : listeners)
								gl.loop(); // TODO maybe move this to a seperate thread
							
							long end = System.currentTimeMillis();
							int time = (int) (end - start);
							sleep(LOOP_SLEEP_TIME - (time > LOOP_SLEEP_TIME ? 0 : time));
						}
					//} catch (IOException e) {
						// nothing
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						sock.close();
					} catch (IOException e) {
					}
				}
			}.start();
			new Thread() {
				public void run() {
					Socket sock = socket;
					try {
						while (sock.isConnected() && running) {
							//System.out.println("read packet 1");
							RWable rw = readPacket();
							
							//System.out.println(in.available());
							if (in.available()!=tempin)
							{
							  tempin=in.available();
							  //System.out.println(in.available());
							}
							if (in.available()==1)
							{
								System.out.println("end");
								//System.out.println(in.readByte());
							}
							
							for (GameListener gl : listeners)
								gl.packetRecieved(rw);
						}
					//} catch (IOException e) {
						// nothing
					} catch (Exception e) {
						e.printStackTrace();
					}
					try {
						sock.close();
					} catch (IOException e) {
					}
				}
			}.start();
		} catch (IOException e) {
			System.err.println("Failed to connect to " + server + " : " + e.getLocalizedMessage());
		}
	}

	private void initCiphers() {
		cipher_toServer = new RC4(DatatypeConverter.parseHexBinary("72C5583CAFB6818995CBD74B80"));
		cipher_fromServer = new RC4(DatatypeConverter.parseHexBinary("311F80691451C71B09A13A2A6E"));
	}

	private RWable readPacket() throws IOException {
		
		//System.out.println(in.available());
		if (in.available()==1)
		{
			//System.out.println(in.readByte());	
		}
		
		int packetLength = in.readInt();
		int packetId = in.readByte();
		
		//System.out.println(packetId);
		
		if (packetId==60)
		{
		  System.out.println("mapinfo packet");
		  System.out.println(packetLength);
		}
		
		if (packetId==0)
		{
		  System.out.println("failer");
		  System.out.println(packetLength);
		}
		
		byte[] buf = new byte[packetLength - 5]; // read the rest of the packet
		in.readFully(buf); //its waiting for the full packet
		byte[] decr = cipher_toServer.rc4(buf);
		RWable rw = Packets.findPacket(packetId);
		if (rw!=null )
		{
		rw.parseFromInput(new ByteArrayDataInput(decr));
		if (!dontPrint.contains(rw.getId()))
			System.out.println(rw);
		}
		return rw;
	}

	private void writePacket(RWable rw) throws IOException {
		if (!dontPrint.contains(rw.getId()))
			System.out.println(rw);
		ByteArrayDataOutput bado = new ByteArrayDataOutput(100000);
		rw.writeToOutput(bado);
		byte[] test = bado.getArray();
		byte[] encr = cipher_fromServer.rc4(bado.getArray());
		if (rw.getId()==17)
		{
			System.out.println("write packet hello");	
			//System.out.println(encr.length);
			//System.out.println(encr.length+5);
			for(int i=0;i<encr.length;i++)
			{
				//System.out.println((int)(encr[i] &0xFF)); 
			}
		}
		if (rw.getId()==36)
		{
		  
		  System.out.println("write packet create");
		  //System.out.println(encr.length);
		  //System.out.println(encr.length+5);
		  System.out.println((int)(test[0] &0xFF));
		  System.out.println((int)(test[1] &0xFF));
		  System.out.println((int)(test[2] &0xFF));
		  System.out.println((int)(test[3] &0xFF));
		  //System.out.println((int)(encr[0] &0xFF));
		  //System.out.println((int)(encr[1] &0xFF));
		  for(int i=0;i<encr.length;i++)
		  {
				//System.out.println((int)(encr[i] &0xFF)); 
		  }
		}
		out.writeInt(encr.length + 5);
		out.writeByte(rw.getId());
		out.write(encr);
		out.flush();
	}

	public void addListener(GameListener gameListener) {
		this.listeners.add(gameListener);
	}

	public String getCurrentAddress() {
		return socket.getInetAddress().getHostAddress();
	}
	
	public void disconnect() {
		System.out.println("DC CALLED");
		try {
			while (sendQueue.size() != 0)
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
				}
			if (socket != null)
				socket.close();
			running = false;
		} catch (IOException e) {
		}
	}

}
