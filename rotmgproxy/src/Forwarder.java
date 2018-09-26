import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import net.clarenceho.crypto.RC4;
import packets.ByteArrayDataInput;
import packets.ByteArrayDataOutput;
import packets.Packets;
import packets.RWable;

public class Forwarder extends Thread {
	DataInputStream in;
	DataOutputStream out;
	private Connection con;
	private boolean isClient;
	private String conType;
	private byte[] key;
	private RC4 rcDECR;
	private RC4 rcENCR;
	private DataOutputStream dosspoofraw;
	public int prevPacketId;
	private static final ArrayList<Integer> dontPrint = new ArrayList<Integer>();
	private static final boolean CHECKING = false;
	public ArrayList<RWable> queue = new ArrayList<RWable>();

	static {
		
		dontPrint.add(Packets.MOVE);
		
		dontPrint.add(Packets.PING);
		dontPrint.add(Packets.NEW_TICK);
		dontPrint.add(Packets.PONG);
		
		dontPrint.add(Packets.SHOW_EFFECT);
		dontPrint.add(Packets.ALLYSHOOT);
		dontPrint.add(Packets.UPDATEACK);
		dontPrint.add(Packets.NOTIFICATION);
		dontPrint.add(Packets.UPDATE);
		dontPrint.add(Packets.SHOOT);
		//dontPrint.add(Packets.SHOOTMULTI);
		dontPrint.add(Packets.SHOOTACK);
		
		//-------------------------
		dontPrint.add(Packets.PLAYERSHOOT);
		dontPrint.add(Packets.PLAYERTEXT);
		dontPrint.add(Packets.TEXT);
		dontPrint.add(Packets.GOTO);
		dontPrint.add(Packets.GOTOACK);
		System.out.println("Dont print: " + dontPrint);
		
	}

	public Forwarder(String type, Connection connection, InputStream in, OutputStream out, byte[] key, boolean isClient) {
		this.conType = type;
		this.key = key;
		this.in = new DataInputStream(in);
		this.out = new DataOutputStream(out);
		this.con = connection;
		this.isClient = isClient;
		this.rcDECR = new RC4(key);
		this.rcENCR = new RC4(key);
	}

	public void run() {
		try {
			FileOutputStream fosrealraw = new FileOutputStream("real " + conType + " raw.txt");
			DataOutputStream dosrealraw = new DataOutputStream(fosrealraw);
			// FileOutputStream foshexlabel = new FileOutputStream(conType + " hexlabeld.txt");
			// FileOutputStream foshexlabeldecr = new FileOutputStream(conType + " hexlabelddecr.txt");
			// DataOutputStream doshexlabeldecr = new DataOutputStream(foshexlabeldecr);
			// DataOutputStream doshexlabel = new DataOutputStream(foshexlabel);

			FileOutputStream fosspoofraw = new FileOutputStream("spoof " + conType + " raw.txt");
			dosspoofraw = new DataOutputStream(fosspoofraw);
			if (isClient) {
				String xx = "311F80691451C71B09A13A2A6E";
				byte[] tkey = fromHexString(xx);

				rcDECR = new RC4(tkey);
				rcENCR = new RC4(tkey);

			} else {

				String xx = "72C5583CAFB6818995CBD74B80";
				byte[] tkey = fromHexString(xx);

				rcDECR = new RC4(tkey);
				rcENCR = new RC4(tkey);
			}
			while (true) {
				while (queue.size() != 0) {
					RWable rw = queue.remove(0);
					writePacket(rw);
				}
				int packetLength = in.readInt();
				int packetId = in.readByte();
				byte[] buf = new byte[packetLength - 5]; // read the rest of the packet
				in.readFully(buf);
				// rc.saveState();
				// String enc = encode(buf);
				// byte[] tx = fromHexString(enc);
				byte[] decr = rcDECR.rc4(buf);
				// rc.restoreState();
				// doshexlabel.writeBytes(packetId + " " + padRight(Packets.allmap.get(packetId), 16) + " | " + packetLength + " | ");
				// doshexlabel.writeBytes(encode(buf));
				// doshexlabel.write('\n');
				//
				// doshexlabeldecr.writeBytes(packetId + " " + padRight(Packets.allmap.get(packetId), 16) + " | " + packetLength + " | ");
				// doshexlabeldecr.writeBytes(encode(decr));
				// doshexlabeldecr.write('\n');

				dosrealraw.writeInt(packetLength);
				dosrealraw.writeByte(packetId);
				dosrealraw.write(buf);
				ByteArrayDataInput badi = new ByteArrayDataInput(decr);

				boolean found = false, written = false;
				ByteArrayDataOutput bado = new ByteArrayDataOutput(100000);
				RWable rw = null;
				
				
				try {
					rw = Packets.findPacket(packetId);
				} catch (Error e) {
                
				}
				
				//System.out.println(packetId);
				
				if (rw != null) {
					try {
						found = true;
						rw.parseFromInput(badi);
						con.log(rw.toString(), isClient);
						int act = con.update(packetId, rw, isClient);
						if (!dontPrint.contains(packetId) && act != Connection.NO_PRINT)
							System.out.println((act == Connection.NO_SEND ? "BLOCKED " : "") + rw);
						if (act == Connection.NO_SEND) { // TODO remove query method
							continue; // consume
						}
						//rw.writeToOutput(bado);
						//written = true;
					} catch (IOException ioe) {
						ioe.printStackTrace();
					} catch (Exception e) {
						// ignored
						e.printStackTrace();
					}
				}
				
				if (found && !written) {
					//System.out.println("fail: " + DatatypeConverter.printHexBinary(bado.getArray()));
					//System.out.println("fail: " + DatatypeConverter.printHexBinary(decr));
					//System.out.println("Failed on packet with id: " + packetId);
				}
				if (found && written) {
					byte[] badoa = bado.getArray();
					// byte[] encr = rcDECR.rc4(badoa);

					// if (CHECKING) {
					// if (Arrays.equals(decr, badoa)) { // cant compare encrypted packets since we're add/removing some
					// decr = badoa;
					// } else {
					// System.out.println("not match! " + packetId);
					// System.out.println(DatatypeConverter.printHexBinary(decr));
					// System.out.println(DatatypeConverter.printHexBinary(badoa));
					// }
					// } else {
					decr = badoa;
					// }
				}

				writePacket(packetId, decr);
				prevPacketId = packetId;
			}
		} catch (IOException ioe) {
			// ioe.printStackTrace();
			con.close();
		}
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

	public static String encodeByte(byte b) {
		StringBuilder s = new StringBuilder(2);
		int v = b & 0xff;
		s.append((char) Hexhars[v >> 4]);
		s.append((char) Hexhars[v & 0xf]);
		return s.toString();
	}

	public void writePacket(int i, byte[] buf) throws IOException {
		byte[] encr = rcENCR.rc4(buf);
		out.writeInt(encr.length + 5);
		out.writeByte(i);
		out.write(encr);
		out.flush();
		dosspoofraw.writeInt(encr.length + 5);
		dosspoofraw.writeByte(i);
		dosspoofraw.write(encr);
	}

	public void writePacket(RWable rw) throws IOException {
//		con.log(rw.toString(), true);
		ByteArrayDataOutput bado = new ByteArrayDataOutput(100000);
		rw.writeToOutput(bado);
		writePacket(rw.getId(), bado.getArray());
	}

}
