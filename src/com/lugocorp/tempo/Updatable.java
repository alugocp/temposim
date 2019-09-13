package com.lugocorp.tempo;
import java.awt.Graphics;

public interface Updatable{
  public boolean update(double dt);
  public void render(Graphics g);
}
