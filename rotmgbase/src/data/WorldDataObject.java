package data;

import java.util.List;

public class WorldDataObject {
	public List<java.util.Map<String,String>> objs;
	public List<java.util.Map<String,String>> regions;
	public String ground;
	
	public String toString() {
		   return "WorldDataObject [ground=" + ground + "], objs=["
			+ objs + "] +  regions=["+ regions + "]";
		}
}
