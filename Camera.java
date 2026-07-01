import GLOOP.*;
import java.awt.*;
public class Camera {
    DllBridge DllTwoMouse;
    boolean crosshairbool = true;    // soll es ein crosshair geben?
    GLWuerfel crosshair;
    GLKamera KameraMain;// ein kamera objekt das nicht aktiv ist aber die position und ausrichtung des spielers definiert
    GLKamera Kamera_Sicht;
    GLVektor positionBlick; /// position des blickpunktes
    GLVektor positionKamera;  // position der kamera
    
    //GLBoden Boden;
    GLLicht Licht;
    GLMaus Maus;
    double empfindlichkeit = 0.1;        // geschwindigkeit der kamera bewegung
    int Mx;
    int Mx2;
    int My;
    int My2;
    double kameabewegung;        // bewegung in x richtung
    
    int Playernumber;   // ob der spieler 0 also single player , oder 1 oder 2 ist

    Dimension d; // größe des bildschirms
    Waffe Waffe1;
    UI UIspieler;
    Hitbox[] HitBoxen;

    GLQuader steinFallOrt;

    public Camera(GLKamera Kamera_Sicht2,GLKamera KameraMain2,int num)
    {
        Kamera_Sicht= Kamera_Sicht2;
        Playernumber = num;


        KameraMain=  KameraMain2;
        UIspieler = new UI(KameraMain);
        //Licht = new GLLicht();            //Sonne
        Maus = new GLMaus();
        
       
        d = Toolkit.getDefaultToolkit().getScreenSize(); // funktion um die Bildschirmgröße zu erfahren
        System.out.println(d.height);
        System.out.println(d.width);

        steinFallOrt = new GLQuader(3, 0, 150, 4, 0.5, 4);    // spieler anzeige wo der werfbare stein aufkommt
        steinFallOrt.setzeSelbstleuchten(1, 0,0);
        steinFallOrt.setzeFarbe(1,0,0);
        
        if (crosshairbool)                            // erstellen eines crossghairs
        {
            crosshair = new GLWuerfel(0, 10, 498, 0.01);
            crosshair.setzeSelbstleuchten(1, 0,0);
            crosshair.setzeFarbe(1,0,0);
        }
        


        if (Playernumber == 0)                 //singleplayer kamera bewegung 
        {
        
            Mx = Maus.gibX();
            Mx2 = Mx;
            My = Maus.gibY();
            My2= My;
        
        }else{                                // multiplayer kamerabewegung
            DllTwoMouse = new DllBridge();
            if (DllTwoMouse.init() != true) {System.out.println("init fehlgeschlagen");}
            DllTwoMouse.Update();
            Mx = DllTwoMouse.getX(Playernumber);
            Mx2 = Mx;
            My = DllTwoMouse.getY(Playernumber);
            My2 = My;
        }
    }

    public void ubergeben(Waffe Waffe2){
        Waffe1 = Waffe2;
        Waffe1.ubergeben(UIspieler);
        
    }

    public void ubergeben( Hitbox[] HitBoxen2){
      
        HitBoxen = HitBoxen2;
    }

    public void Update (){ // wird jeden frame aufgerufen
        
        if(Playernumber != 0)
        { 
            DllTwoMouse.Update(); 
           // System.out.println(DllTwoMouse.getX(2));
        }
       
        schwenken();            // kamerabewegung 
        UIspieler.changeHealth(0);
        UIspieler.Update();
       if (Waffe1.WaffeNummer()==3){
        steinFallOrt.setzeSichtbarkeit(true);
       }else {
        steinFallOrt.setzeSichtbarkeit(false);
       }
       
    }

