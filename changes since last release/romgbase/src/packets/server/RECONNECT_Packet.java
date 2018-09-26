// GOOD Name: RECONNECT, Original AS3 class: _-4f.as

package packets.server;

import java.io.IOException;
import javax.xml.bind.DatatypeConverter;

import packets.*;

public class RECONNECT_Packet implements RWable {
	public String name_; // String
	public String host_; // String
	public int port_; // int
	public int gameId_; // int
	public int keyTime_; // int
	public byte[] key_; // ByteArray

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
		this.host_ = badi.readUTF(); // UTF
		this.port_ = badi.readInt(); // Int
		this.gameId_ = badi.readInt(); // Int
		this.keyTime_ = badi.readInt(); // Int
		short size = badi.readShort();
		this.key_ = new byte[size];
		badi.readFully(key_);
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(name_); // UTF
		bado.writeUTF(host_); // UTF
		bado.writeInt(port_); // Int
		bado.writeInt(gameId_); // Int
		bado.writeInt(keyTime_); // Int
		bado.writeShort(key_.length);
		bado.write(key_);
	}// 	RECONNECT [Cyclops , ec2-174-129-74-69.compute-1.amazonaws.com , 2050 , 0 , 14117376 , ]

public int getId() {
return 4;
}
public String toString() {
		return "RECONNECT [" + name_ + " , " + host_ + " , " + port_ + " , " + gameId_ + " , " + keyTime_ + " , " + (key_ == null ? null : DatatypeConverter.printHexBinary(key_)) + "]";
	}
}
