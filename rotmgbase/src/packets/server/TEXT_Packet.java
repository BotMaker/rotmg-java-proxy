// GOOD Name: TEXT, Original AS3 class: Text.as

package packets.server;

import java.io.IOException;

import packets.*;

public class TEXT_Packet implements RWable {
	public String name_; // String
	public int objectId_; // int
	public int numStars_; // int
	public int bubbleTime_; // uint
	public String recipient_; // String
	public String text_; // String
	public String cleanText_; // String

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.name_ = badi.readUTF(); // UTF
		this.objectId_ = badi.readInt(); // Int
		this.numStars_ = badi.readInt(); // Int
		this.bubbleTime_ = badi.readUnsignedByte(); // UnsignedByte
		this.recipient_ = badi.readUTF(); // UTF
		this.text_ = badi.readUTF(); // UTF
		this.cleanText_ = badi.readUTF(); // UTF
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeUTF(name_); // UTF
		bado.writeInt(objectId_); // Int
		bado.writeInt(numStars_); // Int
		bado.writeByte(bubbleTime_); // UnsignedByte
		bado.writeUTF(recipient_); // UTF
		bado.writeUTF(text_); // UTF
		bado.writeUTF(cleanText_); // UTF
	}

public int getId() {
return Packets.TEXT;
}
public String toString() {
		return "TEXT [" + name_ + " , " + objectId_ + " , " + numStars_ + " , " + bubbleTime_ + " , " + recipient_ + " , " + text_ + " , " + cleanText_ + "]";
	}
}
