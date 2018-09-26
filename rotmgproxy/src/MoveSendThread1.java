import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

// import extra.MoveSendThreadPacket;

public class MoveSendThread1 extends Thread {
	// public ArrayList<MoveSendThreadPacket> sendQueue = new ArrayList<MoveSendThreadPacket>();

	// public void run() {
		// ServerSocket ss = null;
		// try {
			// ss = new ServerSocket(5648);
		// } catch (IOException ioe) {
			// ioe.printStackTrace();
		// }
		// System.out.println("Start");
		// while (true) {
			// try {
				// Socket s = ss.accept();
				// System.out.println("Connect");
				// DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				// sendQueue.clear(); // might contain much old data so clear it
				// while (true) {
					// while (sendQueue.size() != 0) {
						// MoveSendThreadPacket mstp = sendQueue.remove(0);
						// mstp.writeToOutput(dos);
						// dos.flush();
					// }
					// Thread.sleep(20);
				// }
			// } catch (Exception e) {
				// e.printStackTrace();
			// }
		// }
	// }
}
