package ernest.testgame.canvas.setgame;

import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class BestTimesBoardActivity extends Activity {
	TextView [] lineas = new TextView[5];
	static int [] besttimes = new int[]{1200, 1200, 1200, 1200, 1200};
	static String [] topnames = new String[]{"Empty","Empty","Empty","Empty","Empty"};
	AlertDialog.Builder dialogoClear;
	int position;;
	 Typeface type;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainSetGameActiviy.EXTRA_MESSAGE);
		position = Integer.parseInt(message);
		setContentView(R.layout.besttimesboardlayaout);
		lineas[0] = (TextView) findViewById(R.id.linea11);
		lineas[1] = (TextView) findViewById(R.id.linea12);
		lineas[2] = (TextView) findViewById(R.id.linea13);
		lineas[3] = (TextView) findViewById(R.id.linea14);
		lineas[4] = (TextView) findViewById(R.id.linea15);
		this.setTitle("Best Times");
		if(position < 5)
		{
			//lineas[position].setTypeface(Typeface.BOLD_ITALIC);
			//lineas[position].setTypeface(Typeface.create("", Typeface.BOLD_ITALIC));
		    type = lineas[position].getTypeface();
		    lineas[position].setTypeface(type, Typeface.BOLD_ITALIC);
		}
		    
		load("besttimesfile");
		for(int i=0; i<5;i++){
            TTimer Aux = new TTimer();
            Aux.SetTime(besttimes[i]);
			lineas[i].setText(Aux.GetStringII()+"  "+"*** "+topnames[i]+" ***");
		}
		
		dialogoClear = new AlertDialog.Builder(this); 
	    dialogoClear.setTitle("ERASE TIME RECORDS");  
	    dialogoClear.setMessage("Do you want to erase times records definitely?");            
	    dialogoClear.setCancelable(false); 
	    dialogoClear.setPositiveButton("Yes", new DialogInterface.OnClickListener() {  
	        public void onClick(DialogInterface dialogo1, int id) {  
	            ClearScore(); 
	    		for(int i=0; i<5;i++){
	                TTimer Aux = new TTimer();
	                Aux.SetTime(besttimes[i]);
	    			lineas[i].setText(Aux.GetStringII()+"  "+"*** "+topnames[i]+" ***");
	    		}  
	        }  
	    });  
	    dialogoClear.setNegativeButton("No", null);
		
	}//onCreate
	public  void load(String filename) {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(openFileInput(filename)));
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
			} finally {
				try {
					if (in != null)
						in.close();
				} catch (IOException e) {
				}
			}
		}// load
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.besttimes_menu, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.item1:
	        	dialogoClear.show();
	        	//load("besttimesfile");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	//----------------------------------------------------------------
	public void ClearScore(){
		for (int j = 0; j < 5; j++){
			besttimes[j] = 1200;
			topnames[j] = "[Empty]";
		}
		save("besttimesfile");
	}//ClearScore
	//---------------------------------------------------------------------------
	public  void save(String filename) {
		BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(openFileOutput(filename, Context.MODE_PRIVATE)));
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
	
}//BestTimesBoardActivity
