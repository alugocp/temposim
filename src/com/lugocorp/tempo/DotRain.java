package com.lugocorp.tempo;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

class DotRain implements Updatable{
  private static final Random rand=new Random();
  private static final int n=20;
  private final ArrayList<LinkedList<Bubble>> chains=new ArrayList<LinkedList<Bubble>>();
  private static final int RAD=20;
  private static final int DIA=RAD*2;
  private static final int DY=50;

  DotRain(){
    for(int a=0;a<n;a++) chains.add(null);
  }

  @Override
  public boolean update(double dt){
    for(int a=0;a<n;a++){
      if(chains.get(a)==null){
        // Create new chain here
        LinkedList<Bubble> r=new LinkedList<>();
        int x=rand.nextInt(Sim.WIDTH);
        double limit=(rand.nextDouble()*0.55)+0.20;
        int l=rand.nextInt(4)+3;
        for(int b=l-1;b>=0;b--){
          int y=-RAD-(DY*b);
          r.add(new Bubble(limit,x,y));
        }
        chains.set(a,r);
      }
      LinkedList<Bubble> rain=chains.get(a);
      Bubble head=rain.get(0);
      if(head.y-RAD>=Sim.HEIGHT){
        chains.set(a,null);
        continue;
      }
      head.timer-=dt;
      if(head.timer<=0){
        int y=head.y+(DY*rain.size());
        rain.add(new Bubble(head.limit,head.x,y));
        rain.remove(0);
      }
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    for(int a=0;a<n;a++){
      LinkedList<Bubble> rain=chains.get(a);
      if(rain==null) continue;
      for(Bubble b:rain){
        g.setColor(b.c);
        g.fillOval(b.x-RAD,b.y-RAD,DIA,DIA);
      }
    }
  }

  private static class Bubble{
    private final double limit;
    private final Color c;
    private final int x,y;
    private double timer;
    private Bubble(double limit,int x,int y){
      c=new Color(rand.nextInt(256),rand.nextInt(256),rand.nextInt(256));
      this.limit=limit;
      timer=limit;
      this.x=x;
      this.y=y;
    }
  }
}
