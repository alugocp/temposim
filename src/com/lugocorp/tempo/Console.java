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
    new Message(0.10,"Welcome to Alex Lugo's TempoSim"),
    new Message(0.30,"Activating calibration protocol"),
    new Message(0.20,"Calibrating for instrument 1..."),
    new Message(0.20,"Calibrating for instrument 2..."),
    new Message(0.20,"Calibrating for instrument 3..."),
    new Message(0.10,"Calibration complete"),
    new Message(0.40,"Connecting to server..."),
    new Message(0.10,"Connection established"),
    new Message(0.01,"Downloading Fortnite client..."),
    new Message(0.01,"Preparing to bust..."),
    new Message(0.01,"Generating Minecraft seed"),
    new Message(0.01,"Chunk 1 generated"),
    new Message(0.01,"Chunk 2 generated"),
    new Message(0.01,"Chunk 3 generated"),
    new Message(0.01,"Chunk 4 generated"),
    new Message(0.01,"Chunk 5 generated"),
    new Message(0.10,"Full context generated"),
    new Message(0.01,"Downloading more RAM..."),
    new Message(0.01,"Launching Gurpreet virus..."),
    new Message(0.01,"Loading simulation details..."),
    new Message(0.10,"Loading BezierPaths"),
    new Message(0.10,"Loading BubbleSpawner"),
    new Message(0.10,"Loading DotRain"),
    new Message(0.10,"Loading FourierBubbles"),
    new Message(0.10,"Loading FourierWave"),
    new Message(0.10,"Loading GradientBg"),
    new Message(0.10,"Loading LavaLamp"),
    new Message(0.10,"Loading Rectangles"),
    new Message(0.10,"Loading Squares"),
    new Message(0.10,"Loading TunnelOfOz"),
    new Message(0.10,"Loading TwoGradientBg"),
    new Message(0.30,"Simulation build complete"),
    new Message(0.10,"Ready to start simulation"),
    new Message(0.01,""),
    new Message(0.10,"Press any key when ready"),
  };

  Console(int start){
    if(start==-1) current=messages.length;
    else current=start;
  }

  @Override
  public boolean update(double dt){
    boolean keypress=Main.getInstance().consumeKeypress();
    if(current==messages.length) return !keypress;
    timer+=dt;
    if(timer>=messages[current].timer){
      timer-=messages[current].timer;
      current++;
    }
    return !keypress;
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
