package com.lugocorp.tempo;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

class Console implements Updatable{
  static private final MutableColor text=new MutableColor(75,160,235);
  static private final Color bg=Color.BLACK;
  static private final int LINE_HEIGHT=45;
  static private final int MAX=Sim.HEIGHT/LINE_HEIGHT;
  private final Font font=new Font(Font.MONOSPACED,Font.BOLD,35);
  private double timer=0;
  private int current=0;
  private static final Message[] messages={
    new Message(0.1,"Welcome to TempoSim"),
    new Message(0.1,"(Brought to you by LugoCorp)"),
    new Message(0.5,"Activating calibration protocol"),
    new Message(0.3,"Calibrating for clarinet..."),
    new Message(0.3,"Calibrating for harp..."),
    new Message(0.3,"Calibrating for bass..."),
    new Message(0.2,"Calibration complete"),
    new Message(0.2,"Connecting to server..."),
    new Message(0.4,"Connection established"),
    new Message(0.1,"Now playing in 3..."),
    new Message(1.0,"2..."),
    new Message(1.0,"1..."),
  };
  static double getDuration(){
    double total=1.0;
    for(int a=0;a<messages.length;a++) total+=messages[a].timer;
    return total;
  }

  @Override
  public boolean update(double dt){
    if(current==messages.length) return true;
    timer+=dt;
    if(timer>=messages[current].timer){
      timer-=messages[current].timer;
      current++;
    }
    return true;
  }

  @Override
  public void render(Graphics g){
    g.setColor(bg);
    g.fillRect(0,0,Sim.WIDTH,Sim.HEIGHT);
    g.setFont(font);
    g.setColor(text);
    int pos=LINE_HEIGHT;
    int start=(current<MAX)?0:current-MAX;
    for(int a=start;a<current;a++){
      g.drawString(messages[a].text,10,pos);
      pos+=LINE_HEIGHT;
    }
  }

  private static class Message{
    private final double timer;
    private final String text;
    private Message(double timer,String text){
      this.timer=timer;
      this.text=text;
    }
  }
}
