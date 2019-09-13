package com.lugocorp.tempo;

class Sim{
  public static final int HEIGHT=600;
  public static final int WIDTH=1000;
  private double timer=Console.getDuration();
  private boolean done=false;
  private final Main m;
  private int stage=0;

  Sim(Main m){
    this.m=m;
  }

  void schedule(double dt){
    if(done) return;
    timer-=dt;
    if(timer<=0){
      stage++;
      m.clear();
      timer=loadStage();
    }
  }
  private double loadStage(){
    switch(stage){
      case(1):{
        // Load new stage actors here
      }
      default:{
        done=true;
        return 0.0;
      }
    }
  }
}
