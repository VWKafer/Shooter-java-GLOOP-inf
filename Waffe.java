

import GLOOP.*;
public class Waffe {
    int Projektil = 20 ;
    GLObjekt  ProjektileObj[]  = new GLObjekt[Projektil];
    int ProjektilZeit[] = new int[Projektil];
    GLVektor ProjektileVekt[] = new GLVektor[Projektil];
    int ProjektilGeschindigkeit = 15;
    Enemy Enemys[];
    
    boolean FeindeBool[];
    
    
   
    int MagazinGro2 = 6;
    int kugeln2 = 6;
    int schussGeschwindigkeit2= 20;
    int nachladeZeit2= 400;
    int schussZeit;
    
    boolean zielt= false;
    int zielZeit= 50;
    int ZeitZumZielen;
    boolean nachladen = false;
    int waffe = 1;
    Hitbox[] HitBoxen;
    //Stein
    double ybewegung = 0;
    double gravitation = -0.01;
    GLKugel Stein;
    boolean wirft = false;
    GLVektor vektStein;
    int nachladeZeit3= 600;
    int schussGeschwindigkeit3= 100;
    int kugeln3 = 10;
    int gerauschRadius =100;
    int MagazinGro3 = 10;

    //gewehr
    int MagazinGro1 = 10;
    int kugeln1 = 10;
    int schussGeschwindigkeit1= 30;
    int nachladeZeit1= 200;

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

    Global_Variables Global;
    int Feinde;
    public  Waffe(Enemy Enemys2[],Global_Variables Global2,int Feinde2,boolean FeindeBool2[])
    {
        FeindeBool = FeindeBool2;
        Global =Global2;
        Feinde = Feinde2;
        Enemys = Enemys2;
        // stein
        Stein = new GLKugel(3, 8, 493.5, 0.5);
        Stein.setzeSichtbarkeit(false);

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
        vektor.skaliereAuf(ProjektilGeschindigkeit);
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
                
                ProjektileVekt[i] = vektor;
                i = 20; 
                kugeln2--;
                schussZeit=schussGeschwindigkeit2;
                break;
               
                //System.out.println("created");
            }
        }}
        if (waffe ==3){
            if (!wirft&&kugeln3<1){schussZeit= nachladeZeit3; kugeln3 = MagazinGro3;}
            if ( Stein != null&& schussZeit<0&& kugeln3>0&& !wirft) // wenn null also frei, dann neues Objekt einfügen
            { 
                ybewegung = 0.8;
                vektor.skaliereAuf(2);
                vektStein = vektor;
                wirft =true;
                kugeln3--;
                
            }

        }
        
    }
    public void zielen(){
       
        if(ZeitZumZielen<0){
            
        if(zielt == true){
            zielt = false;
            if (waffe ==1){
            GLVektor zurück =new GLVektor(-Zielbewegung1.x, -Zielbewegung1.y, -Zielbewegung1.z) ;
            bewege(zurück);
           }
           if (waffe ==2){
            GLVektor zurück =new GLVektor(-Zielbewegung2.x, -Zielbewegung2.y, -Zielbewegung2.z) ;
            bewege(zurück);
           }
            ZeitZumZielen= zielZeit;
        }else{
            zielt = true;
            if (waffe == 1){
            bewege(Zielbewegung1);}
            if (waffe == 2){
            bewege(Zielbewegung2);}
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
        if (waffe ==1)
            {
            PlayerUI.setMunition(kugeln1,MagazinGro1);
           
            
            for ( int i = 0;i <10;i++ ){
                if(Waffe1Teile[i] == null) break;
                Waffe1Teile[i].setzeSichtbarkeit(true);;
            }
      
            for ( int i = 0;i <10;i++ ){
                if(Waffe2Teile[i] == null) break;
                Waffe2Teile[i].setzeSichtbarkeit(false);
            }
            Stein.setzeSichtbarkeit(false);
        }

        if (waffe ==2){
        PlayerUI.setMunition(kugeln2,MagazinGro2);

           for ( int i = 0;i <10;i++ ){
                if(Waffe1Teile[i] == null) break;
                Waffe1Teile[i].setzeSichtbarkeit(false);;
            }
      
            for ( int i = 0;i <10;i++ ){
                if(Waffe2Teile[i] == null) break;
                Waffe2Teile[i].setzeSichtbarkeit(true);
            }
            Stein.setzeSichtbarkeit(false);
        }
        if (waffe ==3)
            {
            PlayerUI.setMunition(kugeln3,MagazinGro3);
           
            
            for ( int i = 0;i <10;i++ ){
                if(Waffe1Teile[i] == null) break;
                Waffe1Teile[i].setzeSichtbarkeit(false);;
            }
      
            for ( int i = 0;i <10;i++ ){
                if(Waffe2Teile[i] == null) break;
                Waffe2Teile[i].setzeSichtbarkeit(false);
            }
            Stein.setzeSichtbarkeit(true);
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
                             {FeindeBool[i]= false;
                                Global.changeVariable(-1);
                             }
                            
                            ProjektileObj[n].loesche();
                            n = Projektil+10000;
                            i = Feinde +10000;
                            
                            
                        }
                    }
                }
            }/*else{
                if (Feinde> i+1&& Enemys[i+1]!= null){
                if (FeindeBool[i+1] == true){
                    Enemys[i]= Enemys[i+1];
                    FeindeBool[i]= true;
                    FeindeBool[i+1] = false;
                    
                    
                }
                
                }
            }*/
        }
        
    } 
    public void moveProjektils()
    {
       for (int i=0; i< Projektil ;i++)
        {
            
            if ( ProjektileObj[i] != null) // wenn besetzt
            {
                ProjektilZeit[i]--;
                ProjektileObj[i].verschiebe(ProjektileVekt[i]);
                //System.out.println("Move");
               
                    //System.out.println("Move1");
                for (int b =0; b<90; b++){
                    if (HitBoxen[b]!= null){
                        //System.out.println("Move2");
                        if (HitBoxen[b].beruehrt(ProjektileObj[i].gibPosition())){
                            System.out.println("Move3");
                   
                            ProjektilZeit[i] = 5;
                
                            GLVektor vekt = ProjektileVekt[i];
                            vekt.multipliziere(-1);
                            ProjektileVekt[i]= vekt;
                        }
                    }
                }
            
                
                if ( ProjektilZeit[i] <=0) 
                {
                    ProjektileObj[i].loesche();
                    ProjektileObj[i] = null;
                    ProjektilZeit[i] = 0;
                    ProjektileVekt[i] = null;
                }
            }
        }
        if (wirft){
            Stein.verschiebe(vektStein);
            ybewegung = ybewegung+ gravitation;
            Stein.verschiebe(0,ybewegung,0);
            if (Stein.gibY()<-1){
                int x= (int)Stein.gibX();
                int z= (int)Stein.gibZ();
                for(int i= 0;i<Feinde;i++){
                    if (Math.abs(x-Enemys[i].gibX())<200&&Math.abs(z-Enemys[i].gibZ())<200){
                        Enemys[i].ZielX = x;
                        Enemys[i].ZielZ = z;
                    }
                }
                Stein.loesche();
                wirft = false;
                ybewegung= 0;
                Stein = new GLKugel(schalldaempfer.gibPosition(), 0.5);
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

        if (!wirft){
            Stein.drehe(x, y, z, rotationPunkt);
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

        if (!wirft){
             GLVektor vekt = Stein.gibPosition();
            vekt.addiere(richtung);
            Stein.setzePosition( vekt);
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
public int WaffeNummer(){
    return waffe;}
}

