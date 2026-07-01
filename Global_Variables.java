public class Global_Variables {
    static int Feinde = 0;
    static int Zombies = 0;
    static int Lampenv= 0;
    static int Ldist = 100;
    
    
    public void changeVariable(int change){ Feinde = Feinde+ change;
        System.out.println(Feinde);
    }
    public void setVariable(int Feinde2){Feinde = Feinde2;}
    public int getFeinde(){return Feinde;}

    public void changeVariableL(int change){ Lampenv = Lampenv+ change;
        System.out.println(Lampenv);
    }
    public void setVariableL(int Lampenv2){Lampenv = Lampenv2;}
    public int getLampen(){return Lampenv;}


   
    public void changeVariableLD(int change){ Ldist = Ldist+ change;
        System.out.println(Ldist);
    }
    public void setVariableLD(int Ldist2){Ldist = Ldist2;}
    public int getLdist(){return Ldist;}
    
    public void changeVariableZ(int change){ Zombies = Zombies+ change;
        System.out.println(Zombies);
    }
    public void setVariableZ(int Zombies2){Zombies = Zombies2;}
    public int getZombies(){return Zombies;}
}
