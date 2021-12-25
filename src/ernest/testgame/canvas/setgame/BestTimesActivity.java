package ernest.testgame.canvas.setgame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;

public class BestTimesActivity extends Activity {
	public final static String EXTRA_MESSAGE = "ernest.testgame.canvas.setgame";  //agregado v_0.8
	static int [] besttimes = new int[]{1200, 1200, 1200, 1200, 1200};
	static String [] topnames = new String[]{"Empty","Empty","Empty","Empty","Empty"};
	Button donebttn;
	EditText editname;
	AlertDialog.Builder dialogNoName;
	int position;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.besttimeslayaout);
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainSetGameActiviy.EXTRA_MESSAGE);
		position = Integer.parseInt(message);
		load("besttimesfile");
		donebttn = (Button) findViewById(R.id.bttndone);
		editname = (EditText)findViewById(R.id.eTextNameRecord);
		//editname.setText("");
		dialogNoName = new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Time Record").setMessage("Please enter  your name and touch the button 'Done'").setNeutralButton("OK",null);
		dialogNoName.setCancelable(false); 
		donebttn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			//euro2Dolar();
			String cadena = editname.getText().toString();
			if(cadena.length()==0){
				//new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Record Time").setMessage("Please enter  your name and touch the button 'Done'").setNeutralButton("OK",null).show(); 
				dialogNoName.show();
			}
			else{
				 String championname = editname.getText().toString();
				 if(championname.length()>25){
					 char [] aux = new char[25];
					 championname.getChars(0, 24,aux, 0);
					 championname = new String(aux);
				 }
				 topnames[position] = championname;
				 save("besttimesfile");
		          Intent intent2 = new Intent(getApplicationContext(),BestTimesBoardActivity.class);
				  String message = Integer.toString(position);
				  intent2.putExtra(EXTRA_MESSAGE, message);
		          startActivity(intent2);	
				 finish();
			}
				
			}});
	}//onCreate
	
	@Override public boolean onKeyDown(int keyCode, KeyEvent event) {
		//Handle the back button 
		if(keyCode == KeyEvent.KEYCODE_BACK) { 
			//Ask the user if they want to quit 
			//new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Record Time").setMessage("Please enter  your name and touch the button 'Done'").setNeutralButton("OK",null).show(); 
			dialogNoName.show();
			return true; 
		} 
		else { 
			return super.onKeyDown(keyCode, event); 
		} 
	}//onKeyDown 
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
	//----------------------------------------
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
	//--------------------------------------------------------------
	
	

}// BestTimesActivity
