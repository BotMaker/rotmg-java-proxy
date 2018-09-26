package viewer;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.DatatypeConverter;

import net.clarenceho.crypto.RC4;
import packets.ByteArrayDataInput;
import packets.Packets;
import packets.RWable;
import packets.server.TRADESTART_Packet;

public class Viewer {
	private DataInputStream in;
	private RC4 rcDECR;
	public boolean server2client = true;
	private ViewerFrame vf;
	public boolean real = false;

	public static void main(String[] args) {
		new Viewer();
	}

	public Viewer() {
		vf = new ViewerFrame(this);
		vf.setVisible(true);
		load();
		setNLAF();
	}
	
	public void load() {
		ArrayList<String[]> al = new ArrayList<String[]>();
		try {
			in = new DataInputStream(new FileInputStream((real ? "real " : "spoof ") + (server2client ? "s2c" : "c2s") + " raw.txt"));
			while (true) {
				int packetLength;
				try {
					packetLength = in.readInt();
				} catch (EOFException eofe) {
					// reached end of file, nothing unusual
					break;
				}
				int packetId = in.readByte();
				if (packetId == 17) {
					String xx = "311F80691451C71B09A13A2A6E";
					byte[] tkey = fromHexString(xx);
					rcDECR = new RC4(tkey);
				}
				if (packetId == 37) {
					String xx = "72C5583CAFB6818995CBD74B80";
					byte[] tkey = fromHexString(xx);
					rcDECR = new RC4(tkey);
				}
				byte[] buf = new byte[packetLength - 5]; // read the rest of the packet
				in.readFully(buf);
				byte[] decr = rcDECR.rc4(buf);
				ByteArrayDataInput badi = new ByteArrayDataInput(decr);

				RWable rw = Packets.findPacket(packetId);
				if (rw != null) {
					try {
						rw.parseFromInput(badi);
						updateWorldStatus(packetId, rw);
						System.out.println(rw);
						String x = rw.toString().split(" ", 2)[1];
						al.add(new String[] { Packets.allmap.get(packetId) + " (" + packetId + ")", x.substring(1, x.length() - 1) });
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					al.add(new String[] { Packets.allmap.get(packetId) + " (" + packetId + ")", encode(decr) });
				}

			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
		}
		DefaultTableModel dtm = new DefaultTableModel(al.toArray(new String[al.size()][2]), new String[] { "Packet", "Content" });
		vf.getTable1().setModel(dtm);
		// vf.getTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		vf.getTable1().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		vf.getTable1().getColumnModel().getColumn(0).setMaxWidth(150);
		vf.getTable1().getColumnModel().getColumn(0).setMinWidth(150);
		vf.getTable1().getColumnModel().getColumn(1).setPreferredWidth(520);
		vf.setTitle(server2client ? "Server to Client" : "Client to Server");
	}

	private void setNLAF() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
	}

	private static void updateWorldStatus(int packetId, RWable rw) {

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

	public String padRight(String s, int num) {
		return String.format("%1$-" + num + "s", s);
	}

	protected static final byte[] Hexhars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static String encode(byte[] b) {
		StringBuilder s = new StringBuilder(2 * b.length);
		for (int i = 0; i < b.length; i++) {
			int v = b[i] & 0xff;
			s.append((char) Hexhars[v >> 4]);
			s.append((char) Hexhars[v & 0xf]);
		}
		return s.toString();
	}

}
