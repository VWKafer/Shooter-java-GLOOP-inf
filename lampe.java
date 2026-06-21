import GLOOP.*;
public class lampe {
int x;
    int y;
    int z;
    double size;
    GLZylinder pfosten;
    GLZylinder quer;
    Hitbox HitBox;
    int hohe = 70;
    int breite =3;
    public lampe (int px, int py, int pz, double psize){
        hohe = (int) (hohe*psize);
        x= px;
        y= py;
        z= pz;
        size = psize;
        pfosten = new GLZylinder(x,y+hohe/2-5,z,breite,hohe);
        pfosten.drehe(90, 0, 0);
        pfosten.setzeFarbe(0.5,0.3,0);
        quer = new GLZylinder(x,y+hohe,z, 1,breite);
        quer.setzeFarbe(0.2 ,0.8 ,0.2);
        HitBox = new Hitbox(pfosten.gibPosition(), breite, hohe, breite, true);
    
   }
   public Hitbox gibHitBox()
    {return  HitBox;}

}
