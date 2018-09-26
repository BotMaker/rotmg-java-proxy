// 123.0.0 _-Tq.as

package data;

import java.io.IOException;

import packets.*;

public class Location {
	public float x, y;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + Float.floatToIntBits(x);
		//result = prime * result + Float.floatToIntBits(y);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}
	
	public Location clone() {
		return new Location(x, y);
	}
	
	public Location()
	{
		
	}
	

	public Location(ByteArrayDataInput badi) throws IOException {
		parseFromInput(badi);
	}

	public Location(float x, float y) {
		this.x = x;
		this.y = y;
		return;
	}

	public void writeToOutput(ByteArrayDataOutput bado) throws IOException {
		bado.writeFloat(this.x);
		bado.writeFloat(this.y);
		//bado.writeDouble(this.x);
		//bado.writeDouble(this.y);
		return;
	}

	public void parseFromInput(ByteArrayDataInput badi) throws IOException {
		this.x = badi.readFloat();
		this.y = badi.readFloat();
		//this.x = badi.readDouble();
		//this.y = badi.readDouble();
		return;
	}

	public String toString() {
		return "location [" + x + ", " + y + "]";
	}
	
	public float getAngleTo(Location l) {
		return (float) (180-Math.atan2(l.x - this.x, l.y - this.y)*180/Math.PI);
	}
	
	public double distanceTo(float dx, float dy) {
		return Math.sqrt((dx - x)*(dx - x) + (dy - y)*(dy - y));
	}
	
	public double distanceTo(Location loc) {
		return distanceTo(loc.x, loc.y);
	}
	
	public double distanceToSq(float dx, float dy) {
		return (dx - x)*(dx - x) + (dy - y)*(dy - y);
	}
	
	public double distanceToSq(Location loc) {
		return distanceToSq(loc.x, loc.y);
	}
	
	public Location subtract(Location l) {
		return new Location(l.x - x, l.y - y);
	}
}
