// GOOD Name: FAILURE, Original AS3 class: _-9t.as

package packets.server;

import java.io.IOException;

import packets.*;

public class FAILURE_Packet implements RWable {
	public int errorId_; // int
	public String errorDescription_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.errorId_ = badi.readInt(); // Int
		this.errorDescription_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(errorId_); // Int
		bado.writeUTF(errorDescription_); // UTF
	}

public int getId() {
return 0;
}
public String toString() {
		return "FAILURE [" + errorId_ + " , " + errorDescription_ + "]";
	}
}
