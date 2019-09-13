package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class FourierWave implements Updatable{
  private static final Color color=new Color(175,175,175);
  private static final int yHalf=Sim.HEIGHT/2;
  private final Random rand=new Random();
  private static final int fMin=200;
  private static final int fMax=400;
  private static final int fInc=10;
  private static final int hMin=25;
  private static final int hMax=50;
  private final double[] levels;
  private final int xInc,xHalf;
  private final int hInc;
  private double timer;
  private final int n;

  FourierWave(){
    n=(fMax-fMin)/fInc;
    levels=new double[n];
    for(int a=0;a<n;a++) levels[a]=hMin;
    hInc=(hMax-hMin)*5;
    xInc=Sim.WIDTH/n;
    xHalf=xInc/2;
  }

  @Override
  public boolean update(double dt){
    for(int a=0;a<n;a++){
      if(levels[a]>hMin) levels[a]-=hInc*dt;
    }
    timer+=dt;
    if(timer>=0.1){
      int i=rand.nextInt(n);
      levels[i]=hMax;
      timer=0;
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    int x=xHalf;
    g.setColor(color);
    for(int a=0;a<n;a++){
      int y=yHalf-(int)levels[a];
      g.fillRect(x-5,y,10,((int)levels[a])*2);
      g.fillOval(x-5,y-5,10,10);
      g.fillOval(x-5,yHalf+(int)levels[a]-5,10,10);
      x+=xInc;
    }
  }
}
