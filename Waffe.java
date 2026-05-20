import GLOOP.*;
public class Waffe {
    int Projektil = 20 ;
    GLObjekt  ProjektileObj[]  = new GLObjekt[Projektil];
    int ProjektilZeit[] = new int[Projektil];
    GLVektor ProjektileVekt[] = new GLVektor[Projektil];
    int ProjektilGeschindigkeit = 15;
    Enemy Enemys[];
    int Feinde;
    boolean FeindeBool[];
    int MagazinGro1 = 10;
    int kugeln1 = 10;
    int schussGeschwindigkeit1= 30;
    int MagazinGro2 = 6;
    int kugeln2 = 6;
    int schussGeschwindigkeit2= 20;
    int nachladeZeit2= 400;
    int schussZeit;
    int nachladeZeit1= 200;
    boolean zielt= false;
    int zielZeit= 50;
    int ZeitZumZielen;
    boolean nachladen = false;
    int waffe = 1;
    Hitbox[] HitBoxen;
    //gewehr
    GLObjekt Waffe1Teile[] = new GLObjekt[10];
    GLZylinder schaft;
    GLZylinder Lauf;
    GLQuader Magazin;
    GLQuader schulterstütze;
    GLZylinder schalldaempfer;
    GLQuader Kimme;
    GLQuader koerper;

    GLZylinder ArmL1;
    GLZylinder ArmR1;

    // pistole
    GLObjekt Waffe2Teile[] = new GLObjekt[10];
    GLQuader Griff;
    GLZylinder rohr;
    GLQuader ladeblock;

    GLZylinder ArmL2;
    GLZylinder ArmR2;

    
    Player Player;
    UI PlayerUI;
    Camera Camera;
    
