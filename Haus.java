import GLOOP.*;
public class Haus {
    int x;
    int y;
    int z;
    int lx;
    int ly;
    int lz;
    GLQuader Haus;
    GLQuader Dach;
    Hitbox HitBox;
    public Haus(int px, int py, int pz, int plx, int ply, int plz,int rotationy){
    x= px;
    y= py;
    z= pz;
    lx = plx;
    ly = ply;
    lz = plz;
    Haus = new GLQuader(x, y, z, lx, ly, lz,"brickside.jpg");
    Dach= new GLQuader(x, y+ ly/2, z, lx/2, lx/2, lz-1,"metallDach.jpg");
    Dach.setzeDrehung(0,0,45);
    Haus.setzeDrehung(0, rotationy,0);
    Dach.drehe(0, rotationy, 0,Haus.gibPosition());
    if (rotationy == 0 ){HitBox = new Hitbox(Haus.gibPosition(), plx, ply, plz, true);}
    if (rotationy == 90){HitBox = new Hitbox(Haus.gibPosition(), plz, ply, plx, true);}
   }
   public Hitbox gibHitBox()
    {return  HitBox;}

}
