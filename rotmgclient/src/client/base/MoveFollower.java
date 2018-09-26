package client.base;

import client.GameConsts;
import client.LoadMany;
import data.Location;
import data.ObjectStatus;

public class MoveFollower implements MoveClass {
	private GameConnection gCon;
	private GameClient gCli;
	private String followTarget;
	private boolean teleport;
	private float followDist = 0.25f;
	
	int inca=0;
	int mode=0;
	float dist=2;
	Location pos= new Location(134,140);

	public MoveFollower(GameConnection gCon,GameClient gameClient) {
		this.gCon = gCon;
		this.gCli = gameClient;
		//System.out.println(gameClient);
	}
	
	public void setFollowTarget(String target) {
		this.followTarget = target;
	}

	public Location move(int time) {
		ObjectStatus os = gCon.world.getObjectByName(followTarget);
		if (os != null) {
			if (teleport && os.objStatData.position.distanceToSq(gCon.world.getPosition()) > 144 && gCon.world.isTeleportAvailable()) {
				gCon.world.teleport(os.objStatData.objectId);
			}
			//System.out.println( os.objStatData.position );
			if (os.objStatData.position.distanceToSq(gCon.world.getPosition()) > followDist) {
				return getFollowPosition(time, os.objStatData.position);
			}
		}
		
		return getFollowPosition(time, pos);
		//return gCon.world.getPosition();
	}
	
	public Location getFollowPosition(int time, Location targ2) {
		
		//System.out.println(gCli.index);
		
		Location targ=gCli.tpos;//targ2.clone();
		//Location targ=targ2.clone();
		
		/*
		targ.x += dist*Math.sin(Math.toRadians((90*gCli.index)+inca));
		targ.y += dist*Math.cos(Math.toRadians((90*gCli.index)+inca));
		inca+=2;
		if (inca>360)
		{
			inca=0;
		}
		
		if (mode==0)
		{
		  dist-=0.1;
		  if (dist<=0.5)
		  {
		   mode=1;
		  }
		}
		
		if (mode==1)
		{
			dist+=0.1;
			if (dist>=2)
			{
			  mode=0;
			}
		}
		*/
		
		
		float angle = gCon.world.getPosition().getAngleTo(targ);
		Location loc = gCon.world.getPosition().clone();
		boolean xLess = false, yLess = false;
		if (loc.x < targ.x)
			xLess = true;
		if (loc.y < targ.y)
			yLess = true;
		//oringal speed location
		double speed = GameConsts.SPEED_BASE + (GameConsts.SPEED_MULTIPLIER * gCon.world.getUs().getTotalSpeed());
		if (time > 600)
			time = 600; // even if we had a lag spike, moving more than a certain amount will still kick us
		
		if (time > 200)
			time = 200; // even if we had a lag spike, moving more than a certain amount will still kick us
		if (loc.distanceTo(gCli.tpos)>0.01)
		{
		loc.x += time * speed * Math.sin(Math.toRadians(angle));
		loc.y -= time * speed * Math.cos(Math.toRadians(angle));
		
		if (xLess && loc.x > targ.x)
			loc.x = targ.x;
		if (!xLess && loc.x < targ.x)
			loc.x = targ.x;

		if (yLess && loc.y > targ.y)
			loc.y = targ.y;
		if (!yLess && loc.y < targ.y)
			loc.y = targ.y;
		}
		else
		{
		  //loc=targ;
		}
		return loc;
	}

	public void setTeleport(boolean b) {
		this.teleport = b;
	}

	public void setFollowDist(float f) {
		this.followDist = f*f;
	}

}
