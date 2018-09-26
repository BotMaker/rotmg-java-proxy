// GOOD Name: NOTIFICATION, Original AS3 class: _-FK.as

package packets.server;

import java.io.IOException;

import packets.*;

public class NOTIFICATION_Packet implements RWable {
	public int objectId_; // int
	public String text_; // String
	public int color_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.objectId_ = badi.readInt(); // Int
		this.text_ = badi.readUTF(); // UTF
		this.color_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(objectId_); // Int
		bado.writeUTF(text_); // UTF
		bado.writeInt(color_); // Int
	}

public int getId() {
return 63;
}
public String toString() {
		return "NOTIFICATION [" + objectId_ + " , " + text_ + " , " + color_ + "]";
	}
}
