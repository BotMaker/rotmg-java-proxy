import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class PolicyServer extends Thread {
	public static final String POLICY_XML = "<?xml version=\"1.0\"?>" + "<cross-domain-policy>" + "<allow-access-from domain=\"*\" to-ports=\"*\" />" + "</cross-domain-policy>\n\u0000";
	private static final int POLICY_PORT = 843;
	protected ServerSocket serverSocket;

	public void run() {
		try {
			this.serverSocket = new ServerSocket(POLICY_PORT);

			while (serverSocket.isBound() && !serverSocket.isClosed()) {
				final Socket socket = this.serverSocket.accept();
				new Thread() {
					public void run() {
						try {
							OutputStream out = socket.getOutputStream();
							out.write(POLICY_XML.getBytes());
							out.flush();
							socket.close();
							return;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}