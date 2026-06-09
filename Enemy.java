import GLOOP.*;
public class Enemy {
    int Projektil = 5;
    GLObjekt  ProjektileObj[] = new GLObjekt[Projektil];
    GLVektor ProjektileVekt[] = new GLVektor[Projektil];
    int ProjektilZeit[] = new int[Projektil];
    int ProjektilGeschindigkeit = -20;
    int SpielerSchaden = -5;
    GLZylinder sicht;
    int sichtWeiteVorne = 200;
    int sichtWeiteoffset = 40;
    int projektilexistenzZeit = 100;// in 10 ms also 100 = 1 Sekunde
    int health = 5;
    double xAlt;
    double zAlt;
    GLQuader Body;
    GLQuader Head;
    GLKugel SpielerSichtAnzeige;
    GLKugel SpielerSichtAnzeigeRamen;
    
    int ZielX = 100;
    int ZielZ= 0;
    double Geschwindigkeit = 0.5;
    Player Player;
    int  shootZaehler = 0;
    boolean stationaer = true;

    double SpielerSicht = 0.1;       // 0.0 -0.2 nichts    0.3-0.5 verdacht     0.6-0.9 angreifen     s
    double LetzteSpielersicht= 0.1;
    boolean FeindeBool[];

    int maxx = 500;
    int minx = -500;
    int maxz = 500;
    int minz = -500;

    int feindnummer;
    //double winkel=0;

 ///////////////////////  RAYCAST

    int rays = 100;
    GLVektor SpielerRichtung;
    GLObjekt[] Rayteile = new GLObjekt[rays]; 
    boolean siehtSpieler = false;
    Hitbox HitBox;
    Hitbox HitBoxen[];

 /////////////////
 
    int Feinde;
    Global_Variables Global;
    public Enemy(int nummer,Global_Variables Global2,Player Player2,boolean stationaer2){
        feindnummer= nummer;
        stationaer = stationaer2;
        if( stationaer){ Geschwindigkeit = 0.0;}
        Player = Player2;
        Global = Global2;
        Feinde = Global.getFeinde();
        Body = new GLQuader( ranNummer(-500,500) , 0,ranNummer(-500, 500),5, 25, 5);
        Body.setzeFarbe(1,0,0);

        Head = new GLQuader(  Body.gibX()+2 , 10, Body.gibZ(),2, 2, 4);
        SpielerSichtAnzeige = new GLKugel(Body.gibX(),Body.gibY()+20,Body.gibZ(),5);
        SpielerSichtAnzeigeRamen = new GLKugel(Body.gibX(),Body.gibY()+20,Body.gibZ(),-5.5);
        SpielerSichtAnzeigeRamen.setzeSelbstleuchten(1, 1, 1);
       
        sicht = new GLZylinder(Body.gibX()+sichtWeiteVorne-sichtWeiteoffset,0,Body.gibZ(), sichtWeiteVorne,1);
        sicht.setzeDrehung(90,0,0);
        sicht.setzeSelbstleuchten(1,1,0);

        drehe(0, 180, 0, Body.gibPosition());
        HitBox = new Hitbox(Body.gibPosition(),7, 28, 7, true);

        SpielerRichtung = new GLVektor(Body.gibX(),Body.gibY(),Body.gibZ(),    Player.gibX(),Player.gibY(),Player.gibZ());
        for(int i = 0; i<rays ;i++)
        {  
            
            SpielerRichtung.skaliereAuf(-2);
            GLVektor pos = new GLVektor(Body.gibPosition().x,Body.gibPosition().y+5,Body.gibPosition().z);

            for (int b =0;b<i;b++)
            {
                pos.addiere(SpielerRichtung);
            }
            Rayteile[i] = new GLKugel( pos , 1);
        }
    }

    public void Update()
    {
        siehtSpieler = false;
        raycast();
        shootZaehler--;
        rotiere();
        geherand(minx, maxx, minz, maxz); 
      
        Shoot();
        moveProjektils();
        nachSpielerSchauen();
        HitBox.setzePosition(Body.gibPosition());
    }

