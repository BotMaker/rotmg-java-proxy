// 123.0.0 _-MV.as

package data;

import java.io.IOException;
import packets.*;

public class Tile {
	public int x, y;
	public int type; // byte
//	static ArrayList<Integer> list = new ArrayList<Integer>();
	
	public Tile(ByteArrayDataInput badi) throws IOException {
		parseFromInput(badi);
	}

	public Tile(int x, int y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		return;
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeShort(this.x);
		bado.writeShort(this.y);
		bado.writeShort(this.type);
		return;
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.x = badi.readShort();
		this.y = badi.readShort();
		this.type = badi.readShort();
//		if (!list.contains((int)type)) {
//			list.add((int)type);
//			System.out.println(list);
//		}
		// 72 grass
		// 100 -godlands black
		// 116 other random tile in nexus
		// 117 other random tile in nexus
		// 118 other random tile in nexus
		// 119 other random tile in nexus
		// 120 random tile in nexus :S
		// 121 speed tile nexus
		// 122 water
		// 123 tile under fountain nexus
		return;
	}

	public String toString() {
		return "tile [" + x + ", " + y + ", " + type + "]";
	}
}
