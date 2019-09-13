package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class BubbleSpawner implements Updatable{
  private static final double LIMIT=0.75;
  private static final double DEC=255.0*0.75;
  private final Random rand=new Random();
  private final MutableColor color;
  private boolean die=false;
  private final int x0,y0;
  private boolean spawner;
  private double timer=0;
  private int x,y,vx,vy;
  private final int r;

  private BubbleSpawner(Color color,int x,int y,int r,int vx,int vy){
    this.color=new MutableColor(color);
    spawner=false;
    this.vx=vx;
    this.vy=vy;
    this.x=x;
    this.y=y;
    this.r=r;
    x0=x;
    y0=y;
  }
  BubbleSpawner(Color color,int x,int y,int r){
    this(color,x,y,r,0,0);
    spawner=true;
  }

  @Override
  public boolean update(double dt){
    if(spawner){
      timer+=dt;
      if(timer>LIMIT){
        Main.getInstance().add(
          new BubbleSpawner(color,x,y,rand.nextInt(r/2)+10,
          (rand.nextInt(65)+35)*(rand.nextBoolean()?1:-1),
          (rand.nextInt(65)+35)*(rand.nextBoolean()?1:-1)));
        timer-=LIMIT;
      }
      return true;
    }
    int diff=(int)(DEC*dt);
    if(die){
      if(diff>=color.getAlpha()) color.setAlpha(0);
      else color.setAlpha(color.getAlpha()-diff);
    }
    x+=vx*dt;
    y+=vy*dt;
    if(Math.abs(x-x0)>=r*1.5) die=true;
    return color.getAlpha()>0;
  }

  @Override
  public void render(Graphics g){
    //g.setColor(new Color(color.getRed(),color.getGreen(),color.getBlue(),color.getAlpha()));
    g.setColor(color);
    g.fillOval(x-r,y-r,r*2,r*2);
  }
}
