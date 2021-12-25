package ernest.testgame.canvas.setgame;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainSetGameActiviy extends Activity {
	/*
	static Rect rectgle;
	Window window;
	TableOfGame mesa;
	int A, H;
	*/
	//boolean tablet = false;
    Menu menuPPAL;
    AlertDialog.Builder dialogAbout, dialogHelp, dialogWelcome;
  
	
	public final static String EXTRA_MESSAGE = "ernest.testgame.canvas.setgame";
	
	//nueva version 0.6:
	Button bttnSinglePlayer, bttnMultiPlayer, bttnHelp, bttnAbout;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//nueva version 0.6:
		 setContentView(R.layout.activity_main_set_game_activiy); 
		
		bttnSinglePlayer = (Button) findViewById(R.id.bttnSG);
		bttnMultiPlayer = (Button) findViewById(R.id.bttnMP);
		bttnHelp = (Button) findViewById(R.id.bttnHelp);
		bttnAbout = (Button) findViewById(R.id.bttnAbout);
		
		bttnSinglePlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),SinglePlayerActivity.class);
	        	startActivity(intent);
				
			}
		});
		
		bttnMultiPlayer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),MultiPlayerActivity.class);
	        	startActivity(intent);
				
			}
		});
		bttnHelp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogHelp.show();
				
			}
		});
		bttnAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialogAbout.show();
			}
		});
		
		dialogAbout = new AlertDialog.Builder(this).setIcon(R.drawable.viewsetgame).setTitle("About").setMessage("Set  is a real-time card game designed by Marsha Falco in 1974 and published by Set Enterprises in 1991. The game evolved out of a coding system that the designer used in her job as a geneticist. Set won American Mensa's Mensa Select award in 1991 and placed 9th in the 1995 Deutscher Spiele Preis.\n\n(Developed by EAP Sw 2013)");
		dialogAbout.setCancelable(false); 
	    dialogAbout.setNeutralButton("OK",null);
		
		dialogHelp = new AlertDialog.Builder(this).setIcon(R.drawable.viewsetgame).setTitle("Help").setMessage("To create a SET, a player must locate three cards in which each of the four features is either all the same on each card or all different on each card, when looked at individually. The four features are, symbol(rectangle, circle or diamond), color(red, blue or green), number(one, two or three) or shading(solid, striped or open).\n\n(Developed by EAP Sw 2013)");
		dialogHelp.setCancelable(false); 
	    dialogHelp.setNeutralButton("OK",null);
	    
	    dialogWelcome = new AlertDialog.Builder(this).setIcon(R.drawable.viewsetgame).setTitle("Welcome to SET GAME").setMessage("SET is a highly addictive, original game of visual perception; a fascinating challenge for either solitaire or competitive play. Age is no advantage in this fast paced family game.\nGO, beat the time!!!\n\n (Developed by EAP Sw 2013)");
		dialogWelcome.setCancelable(false); 
	    dialogWelcome.setNeutralButton("OK",null);
		
	    dialogWelcome.show();

    }

/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_set_game_activiy, menu);
		menuPPAL = menu;
		return true;
	}
	*/
	
	/*
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.item1:
	            // mesa.newGame();
	            return true;
	        case R.id.item2:
	        	Intent intent = new Intent(getApplicationContext(),BestTimesBoardActivity.class);
	        	startActivity(intent);
	            return true;
	        case R.id.item3:
	        	 //mesa.ClearRecords();
	            return true;
	        case R.id.item4:	//About
	        	dialogAbout.show();
	            return true;
	        case R.id.item5:	//Help
	        	dialogHelp.show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	*/
	
	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Handle the back button 
		if(keyCode == KeyEvent.KEYCODE_BACK) { 
			//Ask the user if they want to quit 
			 new AlertDialog.Builder(this) .setTitle("Exit").setCancelable(false) .setPositiveButton("Yes", new DialogInterface.OnClickListener() { public void onClick(DialogInterface dialog, int id) { finish(); } }).setNegativeButton("No", null) .show();  
			return true; 
		} 
		else { 
			return super.onKeyDown(keyCode, event); 
		} 
	}//onKeyDown 
	
}//MainSetGameActiviy

