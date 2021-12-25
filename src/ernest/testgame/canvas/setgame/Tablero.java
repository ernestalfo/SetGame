package ernest.testgame.canvas.setgame;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Tablero {
	//atributos:
	private int CtdSel = 0;
	
	private int [] SEL = new int[3];
	private int [] SET = new int[3];
	
	private int CtdCasillasOcupadas;
	//private TPaqueteCartas MiPaqueteCartas = new TPaqueteCartas();
	private TPaqueteCartas MiPaqueteCartas;
	private TCasilla [] board = new TCasilla[21];
	
	//private int ply1_setsrealizados = 0, ply2_setsrealizados = 0;
	public int ply1_setsrealizados = 0, ply2_setsrealizados = 0;
	int ctdsets = 0;

	private float anchopaintcards, anchopaintleft, anchopaintnumber,  anchopaintply1, anchoplayer;

	private Paint paintmarcador = new Paint();
	private Paint paintnumber = new Paint();
	private Paint cartelpossiblessets = new Paint();
	private Paint cartelPLAYER = new Paint();  
	private Paint paintplayer1 = new Paint();
	private Paint paintplayer2 = new Paint();
	
	boolean tablet = false;
	
	TPoint TLleft = new TPoint();
	TPoint TLnumberI = new TPoint();
	TPoint TLcards = new TPoint();
	TPoint TLply1 = new TPoint();
	TPoint TLnumberII = new TPoint();
	TPoint TLply2 = new TPoint();
	TPoint TLnumberIII = new TPoint();
	TPoint TLpossiblessets = new TPoint();  //agregado el 17-dic-2013
	TPoint TLplayer = new TPoint();     //agregado el 15/06/2014
	
	int framenumber = 0; //agregado el 02-ene-2014
	boolean intransition = false;
	
	//NUEVO VERSION 0_6:
	boolean ply1;  //Indicara que jugador es el dueNo de este tablero
	boolean multi; //Indica si esta en modo multijugador o no
	MultiGame_Activity MultiGameAct;  //Sera la actividad a la que dirigirse para enviar la info del juego
	int jugadorencurso = 0;
	private TMonedaJusta MiMoneda;
	private boolean InColision = false;
	
	private int CtdSel_Ant = 0;
	private int [] SEL_Ant = new int[3];
	
	//Nuevo version 0.7d
	float escala = (float) 0.1125;             //Aqui esta bien 0.1125 = 13.5px / 120dpi
	//float escalaTablet = (float) 0.15;         //Aqui asumi q mi tablet tambien tiene 120dpi, 0.15 = 18px / 120dpi
	float pxsize;
	
	float espacio_letrero1, espacio_letrero2, espacio_letrero3, espacio_letrero4, espacio_letrero5, espacio_letrero6;
	
	final int density;
 //------------------------------------
//Costructor:
public Tablero(MultiGame_Activity MGact, boolean P1, boolean multiP, boolean Istablet, int densidad)
{
	MultiGameAct = MGact;
	ply1 = P1;
	multi = multiP;
	
	MiPaqueteCartas = new TPaqueteCartas();
	for(int i=0; i<21; i++)
		board[i]=new TCasilla(densidad);
	//---
	if(Istablet)
		 tablet = true;

	density = densidad;
	pxsize = densidad*escala;
	paintmarcador.setTextSize(pxsize);  
	paintnumber.setTextSize(pxsize);      
	paintplayer1.setTextSize(pxsize);     
	paintplayer2.setTextSize(pxsize);   
	cartelpossiblessets.setTextSize(pxsize);        
	cartelPLAYER.setTextSize(pxsize);       
	
	espacio_letrero1 = (float) (0.1666666666666667 * densidad);
	espacio_letrero2 = (float) (0.3333333333333333 * densidad);
	espacio_letrero3 = (float) (0.6666666666666667 * densidad);
	espacio_letrero4 = (float) (0.8333333333333333 * densidad);
	espacio_letrero5 = (float) (1.166666666666667 * densidad);
	espacio_letrero6 = (float) (1.333333333333333 * densidad);
	
	anchopaintnumber = paintnumber.measureText("00");
	anchopaintcards = paintmarcador.measureText("cards");
	anchopaintply1 = paintplayer1.measureText("Ply1");
	anchopaintleft = paintmarcador.measureText("Left");
	anchoplayer = cartelPLAYER.measureText("Player 1");  
	
	//paintmarcador.setColor(Color.CYAN);
	paintmarcador.setColor(Color.rgb(255, 127, 0));
	paintplayer1.setColor(Color.YELLOW);
	//paintplayer1.setAlpha(240);
	paintplayer2.setColor(Color.CYAN);
	//paintplayer2.setAlpha(240);
	paintnumber.setColor(Color.WHITE);
	cartelpossiblessets.setColor(Color.rgb(255, 127, 0));
	cartelpossiblessets.setAlpha(220);
	cartelPLAYER.setColor(Color.rgb(160,180, 0));
	cartelPLAYER.setAlpha(240); 
	
	SEL[0]=2;    //modificacion 21-dic-2013
	SEL[1]=5;    //modificacion 21-dic-2013
	SEL[2]=10;   //modificacion 21-dic-2013, Se daba el caso de que como al iniciar, si no se inicializaban 
	
}//public Tablero()
public Tablero(MultiGame_Activity MGact, boolean P1, boolean multiP, long seed, boolean Istablet, int densidad)
{
	MultiGameAct = MGact;
	ply1 = P1;
	multi = multiP;
	
	MiPaqueteCartas = new TPaqueteCartas(seed);
	for(int i=0; i<21; i++)
		board[i]=new TCasilla(densidad);
	//---
	MiMoneda = new TMonedaJusta(seed);
	//---
	if(Istablet)
		 tablet = true;
	
	density = densidad;
	pxsize = densidad*escala;
	paintmarcador.setTextSize(pxsize);  
	paintnumber.setTextSize(pxsize);      
	paintplayer1.setTextSize(pxsize);     
	paintplayer2.setTextSize(pxsize);   
	cartelpossiblessets.setTextSize(pxsize);        
	cartelPLAYER.setTextSize(pxsize);       
	
	espacio_letrero1 = (float) (0.1666666666666667 * densidad);
	espacio_letrero2 = (float) (0.3333333333333333 * densidad);
	espacio_letrero3 = (float) (0.6666666666666667 * densidad);
	espacio_letrero4 = (float) (0.8333333333333333 * densidad);
	espacio_letrero5 = (float) (1.166666666666667 * densidad);
	espacio_letrero6 = (float) (1.333333333333333 * densidad);
	
	anchopaintnumber = paintnumber.measureText("00");
	anchopaintcards = paintmarcador.measureText("cards");
	anchopaintply1 = paintplayer1.measureText("SETS");
	anchopaintleft = paintmarcador.measureText("Left");
	anchoplayer = cartelPLAYER.measureText("Player 1");  
	
	paintmarcador.setColor(Color.rgb(255, 127, 0));
	paintplayer1.setColor(Color.YELLOW);
	paintplayer2.setColor(Color.CYAN);
	paintnumber.setColor(Color.WHITE);
	anchopaintcards = paintmarcador.measureText("cards");
	cartelpossiblessets.setColor(Color.rgb(255, 127, 0));
	cartelpossiblessets.setAlpha(220);
	cartelPLAYER.setColor(Color.rgb(160,180, 0));
	cartelPLAYER.setAlpha(240);

	SEL[0]=2;    //modificacion 21-dic-2013
	SEL[1]=5;    //modificacion 21-dic-2013
	SEL[2]=10;   //modificacion 21-dic-2013, Se daba el caso de que como al iniciar, si no se inicializaban, y entonces no habia set en las primeras 12 ... 
	

}//public Tablero()
/*
public void SetTablet(){
	 tablet = true;
	 paintmarcador.setTextSize(18);   //(solo en v. tablet)
	 paintnumber.setTextSize(18);    //(solo en v. tablet)
	 paintplayer1.setTextSize(18);
	 paintplayer2.setTextSize(18);
	 cartelpossiblessets.setTextSize(20);           //(solo en v. tablet)
	 cartelPLAYER.setTextSize(20);  //Agregado el 15/06/2014, SetGame_v0.5d         
	 anchopaintnumber = paintnumber.measureText("00");
	 anchopaintcards = paintmarcador.measureText("cards");
	 anchopaintply1 = paintplayer1.measureText("SETS");
	 anchopaintleft = paintmarcador.measureText("Left");
	 anchoplayer = cartelPLAYER.measureText("Player 1");  //agregado el 15/06/2014
}
*/

public void ResetTablero(){
	//for(int i =0; i<12; i++)   19-11-2013
	// board[i].Deseleccionar();  //ya se deseleccionaron, al seleccionar la tercera en el metodo Seleccionar
	DeseleccionarTodasTodas();
	for(int i =0; i<12; i++)
		//board[i].SetApha(255);
		board[i].SetApha(225);
	framenumber = 0;
	intransition = false;
	ply1_setsrealizados = 0;
	ply2_setsrealizados = 0;
	//CtdSel = 0;        Ya se hizo en DeseleccionarTodastodas
	//for(int k = 0; k < 12; k++)
		 //MiPaqueteCartas.Recibir(board[k].GetCarta());
	CtdCasillasOcupadas = 0;
    MiPaqueteCartas.Reset();
    SEL[0]=2;    //modificacion 21-dic-2013
	SEL[1]=5;    //modificacion 21-dic-2013
	SEL[2]=10;   //modificacion 21-dic-2013,Aqui no creo que sea realmente necesario, lo dejo por buenas 
	//practicas
	Repartir();
}//public ResetTablero()
//------------------------------------
//Seleccionar. A partir de las coordenadas del pto. Devuelve :
// 0 - No ha sido seleccionada ninguna casilla
// 1 - La carta a seleccionar, ya estaba seleccionada y se desmarco(o se deslecciono)
// 2 - Se selecciono una casilla, dado que el pto pertenece a esta
// 3 - Se selecciono una tercera casilla para hacer SET y fue positivo
// 4 - Se selecciono una tercera casilla para hacer SET y fue negativo
// 5 - Se selecciono MENU
public int Seleccionar(TPoint pto){
	int i;
	if(!intransition)
	for(i=0; i < 12; i++){
		int x = board[i].PertenecePto(pto, ply1);
		if(x == 2){//MarcO la casilla
			if(multi){
				int TimeTemp = MultiGameAct.mesa.Reloj.GetTime();
				MultiGameAct.Send(Integer.toString(i)+':'+Integer.toString(TimeTemp)+':'+Integer.toString(MultiGameAct.mesa.TurnoDeJuego));
				//MultiGameAct.mesa.TiempoDeAlarma = TimeTemp + 10;
			}
				//------------------------------------------
			SEL[CtdSel]=i;
			CtdSel++;
			if(CtdSel ==1) //NUEVA VERSION 0_6
			{
				if(ply1)
				 jugadorencurso = 1;
				else
			     jugadorencurso = 2;	
			}
			if(CtdSel == 3){//Ya hay 3 seleccionadas
				this.DeseleccionarTodasTodas(); 
				if(board[SEL[0]].EsSET(board[SEL[1]], board[SEL[2]])){
					if(ply1)
						ply1_setsrealizados++;
					else 
						ply2_setsrealizados++;
					jugadorencurso = 0;
					InColision = false;
					return 3; //Hay SET
				}
				else{
					if(ply1 && multi)
						ply1_setsrealizados--;
					else if(multi)
						ply2_setsrealizados--;
					InColision = false;
					jugadorencurso = 0;
					return 4; //No hay SET
				}
			}//if(CtdSel==3)
			else
				return 2;//MarcO la casilla
		}//if(x == 2)
		else
			if(x == 1){
				if(multi)
					//MultiGameAct.Send(Integer.toString(i));
					//MultiGameAct.Send(i);
					MultiGameAct.Send(Integer.toString(i)+':'+Integer.toString(MultiGameAct.mesa.Reloj.GetTime())+':'+Integer.toString(MultiGameAct.mesa.TurnoDeJuego));
				CtdSel--;
				return 1;//DesmarcO la casilla
			}
	}//for...
	//if(!multi)
	//if((pto.x >= TLmenu.x && pto.x <= TLmenu.x + anchomenu) && (pto.y >= TLmenu.y - 10)) //Dandole 10 mas para q no sea tan dificil tocarlo
		//return 5;
	//else
	return 0;
}//public int Seleccionar(TPoint pto)
//------------------------------------
//NUEVO VERSION 0_6
//Agregado especialmente para el juego en modo multiplayer
//Seleccionar. A partir del num de casilla seleccionada. Devuelve :
//1 - La carta a seleccionar, ya estaba seleccionada y se desmarco(o se deslecciono)
//2 - Se selecciono una casilla, dado que el pto pertenece a esta
//3 - Se selecciono una tercera casilla para hacer SET y fue positivo
//4 - Se selecciono una tercera casilla para hacer SET y fue negativo
//5 - Hubo colision pero sigue jugando el dueNo del tablero
//6 - Producto de una colision anterior se decidio dejar jugando al dueNo del taVlero
//y no hace falta analizar esta nueva colision, porque el turno donde ocurrio la ant
//todavia se desarrolla
public int Seleccionar(int CasSel){
		if(InColision){
			return 6;
		}
		if(ply1)
		{
			if(jugadorencurso == 1)
			{
			//Entra aqui si estaba jugando el dueNo del tablero y se recibe otra seleccion por otro lado
				if(MiMoneda.LanzarMoneda() == 1){
					InColision = true;
					return 5;
				}
					
				else{
					DeseleccionarTodasTodas();
					//MultiGameAct.mesa.cont10Seg = 0;
					MultiGameAct.mesa.Alarma10Seg = false;
					//jugadorencurso = 2;
				}
			}
		}else if(jugadorencurso == 2)
		{
		//Entra aqui si estaba jugando el dueNo del tablero y se recibe otra seleccion por otro lado
			if(MiMoneda.LanzarMoneda() == 2){
				InColision = true;
				return 5;
			}
			else{
				DeseleccionarTodasTodas();
				//MultiGameAct.mesa.cont10Seg = 0; 
				MultiGameAct.mesa.Alarma10Seg = false;
				//jugadorencurso = 1;
			}
		}
		//-------------------------------------------------		
		if(!board[CasSel].seleccionada)//MarcO la casilla
		{
			board[CasSel].seleccionada = true;
			///if(ply1)
				//board[CasSel].ply1 = false;
			//else
				//board[CasSel].ply1 = true;
			board[CasSel].ply1 = !ply1;
			SEL[CtdSel]=CasSel;
			CtdSel++;
			//if(CtdSel ==1) //NUEVA VERSION 0_6
			//{
				if(ply1)
				 jugadorencurso = 2;
				else
			     jugadorencurso = 1;	
			//}
			if(CtdSel == 3)//Ya hay 3 seleccionadas
			{
				this.DeseleccionarTodasTodas(); 
				if(board[SEL[0]].EsSET(board[SEL[1]], board[SEL[2]]))
				{
					if(ply1)
						ply2_setsrealizados++;//Aqui es al reves, porq este met sera usado para seleccionar en este tablero 
					else				      //lo q el otro jugador ha seleccionado en su tablero
						ply1_setsrealizados++;
					jugadorencurso = 0;
					InColision = false;
					return 3; //Hay SET
				}
				else
				{
					InColision = false;
					jugadorencurso = 0;
					if(ply1)
						ply2_setsrealizados--;//Aqui es al reves, porq este met sera usado para seleccionar en este tablero 
					else				      //lo q el otro jugador ha seleccionado en su tablero
						ply1_setsrealizados--;
					return 4; //No hay SET
				}
			}//if(CtdSel==3)
			else
				return 2;//MarcO la casilla
		}//if(!board[CasSel].seleccionada)
		else  //La casilla ya estaba seleccionada
		{
			CtdSel--;
			board[CasSel].seleccionada = false;
			if(ply1)
				 jugadorencurso = 2;
				else
			     jugadorencurso = 1;
			return 1;//DesmarcO la casilla
		}
}//public int Seleccionar(int CasSel)
//-------------------------------------------
public void Transicion()
{
	switch(framenumber){
	case 0:
		intransition = true;
		board[SEL[0]].SetApha(170);
		board[SEL[1]].SetApha(170);
		board[SEL[2]].SetApha(170);
		break;
	case 1:	
		board[SEL[0]].SetApha(160);
		board[SEL[1]].SetApha(160);
		board[SEL[2]].SetApha(160);
		break;
	case 2:
		board[SEL[0]].SetApha(150);
		board[SEL[1]].SetApha(150);
		board[SEL[2]].SetApha(150);
		break;
	case 3:
		board[SEL[0]].SetApha(140);
		board[SEL[1]].SetApha(140);
		board[SEL[2]].SetApha(140);
		break;
	case 4:
		board[SEL[0]].SetApha(130);
		board[SEL[1]].SetApha(130);
		board[SEL[2]].SetApha(130);
		break;
	case 5:
		board[SEL[0]].SetApha(120);
		board[SEL[1]].SetApha(120);
		board[SEL[2]].SetApha(120);
		break;
	case 6:
		board[SEL[0]].SetApha(110);
		board[SEL[1]].SetApha(110);
		board[SEL[2]].SetApha(110);
		break;
	case 7:
		board[SEL[0]].SetApha(100);
		board[SEL[1]].SetApha(100);
		board[SEL[2]].SetApha(100);
		break;
	case 8:
		board[SEL[0]].SetApha(90);
		board[SEL[1]].SetApha(90);
		board[SEL[2]].SetApha(90);
		break;
	case 9:
		board[SEL[0]].SetApha(80);
		board[SEL[1]].SetApha(80);
		board[SEL[2]].SetApha(80);
		break;
	case 10:
		board[SEL[0]].SetApha(70);
		board[SEL[1]].SetApha(70);
		board[SEL[2]].SetApha(70);
		break;
	case 11:
		board[SEL[0]].SetApha(60);
		board[SEL[1]].SetApha(60);
		board[SEL[2]].SetApha(60);
		break;
	case 12:
		board[SEL[0]].SetApha(50);
		board[SEL[1]].SetApha(50);
		board[SEL[2]].SetApha(50);
		break;
	case 13:	
		board[SEL[0]].SetApha(40);
		board[SEL[1]].SetApha(40);
		board[SEL[2]].SetApha(40);
		break;
	case 14:	
		board[SEL[0]].SetApha(30);
		board[SEL[1]].SetApha(30);
		board[SEL[2]].SetApha(30);
	case 15:	
		break;
	case 16:	
		board[SEL[0]].SetApha(40);
		board[SEL[1]].SetApha(40);
		board[SEL[2]].SetApha(40);
		break;
	case 17:	
		board[SEL[0]].SetApha(50);
		board[SEL[1]].SetApha(50);
		board[SEL[2]].SetApha(50);
		break;
	case 18:	
		board[SEL[0]].SetApha(60);
		board[SEL[1]].SetApha(60);
		board[SEL[2]].SetApha(60);
		break;
	case 19:	
		board[SEL[0]].SetApha(70);
		board[SEL[1]].SetApha(70);
		board[SEL[2]].SetApha(70);
		break;
	case 20:
		board[SEL[0]].SetApha(80);
		board[SEL[1]].SetApha(80);
		board[SEL[2]].SetApha(80);
		break;
	case 21:
		board[SEL[0]].SetApha(90);
		board[SEL[1]].SetApha(90);
		board[SEL[2]].SetApha(90);
		break;
	case 22:
		board[SEL[0]].SetApha(100);
		board[SEL[1]].SetApha(100);
		board[SEL[2]].SetApha(100);
		break;
	case 23:
		board[SEL[0]].SetApha(110);
		board[SEL[1]].SetApha(110);
		board[SEL[2]].SetApha(110);
	case 24:
		board[SEL[0]].SetApha(120);
		board[SEL[1]].SetApha(120);
		board[SEL[2]].SetApha(120);
	case 25:
		board[SEL[0]].SetApha(130);
		board[SEL[1]].SetApha(130);
		board[SEL[2]].SetApha(130);
		break;
	case 26:
		board[SEL[0]].SetApha(140);
		board[SEL[1]].SetApha(140);
		board[SEL[2]].SetApha(140);
	case 27:
		board[SEL[0]].SetApha(150);
		board[SEL[1]].SetApha(150);
		board[SEL[2]].SetApha(150);
	case 28:
		board[SEL[0]].SetApha(160);
		board[SEL[1]].SetApha(160);
		board[SEL[2]].SetApha(160);
	case 29:
		board[SEL[0]].SetApha(170);
		board[SEL[1]].SetApha(170);
		board[SEL[2]].SetApha(170);
		break;
	case 30: 
		//board[SEL[0]].SetApha(255);
		//board[SEL[1]].SetApha(255);
		//board[SEL[2]].SetApha(255);
		board[SEL[0]].SetApha(225);
		board[SEL[1]].SetApha(225);
		board[SEL[2]].SetApha(225);
		framenumber = 0;
		intransition = false;
		return;
	//default:
		//return;
	}
    framenumber++;
}
//------------------------------------
//Repartir se encarga de que siempre haya 12 casillas ocupadas en el tablero
//y de que tanto al principio como en ocasiones posteriores entre estas 12 cartas exista
//aunque sea la posibilidad de vislumbrar un conjunto(un SET).
public boolean Repartir(){
	if(CtdCasillasOcupadas == 0){//Condicion Inicial
		for(int i=0; i<12; i++)
		{
			board[i].SetCarta(MiPaqueteCartas.Entregar());
			//CtdCasillasOcupadas++;
		}
		CtdCasillasOcupadas = 12;
	}//if(CtdCasillas == 0)
	else{
		//Se trata entonces de una reposicion
		board[SEL[0]].SetCarta(MiPaqueteCartas.Entregar());
		board[SEL[1]].SetCarta(MiPaqueteCartas.Entregar());
		board[SEL[2]].SetCarta(MiPaqueteCartas.Entregar());
	}
	//En este pto. ya existen 12 casillas ocupadas. Se verificara si existe SET en ellas:
	//for(int base = 0; base < CtdCasillasOcupadas...
	boolean centinela = true;
	//int combinaciones = 0;
	for(int base = 0; base < 10 && centinela; base++)
		for(int pivote = base + 1; pivote < 11 && centinela; pivote++)
			//for(int extension = base + 2; extension < 12 && centinela; extension++){
			for(int extension = pivote + 1; extension < 12 && centinela; extension++){  //Arreglado el 10/sept/2014 , q pena,... 
				//combinaciones++;
				if(board[base].EsSET(board[pivote], board[extension])){
					centinela = false;
					//15-dic-2013 Modificacion para que sirva para simular
					SET[0]=base;         //15-dic-2013
					SET[1]=pivote;       //15-dic-2013
					SET[2]=extension;    //15-dic-2013
					break; 	
				}//si hay set
			}//3er for...
		//combinaciones = combinaciones + 5;
	if(centinela){
		//Si entra aqui sera porque entre todas las combinaciones posibles con las 12 cartas 
		//no encontro ni un solo SET.
		//Se ocuparan todas las casillas que se puedan(mientras haya cartas en el paquete) del tablero(maximo 21)
		//nota: en 21 cartas tiene que existir al menos un SET (fuente wikipedia, razon :por probabilidades)
		for(int j = 12; j <21; j++)
		{
			TCard x = MiPaqueteCartas.Entregar();
			if(x == null)
				break;
			else{
				board[j].SetCarta(x);
				CtdCasillasOcupadas++;
			}//else
		}//for
		for(int base2 = 0; base2 < CtdCasillasOcupadas-2 && centinela; base2++)
			for(int pivote2 = base2 + 1; pivote2 < CtdCasillasOcupadas-1 && centinela; pivote2++){
				int extension2;
				if(base2 <= 10  && CtdCasillasOcupadas > 12)
					extension2 = 12;   //para no volver a cheqear las combinaciones que ya se chequearon anteriormente
				else 
					//extension2 = base2 + 2;
					extension2 = pivote2 + 1;   //Arreglado el 10/sept/2014 , q pena,...
				
				for(extension2 = extension2; extension2 < CtdCasillasOcupadas && centinela; extension2++){
					if(board[base2].EsSET(board[pivote2], board[extension2])){
						SET[0] = base2;
						SET[1] = pivote2;
						SET[2] = extension2;
						centinela = false;
						break; 	
					}//si hay set
				}//2do for
				}//3er for...
		if(centinela)//II if(centinela)
			return false; //Se acabo el juego no encontro SET
		else{
			//Encontro SET, si alguna carta de las que hizo SET esta fuera de las primeras 12 cartas, se traslada para esa zona(la de las primeras 12 cartas, "se permuta")
			TCard temporal;
			if(SET[0] >= 12){
				if(SET[1]!= SEL[0] && SET[2]!= SEL[0]){
					temporal = board[SEL[0]].GetCarta();
					board[SEL[0]].SetCarta(board[SET[0]].GetCarta());   //poniendo en la primera cas sel en el turno una de las cartas que hace SET
					board[SET[0]].SetCarta(temporal);
					SET[0] = SEL[0]; //Modificacion para que sirva para simular//15-dic-2013
				}
				else
					if(SET[1]!= SEL[1] && SET[2]!= SEL[1]){
						//Sino no se pudo permutar para la primera cas sel, porque ahi se encuentra una de las cartas que hacen SET, intenta aqui, en la segunda cas seleccionada
						temporal = board[SEL[1]].GetCarta();
						board[SEL[1]].SetCarta(board[SET[0]].GetCarta());	
						board[SET[0]].SetCarta(temporal);
						SET[0]=SEL[1];//Modificacion para que sirva para simular//15-dic-2013
					}
					else{
						temporal = board[SEL[2]].GetCarta();
						board[SEL[2]].SetCarta(board[SET[0]].GetCarta());	
						board[SET[0]].SetCarta(temporal);
						SET[0]=SEL[2];//Modificacion para que sirva para simular//15-dic-2013
						//20-dic-2013 la modificacion anterior era necesaria no solamente para simular,sino para garantizar realmente que
						//siempre haya set. Valido por supuesto para todos los lugares donde se hizo esta modificacion, con excepcion de cuando
						//se pregunta por el ultimo set(SET[2]). 
					}
				//board[SET[0]].SetCarta(temporal);
			}//if(SET[0] >= 12)
			if(SET[1] >= 12){
				if(SET[0]!= SEL[0] && SET[2]!= SEL[0]){
					temporal = board[SEL[0]].GetCarta();
					board[SEL[0]].SetCarta(board[SET[1]].GetCarta());
					board[SET[1]].SetCarta(temporal);  
					SET[1]=SEL[0];//Modificacion para que sirva para simular//15-dic-2013
					//20-dic-2013 la modificacion anterior...
				}
				else
					if(SET[0]!= SEL[1] && SET[2]!= SEL[1]){
						temporal = board[SEL[1]].GetCarta();
						board[SEL[1]].SetCarta(board[SET[1]].GetCarta());
						board[SET[1]].SetCarta(temporal);  
						SET[1]=SEL[1];//Modificacion para que sirva para simular//15-dic-2013
						//20-dic-2013 la modificacion anterior...
					}
					else{
						temporal = board[SEL[2]].GetCarta();
						board[SEL[2]].SetCarta(board[SET[1]].GetCarta());
						board[SET[1]].SetCarta(temporal); 
						SET[1]=SEL[2];//Modificacion para que sirva para simular//15-dic-2013
						//20-dic-2013 la modificacion anterior...
					}
			}//if(SET[1] >= 12)
			if(SET[2] >= 12){
				if(SET[0]!= SEL[0] && SET[1]!= SEL[0]){
					temporal = board[SEL[0]].GetCarta();
					board[SEL[0]].SetCarta(board[SET[2]].GetCarta());
					board[SET[2]].SetCarta(temporal); //Modificacion para que sirva para simular//15-dic-2013
					SET[2]=SEL[0];//Modificacion para que sirva para simular//15-dic-2013
				}
				else
					if(SET[0]!= SEL[1] && SET[1]!= SEL[1]){
						temporal = board[SEL[1]].GetCarta();
						board[SEL[1]].SetCarta(board[SET[2]].GetCarta());	
						board[SET[2]].SetCarta(temporal); //Modificacion para que sirva para simular//15-dic-2013
						SET[2]=SEL[1];//Modificacion para que sirva para simular//15-dic-2013
					}
					else{
						temporal = board[SEL[2]].GetCarta();
						board[SEL[2]].SetCarta(board[SET[2]].GetCarta());
						board[SET[2]].SetCarta(temporal); //Modificacion para que sirva para simular//15-dic-2013
						SET[2]=SEL[2];//Modificacion para que sirva para simular//15-dic-2013
					}
				//board[SET[2]].SetCarta(temporal);  //comentareado para que sirva para simular//15-dic-2013
			}//if(SET[2] >= 12)
			for(int k = 12; k < CtdCasillasOcupadas; k++)
				 MiPaqueteCartas.Recibir(board[k].GetCarta());
			CtdCasillasOcupadas = 12;
			ctdsets = this.QtySETS(); //nuevo 17-dic-2013
			return true;
		}//else II if(centinela)
	}//I if(centinela)
	//else
		ctdsets = this.QtySETS();  //nuevo 17-dic-2013
		return true;//Sigue el juego
}//public boolean Repartir()
//------------------------------------
//Creo q aqui es un buen lugar para aclarar que cunado se dibuja un texto las coordenadas son en el extremo izquierdo bajo del texto, y no arriba, pues trae confusion
public void Dimensionar(boolean orientation, int H, int A){
	//H es la altura en modo  landscape
	//A es el ancho en modo landscape
	float a1, a2;
	float inicial_x, inicial_y;
	float espacios;
	
	float Hreal = (float)(H - pxsize - (float)3.5/120*density);  //(solo en v. telefono)//reservando 13.5 para el timer, 3.5 para los margenes por arriva y por avajo
	float Areal = (float)(A - pxsize - (float)3.5/120*density);  //30/08/2014 ahora solo reservo para el margen de abajo
	
	if(tablet){
		Hreal = (float) (H - 48 - (float)17/120*density);  //(solo en v. tablet) //48 es es tamaNo de la barra del sistema operativo
		//Hreal = H - 27;  //linea de prueVa para ver como se ve el juego cuando es en una taVleta
		Areal = (float) (A - 48 - (float)17/120*density);  //reservando 20 para el timer, mas los 3.5 por arriva y por avajo
		//Areal = A - 27;    //linea de prueVa para ver como se ve el juego cuando es en una taVleta
	}
	
	if(orientation){
		//en landscape
		a1 = (float) (Hreal * 30.0/100.0);
		//a2 = (float) ((A - 100) * 22.5/100.0);//100 es el espacio que quiero reservar para mostrar informacion (SETS realizados, cartas quevan quedando en el paquete)
		a2 = (float) ((A - anchopaintcards - 10) * 22.5/100.0); 
		if(a1 < a2){
			espacios = (float) (0.025 * Hreal);
		}
		else {
			a1 = a2;
			espacios = (float) (0.02 * (A - anchopaintcards - 10)); 
		}
		TLleft.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintleft/2.0);
		TLleft.y = (float) (Hreal*0.1);
		TLnumberI.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintnumber/2.0);
	    TLnumberI.y = (float) (Hreal *0.1) + espacio_letrero1;
		TLcards.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintcards/2.0);
		TLcards.y = (float) (Hreal*0.1) + espacio_letrero2; 
		TLply1.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintply1/2.0);
		TLply1.y = (float) (Hreal *0.1) + espacio_letrero3;
		TLnumberII.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintnumber/2.0);
		TLnumberII.y = (float) (Hreal*0.1) + espacio_letrero4;
		TLply2.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintply1/2.0);
		TLply2.y = (float) (Hreal *0.1) + espacio_letrero5;
		TLnumberIII.x = (float) (A - (anchopaintcards + 10)/2.0 - anchopaintnumber/2.0);
		TLnumberIII.y = (float) (Hreal*0.1) + espacio_letrero6;
		
		TLpossiblessets.x = 7;
		TLplayer.x = (A - anchopaintcards - 10 - anchoplayer)/(float)2.0;
		TLpossiblessets.y = Hreal + pxsize;   //13.5 + 3.5  
		TLplayer.y = TLpossiblessets.y;            //13.5 + 3.5
			
	}else{
		//enportrait
		//a1 = (float) ((H - 100) * 30.0/100.0);
		a1 = (float) ((H - anchopaintcards - 10) * 30.0/100.0);
		a2 = (float) (Areal * 22.5/100.0);
		if(a1 < a2){
			espacios = (float) (0.025 * (H - anchopaintcards - 10)); 
		}
		else {
			a1 = a2;
			espacios = (float) (0.02 * Areal); 
		}
		TLleft.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintleft/2.0);
		TLleft.y = (float) (Areal*0.1);
		TLnumberI.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintnumber/2.0);
		TLnumberI.y = (float) (Areal*0.1) + espacio_letrero1;
		TLcards.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintcards/2.0);
		TLcards.y = (float) (Areal*0.1) + espacio_letrero2;
		TLply1.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintply1/2.0);
		TLply1.y = (float) (Areal*0.1) + espacio_letrero3;
		TLnumberII.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintnumber/2.0);
		TLnumberII.y = (float) (Areal*0.1) + espacio_letrero4;
		TLply2.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintply1/2.0);
		TLply2.y = (float) (Areal*0.1) + espacio_letrero5;
		TLnumberIII.x = (float) (H - (anchopaintcards + 10)/2.0 - anchopaintnumber/2.0);
		TLnumberIII.y = (float) (Areal*0.1) + espacio_letrero6;
		
		TLpossiblessets.x = 7;
		TLplayer.x = (H - anchopaintcards - 10 - anchoplayer)/(float)2.0;
		TLpossiblessets.y = Areal + pxsize; 			//13.5 + 3.5
		TLplayer.y = TLpossiblessets.y; 

	}//if(orientation)

	TCasilla.SetSize(a1);
	inicial_x = espacios;
	inicial_y = inicial_x;
	int index = 0;
	TPoint PTL = new TPoint();
	if(orientation)
	 for(int i=0; i<3 ; i++)
		  for(int j=0; j<4; j++){
		  	PTL.x = inicial_x + j*(TCasilla.GetSize()+ espacios);
		 	PTL.y = inicial_y + i*(TCasilla.GetSize()+ espacios);
		 	board[index].SetPtoTL(PTL);
		 	index++;
		  }
	else {
	 inicial_x = inicial_x + espacios + espacios + TCasilla.GetSize() + TCasilla.GetSize();    //12 sept 2013	
	 for(int j=0; j<3 ; j++)
		  for(int i=0; i<4; i++)   {
		  	//PTL.x = inicial_x + j*(TCasilla.GetSize()+ espacios);
			PTL.x = inicial_x - j*(TCasilla.GetSize()+ espacios);        //12 sept 2013
		 	PTL.y = inicial_y + i*(TCasilla.GetSize()+ espacios);
		 	board[index].SetPtoTL(PTL);
		 	index++;
		  }
	}
}//public void Dimensionar()
//------------------------------------
public void display(Canvas canvas){
	for(int i =0; i<12; i++)
		board[i].Dibujate(canvas);
	canvas.drawText("Left", TLleft.x, TLleft.y, paintmarcador);
	canvas.drawText(String.valueOf(MiPaqueteCartas.GetQtyCards()), TLnumberI.x, TLnumberI.y, paintnumber);
	canvas.drawText("cards", TLcards.x, TLcards.y, paintmarcador);
	canvas.drawText("Ply1", TLply1.x, TLply1.y, paintplayer1);
	canvas.drawText(String.valueOf(ply1_setsrealizados), TLnumberII.x, TLnumberII.y, paintnumber);
	canvas.drawText("Ply2", TLply2.x, TLply2.y, paintplayer2);
	canvas.drawText(String.valueOf(ply2_setsrealizados), TLnumberIII.x, TLnumberIII.y, paintnumber);
	
	//canvas.drawText(String.valueOf(ctdsets)+" Possibles SETS", TLpossiblessets.x, TLpossiblessets.y, cartelpossiblessets);
	canvas.drawText(String.valueOf(ctdsets)+" SETS", TLpossiblessets.x, TLpossiblessets.y, cartelpossiblessets);
	//canvas.drawText("MENU", TLmenu.x, TLmenu.y, cartelMENU);  //Agregado el 15/06/2014
	if(jugadorencurso == 1 && multi){
		cartelPLAYER.setColor(Color.YELLOW);
		canvas.drawText("Player 1", TLplayer.x, TLplayer.y, cartelPLAYER);  //Agregado el 15/06/2014
	}
	else if(jugadorencurso == 2 && multi){
		cartelPLAYER.setColor(Color.CYAN);
		canvas.drawText("Player 2", TLplayer.x, TLplayer.y, cartelPLAYER);
	}
}//public void display()

