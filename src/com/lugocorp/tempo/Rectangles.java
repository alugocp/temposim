package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class Rectangles implements Updatable{
  private static final int n=16;
  private static final Random rand=new Random();
  private final Rect[] rects=new Rect[n];

  @Override
  public boolean update(double dt){
    for(int a=0;a<n;a++){
      if(rects[a]==null) rects[a]=new Rect();
      Rect r=rects[a];
      r.x+=r.v*dt;
      if(r.x+r.w<0 || r.x>Sim.WIDTH) rects[a]=null;
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    for(int a=0;a<n;a++){
      if(rects[a]==null) continue;
      Rect r=rects[a];
      g.setColor(r.c);
      g.fillRect((int)r.x,r.y,r.w,r.h);
    }
  }

  private static class Rect{
    private final Color c;
    private int y,w,h,v;
    private double x;
    private Rect(){
      c=new Color(
        rand.nextInt(256),
        rand.nextInt(256),
        rand.nextInt(256)
      );
      y=rand.nextInt(Sim.HEIGHT);
      w=rand.nextInt(526)+75;
      h=rand.nextInt(251)+25;
      v=rand.nextInt(101)+25;
      if(rand.nextBoolean()) x=-w;
      else{
        x=Sim.WIDTH;
        v*=-1;
      }
    }
  }
}
