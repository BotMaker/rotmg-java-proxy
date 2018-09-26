import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class prox {
	public static byte[] CLIENTKEY = fromHexString("311F80691451C71B09A13A2A6E");
	public static byte[] SERVERKEY = fromHexString("72c5583cafb6818995cdd74b80");
//	public static MoveSendThread mst;
	// public static final String CLIENT_KEY = "311f80691451c71b09a13a2a6e";
	// public static final String SERVER_KEY = "72c5583cafb6818995cbd74b80";
	
	private ServerSocket ss;

	public static void main(String[] a) throws IOException {
		int i;

		for(i=0;i<CLIENTKEY.length;i+=1)
		{
		System.out.println(SERVERKEY[i]&0xff);
		}
		PolicyServer p = new PolicyServer();
		p.setDaemon(true);
		p.start();
		
//		mst = new MoveSendThread();
//		mst.setDaemon(true);
//		mst.start();
		new prox();
	}

	private static byte[] fromHexString(final String encoded) {
		if ((encoded.length() % 2) != 0)
			throw new IllegalArgumentException("Input string must contain an even number of characters");

		final byte result[] = new byte[encoded.length() / 2];
		final char enc[] = encoded.toCharArray();
		for (int i = 0; i < enc.length; i += 2) {
			StringBuilder curr = new StringBuilder(2);
			curr.append(enc[i]).append(enc[i + 1]);
			int ix = Integer.parseInt(curr.toString(), 16);
			result[i / 2] = (byte) ix;
		}
		return result;
	}

	public prox() throws IOException {
		this.ss = new ServerSocket(2050);
		while (!this.ss.isClosed())
			try {
				System.out.println("Waiting for client connection...");
				final Socket s = this.ss.accept();
				new Thread() {
					public void run() {
						System.out.println("Client connected: " + s.getInetAddress().getHostAddress());
						try {
							new Connection(s);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			} catch (IOException ioe) {
			}
	}
}
