package com.lugocorp.tempo;
import java.awt.Color;

class Sim{
  static final int DREAM=0;
  static final int SWING=1;
  static int HEIGHT=600;
  static int WIDTH=1000;
  private boolean done=false;
  private final int type;
  private final Main m;
  private double timer;
  private int stage=0;

  Sim(Main m,int type){
    this.type=type;
    this.m=m;
  }

  void schedule(double dt){
    if(done) return;
    if(stage>0) timer-=dt;
    if(m.cleared() || timer<0){
      stage++;
      m.clear();
      timer=(type==DREAM)?loadDreamStage():loadSwingStage();
    }
  }

  // Jazzy swing team
  private double loadSwingStage(){
    if(stage==1){
      m.add(new GradientBg(Color.BLUE));
      m.add(new LavaLamp(new Color(200,0,200)));
    }else if(stage==2){
      m.add(new TwoGradientBg(Color.BLUE));
      m.add(new Rectangles());
    }else if(stage==3){
      m.add(new GradientBg(Color.BLUE));
      m.add(new BezierPaths());
    }else if(stage==4){
      m.add(new TwoGradientBg(Color.BLUE));
      m.add(new Squares());
      done=true;
    }
    return 25.0;
  }

  // Dream team
  private double loadDreamStage(){
    if(stage==1){
      m.add(new TunnelOfOz());
      m.add(new FourierWave());
    }else if(stage==2){
      m.add(new TwoGradientBg(Color.BLUE));
      m.add(new LavaLamp(new Color(200,0,200)));
    }else if(stage==3){
      m.add(new TunnelOfOz());
      m.add(new Squares());
    }else if(stage==4){
      m.add(new GradientBg(Color.BLUE));
      m.add(new BubbleSpawner(new Color(200,0,200),250,150,50));
      m.add(new BubbleSpawner(new Color(200,0,200),100,720,75));
      m.add(new BubbleSpawner(new Color(200,0,200),1600,450,35));
      m.add(new BubbleSpawner(new Color(200,0,200),900,800,100));
      m.add(new BubbleSpawner(new Color(200,0,200),1700,900,50));
      m.add(new BubbleSpawner(new Color(200,0,200),500,700,65));
      m.add(new BubbleSpawner(new Color(200,0,200),1200,300,200));
    }else if(stage==5){
      m.add(new TwoGradientBg(Color.BLUE));
      m.add(new BezierPaths());
    }else if(stage==6){
      m.add(new GradientBg(Color.BLUE));
      m.add(new FourierBubbles());
    }else{
      m.add(new GradientBg(Color.BLUE));
      m.add(new DotRain());
      done=true;
    }
    return 30.0;
  }
}
