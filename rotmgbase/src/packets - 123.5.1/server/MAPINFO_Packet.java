// GOOD Name: MAPINFO, Original AS3 class: _-U7.as

package packets.server;

import java.io.IOException;
import java.util.ArrayList;

import packets.*;

public class MAPINFO_Packet implements RWable {
	public int width_; // int
	public int height_; // int
	public String name_; // String
	public int fp_; // uint
	public int background_; // int
	public Boolean allowPlayerTeleport_; // Boolean
	public Boolean showDisplays_; // Boolean
	public ArrayList<String> clientXML_; // Vector.<String>
	public ArrayList<String> extraXML_; // Vector.<String>

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.width_ = badi.readInt(); // Int
		this.height_ = badi.readInt(); // Int
		this.name_ = badi.readUTF(); // UTF
		this.fp_ = badi.readInt(); // UnsignedInt
		this.background_ = badi.readInt(); // Int
		this.allowPlayerTeleport_ = badi.readBoolean(); // Boolean
		this.showDisplays_ = badi.readBoolean(); // Boolean

		/*
		int numLines = badi.readUnsignedShort();
		this.clientXML_ = new ArrayList<String>(numLines);
		for (int i = 0; i < numLines; i++) {
			int size = badi.readInt();
			byte[] buf = new byte[size];
			badi.readFully(buf);
			String line = new String(buf, Charset.forName("UTF-8"));
			clientXML_.add(line);
		}

		int numLines2 = badi.readUnsignedShort();
		this.extraXML_ = new ArrayList<String>(numLines2);
		for (int i = 0; i < numLines2; i++) {
			int size = badi.readInt();
			byte[] buf = new byte[size];
			badi.readFully(buf);
			String line = new String(buf, Charset.forName("UTF-8"));
			extraXML_.add(line);
		}
		*/
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(width_); // Int
		bado.writeInt(height_); // Int
		bado.writeUTF(name_); // UTF
		bado.writeInt(fp_); // UnsignedInt
		bado.writeInt(background_); // Int
		bado.writeBoolean(allowPlayerTeleport_); // Boolean
		bado.writeBoolean(showDisplays_); // Boolean
		bado.writeShort(clientXML_.size());
		for (String s : clientXML_) {
			bado.writeBytes(s);
		}
		bado.writeShort(extraXML_.size());
		for (String s : extraXML_) {
			bado.writeBytes(s);
		}
	}

	public int getId() {
		return 60;
	}

	public String toString() {
		return "MAPINFO [" + width_ + " , " + height_ + " , " + name_ + " , " + fp_ + " , " + background_ + " , " + allowPlayerTeleport_ + " , " + showDisplays_ + " , " + clientXML_ + " , " + extraXML_ + "]";
	}
}
