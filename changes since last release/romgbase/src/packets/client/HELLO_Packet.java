// Name: HELLO, Original AS3 class: _-5G.as

package packets.client;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.xml.bind.DatatypeConverter;

import packets.*;

public class HELLO_Packet implements RWable {
	public String buildVersion_; // String
	public int gameId_ = 0; // int
	public String guid_; // String
	public String password_; // String
	public String secret_; // String
	public int keyTime_ = 0; // int
	public byte[] key_; // ByteArray
	public String jD; // String
	public String pk = ""; // String
	public String Tq = ""; // String
	public String H = ""; // String
	public String playPlatform = ""; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.buildVersion_ = badi.readUTF(); // UTF
		this.gameId_ = badi.readInt(); // Int
		this.guid_ = badi.readUTF(); // UTF
		this.password_ = badi.readUTF(); // UTF
		this.secret_ = badi.readUTF(); // UTF
		this.keyTime_ = badi.readInt(); // Int
		this.key_ = new byte[badi.readShort()]; // Short
		badi.readFully(this.key_); // Bytes

		byte[] buf = new byte[badi.readInt()]; // Int
		badi.readFully(buf);
		this.jD = new String(buf, Charset.forName("UTF-8"));		

		this.pk = badi.readUTF(); // UTF
		this.Tq = badi.readUTF(); // UTF
		this.H = badi.readUTF(); // UTF
		this.playPlatform = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(this.buildVersion_); // UTF
		bado.writeInt(this.gameId_); // Int
		bado.writeUTF(this.guid_); // UTF
		bado.writeUTF(this.password_); // UTF
		bado.writeUTF(this.secret_); // UTF
		bado.writeInt(this.keyTime_); // Int
		bado.writeShort(this.key_.length); // Short
		bado.write(this.key_); // Bytes
		bado.writeInt(this.jD.getBytes().length); // Int
		bado.write(this.jD.getBytes()); // UTFBytes

		
		bado.writeUTF(this.pk); // UTF
		bado.writeUTF(this.Tq); // UTF
		bado.writeUTF(this.H); // UTF
		bado.writeUTF(this.playPlatform); // UTF
	}

	public int getId() {
		return Packets.HELLO;
	}

	public String toString() {
		return "HELLO [" + buildVersion_ + " , " + gameId_ + " , " + guid_ + " , " + password_ + " , " + secret_ + " , " + keyTime_ + " , " + (key_ == null ? null : DatatypeConverter.printHexBinary(key_)) + " , " + jD + " , " + pk + " , " + Tq + " , " + H + " , " + playPlatform + "]";
	}
}
