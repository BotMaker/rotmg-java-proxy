// GOOD Name: DAMAGE, Original AS3 class: Damage.as

package packets.server;

import java.io.IOException;
import java.util.ArrayList;

import packets.*;

public class DAMAGE_Packet implements RWable {
	private int targetId_; // int
	private ArrayList<Integer> effects_; // Vector.<uint>
	private int damageAmount_; // int
	private Boolean kill_; // Boolean
	private int bulletId_; // uint
	private int objectId_; // int

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.targetId_ = badi.readInt(); // Int
		int size = badi.readUnsignedByte();
		effects_ = new ArrayList<Integer>(size);
		for (int i = 0 ; i < size ; i++) {
			effects_.add(badi.readUnsignedByte());
		}
		this.damageAmount_ = badi.readUnsignedShort(); // UnsignedShort
		this.kill_ = badi.readBoolean(); // Boolean
		this.bulletId_ = badi.readUnsignedByte(); // UnsignedByte
		this.objectId_ = badi.readInt(); // Int
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(targetId_); // Int
		bado.writeByte(effects_.size());
		for (int i : effects_)
			bado.writeByte(i);
		bado.writeShort(damageAmount_); // UnsignedShort
		bado.writeBoolean(kill_); // Boolean
		bado.writeByte(bulletId_); // UnsignedByte
		bado.writeInt(objectId_); // Int
	}

public int getId() {
return 47;
}
public String toString() {
		return "DAMAGE [" + targetId_ + " , " + effects_ + " , " + damageAmount_ + " , " + kill_ + " , " + bulletId_ + " , " + objectId_ + "]";
	}
}