    public void raycast(){
        SpielerRichtung = new GLVektor(Body.gibX(),Body.gibY(),Body.gibZ(),    Player.gibX(),Player.gibY(),Player.gibZ());
        SpielerRichtung.skaliereAuf(-4);
       
        boolean beruehrt = false;
        for(int i = 0; i<rays ;i++)    // alle punkte durchgehen
        {  
            
            GLVektor pos = new GLVektor(Body.gibPosition().x,Body.gibPosition().y +5,Body.gibPosition().z);

            for (int b =0;b<i;b++)  // die snzahl der schritte addieren
            {
                pos.addiere(SpielerRichtung);
            }
           
            Rayteile[i].setzePosition(pos);

            if (beruehrt ==false)
            {
                boolean beruehrt2 = false;
                for (int b = 1;b <10;b++){
                    if (HitBoxen[b]!= null){
                        if (HitBoxen[b].beruehrt(Rayteile[i].gibPosition())){
                            beruehrt2= true;
                            //System.out.println("beruhert");
                            beruehrt = true;
                        }
                        //System.out.println("beruhrt nicht");

                    }
                
                }
                if (beruehrt2){
                    Rayteile[i].setzeFarbe(1, 0, 0);
                    Rayteile[i].setzeSkalierung(2);
                    
                    if (i < (rays-1)){Rayteile[i+1].setzeFarbe(1, 1, 0);
                        Rayteile[i+1].setzeSkalierung(0.5);
                    }


                    return;
                }else{
                    Rayteile[i].setzeFarbe(0, 1, 0);
                    Rayteile[i].setzeSkalierung(0.5);
                    if (HitBoxen[0].beruehrt(pos))
                    {
                        siehtSpieler = true;
                       
                        return;
                    }
                }
            }else{Rayteile[i].setzeFarbe(1, 1, 0);
                Rayteile[i].setzeSkalierung(0.5);
            }
        
        }



    }

    public void schaden()
    {health--;}

    public int  Tot()
    {
        
        if (health<1){ 
            Body.loesche();
            Head.loesche();
            sicht.loesche();
            FeindeBool[feindnummer]= false;
            

           

            
            for (int i=0; i<Projektil;i++)
            {
                if(ProjektileObj[i] != null){
                    ProjektileObj[i].loesche();
                    ProjektileObj[i] = null;
                    ProjektileVekt[i] = null;
                }
            }
            return 1; 
        }
       
        return 0;
    }

    public int ranNummer(int min, int max)
    {
       int x = (int)(min+(Math.random()*(max-min)));
       return x;
    }

