package ernest.testgame.canvas.setgame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class TableMultiGame extends View implements OnTouchListener {
	public final static String EXTRA_MESSAGE = "ernest.testgame.canvas.setgame";
	public Tablero MiTablero;
	boolean onesecwatch = true;
	boolean transitionwatch = true;
	boolean enejecuccion = false;
	int result = 0;
	boolean orientation = false;
	boolean changeorientation = true;
	//Nuevo VERSION 0_6
	Bitmap fondo = BitmapFactory.decodeResource(getResources(), R.drawable.fondillo);
	Rect rectOrienT, rectOrienF; 
	
	int A, H;
	AlertDialog.Builder dialogogameover, dialognewgame, dialogMenu, dialogHelp;
	ProgressDialog dialogP;      //Agregado el 20/06/2014
    MultiGame_Activity MyApp;
	TTimer Reloj;
	boolean noend = true;
	Thread OneSecPulse, TransitionEffect;
	int callstransition = 0;
	
	
	//--------------------------------------
	//NUEVO VERSION 0_6
	int cont10Seg = 0;
	//private boolean bloquear = false;
	public boolean bloquear = true;  //Esta flag vigila que entre un msg enviado y otro haya al menos medio segundo
	public boolean bloquear2 = false; //Esta flag vigila  que mientras esten los 10 segundos del jugador contrario
	//ninguna accion (toque) sobre la mesa(esta vista es la mesa) sea procesada.
	Thread HiloDelay;
	//Message genericMsg = new Message();
	int TurnoDeJuego = 1;
	long TimeInicio, TimeTranscurrido, TimeDiferencia;
	int TiempoEnSegundos, TiempoDeAlarma;
	boolean Alarma10Seg = false;
	//public TableOfGame(Context context)
	public TableMultiGame(Context context, int Ancho, int Heigth, long seed, boolean P1, MultiGame_Activity MGAct, boolean istablet, int densidad) 
	{//nuevo constructor VERSION 0_6
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
		
		MiTablero = new Tablero(MGAct, P1, true, seed, istablet, densidad);
		
		//Nuevo VERSION 0_6
		A = Ancho;
		H = Heigth;
		rectOrienT = new Rect(0, 0, A, H);
		rectOrienF = new Rect(0, 0, H, A);
		//---------------------------------------------
		Reloj = new TTimer(istablet, densidad);
		
		dialogogameover = new AlertDialog.Builder(this.getContext()); 
	    dialogogameover.setTitle("FIN DEL PARTIDO");           
	    dialogogameover.setCancelable(false); 
	    
	    dialogHelp = new AlertDialog.Builder(this.getContext()).setIcon(R.drawable.viewsetgame).setTitle("Help").setMessage("To create a SET, a player must locate three cards in which each of the four features is either all the same on each card or all different on each card, when looked at individually. The four features are, symbol(rectangle, circle or diamond), color(red, blue or green), number(one, two or three) or shading(solid, striped or open).\n\n(Developed by EAP Sw 2013)");
		dialogHelp.setCancelable(false); 
	    dialogHelp.setNeutralButton("OK",null);
	        
	    
	    dialognewgame = new AlertDialog.Builder(this.getContext()); 
	    dialognewgame.setTitle("New Game");  
	    dialognewgame.setCancelable(false); 
	    //dialognewgame.setNeutralButton("OK", null);
	    dialognewgame.setMessage("Are you sure to start a new game");
	    dialognewgame.setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            newGameJAJA();  
	        }  
	    });  
	    dialognewgame.setNegativeButton("No ",null);
	    
	    
	    //dialogogameover.setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
	    dialogogameover.setPositiveButton("OK", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            aceptar();  
	        }  
	    }); 
	    /*
	    dialogogameover.setNegativeButton("No", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            cancelar();
	        }  
	    });  */
	    
	    //final CharSequence[] items={"New Game","Help", "Best Times"};
	    final CharSequence[] items={"Help"};
	    dialogMenu = new AlertDialog.Builder(this.getContext()); 
	    dialogMenu.setTitle("MENU").setItems(items, new DialogInterface.OnClickListener() {
	    	 @Override
	    	 public void onClick(DialogInterface dialog, int which) {
	    	// TODO Auto-generated method stub
	    		 switch(which){
	    		 //case 0:   //New Game
	    			// newGame(); 
	    			 //break;
	    		// case 1:	//Help
	    		 case 0:
	    			 dialogHelp.show();
	 	        	break;
	    		 //case 2:	//Best Times
	    			//    Intent intent = new Intent(MyApp.getApplicationContext(),BestTimesBoardActivity.class);
		 	        	//MyApp.startActivity(intent);;
	    			 //break;
	    		 }
	    	}
	    	});

	    
		MiTablero.Repartir();
		onesecwatch = true;
    	OneSecPulse = new Thread(myThread);
    	//OneSecPulse.start(); 
		
	}//TableMultiGame(Context context)
	/*
	public void SetTablet(){
		Reloj.SetTablet();
		MiTablero.SetTablet();
		TCasilla.SetTablet();
		
	}
	*/
	
	// /*
	private Runnable myThread = new Runnable(){

		@Override
		public void run() {
			if(onesecwatch){	//22-06-2014
				while (onesecwatch){
					try{
						Message msg = new Message();
						int tipo = 1;
						msg.obj = tipo;
						Thread.sleep(1000);
						myHandle.sendMessage(msg);
						//enejecuccion = true;
					}
					catch(Throwable t){
					}
				}//while (onesecwatch)
				try{
					Message msg = new Message();
					int tipo = 0;
					msg.obj = tipo;
					myHandle.sendMessage(msg);
					//enejecuccion =  false;
				}
				catch(Throwable t){
				}
			}//while (onesecwatch)
		}//public void run()
	};   //  */
	private Runnable TransitionRunnable = new Runnable(){

		@Override
		public void run() {
			while (transitionwatch){
				try{
					//Thread.sleep(21);
					Thread.sleep(15);//15-06-2014
					Message msg = new Message();
					TransitionHandler.sendMessage(msg);
				}
				catch(Throwable t){
				}
			}//while (onesecwatch)

		}	
	}; 
	
  //    /*
		Handler myHandle = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				int tipo = (Integer)msg.obj;
				if(tipo == 1){
					if(onesecwatch){
						/* Reloj.Seg++;
						if(Reloj.Seg == 60){
							Reloj.Min++;
							if(Reloj.Min == 60){
								Reloj.Hr++;
								Reloj.Min=0;
							}
							Reloj.Seg=0;
						}//if(Reloj.Seg == 60)*/
						//NUEVO VERSION 0_7c
						TimeTranscurrido = System.currentTimeMillis();
						TimeDiferencia = TimeTranscurrido - TimeInicio;
						TiempoEnSegundos = (int) (TimeDiferencia/1000);
						Reloj.SetTime((int) TiempoEnSegundos);
						//NUEVO VERSION 0_6
						if(Alarma10Seg && ((TiempoEnSegundos - TiempoDeAlarma ) >= 0))
						//if(cont10Seg > 0)
						{
							///cont10Seg--;
							//if(cont10Seg == 0){
								MiTablero.BackupSelection();
								MiTablero.ResetJugadorEnCurso();
								MiTablero.DeseleccionarTodasTodas();
								//bloquear2 = false;
								HiloDelay = new Thread(DelayRunnable);  //tengo dudas soVre si comentarearlo o no
								HiloDelay.start();   					//por ahora voy a dejar estas dos lineas
								TurnoDeJuego++;
								Alarma10Seg = false;
								
							//}
						}//if(cont10Seg > 0) 
						invalidate();
						//enejecuccion = true;
					}//if(onesecwatch)
				}//(tipo == 1)
				else {
					MiTablero.ResetTablero(); //ResetTablero incluye un Repartir() 
					Reloj.Reset();
			    	OneSecPulse = new Thread(myThread);
			    	onesecwatch = true;
			    	dialogP.dismiss();
			    	OneSecPulse.start(); 
			    	invalidate();
				}
			}
		};  //   */
		   //-------------------------------------------
			private Runnable DelayRunnable = new Runnable(){

				@Override
				public void run() 
				{
					try {
						//Thread.sleep(500);
						Thread.sleep(750);
						Message msg = new Message();
						//msg.what = 2;
						DelayHandler.sendMessage(msg);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}//public void run()	
			}; 
//-------------------------------------------
		Handler DelayHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) 
			{
				bloquear2 = false;
			}
		};
	//-------------------------------------------
		Handler TransitionHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(callstransition == 14 && transitionwatch == true){
					MiTablero.Transicion();
					callstransition++;
					if(!MiTablero.Repartir()){   //este es el codigo que va, el otro es solo para probar
					//if(MiTablero.Repartir()){      //codigo para probar
						//onesecwatch = false;   //comentareado el 22-06-2014
						transitionwatch = false;
                    	callstransition = 0;
                    	if(MiTablero.ply1_setsrealizados > MiTablero.ply2_setsrealizados)
                    		dialogogameover.setMessage("El ganador es Player 1.\n\nPly1: "+MiTablero.ply1_setsrealizados+"		PLy2: "+MiTablero.ply2_setsrealizados+"\n\n Time: "+Reloj.GetString());
                    	else if(MiTablero.ply1_setsrealizados < MiTablero.ply2_setsrealizados)
                    		dialogogameover.setMessage("El ganador es Player 2.\n\nPly1: "+MiTablero.ply1_setsrealizados+"		PLy2: "+MiTablero.ply2_setsrealizados+"\n\n Time: "+Reloj.GetString());
                    	else
                    		dialogogameover.setMessage("Y tenemos un empate.\n\nPly1: "+MiTablero.ply1_setsrealizados+"		PLy2: "+MiTablero.ply2_setsrealizados+"\n\n Time: "+Reloj.GetString());
						dialogogameover.show();
					} //if(!MiTablero.Repartir()) 
				}
				else{
					if(callstransition == 29 && transitionwatch == true)
					{
						MiTablero.Transicion();
						transitionwatch = false;
                    	callstransition = 0;
					}
					else
						if(transitionwatch == true){
							MiTablero.Transicion();
							callstransition++;
						}
				}
				invalidate();
	
			}
		}; 
	//------------------------------------------------------------

	public void aceptar() 
	{
		//dialogP = ProgressDialog.show(this.getContext(), "", "Loading. Please wait...", true);
		//onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...
		MyApp.SetGameConnection.close();
		MyApp.finish();
	}
	public void newGameJAJA()
	{
		onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...

		while(enejecuccion){} //esperando porque acabe el hilo q cuenta el tiempo.
		Reloj.Reset();
	
		MiTablero.ResetTablero(); //ResetTablero incluye un Repartir()
		onesecwatch = true;
    	OneSecPulse = new Thread(myThread);
    	OneSecPulse.start();   //comentarear para simular un juego 15-dic-2013
		invalidate();
	}
	
	public void newGame()
	{

		dialogP = ProgressDialog.show(this.getContext(), "", "Loading. Please wait...", true);
		onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...		
		//invalidate();
	}

	public void cancelar() 
	{
		MyApp.finish();

	}
	@Override
	public void onDraw(Canvas canvas) 
	{
		//canvas.drawColor(Color.BLACK);
		if(changeorientation){
			MiTablero.Dimensionar(orientation, H, A);// solamente cuando haga falta
			Reloj.Dimensionar(orientation, H, A);
			changeorientation = false;
		}

			//canvas.drawColor(Color.BLACK);
		    if(orientation)
		    	//canvas.drawBitmap(fondo, null, new Rect(0, 0, A, H), null);
		    	canvas.drawBitmap(fondo, null, rectOrienT, null);
		    else
		    	//canvas.drawBitmap(fondo, R.drawable.linux_015), null, new Rect(0, 0, H, A), null);
		    	canvas.drawBitmap(fondo,null, rectOrienF, null);
		    	
		    MiTablero.display(canvas);
			Reloj.display(canvas);	
	}

	@Override
	public boolean onTouch(View view, MotionEvent event)
	{
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				try{
				if(bloquear2 || bloquear)
					return false;
				TPoint p = new TPoint();
				p.x = event.getX();
				p.y = event.getY();
				result = MiTablero.Seleccionar(p);
				if(result == 3){
					MiTablero.Transicion();
					transitionwatch = true;
					TransitionEffect = new Thread(TransitionRunnable);
					TransitionEffect .start(); 
					//cont10Seg = 0;
					Alarma10Seg = false;
					TurnoDeJuego++;
				}
				if(result == 4)
				{
					//cont10Seg = 0;
					Alarma10Seg = false;
					TurnoDeJuego++;
				}
				if(result == 2)
					//if(cont10Seg == 0)
					if(!Alarma10Seg){
						Alarma10Seg = true;
						TiempoDeAlarma = Reloj.GetTime() +  10;
					}
				invalidate();
				}//try
				catch(Exception e){
					Toast.makeText(getContext(), "Error procesando el toque", Toast.LENGTH_LONG).show();
				}
		}//switch(event.getAction()){
		return false; //porq no quiero q despues de actiondown vuelva a ejecutarse OnTouch		
	}
	//------------------------------------------------------------------------------------
	public void VirtualTouch(int CasSel, int TimeX, int Turno) 
	{
		if(Turno == TurnoDeJuego)
		{
			ProcesarNormalmente(CasSel, TimeX);
		}//if(Turno == TurnoDeJuego)
		else if(Turno > TurnoDeJuego)
		{//TURNO FUTURO
			//Si es un turno superior o  futuro, deVe ser porq el tiempo se acaVo en el otro taVlero, 
			//Actuar como si se huViera acaVado aqui tamVien
			MiTablero.ResetJugadorEnCurso();
			MiTablero.DeseleccionarTodasTodas();
			TurnoDeJuego++;
			//cont10Seg = 0;
			Alarma10Seg = false;
			//Luego Procesar normalmente
			//Asumiendo q es la primera carta seleccionada en este turno
			ProcesarNormalmente(CasSel, TimeX);
		}
		else  //es decir (Turno < TurnoDeJuego)
		{  //TURNO ANTERIOR
			MiTablero.DeseleccionarTodasTodas();
			MiTablero.RestoreSelection();
			TurnoDeJuego--;
			//Procesar asumiendo que es la ultima carta
			ProcesarUrgente(CasSel);	
		}
		invalidate();
	}//public void VirtualTouch(int CasSel) 
	//------------------------------------------------------------------------------------
	public void TimerStart()
	{
		TimeInicio = System.currentTimeMillis();
		OneSecPulse.start();
		bloquear = false;
	}
	//--------------------------------------------------------------------------------------
	public void ProcesarNormalmente(int CasSel, int TimeX)
	{
		//Procesar Normalmente:
		result = MiTablero.Seleccionar(CasSel);
		if(result != 5 && result != 6)
		{
		
			//if(cont10Seg == 0){
			if(!Alarma10Seg){
				Alarma10Seg = true;
				TiempoDeAlarma = TimeX + 10;
				bloquear2 = true;
			}
			
		//result = MiTablero.Seleccionar(CasSel);
			if(result == 3){
				MiTablero.Transicion();
				transitionwatch = true;
				TransitionEffect = new Thread(TransitionRunnable);
				TransitionEffect.start(); 
				//cont10Seg = 0;
				Alarma10Seg = false;
				TurnoDeJuego++;
				bloquear2 = false;
			}
			if(result == 4)
			{
				//cont10Seg = 0;
				Alarma10Seg = false;
				TurnoDeJuego++;
				bloquear2 = false;
			}
			//invalidate();
		}//if(result != 5)	
	}//void ProcesarNormalmente(int CasSel, int TimeX)
//---------------------------------------------------------------------
	public	void ProcesarUrgente(int CasSel)
	{
		//Procesar Normalmente:
		result = MiTablero.Seleccionar(CasSel);
		//if()
		if(result != 5 && result != 6)
		{
			if(result == 3){
				MiTablero.Transicion();
				transitionwatch = true;
				TransitionEffect = new Thread(TransitionRunnable);
				TransitionEffect.start(); 
				//cont10Seg = 0;
				Alarma10Seg = false;
				TurnoDeJuego++;
				bloquear2 = false;
				return;
			}
			if(result == 4)
			{
				//cont10Seg = 0;
				Alarma10Seg = false;
				TurnoDeJuego++;
				bloquear2 = false;
				return;
			}
			//Si cayo aqui es porq no era la tercera carta, aunque fuera la ultima, se le dio este chance de jugar
			//pero ya se deVe haVer acaVado el turno en el otro lado tamVien asi q se trata como tal
			MiTablero.ResetJugadorEnCurso();
			MiTablero.DeseleccionarTodasTodas();
			TurnoDeJuego++;
			bloquear2 = false;
		}//if(result != 5)	
	}//public void ProcesarUrgente(int CasSel)
//---------------------------------------------------------------------
}//TableMultiGame

