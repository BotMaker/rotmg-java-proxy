// 123.0.0 _-Tj.as

package data;

import java.io.IOException;

import packets.*;

public class LocationWTime extends Location {
	public int time;
	
	public LocationWTime(ByteArrayDataInput badi) throws IOException {
		parseFromInput(badi);
	}

	public LocationWTime(int time, float x, float y) {
		this.time = time;
		this.x = x;
		this.y = y;
		return;
	}

	public LocationWTime(int time_, Location l) {
		this.time = time_;
		this.x = l.x;
		this.y = l.y;
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeInt(this.time);
		super.writeToOutput(bado);
		return;
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.time = badi.readInt();
		super.parseFromInput(badi);
		return;
	}

	public String toString() {
		return "locationWTime [" + time + ", " + x + ", " + y + "]";
	}

	public Location asLocation() {
		return new Location(x, y);
	}
}
