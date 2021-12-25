package ernest.testgame.canvas.setgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


public class TTimer {
 int Seg =0;
 int Min =0;
 int Hr=0;
 TPoint PtoTopLeft = new TPoint();
 Paint cartel = new Paint();
 Paint paintmarcador = new Paint();
 //Paint BackgroundCartel = new Paint();
 float anchocartel;
 //float anchoBackgroundCartel;
 //float h = 20;   //(solo en v. tablet)
 float h = (float) 13.5;       //(solo en v. telefono)
 float osbar = 48;
 
 float anchopaintcards;
 
 boolean tablet = false;
 
final float escala = (float) 0.1125;             //Aqui esta bien 0.1125 = 13.5px / 120dpi
//float escalaTablet = (float) 0.15;         //Aqui asumi q mi tablet tambien tiene 120dpi, 0.15 = 18px / 120dpi
//float pxsize;
final int density;
 
 public TTimer(boolean IsTablet, int densidad){
	 	if(IsTablet)
	 		tablet = true;
	
	 	density  = densidad;
	 	h = densidad * escala;
	 	
		cartel.setTextSize(h);
		cartel.setColor(Color.WHITE);
		cartel.setAlpha(180);
		anchocartel = cartel.measureText("00:00:00");
		paintmarcador.setTextSize(h);    
		anchopaintcards = paintmarcador.measureText("cards");

 }
 public TTimer(){density = 0;}
 /*
 public void SetTablet(){
	 tablet = true;
	 h = 20; 
	 cartel.setTextSize(h);
	 anchocartel = cartel.measureText("00:00:00");
	 paintmarcador.setTextSize(18);   //(solo en v. tablet)
	 anchopaintcards = paintmarcador.measureText("cards");
 }
 */
 public String GetString(){
	 String letrero = new String();
	 letrero = "";
	 if(Hr <= 9)
		 letrero += "0";
	 letrero += Hr + ":";
	 if(Min <= 9)
		 letrero += "0";
	 letrero += Min + ":";
	 if(Seg <= 9)
		 letrero += "0";
	 letrero += Seg;
	 return letrero;
 }
 public void display(Canvas lienzo){
	 String letrero = new String();
	 letrero = "";
	 if(Hr <= 9)
		 letrero += "0";
	 letrero += Hr + ":";
	 if(Min <= 9)
		 letrero += "0";
	 letrero += Min + ":";
	 if(Seg <= 9)
		 letrero += "0";
	 letrero += Seg;
	 		
	 
	 lienzo.drawText(letrero, PtoTopLeft.x, PtoTopLeft.y + h, cartel);
	 
 }
 
 /*
 public void atrasar(int segundos){
	 int totalseg = Hr*3600 + Min*60 + Seg - segundos;
	 if(totalseg  < 0 )
		 totalseg = 0;
	  this.SetTime(totalseg); 
 }
 */
 
 public void Dimensionar(boolean ortn,int  H, int A){
		float Hreal = H;  //(solo en v. telefono)
		//float Hreal = H - 48; //(solo en v. tablet) //48 es es tamaNo de la barra del sistema operativo
	    float Areal = A; //(solo en v. telefono)
		//float Areal = A - 48; //(solo en v. tablet)
	    if(tablet){
	    	Hreal = H - 48;
	    	//Hreal = H;  //linea de prueVa para ver como se ve el juego cuando es en una taVleta
	    	Areal = A - 48;
	    	//Areal = A;  //linea de prueVa para ver como se ve el juego cuando es en una taVleta
	    }
		
		
		if(ortn){
			//en landscape
			PtoTopLeft.x = (float) (A - anchopaintcards - 20 - anchocartel);	
			PtoTopLeft.y = (float) (Hreal - (h + 3.5/120*density));	
		}
		else{
			//en portrait
			PtoTopLeft.x = (float) (H - anchopaintcards - 20 - anchocartel);
			PtoTopLeft.y = (float) (Areal - (h + 3.5/120*density));	
		}
 }
 public void Reset(){
	 Seg =0;
	 Min =0;
	 Hr=0; 
 }
 public void SetTime (int Seconds){
	 Hr = Seconds / 3600;
	 Min =  (Seconds - Hr*3600)/60;
	 Seg = Seconds - Hr*3600 - Min*60; 
 }
 public String GetStringII(){
	 String letrero = new String();
	 letrero = "";
	 if(Hr != 0)
		 letrero += Hr + "hours";
	 letrero += Min + " mins " + Seg + " secs";
	 return letrero;
 }
 public int  GetTime()
 {
	 return Seg + Hr*3600 + Min*60;
 }
 //-------------------------------
 public boolean ToqueReloj(TPoint pto)
 {
	 //if((pto.x >= TLmenu.x && pto.x <= TLmenu.x + anchomenu) && (pto.y >= TLmenu.y - 10)) //Dandole 10 mas para q no sea tan dificil tocarlo
	 if((pto.x >= PtoTopLeft.x && pto.x <= PtoTopLeft.x + anchocartel) && (pto.y >= PtoTopLeft.y - 10)) //Dandole 10 mas para q no sea tan dificil tocarlo
			return true;
	 else
		 return false;
 }
 
 
 
}//TTimer
