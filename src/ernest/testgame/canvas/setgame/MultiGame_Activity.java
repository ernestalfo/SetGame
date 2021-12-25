package ernest.testgame.canvas.setgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MultiGame_Activity extends Activity {
	MultiGame_Activity estaActividad;
	WifiManager mWifiManager;
	ConnectionHandler SetGameConnection;	

	int SERVERPORT = 7862;
	boolean hiloppal = true;
	//----------------------
	boolean server = false;//Tambien sirve para saber si es ply1 o ply2
	BufferedReader input = null;
	PrintWriter out = null;
	String MsgRec = "";
	String MsgEnv = "newsetgame";
	//--------------------------
	TableMultiGame mesa = null;
	static Rect rectgle;
	Window window;
	int A, H;
	long seed = 0;
	boolean start = false; //Sera una bandera para indicar que ya el juego comenzo
	//--------------------------
	String ServerAddr;
	//--------------------------
	String[] ArrayOfString = new String[13];
	boolean protenvio = false;
	//private static final String TAG = "Sherlock";  //Usado para los Logs
	boolean pause = false;
	String NotifMessage;
	boolean shyncro = false;
	boolean Msg2Sincro = false;
	int latency = 500;
	Thread HiloDelay;
	int indexP, indexP2;
	//-------------------------
	int density;
	//-------------------------
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainSetGameActiviy.EXTRA_MESSAGE);
		//String cmp = "server";
		estaActividad = this;
		//----------------------------
		//myCommsThread = new Thread(new CommsThread());
		//----------------------------
		if(message.compareTo("server")==0)
		{
			server =  true;
		    seed = System.currentTimeMillis();
		}
		SetGameConnection = new ConnectionHandler(SERVERPORT, server);
			
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//------------------------------------------------------
		rectgle= new Rect();
		window= getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		
		DisplayMetrics metrics = new DisplayMetrics();
		window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		density = metrics.densityDpi;
		
	    ArrayOfString[0] = "0";
	    ArrayOfString[1] = "1";
	    ArrayOfString[2] = "2";
	    ArrayOfString[3] = "3";
	    ArrayOfString[4] = "4";
	    ArrayOfString[5] = "5";
	    ArrayOfString[6] = "6";
	    ArrayOfString[7] = "7";
	    ArrayOfString[8] = "8";
	    ArrayOfString[9] = "9";
	    ArrayOfString[10] = "10";
	    ArrayOfString[11] = "11";
	    ArrayOfString[12] = "12";
			
		//myCommsThread.start();
		
		//setContentView(mesa); 
		setContentView(R.layout.waiting_for_network); 
	}//OnCreate
	/*
	   @Override
	   protected void onStop() {
		super.onStop();
		SetGameConnection.close();
	   }//onStop()  */
	 //---------------------------------------------------------------
	 
