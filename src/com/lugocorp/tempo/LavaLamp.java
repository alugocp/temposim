package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class LavaLamp implements Updatable{
  private static final int n=15;
  private static final int HALF=Sim.HEIGHT/2;
  private static final Random rand=new Random();
  private final Bubble[] bubbles=new Bubble[n*3];
  private final Color c;

  LavaLamp(Color c){
    this.c=c;
    for(int a=0;a<bubbles.length;a+=3){
      bubbles[a+2]=new Bubble(Sim.HEIGHT);
      bubbles[a+1]=new Bubble();
      bubbles[a]=new Bubble(0);
    }
  }

  @Override
  public boolean update(double dt){
    for(int a=0;a<bubbles.length;a++){
      Bubble b=bubbles[a];
      b.y-=b.v*dt;
      double bonus=((HALF-Math.abs(b.y-HALF))/(double)HALF)*1.0;
      b.r=(int)(b.r0*(1.0+bonus));
      if(b.y+b.r<=0) b.y=Sim.HEIGHT+b.r;
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    g.setColor(c);
    for(Bubble b:bubbles){
      int dia=b.r*2;
      g.fillOval(b.x-b.r,b.y-b.r,dia,dia);
    }
  }

  private static class Bubble{
    private int x,y,v,r,r0;
    private Bubble(int y){
      x=rand.nextInt(Sim.WIDTH);
      r=rand.nextInt(Sim.HEIGHT/10)+10;
      this.y=y;
      r0=r;
    }
    private Bubble(){
      this(rand.nextInt(Sim.HEIGHT));
      r0=rand.nextInt(Sim.HEIGHT/15)+10;
      v=rand.nextInt(46)+5;
    }
  }
}
