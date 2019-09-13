package com.lugocorp.tempo;
import java.util.Random;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Color;

class TwoGradientBg implements Updatable{
  private final MutableColor c1,c2,g1,g2;
  private final Random rand=new Random();
  private static final int SPEED=50;
  private int vr1,vg1,vb1,vr2,vg2,vb2;
  private final GradientPaint paint;

  TwoGradientBg(Color c){
    c1=new MutableColor(c);
    c2=new MutableColor(c);
    g1=new MutableColor(c);
    g2=new MutableColor(c);
    paint=new GradientPaint(0,0,c1,Sim.WIDTH,Sim.HEIGHT,c2);
    setGoal1();
    setGoal2();
  }

  @Override
  public boolean update(double dt){
    int d=(int)(SPEED*dt);
    int dr1=d*vr1,dg1=d*vg1,db1=d*vb1;
    int dr2=d*vr2,dg2=d*vg2,db2=d*vb2;
    if(closeEnough(c1,g1,dr1,dg1,db1)) setGoal1();
    else{
      int nr=c1.getRed()+dr1;
      int ng=c1.getGreen()+dg1;
      int nb=c1.getBlue()+db1;
      if(nr<0 || nr>255 || ng<0 || ng>255 || nb<0 || nb>255) setGoal1();
      else{
        c1.setRed(nr);
        c1.setGreen(ng);
        c1.setBlue(nb);
      }
    }
    if(closeEnough(c2,g2,dr2,dg2,db2)) setGoal2();
    else{
      int nr=c2.getRed()+dr2;
      int ng=c2.getGreen()+dg2;
      int nb=c2.getBlue()+db2;
      if(nr<0 || nr>255 || ng<0 || ng>255 || nb<0 || nb>255) setGoal2();
      else{
        c2.setRed(nr);
        c2.setGreen(ng);
        c2.setBlue(nb);
      }
    }
    return true;
  }
  private void setGoal1(){
    g1.setGreen(rand.nextInt(51));
    g1.setRed(rand.nextInt(101-g1.getGreen())+g1.getGreen());
    g1.setBlue(rand.nextInt(256-g1.getRed())+g1.getRed());
    vr1=(g1.getRed()>c1.getRed())?1:-1;
    vg1=(g1.getGreen()>c1.getGreen())?1:-1;
    vb1=(g1.getBlue()>c1.getBlue())?1:-1;
  }
  private void setGoal2(){
    g2.setGreen(rand.nextInt(51));
    g2.setRed(rand.nextInt(101-g2.getGreen())+g2.getGreen());
    g2.setBlue(rand.nextInt(256-g2.getRed())+g2.getRed());
    vr2=(g2.getRed()>c2.getRed())?1:-1;
    vg2=(g2.getGreen()>c2.getGreen())?1:-1;
    vb2=(g2.getBlue()>c2.getBlue())?1:-1;
  }
  private boolean closeEnough(Color c,Color goal,int dr,int dg,int db){
    return (dr==0 || Math.abs(c.getRed()-goal.getRed())<dr) ||
      (dg==0 || Math.abs(c.getGreen()-goal.getGreen())<dg) ||
      (db==0 || Math.abs(c.getBlue()-goal.getBlue())<db);
  }

  @Override
  public void render(Graphics g){
    Graphics2D g2=(Graphics2D)g;
    g2.setPaint(paint);
    g2.fillRect(0,0,Sim.WIDTH,Sim.HEIGHT);
  }
}
