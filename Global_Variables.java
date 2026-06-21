public class Global_Variables {
    static int Feinde = 0;
    
    
    public void changeVariable(int change){ Feinde = Feinde+ change;
        System.out.println(Feinde);
    }
    public void setVariable(int Feinde2){Feinde = Feinde2;}
    public int getFeinde(){return Feinde;}
    
}
