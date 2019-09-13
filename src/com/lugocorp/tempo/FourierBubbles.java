package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class FourierBubbles implements Updatable{
  private static final Random rand=new Random();
  private static final double rInc=100;
  private static final int fMin=200;
  private static final int fMax=400;
  private static final int fInc=10;
  private final Bubble[] bubbles;
  private double timer;
  private final int n;

  FourierBubbles(){
    n=(fMax-fMin)/fInc;
    bubbles=new Bubble[n];
    int x=rand.nextInt(Sim.WIDTH/2)+(Sim.WIDTH/4);
    int y=rand.nextInt(Sim.HEIGHT/2)+(Sim.HEIGHT/4);
    for(int a=0;a<n;a++) bubbles[a]=new Bubble(x,y);
  }

  @Override
  public boolean update(double dt){
    for(int a=0;a<n;a++){
      if(bubbles[a].r>bubbles[a].r0) bubbles[a].r-=rInc*dt;
    }
    timer+=dt;
    if(timer>=0.06){
      int i=rand.nextInt(n);
      bubbles[i].r=bubbles[i].r0*1.25;
      timer=0;
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    for(int a=0;a<n;a++){
      Bubble b=bubbles[a];
      int dia=((int)b.r)*2;
      g.setColor(b.c);
      g.fillOval(b.x-(int)b.r,b.y-(int)b.r,dia,dia);
    }
  }

  private static class Bubble{
    private final double r0;
    private final Color c;
    private final int x,y;
    private double r;
    private Bubble(int x,int y){
      this.x=x+rand.nextInt(601)-275;
      this.y=y+rand.nextInt(601)-275;
      int green=rand.nextInt(101);
      int red=rand.nextInt(151-green)+green;
      int blue=rand.nextInt(256-red)+red;
      c=new Color(red,green,blue,rand.nextInt(101)+100);
      r0=rand.nextInt(101)+25;
      r=r0;
    }
  }
}
