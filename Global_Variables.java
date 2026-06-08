public class Global_Variables {
    static int Feinde = 0;
    static int nochsterben = 0;// phase 1
    static int nochsterben2 = 0; //phase2
    static int phase =1 ;
    public void changeVariable(int change){ Feinde = Feinde+ change;}
    public void setVariable(int Feinde2){Feinde = Feinde2;}
    public int getFeinde(){return Feinde;}
    public void addLateUpdate(int i){if (phase == 1){nochsterben2= nochsterben2+i;}else if (phase == 2){nochsterben= nochsterben+i;}}
    public void lateUpdate(){
        if (phase ==1){
            Feinde = Feinde+nochsterben2;
            nochsterben2 =0;
            phase = 2;
        }else if(phase==2){Feinde = Feinde+nochsterben;
            nochsterben =0;
            phase = 1;}
    }
}
