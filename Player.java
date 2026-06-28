import GLOOP.*;
public class Player {
    Camera Camera;
    GLTastatur Tastatur;
    double Geschwindigkeit = 1.0;
    GLKamera KameraMain;
    
  
    Waffe Waffe;
    int Playernumber;
    GLQuader PlayerBody;
    GLKamera Kamera_Sicht;
    Hitbox HitBox;
    boolean Lebt = true;
    Hitbox[] HitBoxen;
    int Bodenhöhe= 0;
   Global_Variables Global;
    int Feinde;
    GLKugel Sichtweite;
    public Player( int num)
    {
        
        Playernumber = num;
        
        int fensterH = 1080;
        int fensterB = 1920;
        if(Playernumber==0){
            
            Kamera_Sicht = new GLKamera(fensterB,fensterH);
            Kamera_Sicht.setzePosition(0,10,500);
            KameraMain = new GLKamera(10,10);
            KameraMain.aus();
        }else{
            Kamera_Sicht = new GLKamera(fensterB,fensterH/2);
            if (Playernumber== 2){
            Kamera_Sicht.setzeFensterposition(0, fensterH/2);}
        }
        Camera = new Camera(Kamera_Sicht,KameraMain,num);
        Tastatur = new GLTastatur();
        PlayerBody = new GLQuader(gibX(), 0, gibZ(), 5, 25, 5);
        //Sichtweite = new GLKugel(PlayerBody.gibPosition(), -400);
        if (Playernumber ==1) {
            PlayerBody.setzeFarbe(0,1,0);
            PlayerBody.setzeSelbstleuchten(0, 1,0);
        }else{
            PlayerBody.setzeFarbe(0,0,1);
            PlayerBody.setzeSelbstleuchten(0, 0,1);
        }
        HitBox = new Hitbox(PlayerBody.gibPosition(), 7, 28, 7, false);
    }

    public void ubergeben(Waffe Waffe2){
         Waffe = Waffe2;
         
         Camera.ubergeben(Waffe);
    }
    public void ubergeben(Hitbox[] HitBoxen2){
         HitBoxen = HitBoxen2;
         Waffe.ubergeben(this,Camera, HitBoxen);
         Camera.ubergeben(HitBoxen);
    }
    public void ubergeben( Global_Variables Global2,int Feinde2){
        Global = Global2;
        Feinde = Feinde2;
    }

    public void Update (){
        Waffe.Update();
        Camera.Update();
        HitBox.setzePosition(PlayerBody.gibPosition());
        //Sichtweite.setzePosition(PlayerBody.gibPosition());
        if(Tastatur.esc()|| (Global.getFeinde() ==0 && Feinde!=0)){
            end();
        }

        PlayerBody.setzePosition(gibX(), 0,gibZ());

        if (Playernumber == 1||Playernumber == 0) {
            
            if (Tastatur.istGedrueckt(' ')) {
                System.out.println(((int) HitBox.x)+",0,");
                System.out.println(((int) HitBox.z)+",");
                lampe Baum;
                Baum = new lampe((int) HitBox.x, 0, (int) HitBox.z, 1,false,this,Global);
                //GLVektor Richtung = new GLVektor(0,1,0);
                //Richtung.skaliereAuf(Geschwindigkeit);
                //Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('w')) {
            
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('d')) {
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Richtung.drehe(0,-90,0);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('a')) {
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Richtung.drehe(0,90,0);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('s')) {
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Richtung.drehe(0 ,180, 0);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('1')) {
              
                Waffe.wechsel(1);
            }
            if (Tastatur.istGedrueckt('2')) {
            
                Waffe.wechsel(2);
            }
            if (Tastatur.istGedrueckt('3')) {
            
                Waffe.wechsel(3);
            }
            if (Camera.RPressed())
                {Waffe.zielen();}

            if (Camera.LPressed()) 
            { Waffe.Shoot((int)KameraMain.gibX(),(int)KameraMain.gibY(),(int)KameraMain.gibZ(), KameraMain.gibBlickrichtung());}
        }
        if (Playernumber == 2) {
            
            if (Tastatur.istGedrueckt('t')) {
            
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('h')) {
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Richtung.drehe(0,-90,0);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('f')) {
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Richtung.drehe(0,90,0);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('g')) {
                GLVektor Richtung = KameraMain.gibBlickrichtung();
                Richtung.skaliereAuf(Geschwindigkeit);
                Richtung.drehe(0 ,180, 0);
                Camera.moveCameraRel(Richtung);
            }
            if (Tastatur.istGedrueckt('x')) 
            {Waffe.Shoot((int)KameraMain.gibX(),(int)KameraMain.gibY(),(int)KameraMain.gibZ(), KameraMain.gibBlickrichtung());}
        }
    }

    public void end()
    {Sys.beenden();}

    public double gibX()
    {return  KameraMain.gibX();}

     public double gibY()
    {return  KameraMain.gibY();}

    public double gibZ()
    {return  KameraMain.gibZ();}

    public GLVektor gibPosition()
    {return  KameraMain.gibPosition();}

    public Hitbox gibHitBox()
    {return  HitBox;}

    public boolean isAllive(){
        if ( Camera.Leben()>1){Lebt = true;}else{Lebt= false;}
        return Lebt;
    }
    public void schaden(int i)
    {Camera.schaden(i);}
}
