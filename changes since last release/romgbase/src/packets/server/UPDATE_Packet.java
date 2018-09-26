// GOOD Name: UPDATE, Original AS3 class: Update.as

package packets.server;

import java.io.IOException;
import java.util.ArrayList;

import packets.*;
import data.*;

public class UPDATE_Packet implements RWable {
	public ArrayList<Tile> tiles_; // Vector.<_-MV>
	public ArrayList<ObjectStatus> newObjs_; // Vector.<_-Oe>
	public ArrayList<Integer> drops_; // Vector.<int>
	
	public UPDATE_Packet() {
		this.drops_ = new ArrayList<Integer>();
		this.newObjs_ = new ArrayList<ObjectStatus>();
		this.tiles_ = new ArrayList<Tile>();
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		tiles_ = null;
		newObjs_ = null;
		drops_ = null;

		int size = badi.readShort();
		tiles_ = new ArrayList<Tile>(size);
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				tiles_.add(new Tile(badi));
			}
		}

		size = badi.readShort();
		newObjs_ = new ArrayList<ObjectStatus>(size);
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				newObjs_.add(new ObjectStatus(badi));
			}
		}

		size = badi.readShort();
		drops_ = new ArrayList<Integer>(size);
		if (size != 0) {
			for (int i = 0; i < size; i++) {
				drops_.add(badi.readInt());
			}
		}
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		if (tiles_ == null) {
			bado.writeShort(0);
		} else {
			bado.writeShort(tiles_.size());
			for (int i = 0; i < tiles_.size(); i++) {
				tiles_.get(i).writeToOutput(bado);
			}
		}
		if (newObjs_ == null) {
			bado.writeShort(0);
		} else {
			bado.writeShort(newObjs_.size());
			for (int i = 0; i < newObjs_.size(); i++) {
				newObjs_.get(i).writeToOutput(bado);
			}
		}
		if (drops_ == null) {
			bado.writeShort(0);
		} else {
			bado.writeShort(drops_.size());
			for (int i = 0; i < drops_.size(); i++) {
				bado.writeInt(drops_.get(i));
			}
		}
	}

public int getId() {
return 5;
}
public String toString() {
		return "UPDATE [" + tiles_ + " , " + newObjs_ + " , " + drops_ + "]";
	}
}
