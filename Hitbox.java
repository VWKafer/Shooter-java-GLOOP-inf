import GLOOP.*;

public class Hitbox {
    
    double x;
    double y;
    double z;
    double xL;
    double yL;
    double zL;
    boolean sichtbar;
    GLQuader x1;
    GLQuader x2;
    GLQuader x3;
    GLQuader x4;

    GLQuader y1;
    GLQuader y2;
    GLQuader y3;
    GLQuader y4;

    GLQuader z1;
    GLQuader z2;
    GLQuader z3;
    GLQuader z4;
    Hitbox(double pX, double pY, double pZ, double pLX, double pLY, double pLZ,boolean sicht){
       
        x = pX;
        y = pY;
        z = pZ;
        xL = pLX;
        yL = pLY;
        zL = pLZ;
        sichtbar = sicht;
        
        
        x1 = new GLQuader(pX, pY+pLY/2, pZ-pLZ/2, pLX, 0.4,0.4);
        x2 = new GLQuader(pX, pY+pLY/2, pZ+pLZ/2, pLX, 0.4,0.4);
        x3 = new GLQuader(pX, pY-pLY/2, pZ+pLZ/2, pLX, 0.4,0.4);
        x4 = new GLQuader(pX, pY-pLY/2, pZ-pLZ/2, pLX, 0.4,0.4);

        y1 = new GLQuader(pX+pLX/2, pY, pZ-pLZ/2, 0.4, pLY,0.4);
        y2 = new GLQuader(pX+pLX/2, pY, pZ+pLZ/2, 0.4, pLY,0.4);
        y3 = new GLQuader(pX-pLX/2, pY, pZ+pLZ/2, 0.4, pLY,0.4);
        y4 = new GLQuader(pX-pLX/2, pY, pZ-pLZ/2, 0.4, pLY,0.4);

        z1 = new GLQuader(pX+pLX/2, pY-pLY/2, pZ, 0.4, 0.4,pLZ);
        z2 = new GLQuader(pX+pLX/2, pY+pLY/2, pZ, 0.4, 0.4,pLZ);
        z3 = new GLQuader(pX-pLX/2, pY+pLY/2, pZ, 0.4, 0.4,pLZ);
        z4 = new GLQuader(pX-pLX/2, pY-pLY/2, pZ, 0.4, 0.4,pLZ);

    x1.setzeSelbstleuchten(0, 1, 0);
    x2.setzeSelbstleuchten(0, 1, 0);
    x3.setzeSelbstleuchten(0, 1, 0);
    x4.setzeSelbstleuchten(0, 1, 0);

    y1.setzeSelbstleuchten(0, 1, 0);
    y2.setzeSelbstleuchten(0, 1, 0);
    y3.setzeSelbstleuchten(0, 1, 0);
    y4.setzeSelbstleuchten(0, 1, 0);

    z1.setzeSelbstleuchten(0, 1, 0);
    z2.setzeSelbstleuchten(0, 1, 0);
    z3.setzeSelbstleuchten(0, 1, 0);
    z4.setzeSelbstleuchten(0, 1, 0);
    
    
    sichtbar(sichtbar);
    }

    Hitbox(GLVektor ort, double pLX, double pLY, double pLZ,boolean sicht){

        double pX = ort.x;
        double pY = ort.y;
        double pZ = ort.z;

        x = pX;
        y = pY;
        z = pZ;
        xL = pLX;
        yL = pLY;
        zL = pLZ;
        sichtbar = sicht;
       
        x1 = new GLQuader(pX, pY+pLY/2, pZ-pLZ/2, pLX, 0.4,0.4);
        x2 = new GLQuader(pX, pY+pLY/2, pZ+pLZ/2, pLX, 0.4,0.4);
        x3 = new GLQuader(pX, pY-pLY/2, pZ+pLZ/2, pLX, 0.4,0.4);
        x4 = new GLQuader(pX, pY-pLY/2, pZ-pLZ/2, pLX, 0.4,0.4);

        y1 = new GLQuader(pX+pLX/2, pY, pZ-pLZ/2, 0.4, pLY,0.4);
        y2 = new GLQuader(pX+pLX/2, pY, pZ+pLZ/2, 0.4, pLY,0.4);
        y3 = new GLQuader(pX-pLX/2, pY, pZ+pLZ/2, 0.4, pLY,0.4);
        y4 = new GLQuader(pX-pLX/2, pY, pZ-pLZ/2, 0.4, pLY,0.4);

        z1 = new GLQuader(pX+pLX/2, pY-pLY/2, pZ, 0.4, 0.4,pLZ);
        z2 = new GLQuader(pX+pLX/2, pY+pLY/2, pZ, 0.4, 0.4,pLZ);
        z3 = new GLQuader(pX-pLX/2, pY+pLY/2, pZ, 0.4, 0.4,pLZ);
        z4 = new GLQuader(pX-pLX/2, pY-pLY/2, pZ, 0.4, 0.4,pLZ);

    x1.setzeSelbstleuchten(0, 1, 0);
    x2.setzeSelbstleuchten(0, 1, 0);
    x3.setzeSelbstleuchten(0, 1, 0);
    x4.setzeSelbstleuchten(0, 1, 0);

    y1.setzeSelbstleuchten(0, 1, 0);
    y2.setzeSelbstleuchten(0, 1, 0);
    y3.setzeSelbstleuchten(0, 1, 0);
    y4.setzeSelbstleuchten(0, 1, 0);

    z1.setzeSelbstleuchten(0, 1, 0);
    z2.setzeSelbstleuchten(0, 1, 0);
    z3.setzeSelbstleuchten(0, 1, 0);
    z4.setzeSelbstleuchten(0, 1, 0); 
    sichtbar(sichtbar);
    }