    GLVektor Zielbewegung1;
    GLVektor Zielbewegung2;
    public  Waffe(Enemy Enemys2[],int Feinde2,boolean FeindeBool2[])
    {
        FeindeBool = FeindeBool2;
        Feinde = Feinde2;
        Enemys = Enemys2;
        //gewehr
        schalldaempfer = new GLZylinder(3, 8, 487, 0.2,2);
        schaft = new GLZylinder(3, 8, 492, 0.2, 4);
        Lauf = new GLZylinder(3, 8, 489, 0.15, 4);
        Kimme = new GLQuader(3, 8, 493.5, 0.4,0.4, 0.4);
        Magazin = new GLQuader(3, 7.5, 492, 0.2,1,1 );
        koerper = new GLQuader(3, 8, 492.5, 0.2,0.5,3 );
        schulterstütze = new GLQuader(3, 7.65, 494.8, 0.2,1,2 );
        ArmL1 = new GLZylinder(2.3, 7.3, 492, 0.25, 4);
        ArmL1.drehe(15, -20, 0);

        Waffe1Teile[0] = schalldaempfer;
        Waffe1Teile[1] = schaft;
        Waffe1Teile[2] = Lauf;
        Waffe1Teile[3] = Kimme;
        Waffe1Teile[4] = Magazin;
        Waffe1Teile[5] = koerper;
        Waffe1Teile[6] = schulterstütze;
        Waffe1Teile[7] = ArmL1;

        // pistole
        Griff = new GLQuader(3, 8, 495, 0.2, 1, 0.5);
        Griff.drehe(-15, 0, 0);
        Griff.setzeFarbe(0.3,0.1,0);

        ladeblock= new GLQuader(3, 8.5, 494.6, 0.2, 0.3, 1.5);
        ladeblock.setzeFarbe(0.1,0.1,0.1);

        rohr = new GLZylinder(3, 8.5, 494, 0.08, 2);
        rohr.setzeFarbe(0.1,0.1,0.1); 
        
        ArmL2 = new GLZylinder(2.3, 7.3, 496.5, 0.25, 4);
        ArmL2.drehe(15, -20, 0);

        ArmR2 = new GLZylinder(3.8, 7.3, 496.5, 0.25, 4);
        ArmR2.drehe(15, 20, 0);


        Griff.setzeSichtbarkeit(false);
        ladeblock.setzeSichtbarkeit(false);
        rohr.setzeSichtbarkeit(false); 
        ArmL2.setzeSichtbarkeit(false);
        ArmR2.setzeSichtbarkeit(false);

       
        
        Waffe2Teile[0] =  Griff;
        Waffe2Teile[1] = ladeblock;
        Waffe2Teile[2] = rohr;
        Waffe2Teile[3] = ArmL2;
        Waffe2Teile[4] = ArmR2;
        

        Zielbewegung1= new GLVektor(-3, 1.63,6);
        Zielbewegung2= new GLVektor(-3, 1.2,-2);


        //visirbefest = new ;     
    }
    public void Shoot(int x,int y, int z,GLVektor vektor)
    {
        if (waffe ==1){
        for (int i=0; i<Projektil;i++)
        {   
            if(kugeln1<1){schussZeit= nachladeZeit1;kugeln1= MagazinGro1;nachladen=true;}
            if(kugeln1<1||schussZeit>0){break;}
            if ( ProjektileObj[i] ==null) // wenn null also frei, dann neues Objekt einfügen
            {
                ProjektilZeit[i] = 200; /// 2 Sekunden
                
                ProjektileObj[i] = new GLKugel(schalldaempfer.gibX(),schalldaempfer.gibY(),schalldaempfer.gibZ(), 0.2);
                ProjektileObj[i].setzeFarbe(1,1,0);
                ProjektileObj[i].setzeSelbstleuchten(1, 1, 0);
                vektor.skaliereAuf(ProjektilGeschindigkeit);
                ProjektileVekt[i] = vektor;
                i = 20; 
                kugeln1--;
                schussZeit=schussGeschwindigkeit1;
                break;
               
                //System.out.println("created");
            }
        }}
        if (waffe ==2){
        for (int i=0; i<Projektil;i++)
        {   
            if(kugeln2<1){schussZeit= nachladeZeit2;kugeln2= MagazinGro2;nachladen=true;}
            if(kugeln2<1||schussZeit>0){break;}
            if ( ProjektileObj[i] ==null) // wenn null also frei, dann neues Objekt einfügen
            {
                ProjektilZeit[i] = 200; /// 2 Sekunden
                
                ProjektileObj[i] = new GLKugel(schalldaempfer.gibX(),schalldaempfer.gibY(),schalldaempfer.gibZ(), 0.2);
                ProjektileObj[i].setzeFarbe(1,1,0);
                ProjektileObj[i].setzeSelbstleuchten(1, 1, 0);
                vektor.skaliereAuf(ProjektilGeschindigkeit);
                ProjektileVekt[i] = vektor;
                i = 20; 
                kugeln2--;
                schussZeit=schussGeschwindigkeit2;
                break;
               
                //System.out.println("created");
            }
        }}
        
    }
    public void zielen(){
       
        if(ZeitZumZielen<0){
            
        if(zielt == true){
            zielt = false;
            if (waffe ==1){
            GLVektor zurück =new GLVektor(-Zielbewegung1.x, -Zielbewegung1.y, -Zielbewegung1.z) ;
            bewege(zurück);
           }else{
            GLVektor zurück =new GLVektor(-Zielbewegung2.x, -Zielbewegung2.y, -Zielbewegung2.z) ;
            bewege(zurück);
           }
            ZeitZumZielen= zielZeit;
        }else{
            zielt = true;
            if (waffe == 1){
            bewege(Zielbewegung1);}
            else 
                {bewege(Zielbewegung2);}
            ZeitZumZielen = zielZeit;
        }
        }

    }
    public void ubergeben(Player Player2 ,Camera Camera2, Hitbox[] HitBoxen2){
        Player = Player2;
        Camera =Camera2;
        HitBoxen = HitBoxen2;
     }
    public void ubergeben(UI PlayerUI2){
        PlayerUI = PlayerUI2;
     }
    public void Update()
    {
        if (waffe ==1){PlayerUI.setMunition(kugeln1,MagazinGro1);
           
            
        for ( int i = 0;i <10;i++ ){
            if(Waffe1Teile[i] == null) break;
            Waffe1Teile[i].setzeSichtbarkeit(true);;
        }
      
        for ( int i = 0;i <10;i++ ){
            if(Waffe2Teile[i] == null) break;
            Waffe2Teile[i].setzeSichtbarkeit(false);
        }

        } 
        if (waffe ==2){PlayerUI.setMunition(kugeln2,MagazinGro2);
           for ( int i = 0;i <10;i++ ){
            if(Waffe1Teile[i] == null) break;
            Waffe1Teile[i].setzeSichtbarkeit(false);;
        }
      
        for ( int i = 0;i <10;i++ ){
            if(Waffe2Teile[i] == null) break;
            Waffe2Teile[i].setzeSichtbarkeit(true);
        }
        }
        
    
        moveProjektils();
        schussZeit--;
        ZeitZumZielen--;
        for (int i=0;i< Feinde; i++){
            if (FeindeBool[i]== true){
                ////////// Enemy update
                Enemys[i].Update();
                
                ///////////
                for (int n=0; n<Projektil;n++){
                    if ( ProjektileObj[n] != null) // wenn true
                    {
                        int dx =  (int) Math.abs(ProjektileObj[n].gibX()- Enemys[i].gibX());
                        int dy =  (int) Math.abs(ProjektileObj[n].gibZ()- Enemys[i].gibZ());
                        if (dy < 15 && dx < 15){
                           
                            Enemys[i].schaden();
                            //System.out.println("Schaden an" + i + "mit Abstand: " + dx + " und " + dy);

                            if (Enemys[i].Tot()== 1) 
                             {FeindeBool[i]= false;}
                            
                            ProjektileObj[n].loesche();
                            n = Projektil+10000;
                            i = Feinde +10000;
                            
                        }
                    }
                }
            }
        }
    } 
    public void moveProjektils()
    {
       for (int i=0; i< Projektil ;i++)
        {
            
            if ( ProjektileObj[i] != null) // wenn true also besetzt
            {
                ProjektilZeit[i]--;
                ProjektileObj[i].verschiebe(ProjektileVekt[i]);
                //System.out.println("Move");
               
                    //System.out.println("Move1");
                for (int b =0; HitBoxen[b]!= null; b++){
                    //System.out.println("Move2");
                    if (HitBoxen[b].beruehrt(ProjektileObj[i].gibPosition())){
                    System.out.println("Move3");
                   
                    ProjektilZeit[i] = 5;
                
                   GLVektor vekt = ProjektileVekt[i];
                   vekt.multipliziere(-1);
                    ProjektileVekt[i]= vekt;
                    }
                }
            
                
                if ( ProjektilZeit[i] <=0) 
                {
                    ProjektileObj[i].loesche();
                    ProjektileObj[i] = null;
                    ProjektilZeit[i] = 0;
                    ProjektileVekt[i] = null;
                }
//Ich mag Raphael ganz dolle
            }
        }
    }

