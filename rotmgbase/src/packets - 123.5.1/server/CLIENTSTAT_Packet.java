// GOOD Name: CLIENTSTAT, Original AS3 class: _-Pb.as

package packets.server;

import java.io.IOException;

import packets.*;

public class CLIENTSTAT_Packet implements RWable {
	public String filename_; // String
	public int value_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.filename_ = badi.readUTF(); // UTF
		this.value_ = badi.readInt();
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(filename_); // UTF
		bado.writeInt(value_);
	}

public int getId() {
return 46;
}
public String toString() {
		return "CLIENTSTAT [" + filename_ + " , " + value_ + "]";
	}
}
