package ernest.testgame.canvas.setgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SinglePlayerActivity extends Activity {
	public final static String EXTRA_MESSAGE = "ernest.testgame.canvas.setgame";  //agregado v_0.8
	Button bttnNewGame, bttnBestTimes;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_player);
		this.setTitle("Single Player");
		bttnBestTimes = (Button) findViewById(R.id.bttnU);
		bttnNewGame = (Button) findViewById(R.id.bttnC);
		
		bttnBestTimes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
	        	Intent intent = new Intent(getApplicationContext(),BestTimesBoardActivity.class);
	        	String message = Integer.toString(5);
				intent.putExtra(EXTRA_MESSAGE, message);
	        	startActivity(intent);
			}
		});
		bttnNewGame.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
	        	Intent intent = new Intent(getApplicationContext(),SingleGame_Activity.class);
	        	startActivity(intent);
			}
		});
		
	}
		

}
