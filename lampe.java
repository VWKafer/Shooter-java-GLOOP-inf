import GLOOP.*;
public class lampe {
int x;
    int y;
    int z;
    double size;
    GLZylinder pfosten;
    GLZylinder quer;
    GLKugel lampe;
    GLLicht licht;
    Hitbox HitBox;
    int hohe = 30;
    int breite =1;
    Player Player;
  
    public lampe (int px, int py, int pz, double psize,boolean vorne, Player pPlayer){
        Player = pPlayer;
        hohe = (int) (hohe*psize);
        x= px;
        y= py;
        z= pz;
        size = psize;
        pfosten = new GLZylinder(x,y+hohe/2-5,z,breite,hohe);
        pfosten.drehe(90, 0, 0);
        pfosten.setzeFarbe(0.5,0.5,0.5);

        //if (vorne ==true){ 
        quer = new GLZylinder(x,y+hohe-5,z+10, breite,20);
        lampe = new GLKugel(x,y+hohe-10,z+15, psize*2);//}else{
        //lampe = new GLKugel(x,y+hohe-10,z-15, size*2);
       //quer = new GLZylinder(x,y+hohe-5,z-10, breite,20);
        //lampe = new GLKugel(x,y+hohe-10,z-15, psize*2);}

        quer.setzeFarbe(0.5 ,0.5 ,0.5);
        lampe.setzeFarbe(1 ,1 ,1);
        lampe.setzeSelbstleuchten(1,1,1);
        
        HitBox = new Hitbox(pfosten.gibPosition(), breite, hohe, breite, true);
   }
   public Hitbox gibHitBox()
    {return  HitBox;}
    public void Update(){
       int dist = (int) Math.sqrt((x-Player.gibX())*(x-Player.gibX())+(z-Player.gibZ())*(z-Player.gibZ()));
        System.out.println(dist);
       if (dist<100&& licht == null){
        
        licht = new GLLicht(lampe.gibPosition());
        licht.setzeAbschwaechung(0.05);
       }

       if (dist>100&& licht != null){
        
        licht.loesche();
        licht = null;
       }

    }
}
