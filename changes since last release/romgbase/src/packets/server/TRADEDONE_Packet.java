// GOOD Name: TRADEDONE, Original AS3 class: _-78.as

package packets.server;

import java.io.IOException;

import packets.*;

public class TRADEDONE_Packet implements RWable {
	public int code_; // int
	public String description_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.code_ = badi.readInt(); // Int
		this.description_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(code_); // Int
		bado.writeUTF(description_); // UTF
	}

public int getId() {
return 42;
}
public String toString() {
		return "TRADEDONE [" + code_ + " , " + description_ + "]";
	}
}
