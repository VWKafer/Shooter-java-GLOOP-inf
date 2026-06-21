 import GLOOP.*;
 public class Baum {
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
    Stamm = new GLZylinder(x,y+hohe/2,z,breite,hohe);
    Krone = new GLKugel(x,y+hohe,z, psize);
    HitBox = new Hitbox(Stamm.gibPosition(), breite, hohe, breite, true);
    
   }
   public Hitbox gibHitBox()
    {return  HitBox;}

}