    public void schwenken()
    {
        if (Playernumber == 0) 
        {
            Mx = Maus.gibX();
            My = Maus.gibY();
            if (Maus.gibX()>1750||Maus.gibX()<400)
            {   moveMouse(960,600);
                Mx =960;
                Mx2 =960;
                My =600;
                My2 =600;
            } 
           
            else
            {  
                kameabewegung =(Mx-Mx2) * empfindlichkeit;
                KameraMain.schwenkeHorizontal(kameabewegung);

               
                //drehe
                double x = (KameraMain.gibX()+ (Kamera_Sicht.gibX()- KameraMain.gibX()) *Math.cos(kameabewegung)- (Kamera_Sicht.gibZ()- KameraMain.gibZ()) *Math.sin(kameabewegung))-KameraMain.gibX();
                double y = (KameraMain.gibY()+ (Kamera_Sicht.gibX()- KameraMain.gibX()) *Math.sin(kameabewegung)- (Kamera_Sicht.gibZ()- KameraMain.gibZ()) *Math.cos(kameabewegung))-KameraMain.gibY();
                double z= 0;
                double Bx2 = Kamera_Sicht.gibBlickpunktX();
                double By2 = Kamera_Sicht.gibBlickpunktY();
                double Bz2 = Kamera_Sicht.gibBlickpunktZ();
        
                double Cx2 = Kamera_Sicht.gibX();
                double Cy2 = Kamera_Sicht.gibY();
                double Cz2 = Kamera_Sicht.gibZ();
                Kamera_Sicht.setzeBlickpunkt(Bx2+x, By2+y, Bz2+z);
                Kamera_Sicht.setzePosition(Cx2+x, Cy2+y, Cz2+z);
                
                Kamera_Sicht.schwenkeHorizontal(kameabewegung);
                My2 = My;
                Mx2 = Mx;
                if (crosshairbool)
                {
                    crosshair.drehe(0, -kameabewegung, 0, KameraMain.gibX(), KameraMain.gibY(), KameraMain.gibZ());
                }
                steinFallOrt.drehe(0, -kameabewegung, 0, KameraMain.gibX(), KameraMain.gibY(), KameraMain.gibZ());
                Waffe1.drehe(0, -kameabewegung, 0, KameraMain.gibPosition());
                UIspieler.drehe(0,-kameabewegung, 0, KameraMain.gibPosition());
            }
        }else{
            kameabewegung = DllTwoMouse.getX(Playernumber) * empfindlichkeit;
            KameraMain.schwenkeHorizontal(kameabewegung);
            if (crosshairbool)
            {
                crosshair.drehe(0, -kameabewegung, 0, KameraMain.gibX(), KameraMain.gibY(), KameraMain.gibZ());
            }
            steinFallOrt.drehe(0, -kameabewegung, 0, KameraMain.gibPosition());
            Waffe1   .drehe(0, -kameabewegung, 0, KameraMain.gibPosition());
            UIspieler.drehe(0,-kameabewegung, 0, KameraMain.gibPosition());
        }
         
    }

    public void moveMouse (int x, int y){
        try {  
            Robot robot = new Robot();                       
            robot.mouseMove(x, y);     
        } catch (AWTException e) {
            e.printStackTrace();} 
    } 

   public void moveCameraAbs (int x, int y,int z)
    {
        KameraMain.setzeBlickpunkt(KameraMain.gibBlickpunktX(),y,KameraMain.gibBlickpunktZ());
        KameraMain.setzePosition(KameraMain.gibX(),y,KameraMain.gibZ()); 
    }

