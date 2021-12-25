package ernest.testgame.canvas.setgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;

public class TCasilla {
	//static boolean tablet = false;
	//Otros atributos:
	private  Paint paintLineas = new Paint();  //Todos estos atributos Paint eran static ahora no lo son, 02-ene-2014
	private  Paint paintRayaRect = new Paint();
	private  Paint paintRaya = new Paint();
	private  Paint paintRomboRelleno = new Paint();
	private  Paint paintRelleno = new Paint();
	private  static Paint paintCasillaSeleccionada = new Paint();
	private  Paint paintCasilla = new Paint();
	private  static Paint paintCasillaSeleccionada2 = new Paint();
	
	//private int alpha = 255; //Un nuevo miembro. 02-ene-2014 //alpha = 255 es opaco y alpha = 0 es totalmente transparente 
	public int alpha = 225;
	//private static float anchoRomboRelleno;
	//private static float anchoRaya;
	
	
	//private static float anchoDeLinea = (float)3.0; //only vTavlet
	//private static float anchoDeLinea = (float)1.35; //only vTelf
	private static float anchoDeLinea = (float)1.60; //only vTelf
	private static float anchoRayaRect;
	//atributos:
	private static float radio; //radio del Circulo.
	private static float d;  //diagonal del rombo.
	private static float A;  //Ancho del rectangulo.
	private static float L;  //Largo del rectangulo.
	private static float SizeA; //tamano de la casilla, lado a del cuadrado.
	
	private TPoint PtoTopLeft = new TPoint(); //PtoTopLeft.x = left, PtoTopLeft.y = top, coordenadas 
	                           //del rectangulo que es la casilla.
	private TCard MiCarta;
	//private boolean seleccionada;
	public boolean seleccionada;
	//NUEVA VERSION 0_6:
	public boolean ply1 = true;
	//NUEVO VERSION 0_7d:
	
	//------------------
	//Metodos:
	//Constructor:
	public TCasilla(int densidad){
		MiCarta = null;
		seleccionada = false;
		ply1 = true;
		
		anchoDeLinea = (float) (0.0133333 * densidad);
		anchoRayaRect = (float) (0.085416666 * densidad);
		TCasilla.paintCasillaSeleccionada.setStrokeWidth((float) (0.04583333 * densidad));
		TCasilla.paintCasillaSeleccionada2.setStrokeWidth((float) (0.04583333 * densidad));
		
		//paintLineas.setColor(Color.GREEN); //Esto debe hacerse de acuerdo al color de la carta.
		this.paintLineas.setAntiAlias(true);
		this.paintLineas.setStyle(Style.STROKE);
		this.paintLineas.setStrokeWidth(anchoDeLinea);
		
		this.paintRaya.setAntiAlias(true);
		this.paintRaya.setStyle(Style.STROKE);
		
		this.paintRayaRect.setAntiAlias(true);
		this.paintRayaRect.setStyle(Style.STROKE);
		this.paintRomboRelleno.setAntiAlias(true);
		this.paintRomboRelleno.setStyle(Style.STROKE);
		this.paintRomboRelleno.setStrokeWidth((float)(d/1.4142135623730950488016887242097 + anchoDeLinea/2.0));
		
		this.paintRelleno.setAntiAlias(true);
		this.paintRelleno.setStyle(Style.FILL);
		
		TCasilla.paintCasillaSeleccionada.setAntiAlias(true); 
		TCasilla.paintCasillaSeleccionada.setStyle(Style.STROKE);
		TCasilla.paintCasillaSeleccionada.setColor(Color.YELLOW);
		TCasilla.paintCasillaSeleccionada.setAlpha(240); //Nuevo VERSION 0_6
		
		TCasilla.paintCasillaSeleccionada2.setAntiAlias(true); 
		TCasilla.paintCasillaSeleccionada2.setStyle(Style.STROKE);
		TCasilla.paintCasillaSeleccionada2.setColor(Color.CYAN);
		TCasilla.paintCasillaSeleccionada2.setAlpha(240); //Nuevo VERSION 0_6
		
		this.paintCasilla.setAntiAlias(true);
		this.paintCasilla.setStyle(Style.FILL);
		this.paintCasilla.setColor(Color.WHITE);
		//TCasilla.paintCasilla.setAlpha();
		
	}
	//---------------------
	/*
	 public static void SetTablet(){
			anchoDeLinea = (float)3.25; //only vTavlet
			//this.paintRayaRect.setStrokeWidth(15);  //only vTavlet 
			anchoRayaRect = (float) 15.25;
			//this.paintLineas.setStrokeWidth(anchoDeLinea);
			//TCasilla.paintCasillaSeleccionada.setStrokeWidth(8);
			TCasilla.paintCasillaSeleccionada.setStrokeWidth(10);
			TCasilla.paintCasillaSeleccionada2.setStrokeWidth(10);
	 }
	 */
	
