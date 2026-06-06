import GLOOP.*;
public class UI {
    GLObjekt[] UIElemente = new GLObjekt[100];
    GLQuader LebensLeisteHintergrund;
    GLQuader LebensLeiste;
    GLTafel MunitionDaten;
    GLKamera Kamera;
    int health;
    int Munition= 10;
    int Magazingrosse= 10;
    int MaxHealth = 101;  
    double prevHealth= 101;
    Waffe Waffe;
   

    public UI(GLKamera Kamera2){
        Kamera = Kamera2;
        LebensLeiste =           new GLQuader(Kamera.gibX()-1.7,10.9, Kamera.gibZ()-2, 0.35, 0.08, 0.02);
        LebensLeisteHintergrund= new GLQuader(Kamera.gibX()-1.7,10.9, Kamera.gibZ()-2, 0.4, 0.1, 0.01);
        LebensLeisteHintergrund.setzeFarbe(0, 0, 0);
        LebensLeisteHintergrund.setzeGlanz(0, 0, 0, 4);
        //MunitionDaten =          new GLTafel (Kamera.gibX(),10, Kamera.gibZ()-2, 4, 5);
        LebensLeiste.setzeFarbe(1, 0, 0);
        LebensLeiste.setzeGlanz(1, 0, 0, 4);
        LebensLeiste.setzeSelbstleuchten(1, 0, 0);
        health = 100;
        double ortx = 1.6;
        double orty = 9.1;
        int zaehler= 0;
        for( int i=0;i<2;i++){                                                                        //  X    Y   Z      
            UIElemente[zaehler  ] = new GLQuader(Kamera.gibX()+ortx      +0.1*i, orty, Kamera.gibZ()-2,        0.04, 0.02, 0.002);
            UIElemente[zaehler+1] = new GLQuader(Kamera.gibX()+ortx-0.03 +0.1*i, orty-0.03, Kamera.gibZ()-2,   0.02, 0.04, 0.002);
            UIElemente[zaehler+2] = new GLQuader(Kamera.gibX()+ortx+0.03 +0.1*i, orty-0.03, Kamera.gibZ()-2,   0.02, 0.04, 0.002);
            UIElemente[zaehler+3] = new GLQuader(Kamera.gibX()+ortx      +0.1*i, orty-0.06, Kamera.gibZ()-2,   0.04, 0.02, 0.002);
            UIElemente[zaehler+4] = new GLQuader(Kamera.gibX()+ortx-0.03 +0.1*i, orty-0.09, Kamera.gibZ()-2,   0.02, 0.04, 0.002);
            UIElemente[zaehler+5] = new GLQuader(Kamera.gibX()+ortx+0.03 +0.1*i, orty-0.09, Kamera.gibZ()-2,   0.02, 0.04, 0.002);
            UIElemente[zaehler+6] = new GLQuader(Kamera.gibX()+ortx      +0.1*i, orty-0.12, Kamera.gibZ()-2,   0.04, 0.02, 0.002);
            zaehler= zaehler+7;/**/
        }
      for(int i= 0;i<20;i++){
        if (UIElemente[i] != null){
        UIElemente[i].setzeFarbe(0, 0, 0);
        UIElemente[i].setzeGlanz(0, 0, 0, 4);
        UIElemente[i].setzeSelbstleuchten(0, 0, 0);
         UIElemente[i   ].setzeSichtbarkeit(false );
        }
      }
        
    }
    public void changeHealth(int change)
    {
        health = health+change;
       
        // x*y = z    z/x =y   health/skalierung
        LebensLeiste.skaliere(health/prevHealth ,1, 1);
       
       if (health<1){health = 1;}
        prevHealth = health;
    }
    public int gibHealth(){return health;}
    public void setHealth(int healthNew){
        health = healthNew;
    }
    public void setMunition(int kugeln,int MagazinGro){
        Munition        = kugeln;
        Magazingrosse   = MagazinGro;
    }
    public void Update()
    {
        MunitionsAnzeige(0);
       
        
    }
    public void drehe(double x, double y, double z, GLVektor rotationPunkt)
    {
        LebensLeiste.drehe(x, y, z, rotationPunkt);
        //MunitionDaten.drehe(x, y, z,rotationPunkt);
        LebensLeisteHintergrund.drehe(x, y, z, rotationPunkt);
        /*visier.drehe(x, y, z, rotationPunkt);
        Magazin.drehe(x, y, z, rotationPunkt);
        schulterstütze.drehe(x, y, z, rotationPunkt);*/
        for(int i= 0;i<20;i++){
            if (UIElemente[i] != null){
                 UIElemente[i].drehe(x, y, z, rotationPunkt);
            }
        }
    }

