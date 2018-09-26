package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.xml.sax.SAXException;

public class ROTMGServer {
	public Map map;
	
	public static void main(String[] a) throws IOException {
		new ROTMGServer();
	}
	
	public ROTMGServer() throws IOException {
		PolicyServer p = new PolicyServer();
		p.setDaemon(true);
		p.start();
		
		try {
			this.map = new Map("Nexus");
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.map.start();
		System.out.println("Loaded map.");
		
		System.out.println("ROTMGServer started.");
		
		ServerSocket ss = new ServerSocket(2050);
		
		while(ss.isBound()) {
			final Socket s = ss.accept();
			
			System.out.println( s.getRemoteSocketAddress().toString() );
			//if ((s.getRemoteSocketAddress().toString().contains("188.30.79.0")==true) || (s.getRemoteSocketAddress().toString().contains("127.0.0.1")==true) )
			{
				ClientHandler ch = new ClientHandler(this, s);
				ch.start();
			}
			//else
			//{
			//s.close();
		//	}
			
		}
	}
}
