package com.lugocorp.tempo;
import java.awt.color.ColorSpace;
import java.awt.Transparency;
import java.awt.Color;

class MutableColor extends Color{
  static final long serialVersionUID=107;
  private static final ColorSpace colorspace=ColorSpace.getInstance(ColorSpace.CS_sRGB);
  private boolean dirty=true;
  private int r,g,b,a,rgba;

  MutableColor(Color c){
    this(c.getRed(),c.getGreen(),c.getBlue(),c.getAlpha());
  }
  MutableColor(int r,int g,int b){
    this(r,g,b,255);
  }
  MutableColor(){
    this(0,0,0,255);
  }
  MutableColor(int r,int g,int b,int a){
    super(r,g,b,a);
    this.r=r;
    this.g=g;
    this.b=b;
    this.a=a;
  }

  void setRed(int v){
    dirty=true;
    r=v;
  }
  void setGreen(int v){
    dirty=true;
    g=v;
  }
  void setBlue(int v){
    dirty=true;
    b=v;
  }
  void setAlpha(int v){
    dirty=true;
    a=v;
  }
  private void setRGB(){
    rgba=((a & 0xFF) << 24) |
    ((r & 0xFF) << 16) |
    ((g & 0xFF) << 8) |
    (b & 0xFF);
  }
  @Override
  public int getRGB(){
    if(dirty){
      dirty=false;
      setRGB();
    }
    return rgba;
  }

  @Override
  public boolean equals(Object obj){
    return obj instanceof Color && ((Color)obj).getRGB() == this.getRGB();
  }

  @Override
  public float[] getColorComponents(ColorSpace cs, float[] compArray){
    if (cs == null) {
      cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
    }
    float[] f=new float[3];
    f[0] = ((float)getRed())/255f;
    f[1] = ((float)getGreen())/255f;
    f[2] = ((float)getBlue())/255f;
    float tmp[] = cs.toCIEXYZ(f);
    float tmpout[] = cs.fromCIEXYZ(tmp);
    if (compArray == null) {
      return tmpout;
    }
    for (int i = 0 ; i < tmpout.length ; i++) {
      compArray[i] = tmpout[i];
    }
    return compArray;
  }

  @Override
  public float[] getColorComponents(float[] compArray){
    return getRGBColorComponents(compArray);
  }

  @Override
  public ColorSpace getColorSpace(){ return colorspace; }

  @Override
  public float[] getComponents(ColorSpace cs, float[] compArray){
    if (cs == null) {
      cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
    }
    float[] f=new float[4];
    f[0] = ((float)getRed())/255f;
    f[1] = ((float)getGreen())/255f;
    f[2] = ((float)getBlue())/255f;
    f[3] = ((float)getAlpha())/255f;
    float tmp[] = cs.toCIEXYZ(f);
    float tmpout[] = cs.fromCIEXYZ(tmp);
    if (compArray == null) {
      return tmpout;
    }
    for (int i = 0 ; i < tmpout.length ; i++) {
      compArray[i] = tmpout[i];
    }
    return compArray;
  }

  @Override
  public float[] getComponents(float[] compArray){
    return getRGBComponents(compArray);
  }

  @Override
  public float[] getRGBColorComponents(float[] compArray){
    float[] f;
    if (compArray == null) {
      f = new float[3];
    } else {
      f = compArray;
    }
    f[0] = ((float)getRed())/255f;
    f[1] = ((float)getGreen())/255f;
    f[2] = ((float)getBlue())/255f;
    return f;
  }

  @Override
  public float[] getRGBComponents(float[] compArray){
    float[] f;
    if (compArray == null) {
      f = new float[4];
    } else {
      f = compArray;
    }
    f[0] = ((float)getRed())/255f;
    f[1] = ((float)getGreen())/255f;
    f[2] = ((float)getBlue())/255f;
    f[3] = ((float)getAlpha())/255f;
    return f;
  }

  @Override
  public int hashCode(){
    return ((a & 0xFF) << 24) |
      ((r & 0xFF) << 16) |
      ((g & 0xFF) << 8) |
      (b & 0xFF);
  }
  @Override
  public String toString(){
    return String.format("MutableColor(%s,%s,%s,%s)",r,g,b,a);
  }
}
