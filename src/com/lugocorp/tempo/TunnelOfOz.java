package com.lugocorp.tempo;
import java.util.LinkedList;
import java.util.Random;
import java.util.List;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Stroke;
import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;

class TunnelOfOz implements Updatable{
  private static final double LIMIT=1.5;
  private static final double SPEED=200.0;
  private static final Random rand=new Random();
  private final List<Ring> rings=new LinkedList<>();
  private final Stroke stroke=new BasicStroke(3);
  private double timer=0;

  TunnelOfOz(){
    rings.add(new Ring(Sim.WIDTH/2,Sim.HEIGHT/2));
    Point2D center=new Point2D.Float(Sim.WIDTH/2,Sim.HEIGHT/2);
  }

  @Override
  public boolean update(double dt){
    if(rings.size()>1){
      Ring second=rings.get(1);
      if(second.r>second.d) rings.remove(0);
    }
    for(Ring r:rings){
      r.r+=(int)(SPEED*dt);
      r.c.setBlue((155*Math.min(r.r,r.d)/r.d)+100);
    }
    timer+=dt;
    if(timer>LIMIT){
      rings.add(new Ring(Sim.WIDTH/2,Sim.HEIGHT/2));
      timer=0;
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    g2.setStroke(stroke);
    g.setColor(Color.BLUE);
    g.fillRect(0,0,Sim.WIDTH,Sim.HEIGHT);
    for(int a=0;a<rings.size();a++){
      Ring r=rings.get(a);
      int x=r.x-r.r;
      int y=r.y-r.r;
      int r2=r.r*2;
      g.setColor(r.c);
      g.fillOval(x,y,r2,r2);
      //g.setColor(Color.BLACK);
      //g.drawOval(x,y,r2,r2);
    }
  }

  private static class Ring{
    private final MutableColor c;
    private int x,y,d,r=5;
    private Ring(int x,int y){
      this.x=x+rand.nextInt(51)-25;
      this.y=y+rand.nextInt(51)-25;
      int dx=Math.max(Sim.WIDTH-this.x,this.x);
      int dy=Math.max(Sim.HEIGHT-this.y,this.y);
      d=(int)Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
      c=new MutableColor(50,0,100);
    }
  }
}