//---------------------------------------------------------------------------------
	   Handler myUpdateHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:  //Mensaje recibido
				try{
				if(MsgRec == null)
				{
					Toast.makeText(estaActividad, "Se perdio la conexion con el otro lado",Toast.LENGTH_LONG).show();
					//estaActividad.onStop();
					estaActividad.finish();
					return;
				}
				if(!start)//Si el juego no ha comenzado
				{
					if(server)//si se trata del servidor
					{
						if(MsgRec.compareTo("newsetgame")==0)
						{
							MsgEnv = Long.toString(seed);
							//MsgEnv = "newsetgame");
							ServirMesa();
							SetGameConnection.send(MsgEnv);
							start = true;
						} 
						else
							try {
								SetGameConnection.close();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						break;
					}//if(server)
					else
					{//Si es cliente
						seed = Long.parseLong(MsgRec);
						//ServirMesa();
						SetGameConnection.send("x");//msg de sincro
						ServirMesa();
						start = true;
					}
				}//if(!start)
				else
				{//Si ya el juego comenzo todo lo que se recibe es sencillamente la casilla q selecciono el contrario
					if(shyncro)
					{
						indexP = MsgRec.indexOf(':');
						indexP2 = MsgRec.indexOf(':', indexP + 1);
						mesa.VirtualTouch(Integer.parseInt(MsgRec.substring(0, indexP)), Integer.parseInt(MsgRec.substring(indexP + 1, indexP2)), Integer.parseInt(MsgRec.substring(indexP2 + 1, MsgRec.length())));
					}
					else
					{
						if(server)
						{
							if(Msg2Sincro)
							{
							 //Supuestamente aqui reciVo el nuevo valor de latency y lo actualizo
								shyncro = true;
								mesa.TimerStart();
							}
							else
							{
								SetGameConnection.send("x"); //2do msg de sincro
								//ServirMesa();    //13/sept/2014 Me parece bien aqui, pero ...
								Msg2Sincro = true;
							}
						}
						else
						{
							SetGameConnection.send("l");//Supuestamente en este paso calcule la latencia y se la envio
							//supuestamente espero a q llegue el msg al destino y luego sigo
							shyncro = true;
							mesa.TimerStart();

						}
					}//else de if(shyncro)
				}
				}
				catch(Exception e){
					Toast.makeText(estaActividad, "Error procesando lo recibido", Toast.LENGTH_SHORT).show();
				}
				break;
			case 1:  
				Toast.makeText(estaActividad, NotifMessage, Toast.LENGTH_SHORT).show();
				break;
			case 2: 
				mesa.bloquear = false;
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	   };//new Handler()
	   //-------------------------------------------
		private Runnable DelayRunnable = new Runnable(){

			@Override
			public void run() 
			{
				try {
					Thread.sleep(latency);
					Message msg = new Message();
					msg.what = 2;
					myUpdateHandler.sendMessage(msg);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//public void run()	
		}; 
		//-------------------------------------------
	   public void Send(String MSG)
	   {
		   mesa.bloquear = true;
		   SetGameConnection.send(MSG);
		   HiloDelay = new Thread(DelayRunnable);
		   HiloDelay.start();

	   }
	   //-------------------------------------------
	   public void Send(int x)
	   {
		   MsgEnv = ArrayOfString[x];
		   SetGameConnection.send(MsgEnv);
	   }
	   //-------------------------------------------
	   public void ServirMesa()
	   {
		   	//rectgle= new Rect();
			//window= getWindow();
			//window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
			
			if((rectgle.bottom - rectgle.top) > (rectgle.right - rectgle.left)){
				//estA en portrait
				H = rectgle.right - rectgle.left;
				A = rectgle.bottom - rectgle.top;
				if((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE)
					mesa = new TableMultiGame(this, A, H, seed, server, this, true, density);
				else
					mesa = new TableMultiGame(this, A, H, seed, server, this, false, density);
				mesa.orientation = false;
			}
			else{
				//estA en landscape
				H = rectgle.bottom - rectgle.top;
				A = rectgle.right - rectgle.left;
				if((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE)
					mesa = new TableMultiGame(this, A, H, seed, server, this, true, density);
				else
					mesa = new TableMultiGame(this, A, H, seed, server, this, false, density);
				mesa.orientation = true;
			}
			//if((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE)
			//if(!((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE))
			//aVove: linea de prueVa para ver como se ve el juego cuando es en una taVleta
				//mesa.SetTablet();

			//mesa.H = H; 	//H es la altura en modo  landscape
			//mesa.A = A; 	//A es el ancho en modo landscape
			mesa.MyApp = this;
	
			setContentView(mesa);   
	   }// public void ServirMesa()
	 //-------------------------------------------
		@Override
		public void onConfigurationChanged(Configuration newConfig) {
		    super.onConfigurationChanged(newConfig);
		    if(mesa != null){
		    	mesa.changeorientation = true;
		    	// Checks the orientation of the screen
		    	if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
		    		mesa.orientation = true;
		    		//Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
		    	} else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
		    		mesa.orientation = false;
		    		//Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
		    	}
		    }
		}//public void onConfigurationChanged(Configuration newConfig)
//----------------------------------------------------------------------------------------
		@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
			//Handle the back button 
			if(keyCode == KeyEvent.KEYCODE_BACK) { 
				//Ask the user if they want to quit 
				 new AlertDialog.Builder(this) .setTitle("Quit").setCancelable(false) .setPositiveButton("Yes", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int id) { SetGameConnection.close(); finish();}}).setNegativeButton("No", null) .show();  
				return true; 
			} 
			else { 
				return super.onKeyDown(keyCode, event); 
			} 
		}//onKeyDown 
		
