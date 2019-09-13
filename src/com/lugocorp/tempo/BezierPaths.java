package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class BezierPaths implements Updatable{
  private static final Random rand=new Random();
  private static final int n=6;
  private final Bubble[] bubbles=new Bubble[n];
  private static final int RAD=30;
  private static final int DIA=RAD*2;

  @Override
  public boolean update(double dt){
    for(int a=0;a<n;a++){
      if(bubbles[a]==null) bubbles[a]=new Bubble();
      Bubble b=bubbles[a];
      b.t+=b.v*dt;
      if(b.t>1.25){
        bubbles[a]=null;
        continue;
      }
      double c1=Math.pow(1.0-b.t,2);
      double c2=2.0*(1.0-b.t)*b.t;
      double c3=Math.pow(b.t,2);
      b.x=(int)((c1*b.x1)+(c2*b.x2)+(c3*b.x3));
      b.y=(int)((c1*b.y1)+(c2*b.y2)+(c3*b.y3));
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    g.setColor(Color.YELLOW);
    for(Bubble b:bubbles){
      if(b==null) continue;
      g.fillOval(b.x-RAD,b.y-RAD,DIA,DIA);
    }
  }

  private static class Bubble{
    private int x,y,x1,y1,x2,y2,x3,y3;
    private double t=-0.1,v;
    private Bubble(){
      v=(rand.nextDouble()*0.5)+0.2;
      if(rand.nextBoolean()){
        x1=rand.nextBoolean()?Sim.WIDTH:0;
        y1=rand.nextInt(Sim.HEIGHT);
      }else{
        y1=rand.nextBoolean()?Sim.HEIGHT:0;
        x1=rand.nextInt(Sim.WIDTH);
      }
      if(rand.nextBoolean()){
        x3=rand.nextBoolean()?Sim.WIDTH:0;
        y3=rand.nextInt(Sim.HEIGHT);
      }else{
        y3=rand.nextBoolean()?Sim.HEIGHT:0;
        x3=rand.nextInt(Sim.WIDTH);
      }
      y2=Sim.HEIGHT/2;
      x2=Sim.WIDTH/2;
    }
  }
}