    public double  gibX()
        {return Body.gibX();}
    public double  gibZ()
        {return Body.gibZ();}
    public void geheNach(int x, int z)
    {
       if (health>1) {
        xAlt = Body.gibX();
        zAlt = Body.gibZ();

            GLVektor Richtung =  new GLVektor(x, 0, z, Body.gibX(), 0, Body.gibZ());
            for (int i =0; HitBoxen[i]!= null;i++){
                if (HitBoxen[i].beruehrt(Body.gibPosition())){
                    ZielX = randNum( minx,maxx);
                    ZielZ = randNum( minz,maxz);
                }
            }
            Richtung.skaliereAuf(Geschwindigkeit);
            Body.verschiebe(Richtung);
            Head.verschiebe(Richtung);
            sicht.verschiebe(Richtung); 
            SpielerSichtAnzeige.verschiebe(Richtung);
            SpielerSichtAnzeigeRamen.verschiebe(Richtung);
        }
    }
    public void geherand(int minX, int maxX, int minZ, int maxZ)
    {
        if ( Math.abs(Body.gibX() -  ZielX) >10 ||Math.abs(Body.gibZ() -  ZielZ) >10){ 
            for (int i =0; HitBoxen[i]!= null;i++){
                if (HitBoxen[i].beruehrt(Body.gibPosition())){
                    ZielX = randNum( minX,maxX);
                    ZielZ = randNum( minZ,maxZ);
                }
            }
            geheNach(ZielX, ZielZ);}
        else
        { //System.out.println("Neues Ziel");

            ZielX = randNum( minX,maxX);
            ZielZ = randNum( minZ,maxZ);
        }
        
    }
    public int  randNum(int min, int max)
    {

        int dn = max - min;
        int x = (int) ((Math.random() * dn) - max);
        // System.out.println("rand "+ x);
        return x;
    }
    public void Shoot()
    {
        if (Math.abs(sicht.gibX()-Player.gibX())<sichtWeiteVorne && Math.abs(sicht.gibZ()-Player.gibZ())<sichtWeiteVorne) {
            if (!siehtSpieler){return;}
            if (SpielerSicht>0.6){
                if (shootZaehler> 0){return;} // cooldown abgelaufen
                
                for (int i=0; i<Projektil;i++)
                {
                    if (ProjektileObj[i] == null) // wenn false also frei, dann neues Objekt einfügen
                    {
                        shootZaehler = 20;
                        ProjektilZeit[i] = projektilexistenzZeit; 
                        ProjektileObj[i] = new GLKugel(Body.gibX(),8,Body.gibZ(), 2);
                        ProjektileObj[i].setzeFarbe(1,0,1);
                        GLVektor vektor =  new GLVektor(Body.gibX(), 0, Body.gibZ(), Player.gibX()+randNum(0,5), 0, Player.gibZ()+randNum(0,5));
                        vektor.skaliereAuf(ProjektilGeschindigkeit);
                        ProjektileVekt[i] = vektor;
                        i = 20;
                        //System.out.println("created");
                    }
                }
            }
        }
    }
    public void moveProjektils()
    {
       for (int i=0; i<Projektil;i++)
        {
            
            if (ProjektileObj[i] != null && ProjektileVekt[i] != null) // wenn true also besetzt
            {
                ProjektilZeit[i]--;
                ProjektileObj[i].verschiebe(ProjektileVekt[i]);
                //System.out.println("Move");
                if (Math.abs(ProjektileObj[i].gibX()-Player.gibX())<10&&Math.abs(ProjektileObj[i].gibZ()-Player.gibZ())<10){
                    ProjektilZeit[i]= 0;
                    Player.schaden(SpielerSchaden);
                }
                if ( ProjektilZeit[i]<=0) 
                    {
                    ProjektileObj[i].loesche();
                    ProjektileObj[i] = null;
                    ProjektileVekt[i] = null;
                    }
              
            }
        }
    }
    public void rotiere()
    {
        //drehe(0, -winkel, 0, Body.gibPosition());
       // GLVektor Richtung = new GLVektor( xAlt, 0, zAlt, Body.gibX(), 0,  Body.gibZ());
        //winkel = Math.toDegrees(Math.atan2((zAlt-Body.gibZ()),(xAlt-Body.gibX())));
        
        double winkelneu = Math.toDegrees(Math.atan2((ZielZ-Body.gibZ()),(ZielX-Body.gibX())));
        double winkel = Math.toDegrees(Math.atan2((Head.gibZ()-Body.gibZ()),(Head.gibX()-Body.gibX())));
        //System.out.println("winkel  "+winkel);
         //System.out.println("winkelneu  "+winkelneu);
        double differenz =winkelneu- winkel+180;; 
        if (differenz > 180) differenz -= 360;
        if (differenz < -180) differenz += 360;
        if (differenz> 4){
            drehe(0, 2, 0, Body.gibPosition());
           
        }
        if (differenz< -4){
            drehe(0, -2, 0, Body.gibPosition());
            
        } 
    }
    public void drehe(double x, double y, double z, GLVektor rotationPunkt)
    {
        Body.drehe(x, y, z);
        Head.drehe(x, y, z, rotationPunkt);
        sicht.drehe(x, y, z, rotationPunkt);

    }
    public void nachSpielerSchauen()
    {
        if (Math.abs(sicht.gibX()-Player.gibX())<sichtWeiteVorne && Math.abs(sicht.gibZ()-Player.gibZ())<sichtWeiteVorne && siehtSpieler) { // im bereich und sichtkontakt
            ZielX = (int) Player.gibX();
            ZielZ = (int) Player.gibZ();
            double entfX = Math.abs(Player.gibX()- gibX());
            double entfY = Math.abs(Player.gibZ()-gibZ());
            double entf =  Math.sqrt((entfX*entfX)+(entfY*entfY))*0.001;
            if (entf<1){                                  /// abhängig von der entfernung die sicht variable erhöhen
                double anderung = (1-entf)*0.004;
                SpielerSicht += anderung;
            }  
        }else{SpielerSicht =  SpielerSicht- 0.001;}      // wenn außer reichweite variable verkleinern
        
       
        if (SpielerSicht <0.1){                       //// auf werte begrenzen
            SpielerSicht= 0.1;
        }
        if (SpielerSicht>1){
            SpielerSicht= 1;
        } 
        
        if (SpielerSicht>0.6){ SpielerSichtAnzeige.setzeFarbe(0.5,0,0);
            SpielerSichtAnzeige.setzeSelbstleuchten(0.5,0,0);}
        if (SpielerSicht<0.6){ SpielerSichtAnzeige.setzeFarbe(0,0.5,0);
            SpielerSichtAnzeige.setzeSelbstleuchten(0,0.5,0);}
        SpielerSichtAnzeige.setzeSkalierung(SpielerSicht );
        LetzteSpielersicht = SpielerSicht;

    }

    public void ubergeben(Hitbox pHitBoxen[]){
         HitBoxen = pHitBoxen;
         // fals in einem objekt spawneda
         for (int i =0; HitBoxen[i]!= null;i++){
                if (HitBoxen[i].beruehrt(Body.gibPosition())){
                   double Geschwindigkeit2 = Geschwindigkeit;
                   Geschwindigkeit = 500;
                   geheNach(randNum( -500,500), randNum( -500,500));
                   Geschwindigkeit = Geschwindigkeit2;
                }
            }
    }

    public void ubergeben( boolean FeindeBool2[]){
     FeindeBool =  FeindeBool2;
    }

}
