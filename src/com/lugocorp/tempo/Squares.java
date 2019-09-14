package com.lugocorp.tempo;
import java.util.Random;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;

class Squares implements Updatable{
  private static final int n=12;
  private static final Random rand=new Random();
  private final Square[] squares=new Square[n];

  @Override
  public boolean update(double dt){
    for(int a=0;a<n;a++){
      if(squares[a]==null) squares[a]=new Square();
      Square s=squares[a];
      s.r+=s.v*dt;
      if(s.r/s.r0>=3){
        squares[a]=null;
      }
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    for(int a=0;a<n;a++){
      if(squares[a]==null) continue;
      Square s=squares[a];
      g.setColor(s.c);
      int r=(int)s.r;
      if(s.thicc==null){
        g.fillRect(s.x-r,s.y-r,r*2,r*2);
      }else{
        ((Graphics2D)g).setStroke(s.thicc);
        g.drawRect(s.x-r,s.y-r,r*2,r*2);
      }
    }
  }

  private static class Square{
    private BasicStroke thicc;
    private final Color c;
    private double r,r0;
    private int x,y,v;
    private Square(){
      c=new Color(
        rand.nextInt(256),
        rand.nextInt(256),
        rand.nextInt(256)
      );
      if(rand.nextBoolean()) thicc=new BasicStroke(rand.nextInt(10)+1);
      y=rand.nextInt(Sim.HEIGHT);
      x=rand.nextInt(Sim.WIDTH);
      v=rand.nextInt(56)+25;
      r=rand.nextInt(56)+25;
      r0=r;
    }
  }
}