//----------------------------------------------------------------------------------------
	/*	@Override
		public void onPause() {
			super.onPause();
			hiloppal = false; //mandar a terminar el hilo...
			myCommsThread.interrupt();
			pause =true;
			while(!myCommsThread.isInterrupted()){}
			
		}
		//-------------------------------------------
		@Override
		public void onResume() {
			super.onResume();
			if(pause){
			hiloppal = true; //mandar a terminar el hilo...
			myCommsThread.start();
			pause = false;
			}
		}*/
//--------------------------------------------------------------------------------------------
//Donacion de David J. Eck
		
			/*
		    * Possible states of the thread that handles the network connection.
		    */
		   private enum ConnectionState { LISTENING, CONNECTING, CONNECTED, CLOSED }

		   /*
		    * Add a line of text to the transcript area.
		    * @param message text to be added; a line feed is added at the end.
		    */
		 //  private void postMessage(String message) {
			//  NotifMessage = message;
		     // //Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
			  
		 //  }
		   
		   /*
		    * Defines the thread that handles the connection.  The thread is responsible
		    * for opening the connection and for receiving messages.  This class contains
		    * several methods that are called by the main class, and that are therefore
		    * executed in a different thread.  Note that by using a thread to open the
		    * connection, any blocking of the graphical user interface is avoided.  By
		    * using a thread for reading messages sent from the other side, the messages
		    * can be received and posted to the transcript asynchronously at the same
		    * time as the user is typing and sending messages.
		    */
		   class ConnectionHandler extends Thread {
		      
		      private volatile ConnectionState state;
		      //private String remoteHost;
		      private int port;
		      private ServerSocket listener;
		      private Socket socket;
		      private PrintWriter out;
		      private BufferedReader in;
		      private boolean IsServer = false;
		      /*
		       * Listen for a connection on a specified port.  The constructor
		       * does not perform any network operations; it just sets some
		       * instance variables and starts the thread.  Note that the
		       * thread will only listen for one connection, and then will
		       * close its server socket.
		       */
		      ConnectionHandler(int port, boolean server) {
		    	 if(server)
		    	 {
		    		 IsServer = server;
		    		 state = ConnectionState.LISTENING;
		    		 this.port = port;
		    		 //postMessage("LISTENING");
		    		 start();
		    	 }
		    	 else 
		    	 {
		    		 state = ConnectionState.CONNECTING;
			         //this.remoteHost = remoteHost;
			         this.port = port;
			         //postMessage("CONNECTING TO " + remoteHost + " ON PORT " + port + "\n");
			         //postMessage("CONNECTING TO A SERVER");
			         start();
		    	 }
		      }
		      private void postMessage(String message) {
		    	  NotifMessage = message;
		    	  Message m = new Message();
		    	  m.what = 1;	//m.what = 2 significa 
		    	  myUpdateHandler.sendMessage(m);	
		      }
		      /**
		       * Returns the current state of the connection.  
		       */
		      synchronized ConnectionState getConnectionState() {
		         return state;
		      }
		      
		      /**
		       * Send a message to the other side of the connection, and post the
		       * message to the transcript.  This should only be called when the
		       * connection state is ConnectionState.CONNECTED; if it is called at
		       * other times, it is ignored.
		       */
		      synchronized void send(String message) {
		         if (state == ConnectionState.CONNECTED) {
		        	 //if(mesa!=null)
				    		//mesa.bloquear = true;
		            out.println(message);
		            out.flush();//Realmente me parece q no hace falta, pues fue construido con la opcion de autoflush
		           // if(mesa!=null)
			    		//mesa.bloquear = false;
		            if (out.checkError()) {
		               postMessage("ERROR OCCURRED WHILE TRYING TO SEND DATA.");
		               close();
		            }//  if (out.checkError())
		         }
		      }
		      
		      /**
		       * Close the connection. If the server socket in non-null, the
		       * server socket is closed, which will cause its accept() method to
		       * fail with an error.  If the socket is non-null, then the socket
		       * is closed, which will cause its input method to fail with an
		       * error.  (However, these errors will not be reported to the user.)
		       */
		      synchronized void close() {
		         state = ConnectionState.CLOSED;
		         try {
		            if (socket != null)
		               socket.close();
		            else if (listener != null)
		               listener.close();
		         }
		         catch (IOException e) {
		         }
		      }
		      
		      /**
		       * This is called by the run() method when a message is received from
		       * the other side of the connection.  The message is posted to the
		       * transcript, but only if the connection state is CONNECTED.  (This
		       * is because a message might be received after the user has clicked
		       * the "Disconnect" button; that message should not be seen by the
		       * user.)
		       */
		      synchronized private void received(String message) {
		         if (state == ConnectionState.CONNECTED)
		         {
		            //postMessage("RECEIVE:  " + message);
					Message m = new Message();
					m.what = 0;	//m.what = 0 significa mensaje recibido
					MsgRec = message;
					myUpdateHandler.sendMessage(m);	
		         }
		      }
		      
		      /**
		       * This is called by the run() method when the connection has been
		       * successfully opened.  It enables the correct buttons, writes a
		       * message to the transcript, and sets the connected state to CONNECTED.
		       */
		      synchronized private void connectionOpened() throws IOException {
		         listener = null;
		         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		         out = new PrintWriter(socket.getOutputStream(), true);
		         state = ConnectionState.CONNECTED;
		         //
		        //postMessage("CONNECTION ESTABLISHED");  
		         //Voy a poner aqui otras cosas ya del juego
		         if(!IsServer){
		        	 out.println(MsgEnv);
		         }
		      }
		      
		      /**
		       * This is called by the run() method when the connection is closed
		       * from the other side.  (This is detected when an end-of-stream is
		       * encountered on the input stream.)  It posts a mesaage to the
		       * transcript and sets the connection state to CLOSED.
		       */
		      synchronized private void connectionClosedFromOtherSide() {
		         if (state == ConnectionState.CONNECTED) {
		            postMessage("CONNECTION CLOSED FROM OTHER SIDE");
		            state = ConnectionState.CLOSED;
		         }
		      }
		      
		      /**
		       * Called from the finally clause of the run() method to clean up
		       * after the network connection closes for any reason.
		       */
		      private void cleanUp() {
		         state = ConnectionState.CLOSED;
		         //
		         postMessage("*** CONNECTION CLOSED ***");
		         if (socket != null && !socket.isClosed()) {
		               // Make sure that the socket, if any, is closed.
		            try {
		               socket.close();
		            }
		            catch (IOException e) {
		            }
		         }
		         socket = null;
		         in = null;
		         out = null;
		         listener = null;
		      }
		      
		      
		      /**
		       * The run() method that is executed by the thread.  It opens a
		       * connection as a client or as a server (depending on which 
		       * constructor was used).
		       */
		      public void run() {
		         try {
		            if (state == ConnectionState.LISTENING) {
		                  // Open a connection as a server.
		               listener = new ServerSocket(port);
		               socket = listener.accept();
		               listener.close();
		            }
		            else if (state == ConnectionState.CONNECTING) {
		                  // Open a connection as a client.
		            	mWifiManager = (WifiManager)estaActividad.getSystemService(Context.WIFI_SERVICE);
						DhcpInfo dhcp = mWifiManager.getDhcpInfo();
						int dhc = dhcp.serverAddress;
						String dhcS = ( dhc & 0xFF)+ "."+((dhc >> 8 ) & 0xFF)+"."+((dhc >> 16 ) & 0xFF)+"."+((dhc >> 24 ) & 0xFF);
		               //socket = new Socket(remoteHost,port);
						socket = new Socket(dhcS,port);
		            }
		            connectionOpened();  // Set up to use the connection.
		            while (state == ConnectionState.CONNECTED) {
		                  // Read one line of text from the other side of
		                  // the connection, and report it to the user.
		               String input = in.readLine();
		               if (input == null)
		                  connectionClosedFromOtherSide();
		               else
		                  received(input);  // Report message to user.
		            }
		         }
		         catch (Exception e) {
		               // An error occurred.  Report it to the user, but not
		               // if the connection has been closed (since the error
		               // might be the expected error that is generated when
		               // a socket is closed).
		            if (state != ConnectionState.CLOSED)
		               postMessage("ERROR:  " + e);
		         }
		         finally {  // Clean up before terminating the thread.
		            cleanUp();
		         }
		      }
		      
		   } // end nested class ConnectionHandler
//--------------------------------------------------------------------

		
	   
}//class MultiGame_Activity
//----------------------------------------------------------------------------------------------------------------




