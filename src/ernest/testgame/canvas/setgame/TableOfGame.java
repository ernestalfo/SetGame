package ernest.testgame.canvas.setgame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.view.ViewDebug.FlagToString;
import android.content.DialogInterface;
import android.graphics.Rect;


public class TableOfGame extends View implements OnTouchListener  {
	public final static String EXTRA_MESSAGE = "ernest.testgame.canvas.setgame";
	Tablero MiTablero;
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
	//AlertDialog.Builder dialogogameover, dialognewgame, dialogMenu, dialogHelp;
	AlertDialog.Builder dialogogameover, dialogMenu, dialogHelp;
	ProgressDialog dialogP;      //Agregado el 20/06/2014
    SingleGame_Activity MyApp;
	TTimer Reloj;
	//boolean tocaReloj = false;   //estas variables existieron en un intento de diubujar solo lo que hiciera falta, pero en definitiva
	//boolean tocaDibujar = true;  //siempre hay que dibujar desde el ppo todo, una vez que se ejecuta el OnDraw()
	boolean noend = true;
	Thread OneSecPulse, TransitionEffect;
	int callstransition = 0;
	
	//boolean tablet = false;
	
	static int [] besttimes = new int[]{1200, 1200, 1200, 1200, 1200};
	static String [] topnames = new String[]{"[Empty]","[Empty]","[Empty]","[Empty]","[Empty]"};
	
	
	//version 0.7c
	long TimeInicio, TimeTranscurrido, TimeDiferencia;
	int TiempoEnSegundos;
	//public TableOfGame(Context context)
	
	//version 0.7f PAUSE/RESUME
	boolean pause = false;
	long TimeOffset = 0;   //en milisegundos
	int motivo = 0;
	//String pause_resume;
	
