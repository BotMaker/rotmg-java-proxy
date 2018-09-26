package client.base;

import data.Location;

public class StationaryMover implements MoveClass {
	private GameConnection gCon;

	public StationaryMover(GameConnection gCon) {
		this.gCon = gCon;
	}

	@Override
	public Location move(int time) {
		return gCon.world.getPosition();
	}

}
