package data;

import java.util.List;

  public class WorldData {
	public String data;
	public List<WorldDataObject> dict;
		
	public int width;
	public int height;

	
	@Override
	public String toString() {
	   return "WorldData [width=" + width + ", height=" + height + ", dict=["
		+ dict + "]"+ ", data=" + data +"]";
	}
}
