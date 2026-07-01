 import GLOOP.*;
 public class Baum {
   // ein Baum der eine hitbox im Stamm hat und deko ist
    int x;
    int y;
    int z;
    double size;
    GLZylinder Stamm;
    GLKugel Krone;
    Hitbox HitBox;
    int hohe = 70;
    int breite =3;
    public Baum(int px, int py, int pz, double psize){
    hohe = (int) (hohe*psize);
    x= px;
    y= py;
    z= pz;
    size = psize;
    Stamm = new GLZylinder(x,y+hohe/2-5,z,breite,hohe);
    Stamm.drehe(90, 0, 0);
    Stamm.setzeFarbe(0.5,0.3,0);
    Krone = new GLKugel(x,y+hohe,z, psize*40);
    Krone.setzeFarbe(0.2 ,0.8 ,0.2);
    HitBox = new Hitbox(Stamm.gibPosition(), breite, hohe, breite, true);
    
   }
  
   public Hitbox gibHitBox()
    {return  HitBox;}

}