    public void bewege (GLVektor richtung)
    {
      GLVektor LebensLeisteV = LebensLeiste.gibPosition();
        GLVektor LebensLeisteHintergrundV = LebensLeisteHintergrund.gibPosition();
// GLVektor MunitionDatenV = MunitionDaten.gibPosition();
       // GLVektor visierV =visier.gibPosition();
      //GLVektor MagazinV = Magazin.gibPosition();
      //GLVektor  schulterstützeV =  schulterstütze.gibPosition();
//MunitionDatenV.addiere(richtung);
       LebensLeisteV.addiere(richtung);
        LebensLeisteHintergrundV.addiere(richtung);
        //visierV.addiere(richtung);
        //MagazinV.addiere(richtung);
        //schulterstützeV.addiere(richtung);

       //MunitionDaten.setzePosition(MunitionDatenV);
       LebensLeiste.setzePosition(LebensLeisteV);
        LebensLeisteHintergrund.setzePosition(LebensLeisteHintergrundV);
        //visier.setzePosition(visierV);
        //Magazin.setzePosition(MagazinV);
       //schulterstütze.setzePosition(schulterstützeV);*/

       for(int i= 0;i<20;i++){
            if (UIElemente[i] != null){
                 GLVektor vekt= UIElemente[i].gibPosition();
                vekt.addiere(richtung);
                 UIElemente[i].setzePosition(vekt);
            }
        }
       
    }
    public void MunitionsAnzeige(int start)
    {

///             ===== 1
///         
///         ||         || 3
///         || 2       ||
///     
///             ===== 4
///         
///         ||         ||
///         || 5       || 6
/// 
///             ====== 7
        
        if(Munition >= 10&&Munition < 20){
            UIElemente[start   ].setzeSichtbarkeit(false );// 1
            UIElemente[start+1 ].setzeSichtbarkeit( false);// 2
            UIElemente[start+2 ].setzeSichtbarkeit(true ); // 3
            UIElemente[start+3 ].setzeSichtbarkeit( false);// 4
            UIElemente[start+4 ].setzeSichtbarkeit( false);// 5
            UIElemente[start+5 ].setzeSichtbarkeit(true);//   6
            UIElemente[start+6 ].setzeSichtbarkeit( false);// 7
            Munition= Munition- 10 ;
        }else if(Munition >= 20&&Munition < 30){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( false);
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( false);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
             Munition= Munition-20 ;
        }else  if(Munition >= 30&&Munition < 40){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( false);
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit(true );
            UIElemente[start+6 ].setzeSichtbarkeit( true);
             Munition= Munition-30 ;
        }else  if(Munition >= 40&&Munition < 50){
            UIElemente[start   ].setzeSichtbarkeit( false);
            UIElemente[start+1 ].setzeSichtbarkeit( true);
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( false);
             Munition= Munition- 40;
        }else  if(Munition >= 50&&Munition < 60){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( true);
            UIElemente[start+2 ].setzeSichtbarkeit( false);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
             Munition= Munition-50 ;
        }else  if(Munition >= 60&&Munition < 70){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( true);
            UIElemente[start+2 ].setzeSichtbarkeit( false);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
             Munition= Munition- 60;
        }else  if(Munition >= 70&&Munition < 80){
             UIElemente[start   ].setzeSichtbarkeit(true );// 1
            UIElemente[start+1 ].setzeSichtbarkeit( false);// 2
            UIElemente[start+2 ].setzeSichtbarkeit(true ); // 3
            UIElemente[start+3 ].setzeSichtbarkeit( false);// 4
            UIElemente[start+4 ].setzeSichtbarkeit( false);// 5
            UIElemente[start+5 ].setzeSichtbarkeit(true);//   6
            UIElemente[start+6 ].setzeSichtbarkeit( false);// 7
             Munition= Munition- 70;

        }else  if(Munition >= 80&&Munition < 90){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit(true );
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
            Munition= Munition- 80;
        }else if(Munition >= 90&&Munition < 100){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit(true );
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
             Munition= Munition- 90;
        }else {
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit(true );
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( false);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);

        }/**/
//System.out.println(start);
//System.out.println(Munition);
        start=start +7;
        
        if(Munition == 1){
            UIElemente[start   ].setzeSichtbarkeit(false );// 1
            UIElemente[start+1 ].setzeSichtbarkeit( false);// 2
            UIElemente[start+2 ].setzeSichtbarkeit(true ); // 3
            UIElemente[start+3 ].setzeSichtbarkeit( false);// 4
            UIElemente[start+4 ].setzeSichtbarkeit( false);// 5
            UIElemente[start+5 ].setzeSichtbarkeit(true);//   6
            UIElemente[start+6 ].setzeSichtbarkeit( false);// 7

        }else if(Munition == 2){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( false);
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( false);
            UIElemente[start+6 ].setzeSichtbarkeit( true);

        }else  if(Munition ==3 ){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( false);
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit(true );
            UIElemente[start+6 ].setzeSichtbarkeit( true);

        }else  if(Munition == 4){
            UIElemente[start   ].setzeSichtbarkeit( false);
            UIElemente[start+1 ].setzeSichtbarkeit( true);
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( false);

        }else  if(Munition == 5){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( true);
            UIElemente[start+2 ].setzeSichtbarkeit( false);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);

        }else  if(Munition == 6){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit( true);
            UIElemente[start+2 ].setzeSichtbarkeit( false);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
        }else  if(Munition == 7){
             UIElemente[start   ].setzeSichtbarkeit(true );// 1
            UIElemente[start+1 ].setzeSichtbarkeit( false);// 2
            UIElemente[start+2 ].setzeSichtbarkeit(true ); // 3
            UIElemente[start+3 ].setzeSichtbarkeit( false);// 4
            UIElemente[start+4 ].setzeSichtbarkeit( false);// 5
            UIElemente[start+5 ].setzeSichtbarkeit(true);//   6
            UIElemente[start+6 ].setzeSichtbarkeit( false);// 7


        }else  if(Munition == 8){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit(true );
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);

        }else if(Munition == 9){
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit(true );
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( true);
            UIElemente[start+4 ].setzeSichtbarkeit( false);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);

        }else {
            UIElemente[start   ].setzeSichtbarkeit( true);
            UIElemente[start+1 ].setzeSichtbarkeit(true );
            UIElemente[start+2 ].setzeSichtbarkeit( true);
            UIElemente[start+3 ].setzeSichtbarkeit( false);
            UIElemente[start+4 ].setzeSichtbarkeit( true);
            UIElemente[start+5 ].setzeSichtbarkeit( true);
            UIElemente[start+6 ].setzeSichtbarkeit( true);
        } /**/
}





}