    public void drehe(double x, double y, double z, GLVektor rotationPunkt)
    {
        
        for ( int i = 0;i <10;i++ ){
            if(Waffe1Teile[i] == null) break;
            Waffe1Teile[i].drehe(x, y, z, rotationPunkt);
        }
      
        for ( int i = 0;i <10;i++ ){
            if(Waffe2Teile[i] == null) break;
            Waffe2Teile[i].drehe(x, y, z, rotationPunkt);
        }
        
        
        Zielbewegung1.drehe(x, y, z);
        Zielbewegung2.drehe(x, y, z);
       
    }

    public void bewege (GLVektor richtung)
    {

        
        for ( int i = 0;i <10;i++ ){
            if(Waffe1Teile[i] == null) break;
            GLVektor vekt=Waffe1Teile[i].gibPosition();
            vekt.addiere(richtung);
            Waffe1Teile[i].setzePosition( vekt);
        }
       
        for ( int i = 0;i <10;i++ ){
            if(Waffe2Teile[i] == null) break;
            GLVektor vekt=Waffe2Teile[i].gibPosition();
            vekt.addiere(richtung);
            Waffe2Teile[i].setzePosition( vekt);
            
        }
        
    }
    public void nachladen(){


    if (schussZeit>150)
        {
        
    }
}

public void wechsel(int num){
   
    if (zielt== true){
        if (waffe ==1){
        GLVektor richt = new GLVektor(Zielbewegung1);
        richt.multipliziere(-1);
        bewege (richt);
     }
     else if (waffe ==2){
        GLVektor richt = new GLVektor(Zielbewegung2);
        richt.multipliziere(-1);
        bewege (richt);
     }
    }
    if (waffe ==1){
        if (schussZeit>schussGeschwindigkeit1){
            schussZeit= 0;
            kugeln1 = 0;
            nachladen = false;
        }
   }
   if (waffe ==2){
        if (schussZeit>schussGeschwindigkeit2){

            schussZeit = 0;
            kugeln2 = 0;
            nachladen = false;
        }
   }
    zielt = false;
    waffe= num;
}
}