    public void moveCameraRel (int x, int y,int z)
    {
        double Bx = KameraMain.gibBlickpunktX();
        double By = KameraMain.gibBlickpunktY();
        double Bz = KameraMain.gibBlickpunktZ();

        double Cx = KameraMain.gibX();
        double Cy = KameraMain.gibY();
        double Cz = KameraMain.gibZ();
        KameraMain.setzeBlickpunkt(Bx+x, By+y, Bz+z);
        KameraMain.setzePosition(Cx+x, Cy+y, Cz+z);

        double Bx2 = Kamera_Sicht.gibBlickpunktX();
        double By2 = Kamera_Sicht.gibBlickpunktY();
        double Bz2 = Kamera_Sicht.gibBlickpunktZ();
        
        double Cx2 = Kamera_Sicht.gibX();
        double Cy2 = Kamera_Sicht.gibY();
        double Cz2 = Kamera_Sicht.gibZ();
        Kamera_Sicht.setzeBlickpunkt(Bx2+x, By2+y, Bz2+z);
        Kamera_Sicht.setzePosition(Cx2+x, Cy2+y, Cz2+z);
    }

    public void moveCameraRel (GLVektor richtung)
    {
      
        GLVektor KameraV = KameraMain.gibPosition();
        KameraV.addiere(richtung);
        for(int i = 1;i<90 ;i++)
        {
            if (HitBoxen[i]!= null)
            {
                if (HitBoxen[i].beruehrt(KameraV,10)) {
                    
                   /*richtung = new GLVektor(HitBoxen[i].gibX(),HitBoxen[i].gibY(),HitBoxen[i].gibZ(),gibX(),gibY(),gibZ());
                   if 
                    
                    GLVektor KameraV2 = KameraMain.gibPosition();
                    KameraV2.addiere(richtung);
                    GLVektor KameraSichtV = Kamera_Sicht.gibPosition();
                    GLVektor BlickSichtV = Kamera_Sicht.gibBlickpunkt();  
                    GLVektor BlickV = KameraMain.gibBlickpunkt();
                    //GLVektor crosshairV = crosshair.gibPosition();
      
                    BlickV.addiere(richtung);
        
                    KameraSichtV.addiere(richtung);
                    BlickSichtV.addiere(richtung);
                    //crosshairV.addiere(richtung);

        
        

                    KameraMain.setzeBlickpunkt(BlickV);
                    Kamera_Sicht.setzeBlickpunkt(BlickSichtV);

                    KameraMain.setzePosition(KameraV2);
                    Kamera_Sicht.setzePosition(KameraSichtV);
                    //crosshair.setzePosition(crosshairV);
        
                    Waffe1.bewege(richtung);
                    UIspieler.bewege(richtung);
                    
                    
                     */
                    return;}
            }
        }
        GLVektor KameraSichtV = Kamera_Sicht.gibPosition();
        GLVektor BlickSichtV = Kamera_Sicht.gibBlickpunkt();  
        GLVektor BlickV = KameraMain.gibBlickpunkt();
        GLVektor Steinfall = steinFallOrt.gibPosition();
        if (crosshairbool)
        {
            GLVektor crosshairV = crosshair.gibPosition();
            crosshairV.addiere(richtung);
            crosshair.setzePosition(crosshairV);
        }
      
        BlickV.addiere(richtung);
        
        KameraSichtV.addiere(richtung);
        BlickSichtV.addiere(richtung);
        Steinfall.addiere(richtung);
        
        

        KameraMain.setzeBlickpunkt(BlickV);
        Kamera_Sicht.setzeBlickpunkt(BlickSichtV);

        KameraMain.setzePosition(KameraV);
        Kamera_Sicht.setzePosition(KameraSichtV);
        steinFallOrt.setzePosition(Steinfall);
        
        Waffe1.bewege(richtung);
        UIspieler.bewege(richtung);
    }
    
    public double gibX()
        {return  KameraMain.gibX();}
    public double gibY()
        {return  KameraMain.gibY();}
    public double gibZ()
        {return  KameraMain.gibZ();}
    public int Leben()
        {return UIspieler.gibHealth();}
    public void schaden(int i)
        {UIspieler.changeHealth(i);}
    public boolean RPressed()
        {return Maus.gedruecktRechts();}
    public boolean LPressed()
        {return Maus.gedruecktLinks();}
    public GLVektor gibPosition()
        {return KameraMain.gibPosition();}

}
