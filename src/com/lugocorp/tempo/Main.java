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
import javax.swing.JPanel;
import javax.swing.JFrame;

class Main extends JFrame{
  private static Main instance;
  static final long serialVersionUID=103;
  private static final int WAIT_FRAMES=32;
  private static final double DT=WAIT_FRAMES*0.001;
  private final List<Updatable> actors=new LinkedList<>();
  private final Timer updater;
  private final JPanel panel;
  private Sim sim;

  public static void main(String[] args){
    instance=new Main();
    instance.sim=new Sim(instance);
    instance.start();
  }
  static Main getInstance(){
    return instance;
  }
  private Main(){
    super("TempoSim");
    setResizable(false);
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
    panel.setPreferredSize(new Dimension(Sim.WIDTH,Sim.HEIGHT));
    setLocationRelativeTo(null);
    add(panel);
    pack();

    updater=new Timer(WAIT_FRAMES,(ActionEvent e) -> frame(DT));
    updater.setRepeats(true);
    setVisible(true);
  }
  private void start(){
    // Set 1
    actors.add(new Console());
    //actors.add(new GradientBg(Color.BLUE));
    //actors.add(new TunnelOfOz());
    //actors.add(new BubbleSpawner(new Color(200,0,200),250,150,50));
    //actors.add(new FourierBubbles());
    //actors.add(new FourierWave());

    // Set 2
    //actors.add(new TwoGradientBg(Color.BLUE));
    //actors.add(new LavaLamp(new Color(200,0,200)));
    //actors.add(new BezierPaths());
    //actors.add(new DotRain());
    updater.start();
  }
  void add(Updatable u){
    actors.add(u);
  }
  void clear(){
    actors.clear();
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
