    import GLOOP.*;

public class Main {
    
    public static void main(String args[])
    {System.out.println(System.getProperty("java.version"));
   
        Global_Variables  Global;
        Global = new Global_Variables();

        Player Player1;
        Player Player2;
        Player Player0;
       
        int Feinde;
        boolean multiplayer = false;
        GLTerrain  Boden1;
        Boden1 = new GLTerrain(0, 0,0, "map.png");
        Boden1.setzeSkalierung(10,3,10);
        Boden1.setzePosition(0,-590,0);
        Boden1.setzeTextur("mapTextur.png");
        Baum Baum1;
        Baum1 = new Baum(-350,0,200,1);
        
        
        if (multiplayer)
        {  ///////// 2Spielerd
            Player1 = new Player(1);
            Player2 = new Player(2);
             Global.setVariable(5);
             Feinde = 5;
            boolean FeindeBool[] = new boolean[Feinde];
            Enemy Enemys[] = new Enemy[Feinde];
            for (int i=0 ; i<Feinde; i++){
                FeindeBool[i] = true;
                Enemys[i] = new Enemy(i,Global,Feinde,Player1,false);
            }
            for (int i=0 ; i<Feinde; i++){
               
                Enemys[i].ubergeben(FeindeBool);
            }

        
            Waffe Waffe1 = new Waffe(Enemys,Global,Feinde,FeindeBool);
            Player1.ubergeben(Waffe1);

            Waffe Waffe2 = new Waffe(Enemys,Global,Feinde,FeindeBool);
            Player2.ubergeben(Waffe2);

            boolean running = true;
        
            while(running){
                Player1.Update(); 
            
                Player2.Update();
        
            }

    
        }else
        {//// Single player
            Player0= new Player(0);
            
            Feinde = 0;
            Global.setVariable(Feinde);
            Player0.ubergeben(Global,Feinde);
            boolean FeindeBool[] = new boolean[Feinde];
            Enemy Enemys[] = new Enemy[Feinde];
            for (int i=0 ; i<Feinde; i++){
                FeindeBool[i] = true;
                Enemys[i] = new Enemy(i,Global,Feinde,Player0, false);
            }
            for (int i=0 ; i<Feinde; i++){
               
                Enemys[i].ubergeben(FeindeBool);
                System.out.println(i);
   
            }

        Hitbox [] HitBoxen= new Hitbox[90];
            
            Haus Haus1 = new Haus(300, 0, 100, 100, 80, 500,90); 

            Haus Haus2 = new Haus(0, 0, 300, 200, 100, 200,90);

            Haus Haus3 = new Haus(-350, 35, 100, 100, 80, 50,0);

            Haus Haus4 = new Haus(400, 0, -100, 100, 80, 100,0);

            Haus Haus5= new Haus(300, 0, 500, 100, 80, 500,90); 

            Haus Haus6 = new Haus(500, 0, 700, 200, 100, 200,0);

            Haus Haus7 = new Haus(300, 35, 300, 100, 80, 50,0);

            Haus Haus8 = new Haus(700, 0, 300, 150, 200, 150,0);

           

            HitBoxen[0]= Player0.gibHitBox();
            HitBoxen[1]= Haus1.gibHitBox();
            HitBoxen[2]= Haus2.gibHitBox();
            HitBoxen[3]= Haus3.gibHitBox();
            HitBoxen[4]= Haus4.gibHitBox();
            HitBoxen[5]= Haus5.gibHitBox();
            HitBoxen[6]= Haus6.gibHitBox();
            HitBoxen[7]= Haus7.gibHitBox();
            HitBoxen[8]= Haus8.gibHitBox();
           
int[]koordinatenBaume = {
            -66,0,472,
            -383,0,197,
            -211,0,239,
            -280,0,203,
            -215,0,108,
            -68,0,105,
            -62,0,15,
            -217,0,13,
            -219,0,-85,
            -131,0,-88,
            -29,0,-89,
            96,0,-97,
            229,0,-87,
            340,0,-90,
            221,0,242,
            405,0,349,
            646,0,124,
            647,0,57,};
            
            //           anzahl bäume *3
            for (int i = 0;i<54; i= i+3){
                Baum Baum;
                Baum = new Baum(koordinatenBaume[i], koordinatenBaume[i+1], koordinatenBaume[i+2], 1);
                //System.out.println(koordinatenBaume[i]);
                //System.out.println(koordinatenBaume[i+1]);
                //System.out.println(koordinatenBaume[i+2]);
                HitBoxen[i+9] = Baum.gibHitBox();
            }
        
            Waffe Waffe1 = new Waffe(Enemys,Global,Feinde,FeindeBool);
            Player0.ubergeben(Waffe1);
            Player0.ubergeben(HitBoxen);

            //HitBoxen[0]= Player0.gibHitBox();

            for (int i=0 ; i<Feinde; i++){
               
                Enemys[i].ubergeben(HitBoxen);
            }

           
            while(Player0.isAllive()){
                Sys.warte(8);///// 16ms wäre 60 fps ohne rechnungszeit 8ms wäre 120 fps
                //Sys.warte(2000);
                Player0.Update(); 
            }
            Sys.warte(100);
            Sys.beenden();
        }
    }
}