    public void setzePosition(double pX, double pY, double pZ){
        x = pX;
        y = pY;
        z = pZ;
       UpdatePosition();
    }

    public void setzePosition(GLVektor pPosition){
       
        x = pPosition.x;
        y = pPosition.y;
        z = pPosition.z;
       
        UpdatePosition();
    }

    private void UpdatePosition(){ 
        x1.setzePosition(x, y+yL/2, z-zL/2);
        x2.setzePosition(x, y+yL/2, z+zL/2);
        x3.setzePosition(x, y-yL/2, z+zL/2);
        x4.setzePosition(x, y-yL/2, z-zL/2);

        y1.setzePosition(x+xL/2, y, z-zL/2);
        y2.setzePosition(x+xL/2, y, z+zL/2);
        y3.setzePosition(x-xL/2, y, z+zL/2);
        y4.setzePosition(x-xL/2, y, z-zL/2);

        z1.setzePosition(x+xL/2, y-yL/2, z);
        z2.setzePosition(x+xL/2, y+yL/2, z);
        z3.setzePosition(x-xL/2, y+yL/2, z);
        z4.setzePosition(x-xL/2, y-yL/2, z);
    }

    public boolean beruehrt(double pX,double pY, double pZ){
        if (pX<x+xL/2 &&pX>x-xL/2&&pY<y+yL/2 &&pY>y-yL/2&&pZ<z+zL/2 &&pZ>z-zL/2)
        {return true;
        }else return false;
    }

    public boolean beruehrt(GLVektor pPos){
        if (pPos.x<x+(xL/2) &&pPos.y>y-(yL/2)&&pPos.z<z+(zL/2) &&
            pPos.x>x-(xL/2) &&pPos.y<y+(yL/2 )&&pPos.z>z-(zL/2))
        {return true;
        }else return false;
    }

    public boolean beruehrt(GLVektor pPos,int dist){
        if (pPos.x<x+((xL+dist)/2) &&pPos.y>y-((yL+dist)/2)&&pPos.z<z+((zL+dist)/2) &&
            pPos.x>x-((xL+dist)/2) &&pPos.y<y+((yL+dist)/2 )&&pPos.z>z-((zL+dist)/2))
        {return true;}
        else return false;
    }

    public void sichtbar(boolean sicht){
        
        sichtbar =sicht;

        System.out.println(sichtbar);
        x1.setzeSichtbarkeit(sichtbar);
        x2.setzeSichtbarkeit(sichtbar);
        x3.setzeSichtbarkeit(sichtbar);
        x4.setzeSichtbarkeit(sichtbar);

        y1.setzeSichtbarkeit(sichtbar);
        y2.setzeSichtbarkeit(sichtbar);
        y3.setzeSichtbarkeit(sichtbar);
        y4.setzeSichtbarkeit(sichtbar);

        z1.setzeSichtbarkeit(sichtbar);
        z2.setzeSichtbarkeit(sichtbar);
        z3.setzeSichtbarkeit(sichtbar);
        z4.setzeSichtbarkeit(sichtbar);
    }
    public double gibX()
        {return  x;}
    public double gibY()
        {return  y;}
    public double gibZ()
        {return  z;}

} 

