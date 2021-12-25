package ernest.testgame.canvas.setgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MultiPlayerActivity extends Activity {
	Button bttnCrear, bttnUnirse;
	Activity MultiPlyActivity;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.multi_player);
		this.setTitle("Multiplayer");
		bttnCrear = (Button) findViewById(R.id.bttnC);
		bttnUnirse = (Button) findViewById(R.id.bttnU);
		MultiPlyActivity = this;
		
		bttnCrear.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MultiPlyActivity.getApplicationContext(),MultiGame_Activity .class);
				String message = "server";
				intent.putExtra(MainSetGameActiviy.EXTRA_MESSAGE, message);
				MultiPlyActivity.startActivity(intent);
				
			}
		});
		bttnUnirse.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MultiPlyActivity.getApplicationContext(),MultiGame_Activity .class);
				String message = "cliente";
				intent.putExtra(MainSetGameActiviy.EXTRA_MESSAGE, message);
				MultiPlyActivity.startActivity(intent);
				
			}
		});
		
	}//OnCreate

}//class MultiPlayerActivity


