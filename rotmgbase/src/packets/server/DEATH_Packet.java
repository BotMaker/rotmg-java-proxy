// GOOD Name: DEATH, Original AS3 class: _-ti.as

package packets.server;

import java.io.IOException;

import packets.*;

public class DEATH_Packet implements RWable {
	private int accountId_; // int
	private int charId_; // int
	private String killedBy_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.accountId_ = badi.readInt(); // Int
		this.charId_ = badi.readInt(); // Int
		this.killedBy_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(accountId_); // Int
		bado.writeInt(charId_); // Int
		bado.writeUTF(killedBy_); // UTF
	}

public int getId() {
return 47;
}
public String toString() {
		return "DEATH [" + accountId_ + " , " + charId_ + " , " + killedBy_ + "]";
	}
}
