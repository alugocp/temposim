package com.lugocorp.tempo;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class GradientBg implements Updatable{
  private final Random rand=new Random();
  private static final int SPEED=50;
  private final MutableColor c,goal;
  private int vr,vg,vb;

  GradientBg(Color c){
    this.c=new MutableColor(c);
    goal=new MutableColor(c);
    setGoal();
  }

  @Override
  public boolean update(double dt){
    int d=(int)(SPEED*dt);
    int dr=d*vr,dg=d*vg,db=d*vb;
    if(closeEnough(dr,dg,db)) setGoal();
    else{
      int nr=c.getRed()+dr;
      int ng=c.getGreen()+dg;
      int nb=c.getBlue()+db;
      if(nr<0 || nr>255 || ng<0 || ng>255 || nb<0 || nb>255) setGoal();
      else{
        c.setRed(nr);
        c.setGreen(ng);
        c.setBlue(nb);
      }
    }
    return true;
  }
  private void setGoal(){
    goal.setGreen(rand.nextInt(51));
    goal.setRed(rand.nextInt(101-goal.getGreen())+goal.getGreen());
    goal.setBlue(rand.nextInt(256-goal.getRed())+goal.getRed());
    vr=(goal.getRed()>c.getRed())?1:-1;
    vg=(goal.getGreen()>c.getGreen())?1:-1;
    vb=(goal.getBlue()>c.getBlue())?1:-1;
  }
  private boolean closeEnough(int dr,int dg,int db){
    return (dr==0 || Math.abs(c.getRed()-goal.getRed())<dr) ||
      (dg==0 || Math.abs(c.getGreen()-goal.getGreen())<dg) ||
      (db==0 || Math.abs(c.getBlue()-goal.getBlue())<db);
  }

  @Override
  public void render(Graphics g){
    g.setColor(c);
    g.fillRect(0,0,Sim.WIDTH,Sim.HEIGHT);
  }
}
