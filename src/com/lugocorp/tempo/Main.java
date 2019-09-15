package com.lugocorp.tempo;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.util.LinkedList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.JFrame;

class Main extends JFrame implements KeyListener{
  private static Main instance;
  static final long serialVersionUID=103;
  private static final int WAIT_FRAMES=32;
  private static final double DT=WAIT_FRAMES*0.001;
  private final List<Updatable> actors=new LinkedList<>();
  private boolean keypress=false;
  private final Timer updater;
  private final JPanel panel;
  private Sim sim;

  public static void main(String[] args){
    int type=Sim.DREAM;
    if(args.length!=1){
      System.out.println("Error: incorrect number of parameters");
      System.exit(1);
    }
    if(args[0].equals("swing")) type=Sim.SWING;
    else if(!args[0].equals("dream")){
      System.out.println(String.format("Unknown show '%s'",args[0]));
      System.exit(1);
    }
    instance=new Main();
    instance.sim=new Sim(instance,type);
    instance.start(type);
  }
  static Main getInstance(){
    return instance;
  }
  private Main(){
    super("TempoSim");
    setExtendedState(JFrame.MAXIMIZED_BOTH);
    setUndecorated(true);
    setVisible(true);
    setVisible(true);
    addWindowListener(new WindowAdapter(){
      public void windowClosing(WindowEvent e){
        onClose();
      }
    });
    panel=new JPanel(){
      static final long serialVersionUID=101;
      @Override
      public void paintComponent(Graphics g){
        render(g);
      }
    };
    try{
      Thread.sleep(100);
    }catch(InterruptedException e){}
    Sim.HEIGHT=getHeight();
    Sim.WIDTH=getWidth();
    panel.setPreferredSize(new Dimension(Sim.WIDTH,Sim.HEIGHT));
    setLocationRelativeTo(null);
    addKeyListener(this);
    add(panel);
    pack();
    updater=new Timer(WAIT_FRAMES,(ActionEvent e) -> frame(DT));
    updater.setRepeats(true);
    setVisible(true);
  }
  private void start(int type){
    actors.add(new Console(type==Sim.SWING?0:-1));
    updater.start();
  }
  void add(Updatable u){
    actors.add(u);
  }
  void clear(){
    keypress=false;
    actors.clear();
  }
  boolean cleared(){
    return actors.size()==0;
  }

  @Override
  public void keyPressed(KeyEvent e){}
  @Override
  public void keyTyped(KeyEvent e){}
  @Override
  public void keyReleased(KeyEvent e){
    keypress=true;
  }
  boolean consumeKeypress(){
    if(keypress){
      keypress=false;
      return true;
    }
    return false;
  }

  private void render(Graphics g){
    for(int a=0;a<actors.size();a++) actors.get(a).render(g);
  }
  private void frame(double dt){
    sim.schedule(dt);
    for(int a=0;a<actors.size();a++){
      if(!actors.get(a).update(dt)) actors.remove(a--);
    }
    repaint();
    Toolkit.getDefaultToolkit().sync();
  }
  private void onClose(){
    System.out.println("Visualization complete");
    updater.stop();
    dispose();
    System.exit(0);
  }
}
