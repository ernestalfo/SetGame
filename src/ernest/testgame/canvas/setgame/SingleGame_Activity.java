package ernest.testgame.canvas.setgame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SingleGame_Activity extends Activity {
	static Rect rectgle;
	Window window;
	TableOfGame mesa;
	int A, H;
	
	RelativeLayout mRelativeLayout;
	
	//version 0.7f pause/resume
	Menu menuPPAL;
	boolean enpira = false;
	//String pause_resume;
	public final static String EXTRA_MESSAGE = "ernest.testgame.canvas.setgame";

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		window= getWindow();
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		//------------------------------------------------------
		rectgle= new Rect();
		//window= getWindow();
		window.getDecorView().getWindowVisibleDisplayFrame(rectgle);
		DisplayMetrics metrics = new DisplayMetrics();
		window.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		int density = metrics.densityDpi;
		//Toast.makeText(getApplicationContext(), Integer.toString(density), Toast.LENGTH_LONG).show();
		
		//mesa = new TableOfGame(this); 
		if((rectgle.bottom - rectgle.top) > (rectgle.right - rectgle.left)){
			//estA en portrait
			H = rectgle.right - rectgle.left;
			A = rectgle.bottom - rectgle.top;
			if((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE)
				mesa = new TableOfGame(this, A, H, true, density);
			else
				mesa = new TableOfGame(this, A, H, false, density);
			mesa.orientation = false;
		}
		else{
			//estA en landscape
			H = rectgle.bottom - rectgle.top;
			A = rectgle.right - rectgle.left;
			if((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE)
				mesa = new TableOfGame(this, A, H, true, density);
			else
				mesa = new TableOfGame(this, A, H, false, density);
			mesa.orientation = true;
		}
		//if((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE)
		//if(!((this.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)>= Configuration.SCREENLAYOUT_SIZE_LARGE))
		//aVove: linea de prueVa para ver como se ve el juego cuando es en una taVleta
			//mesa.SetTablet();

		//mesa.H = H; 	//H es la altura en modo  landscape
		//mesa.A = A; 	//A es el ancho en modo landscape
		mesa.MyApp = this;
		
		//pause_resume = getString(R.string.menu_pause_resume);
		setContentView(mesa); 
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    super.onConfigurationChanged(newConfig);
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
	
	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Handle the back button 
		if(keyCode == KeyEvent.KEYCODE_BACK) { 
			//Ask the user if they want to quit 
			 new AlertDialog.Builder(this) .setTitle("Quit").setCancelable(false) .setPositiveButton("Yes", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int id) { enpira = true;finish(); } }).setNegativeButton("No", null) .show();  
			return true; 
		} 
		else { 
			return super.onKeyDown(keyCode, event); 
		} 
	}//onKeyDown 
	//--------------------------------------------------------------------------
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_set_game_activiy, menu);
		menuPPAL = menu;
		return true;
	}
	//---------------------------------------------------------------
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.item1:
	            mesa.newGame();
	            return true;
	        case R.id.item2:
	        	Intent intent = new Intent(getApplicationContext(),BestTimesBoardActivity.class);
	        	String message = Integer.toString(5);
				intent.putExtra(EXTRA_MESSAGE, message);
	        	startActivity(intent);
	            return true;
	        case R.id.item5:	//Help
	        	mesa.dialogHelp.show();
	            return true;
	        case R.id.item3:    //Pause/Resume
	        	mesa.pause_resume();
	        	//if(mesa.get_pause())
	        	 //menuPPAL.getItem(3).setTitle("RESUME");
	        	//else
	        	 //menuPPAL.getItem(3).setTitle("Pause");
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

//version 0.7f pause/resume
	// /*
@Override
	public void onPause() 
	{
    	super.onPause();  // Always call the superclass method first
    	
    	if(!mesa.get_pause() && enpira == false)
    	{
    		mesa.pause_resume();
    		//menuPPAL.getItem(3).setTitle("RESUME");
    	}
	}	
	// */
//---------------------------------

//-------------------------------------------------------
}//public class SingleGame_Activity extends Activity 
