package server;

import data.ObjectStatus;

public class entity {
	
	
	public int objectType;
	public int active;
	public int radius;
	public float x;
	public float y;
	public int hp;
	public int hpmax;
	
	private float startx=0;
    private float starty=0;
    private int range=0;
    private int roaming_radius=64;
    private int ROAMING=0;
    private int ATTACKING=1;
    private int state=ROAMING;
    private int move_dir=90;
    private int direction=90;
    private int speed=2;

    private int bullet_radius=16;
    private int bullet_shot=4;
    private int target_dir=270;
    private int bullet_speed=8;
    private int attack_range=256;
    public int attack_timer=60;
    private int shot_time=60*3;
    private int step_time=0;
    private int dist_to_target=0;
	
	public entity(int type, float x, float y)
	{
	  this.objectType=type;
	  this.y=y;
	  this.x=x;
	  this.startx=x;
	  this.starty=y;
	  this.radius=0;
	  this.active=0;
	  return;
	}
	
	public void entity_tick()
	{
	  float dist,dist2;
	  attack_timer+=1;
	  
	  if (state==ROAMING)
	  {
		dist=point_distance(x,y,startx,starty);
	    if (dist>roaming_radius)
	    {
		  move_dir=((move_dir-180)) % 360;
	      dist2=dist-roaming_radius;
		  x+=Math.cos(degtorad(move_dir))*dist2;
	      y-=Math.sin(degtorad(move_dir))*dist2;
		  move_dir+=irandom(90);
		  move_dir-=irandom(90);
		  direction=move_dir;
		}
	    
	    /*
	    if point_distance(x,y,obj_player.x,obj_player.y) < attack_range
	    {
		  state=ATTACKING;
	    }
	    */
	  }
	}
	
	int irandom(int max)
	{
	  return  (int)(Math.random()%max)+1;   
	}
	
	double degtorad(double deg)
	{
	  return deg* Math.PI / 180 ;     
	}
	
	
	int point_direction(float x1,float y1 , float x2, float y2)
	{
		float angle, x, y;
	    x = x1-x2;
	    y = y1-y2;
	    if (y!=0) 
	    { 
	      angle = 90-(float) -(Math.atan(x/y) * 180 / Math.PI);
	    }
	    else 
	    {
	      if (x1>x2)
	      {
	    	 angle=180;
	      }
	      else if (x1<x2)
	      {
            angle=0;
	      }
	      else
	      {
	         angle=0;
	      }
	    }
	    
	    if (y>0)
	    {
	      angle-=180;
	    }
	    
	    if (angle>0)
	    {
	    	angle-=360;
	    }
	    return (int)-angle;
	}
	
	public float point_distance(float x1,float y1, float x2, float y2)
	{
	    float x, y;
	    x = (float) Math.pow(Math.floor(x1) - Math.floor(x2), 2);
	    y = (float) Math.pow(Math.floor(y1) - Math.floor(y2), 2);
	    return (float) Math.sqrt(x + y);
	}
	
}
