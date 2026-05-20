public class DllBridge {
static {
    // loads file MyDll.dll:
    System.loadLibrary("Dll2");
    System.out.println("loaded");
  }
  int M1 = -1;
  int M2 = -1;
  DllBridge(){
    System.out.println("object created");
  }
  // declare all functions you want to use as "native":
  

  public native boolean init();
  public native void Update();
  public native int getY(int device);
  public native int getX(int device);
  public native boolean getTaste(int device, char taste);

}
