// GOOD Name: NAMERESULT, Original AS3 class: _-In.as

package packets.server;

import java.io.IOException;

import packets.*;

public class NAMERESULT_Packet implements RWable {
	public Boolean success_; // Boolean
	public String errorText_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.success_ = badi.readBoolean(); // Boolean
		this.errorText_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeBoolean(success_); // Boolean
		bado.writeUTF(errorText_); // UTF
	}

public int getId() {
return 67;
}
public String toString() {
		return "NAMERESULT [" + success_ + " , " + errorText_ + "]";
	}
}