//------------------------------------
public void DeseleccionarTodasTodas(){
	// /*
	for(int i =0; i<12; i++)
		board[i].Deseleccionar();
	CtdSel = 0; //Correccion SetGamev0.2plus  18-nov-2013
}
//------------------------------------
public int QtySETS(){
	int ctd = 0;
	for(int base = 0; base < 10; base++)
		for(int pivote = base + 1; pivote < 11; pivote++)
			//for(int extension = base + 2; extension < 12; extension++){
			for(int extension = pivote + 1; extension < 12; extension++){  //Arreglado el 10/sept/2014 , q pena, este error provocaba que se contaran mas sets posibles que los existentes realmente
				if(board[base].EsSET(board[pivote], board[extension])){
					ctd++;	
				}//si hay set
			}//3er for...
	return ctd;
}//public boolean CheckSET()
//------------------------------------
public void ResetJugadorEnCurso()
{
	if(jugadorencurso == 1)
		ply1_setsrealizados--;
	else if (jugadorencurso == 2)
		ply2_setsrealizados--;
	jugadorencurso =0;
	InColision = false;
}
//-------------------------------------------------------
public void BackupSelection()
{
	CtdSel_Ant = CtdSel;
	for(int i = 0; i < CtdSel_Ant; i++)
		SEL_Ant[i] = SEL[i];
}
//-------------------------------------------------------
public void RestoreSelection()
{
	CtdSel = CtdSel_Ant;
	for(int i = 0; i < CtdSel; i++)
	{
		SEL[i] = SEL_Ant[i];
		 board[SEL[i]].seleccionada = true;
	}//for
	//veamos q pasa...
	if(ply1)
		ply2_setsrealizados++; 	//Volver a ponerle el pto q se le quito por tiempo en este taVlero
	else
		ply1_setsrealizados++;
}
//-------------------------------------------------------
}//public class Tablero