	public TableOfGame(Context context, int Ancho, int Heigth, boolean istablet, int densidad) {//nuevo constructor VERSION 0_6
		super(context);
		setFocusable(true);
		setFocusableInTouchMode(true);
		this.setOnTouchListener(this);
		
		MiTablero = new Tablero(null, true, false, istablet, densidad);
		
		//nuevo version 0_7f
		//pause_resume = getString(R.string.menu_pause_resume);
		
		//Nuevo VERSION 0_6
		A = Ancho;
		H = Heigth;
		rectOrienT = new Rect(0, 0, A, H);
		rectOrienF = new Rect(0, 0, H, A);
		//---------------------------------------------
		Reloj = new TTimer(istablet, densidad);
		
		dialogogameover = new AlertDialog.Builder(this.getContext()); 
	    dialogogameover.setTitle("CONGRATULATIONS");           
	    dialogogameover.setCancelable(false); 
	    
	    dialogHelp = new AlertDialog.Builder(this.getContext()).setIcon(R.drawable.viewsetgame).setTitle("Help").setMessage("To create a SET, a player must locate three cards in which each of the four features is either all the same on each card or all different on each card, when looked at individually. The four features are, symbol(rectangle, circle or diamond), color(red, blue or green), number(one, two or three) or shading(solid, striped or open).\n\n(Developed by EAP Sw 2013)");
		dialogHelp.setCancelable(false); 
	    dialogHelp.setNeutralButton("OK",null);
	        
	    
	   // dialognewgame = new AlertDialog.Builder(this.getContext()); 
	   // dialognewgame.setTitle("New Game");  
	  //  dialognewgame.setCancelable(false); 
	    //dialognewgame.setNeutralButton("OK", null);
	  //  dialognewgame.setMessage("Are you sure to start a new game");
	  //  dialognewgame.setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
	  //      public void onClick(DialogInterface dialogo1, int id) {  
	  //          newGameJAJA();  
	     //   }  
	    //});  
	   // dialognewgame.setNegativeButton("No ",null);
	    
	    
	    dialogogameover.setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            aceptar();  
	        }  
	    });  
	    dialogogameover.setNegativeButton("No", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            cancelar();
	        }  
	    });  
	    
       dialogP = new ProgressDialog(this.getContext());
	    
	    /*
	   //final CharSequence[] items={"New Game","Best Times","Clear Time Records","About","Help"};
	    final CharSequence[] items={"New Game","Help", "Best Times"};
	    dialogMenu = new AlertDialog.Builder(this.getContext()); 
	    dialogMenu.setTitle("MENU").setItems(items, new DialogInterface.OnClickListener() {
	    	 @Override
	    	 public void onClick(DialogInterface dialog, int which) {
	    	// TODO Auto-generated method stub
	    		 switch(which){
	    		 case 0:   //New Game
	    			 newGame(); 
	    			 break;
	    		 case 1:	//Help
	    			 dialogHelp.show();
	 	        	break;
	    		 case 2:	//Best Times
	    			    Intent intent = new Intent(MyApp.getApplicationContext(),BestTimesBoardActivity.class);
		 	        	MyApp.startActivity(intent);;
	    			 break;
	    		 }
	    	}
	    	});
	    */
	    
		MiTablero.Repartir();
		onesecwatch = true;
    	OneSecPulse = new Thread(myThread);
    	TimeInicio = System.currentTimeMillis();
    	OneSecPulse.start(); 
		
	}//TableOfGame(Context context)
	
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
					//if(myHandle != null)
					//{
						myHandle.sendMessage(msg);
						//Log.d("ups", "helloooo");
					//}
					//enejecuccion =  false;
				}
				//catch(Throwable t){
				//}
				catch(Exception e)
				{
					
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
					TimeDiferencia = TimeTranscurrido - TimeInicio + TimeOffset;
					TiempoEnSegundos = (int) (TimeDiferencia/1000);
					Reloj.SetTime((int) TiempoEnSegundos);
					invalidate();
					}//if(onesecwatch)
				}//(tipo == 1)
				else {
					//enejecuccion =  false;
					//lineas de este bloque agregadas el 20/06/2014 v0.5d en desarrollo
					//Log.d("ups", "termino el hilo");
					if(motivo == 1)  //juego nuevo
					{
						MiTablero.ResetTablero(); //ResetTablero incluye un Repartir() 
						Reloj.Reset();
						OneSecPulse = new Thread(myThread);
						onesecwatch = true;
						if(dialogP != null)
							dialogP.dismiss();   //loading. please wait...
						//TimeOffset = 0;
						TimeInicio = System.currentTimeMillis();
						OneSecPulse.start(); 
						invalidate();
					}
					else
						if(motivo == 2)  //pausa por el usuario o pausa por el sistema
							if(dialogP != null){
								dialogP.dismiss();   //stop. please wait...
								//Log.d("ups", "termino el hilo y se cancelo el dialogP");
							}
					motivo = 0;
				}//else de if(tipo == 1)
			}
		};  //   */
		
		Handler TransitionHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				if(callstransition == 14 && transitionwatch == true){
					MiTablero.Transicion();
					callstransition++;
					if(!MiTablero.Repartir()){   //este es el codigo que va, el otro es solo para probar
					//if(MiTablero.Repartir()){      //codigo para probar
						MyApp.enpira = true;
						transitionwatch = false;
						load("besttimesfile");
						int num =  addScore(Reloj.Hr * 3600 + Reloj.Min * 60 + Reloj.Seg );
						if( num < 5){
							   save("besttimesfile");
							   Intent intent = new Intent(MyApp.getApplicationContext(),BestTimesActivity .class);
							   String message = Integer.toString(num);
							   intent.putExtra(EXTRA_MESSAGE, message);
							   MyApp.startActivity(intent);
						}
                    	callstransition = 0;
						dialogogameover.setMessage("You finished the game. Do you want to play again? \n\n Time: "+Reloj.GetString());
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

	 
	public void aceptar() {
		motivo = 1;  //juego nuevo
		dialogP = ProgressDialog.show(this.getContext(), "", "Loading. Please wait...", true);
		onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...	
		TimeOffset = 0; 
		MyApp.enpira = false;
	}
	/*
	public void newGameJAJA(){
		onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...

		while(enejecuccion){} //esperando porque acabe el hilo q cuenta el tiempo.
		Reloj.Reset();
		//if(!enejecuccion){
			//onesecwatch = true;
			//OneSecPulse = new Thread(myThread);
			//OneSecPulse.start();
		//}
		//dialogWelcome.show(); //15/06/2014
		//ProgressDialog.show(this.getContext(),"New Game","Loading...",true,false).dismiss();


		MiTablero.ResetTablero(); //ResetTablero incluye un Repartir()
		onesecwatch = true;
    	OneSecPulse = new Thread(myThread);
    	OneSecPulse.start();   //comentarear para simular un juego 15-dic-2013
		invalidate();
	}
	*/
	public void newGame(){

		dialogP = ProgressDialog.show(this.getContext(), "", "Loading. Please wait...", true);
		if(pause)
		{
			MiTablero.ResetTablero(); //ResetTablero incluye un Repartir() 
			Reloj.Reset();
			onesecwatch = true;
	    	OneSecPulse = new Thread(myThread);
	    	TimeInicio = System.currentTimeMillis();
	    	OneSecPulse.start();
	    	TimeOffset = 0;
	    	pause = false;
	    	//MyApp.menuPPAL.getItem(3).setTitle("Pause");
	    	dialogP.dismiss();
	    	invalidate();
	    	return;
				
		}
		motivo = 1;
		onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...
		TimeOffset = 0; 
		//invalidate();
	}

	public void cancelar() {
		MyApp.finish();

	}
	
	@Override
	public void onDraw(Canvas canvas) {
		//ProgressDialog dialog = ProgressDialog.show(this.getContext(), "", "Drawing. Please wait...", true);
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
		    if(!pause)	
		    	MiTablero.display(canvas);
			Reloj.display(canvas);	
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				TPoint p = new TPoint();
				p.x = event.getX();
				p.y = event.getY();
				if(pause)
				{
					if(Reloj.ToqueReloj(p))
						pause_resume();
					//invalidate();
					return false;
				}
				result = MiTablero.Seleccionar(p);
				if(result == 3){
					MiTablero.Transicion();
					transitionwatch = true;
					TransitionEffect = new Thread(TransitionRunnable);
					TransitionEffect .start();  
				}
				//if(result == 5)
				//{
					//dialogMenu.show();
				//}
				//Log.d("OnTouch", "se preguntara si toco el reloj");
				if(Reloj.ToqueReloj(p))
				{
					//Log.d("OnTouch", "se llamara a pause_resume");
					pause_resume();
					return false;
				}
				invalidate();
		}//switch(event.getAction()){
		return false; //porq no quiero q despues de actiondown vuelva a ejecutarse OnTouch		
	}
	
	public  void load(String filename) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(MyApp.openFileInput(filename)));
			//soundEnabled = Boolean.parseBoolean(in.readLine());
			for (int i = 0; i < 5; i++) {
				besttimes[i] = Integer.parseInt(in.readLine());
			}
			for (int i = 0; i < 5; i++) {
				topnames[i] = in.readLine();
			}
			} catch (IOException e) {
				// :( It's ok we have defaults
			} catch (NumberFormatException e) {
				// :/ It's ok, defaults save our day
			}catch (Exception e){
				//
			}
			finally {
				try {
					if (in != null)
						in.close();
				} catch (IOException e) {
				}
			}
		}// load
	// /*
	public  void save(String filename) {
		BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(MyApp.openFileOutput(filename, Context.MODE_PRIVATE)));
				for (int i = 0; i < 5; i++) {
					out.write(Integer.toString(besttimes[i]));
					out.newLine();
				}
				for (int i = 0; i < 5; i++) {
					out.write(topnames[i]);
					out.newLine();
				}
			} catch (IOException e) {
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
				}
			}
		}//save
	// */
	public static int addScore(int time) {
		for (int i = 0; i < 5; i++) {
			if (besttimes[i] > time) {
				for (int j = 4; j > i; j--)
					besttimes[j] = besttimes[j - 1];
				for (int j = 4; j > i; j--)
					topnames[j] = topnames[j - 1];
				besttimes[i] = time;
				//topnames[i] = name;
				//recordtime = true;
				return i; //regresa un numero entre 0 y 4, significando que se rompio record y ademas que se debe pedir el nombre y se sabe en que posicion ponerlo
				//break;
			}//if
		}//for
		return 5; //regresa 5 significando que no se rompio record
	}//addScore
	
	//version 0.7f PAUSE/RESUME
	public void pause_resume()
	{
		//Log.d("pause_resume", "start");
		pause = !pause;
		if(pause)
		{
			//Log.d("pause_resume", "pausing...");
			TimeTranscurrido = System.currentTimeMillis();
			TimeOffset += TimeTranscurrido - TimeInicio;
			//Log.d("pause_resume", "ejecutandose, calculos de tiempo");
		    //dialogP.show();
		    //Log.d("pause_resume", "ejecutandose, dialogP.show();");
			onesecwatch = false; //De esta forma es como mandar a terminar el hilo q cuenta...
			//Log.d("pause_resume", "ejecutandose, onesecwatch = false");
			motivo = 2;
			//Log.d("pause_resume", "ejecutandose, motivo = 2");
	        MyApp.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	        //MyApp.menuPPAL.getItem(3).setTitle("RESUME");
	        //MyApp.pause_resume = "RESUME";
			dialogP = ProgressDialog.show(this.getContext(), "", "Stop. Please wait...", true);
	       		
		}
		else
		{
			//Log.d("pause_resume", "resuming...");
			MyApp.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
			//MyApp.menuPPAL.getItem(3).setTitle("Pause");
			onesecwatch = true;
	    	OneSecPulse = new Thread(myThread);
	    	TimeInicio = System.currentTimeMillis();
	    	OneSecPulse.start(); 
		}
		invalidate();
		//Log.d("ups", "Se ejecuto normalmente el pause_resume");
	}//public void pause_resume()
	// /*
	public boolean get_pause()
	{
	 return pause;	
	}
	// */
	

}//TableOfGame
