// 123.0.0 _-Qd.as

package data;

import java.io.IOException;

import packets.*;

public class Item {

	public int objectId; // int
	public int slotId; // ubyte
	public int itemType; // short

	public Item(int objectId, int slotId, int itemType) {
		this.objectId = objectId;
		this.slotId = slotId;
		this.itemType = itemType;
	}

	public Item(ByteArrayDataInput badi) throws IOException {
		parseFromInput(badi);
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		objectId = badi.readInt();
		slotId = badi.readUnsignedByte();
		itemType = badi.readShort();
		System.out.println(toString());
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(objectId);
		bado.writeByte(slotId);
		bado.writeShort(itemType);
		System.out.println(toString());
	}

	public String toString() {
		return "item [" + objectId + ", " + slotId + ", " + itemType + "]";
	}

}