	//---------------------
	//void SetSize(float a)
	//Al modificar el tamano de la casilla tambien se veran afectados d, A, L, y el radio.
public  static void SetSize(float a){
		SizeA = a;
		radio = (float) (0.15 * a);
		d = (float) (0.3 * a);
		A = (float) (0.2 * a);
		L = (float) (0.7 * a);
		
		//float x = (float)(d/1.4142135623730950488016887242097 + anchoDeLinea/2.0);
		//paintRomboRelleno.setStrokeWidth(x);
		//TCasilla.anchoRomboRelleno = x;                         //02-ene-2014
		//paintRaya.setStrokeWidth((float) (0.40 * x));
		//TCasilla.anchoRaya = (float) (0.40 * x);               //02-ene-2014
		
	}//Fin de void SetSize(float a)
	//--------------------------------------
//Nuevo Metodo, 02-ene-2014
public  void SetApha(int a){
 if(0 <= a && a <= 255)
	 this.alpha = a;
// else
	// alpha = 255;
}//Fin de void SetAlpha(int a)
//--------------------------------------
public static float GetSize(){
	return SizeA;
}//Fin de void SetSize(float a)
//--------------------------------------

public 	void SetPtoTL (TPoint ptoTL){
		PtoTopLeft.x = ptoTL.x;
		PtoTopLeft.y = ptoTL.y;
	
	}
	//--------------------------------------
public 	void SetCarta (TCard carta){
	MiCarta = carta;
}
    //--------------------------------------
public 	TCard GetCarta (){
	return MiCarta;
}
//--------------------------------------
	//retorna true si un pto del canvas pertenece a la casilla, retorna false sino
public 	int PertenecePto (TPoint p, boolean p1){
		if(this.MiCarta == null)  //13 sept 2013
			return 0; //13 sept 2013
		if(p.x >= this.PtoTopLeft.x && p.x <= (this.PtoTopLeft.x + SizeA) && p.y >= this.PtoTopLeft.y && p.y <= (this.PtoTopLeft.y + SizeA)){
			seleccionada = !seleccionada;
			ply1 = p1; 
			if(seleccionada == false){
				//TCasilla.paintCasillaSeleccionada.setColor(Color.YELLOW);
				return 1; //Desmarco la casilla
			}
			return 2; //Marco la casilla
		}
		else{
			return 0;
		}
	}
	//--------------------------------------
//Devuelve el pto central donde debe dibujarse el rectangulo, el rombo , o el circulo
//TPoint Distribucion (TipoFig f, CtdFig c,float SizeAa, TPoint PtoLT,  int pos)
private  TPoint Distribucion(int pos){
	    TPoint P = new TPoint();
		if(pos >= 0 && pos <= MiCarta.cantidadFig){
			P.x = (float) (PtoTopLeft.x + SizeA/2.0);//Coordenadas 
			P.y = (float) (PtoTopLeft.y + SizeA/2.0);//del Pto Central
			if(MiCarta.tipo == 0)
				switch(MiCarta.cantidadFig){
				 case 0:
					return P;
				 case 1:
					if(pos == 1)
						P.y = (float) (P.y + 2.0/30.0 * SizeA + A/2.0);
					else
						P.y = (float) (P.y - 2.0/30.0 * SizeA - A/2.0);
					return P;
				case 2: //default
					if(pos == 1)
						return P;
					else if (pos == 2)
						P.y = (float) (P.y + 3.0/30.0 * SizeA + A);
					else
						P.y = (float) (P.y - 3.0/30.0 * SizeA - A);
					return P;
				}//switch(MiCarta.cantidadFig)
			else //if(MiCarta.tipo == TipoFig.RECTANGULO)
			{
				switch(MiCarta.cantidadFig){
				 case 0:
					 return P;
				 case 1:
					if(pos == 1)
						P.x = (float) (P.x + 0.15 * SizeA + radio/2.0);
					else
						P.x = (float) (P.x - 0.15 * SizeA - radio/2.0);
					return P;
				case 2: //default
					if(pos == 1){
						P.x = (float) (P.x - 0.15 * SizeA - radio/2.0);
					    P.y = (float) (P.y + 0.15 * SizeA + radio/2.0);
					}
					else if (pos == 2){
						P.x = (float) (P.x + 0.15 * SizeA + radio/2.0);
					    P.y = (float) (P.y + 0.15 * SizeA + radio/2.0);
					}
					else
					    P.y = (float) (P.y - 0.15 * SizeA - radio/2.0);
					return P;
				}//II switch(MiCarta.cantidadFig)
			}//else
			return P;
		}//if(pos >= 0 && pos <= MiCarta.cantidadFig.ordinal())
		else
			return null;	
	}//fin de TPoint Distribucion(int pos)
//---------------------------------------
//Dibujar figuras
//---------------------------------------
//Rombo
//--------------------------------------
//Se dibuja el rombo a partir de un arreglo de puntos
//Luego se dibuja la raya
private void romborayado(TPoint PtoCentral, Canvas canvas){
	float [] Ptos = {(float)(PtoCentral.x-d/2.0), PtoCentral.y, PtoCentral.x, (float)(PtoCentral.y-d/2.0),PtoCentral.x, (float)(PtoCentral.y-d/2.0),(float)(PtoCentral.x+d/2.0),PtoCentral.y,(float)(PtoCentral.x+d/2.0),PtoCentral.y, PtoCentral.x,(float)(PtoCentral.y + d/2.0),PtoCentral.x,(float)(PtoCentral.y + d/2.0), (float)(PtoCentral.x-d/2.0), PtoCentral.y};
	paintLineas.setColor(MiCarta.Colour);
	paintLineas.setAlpha(alpha);
	this.paintLineas.setStrokeWidth(anchoDeLinea);
	canvas.drawLines(Ptos, paintLineas); 
	paintRaya.setColor(MiCarta.Colour);
	paintRaya.setAlpha(alpha);
	paintRaya.setStrokeWidth((float) (0.40 * (float)(d/1.4142135623730950488016887242097 + anchoDeLinea/2.0))); //02-ene-2014
	canvas.drawLine((float)(PtoCentral.x - d/4.0),(float)(PtoCentral.y + d/4.0) , (float)(PtoCentral.x + d/4.0), (float)(PtoCentral.y - d/4.0), paintRaya);
}
//--------------------------------------
//El rombo relleno se dibuja como una sola linea suficientemente ancha
private void romborelleno(TPoint PtoCentral, Canvas canvas){
	paintRomboRelleno.setColor(MiCarta.Colour);
	paintRomboRelleno.setAlpha(alpha);
	this.paintRomboRelleno.setStrokeWidth((float)(d/1.4142135623730950488016887242097 + anchoDeLinea/2.0)); //02-ene-2014
	canvas.drawLine((float)(PtoCentral.x - d/4.0),(float)(PtoCentral.y + d/4.0) , (float)(PtoCentral.x + d/4.0), (float)(PtoCentral.y - d/4.0), paintRomboRelleno);
}
//--------------------------------------
//Se dibuja el rombo a partir de un arreglo de puntos
private void rombovacio(TPoint PtoCentral, Canvas canvas){
	float [] Ptos = {(float)(PtoCentral.x-d/2.0), PtoCentral.y, PtoCentral.x, (float)(PtoCentral.y-d/2.0),PtoCentral.x, (float)(PtoCentral.y-d/2.0),(float)(PtoCentral.x+d/2.0),PtoCentral.y,(float)(PtoCentral.x+d/2.0),PtoCentral.y, PtoCentral.x,(float)(PtoCentral.y + d/2.0),PtoCentral.x,(float)(PtoCentral.y + d/2.0), (float)(PtoCentral.x-d/2.0), PtoCentral.y};
	this.paintLineas.setColor(MiCarta.Colour);
	this.paintLineas.setAlpha(alpha);
	this.paintLineas.setStrokeWidth(anchoDeLinea);
	canvas.drawLines(Ptos, paintLineas); 
}
//--------------------------------------
//rectangulo
//--------------------------------------
//Se dibuja el rectangulo y juego se dibuja la raya
private void rectangulorayado(TPoint PtoCentral, Canvas canvas){
	this.paintLineas.setColor(MiCarta.Colour);
	this.paintLineas.setAlpha(alpha);
	this.paintLineas.setStrokeWidth(anchoDeLinea);
	canvas.drawRect((float)(PtoCentral.x - L/2.0), (float)(PtoCentral.y - A/2.0),(float)(PtoCentral.x + L/2.0),(float) (PtoCentral.y + A/2.0),paintLineas);
	this.paintRayaRect.setColor(MiCarta.Colour);
	this.paintRayaRect.setAlpha(alpha);
	this.paintRayaRect.setStrokeWidth(anchoRayaRect);
	//canvas.drawLine((float)(PtoCentral.x - A/2.0), (float)(PtoCentral.y + A/2.0),(float)(PtoCentral.x + A/2.0),(float) (PtoCentral.y - A/2.0),paintRaya);
	canvas.drawLine(PtoCentral.x, (float)(PtoCentral.y + A/2.0),PtoCentral.x,(float) (PtoCentral.y - A/2.0),paintRayaRect);
}
//--------------------------------------
////Se dibuja el rectangulo relleno
private void rectangulorelleno(TPoint PtoCentral, Canvas canvas){
	this.paintRelleno.setColor(MiCarta.Colour);
	this.paintRelleno.setAlpha(alpha);
	canvas.drawRect((float)(PtoCentral.x - L/2.0), (float)(PtoCentral.y - A/2.0),(float)(PtoCentral.x + L/2.0),(float) (PtoCentral.y + A/2.0),paintRelleno);
}
//--------------------------------------
private void rectangulovacio(TPoint PtoCentral, Canvas canvas){
	this.paintLineas.setColor(MiCarta.Colour);
	this.paintLineas.setAlpha(alpha);
	this.paintLineas.setStrokeWidth(anchoDeLinea);
	
	canvas.drawRect((float)(PtoCentral.x - L/2.0), (float)(PtoCentral.y - A/2.0),(float)(PtoCentral.x + L/2.0),(float) (PtoCentral.y + A/2.0),paintLineas);
}
//--------------------------------------
//circulo
//--------------------------------------
//Se dibuja la circunferencia y luego se dibujan dos arcos opuestos para hacer la raya
private void circulorayado(TPoint PtoCentral, Canvas canvas){
	this.paintLineas.setColor(MiCarta.Colour);
	this.paintLineas.setAlpha(alpha);
	this.paintLineas.setStrokeWidth(anchoDeLinea);
	canvas.drawCircle(PtoCentral.x, PtoCentral.y, radio, this.paintLineas);
	this.paintRaya.setColor(MiCarta.Colour);
	this.paintRaya.setAlpha(alpha);
	paintRaya.setStrokeWidth((float) (0.40 * (float)(d/1.4142135623730950488016887242097 + anchoDeLinea/2.0)));  //02-ene-2014
	RectF oval = new RectF();
	oval.set((float)(PtoCentral.x - radio), (float)(PtoCentral.y - radio),(float)(PtoCentral.x + radio),(float) (PtoCentral.y + radio));
	canvas.drawArc(oval, (float)310,(float)0, true, this.paintRaya);
	canvas.drawArc(oval, (float)130,(float)0, true, this.paintRaya);
}
//--------------------------------------
private void circulorelleno(TPoint PtoCentral, Canvas canvas){
	this.paintRelleno.setColor(MiCarta.Colour);
	this.paintRelleno.setAlpha(alpha);
	canvas.drawCircle(PtoCentral.x, PtoCentral.y, radio, this.paintRelleno);
}
//--------------------------------------
private void circulovacio(TPoint PtoCentral, Canvas canvas){
	this.paintLineas.setColor(MiCarta.Colour);
	this.paintLineas.setAlpha(alpha);
	this.paintLineas.setStrokeWidth(anchoDeLinea);
	canvas.drawCircle(PtoCentral.x, PtoCentral.y, radio,this.paintLineas);
}
//--------------------------------------
public void Dibujate(Canvas canvas){
	TPoint p;
	RectF cartaRect = new RectF();
	if(MiCarta != null){
	cartaRect.set(this.PtoTopLeft.x, this.PtoTopLeft.y, this.PtoTopLeft.x + TCasilla.SizeA , this.PtoTopLeft.y + TCasilla.SizeA );
	this.paintCasilla.setAlpha(alpha);
	canvas.drawRoundRect(cartaRect, 10,10 ,paintCasilla);
	switch(MiCarta.tipo){
		case 1:{
			switch(MiCarta.rell){
		    	case 0:{
		    		for(int i=0; i <= MiCarta.cantidadFig ;i++){
		    			p= Distribucion(i);
		    			this.romborelleno(p, canvas);
		    		}//for...
		    		break;}
		    	case 1:{
		    		for(int i=0; i <= MiCarta.cantidadFig;i++){
		    			p= Distribucion(i);
		    			this.romborayado(p, canvas);
		    		}//for...
		    		break;}
		    	default:{//case VACIO:
		    		for(int i=0; i <= MiCarta.cantidadFig;i++){
		    			p= Distribucion(i);
		    			this.rombovacio(p, canvas);
		    		}//for...
		    		break;}
			}//switch(MiCarta.rell) I
			break;
		}//case ROMBO
		case 0:{
			switch(MiCarta.rell){
	    	case 0:{
	    		for(int i=0; i <= MiCarta.cantidadFig;i++){
	    			p= Distribucion(i);
	    			this.rectangulorelleno(p, canvas);
	    		}//for
	    		break;}
	    	case 1:{
	    		for(int i=0; i <= MiCarta.cantidadFig;i++){
	    			p= Distribucion(i);
	    			this.rectangulorayado(p, canvas);
	    		}//for
	    		break;}
	    	default:{//case VACIO:
	    		for(int i=0; i <= MiCarta.cantidadFig;i++){
	    			p= Distribucion(i);
	    			this.rectangulovacio(p, canvas);
	    		}//for
	    		break;}
		}//switch(MiCarta.rell) II
			break;
	    }//case RECTANGULO:
		default:{ //case CIRCULO
			switch(MiCarta.rell){
	    	case 0:{
	    		for(int i=0; i <= MiCarta.cantidadFig;i++){
	    			p= Distribucion(i);
	    			this.circulorelleno(p, canvas);
	    		}//for
	    		break;}
	    	case 1:{
	    		for(int i=0; i <= MiCarta.cantidadFig;i++){
	    			p= Distribucion(i);
	    			this.circulorayado(p, canvas);
	    		}//for
	    		break;}
	    	default:{//case VACIO:
	    		for(int i=0; i <= MiCarta.cantidadFig;i++){
	    			p= Distribucion(i);
	    			this.circulovacio(p, canvas);
	    		}//for
	    		break;}
		}//switch(MiCarta.rell) III
	    }//default:
	}//switch(MiCarta.tipo)
	 if(seleccionada){
		cartaRect.set(this.PtoTopLeft.x, this.PtoTopLeft.y, this.PtoTopLeft.x + TCasilla.SizeA , this.PtoTopLeft.y + TCasilla.SizeA );
		if(ply1)
			canvas.drawRoundRect(cartaRect, 10,10 ,TCasilla.paintCasillaSeleccionada);
		else
			canvas.drawRoundRect(cartaRect, 10,10 ,TCasilla.paintCasillaSeleccionada2);
	 } 
	}//	if(MiCarta != null)
}//public void Diujate(Canvas canvas)
//--------------------------------------
public boolean EsSET(TCasilla B, TCasilla C)
{
//Mira si casilla A, B, y C hacen SET. A es a la que pertenece el metodo.
	
	if(this.MiCarta==null || B.MiCarta==null ||  C.MiCarta==null)
		return false;
	/*
	if(((this.MiCarta.cantidadFig == B.MiCarta.cantidadFig) && (B.MiCarta.cantidadFig ==  C.MiCarta.cantidadFig)) || ((this.MiCarta.cantidadFig != B.MiCarta.cantidadFig) && (B.MiCarta.cantidadFig !=  C.MiCarta.cantidadFig)&& (this.MiCarta.cantidadFig != C.MiCarta.cantidadFig))){
		if(((this.MiCarta.color == B.MiCarta.color) && (B.MiCarta.color ==  C.MiCarta.color)) || ((this.MiCarta.color != B.MiCarta.color) && (B.MiCarta.color !=  C.MiCarta.color)&& (this.MiCarta.color != C.MiCarta.color)))
			if(((this.MiCarta.rell == B.MiCarta.rell) && (B.MiCarta.rell ==  C.MiCarta.rell)) || ((this.MiCarta.rell != B.MiCarta.rell) && (B.MiCarta.rell !=  C.MiCarta.rell)&& (this.MiCarta.rell != C.MiCarta.rell)))
				if(((this.MiCarta.tipo == B.MiCarta.tipo) && (B.MiCarta.tipo ==  C.MiCarta.tipo)) || ((this.MiCarta.tipo != B.MiCarta.tipo) && (B.MiCarta.tipo !=  C.MiCarta.tipo)&& (this.MiCarta.tipo != C.MiCarta.tipo))){
					//TCasilla.paintCasillaSeleccionada.setColor(Color.GREEN); SetGamev0.2
					return true;//Hay SET
				}
	}//1er if(((this.MiCarta.cantidadFig == B.MiCarta.cantidadFig)...
	//TCasilla.paintCasillaSeleccionada.setColor(Color.RED);   19-11-2013  */
	if((this.MiCarta.cantidadFig + B.MiCarta.cantidadFig + C.MiCarta.cantidadFig)%3 == 0)
		if((this.MiCarta.color + B.MiCarta.color + C.MiCarta.color)%3 == 0)
			if((this.MiCarta.rell + B.MiCarta.rell + C.MiCarta.rell)%3 == 0)
				if((this.MiCarta.tipo + B.MiCarta.tipo + C.MiCarta.tipo)%3 == 0)
					return true;
					//return false;     //linea de prueVa
	//return true; //linea de prueVa
	return false;//No hay SET
}//public boolean EsSET(TCasilla B, TCasilla C)
//--------------------------------------
public void Deseleccionar(){
	this.seleccionada = false;
	//TCasilla.paintCasillaSeleccionada.setColor(Color.YELLOW);	
}
//--------------------------------------
}//Fin de la declaracion de TCasilla