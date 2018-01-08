package playground.wso2telco.sheshan.com.wso2playgroundapp;

import com.sheshan.wso2telco.plyground.model.EnvironmentDTO;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class OperatorsMenu extends Activity{
	
	

	final String AIRCEL ="aircel";
	final String AIRTEL ="airtel";
	final String IDEA ="idea";
	final String SPARK ="spark";
	final String TATADOCOMO ="tatadocomo";
	final String TELENOR ="telenor";
	final String VODAFONE ="vodafone";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opertaor_menu);
		
		ImageButton aircelButton = (ImageButton) findViewById(R.id.button_aircel);
		aircelButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = AIRCEL;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton airtellButton = (ImageButton) findViewById(R.id.button_airtel);
		airtellButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = AIRTEL;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton ideaButton = (ImageButton) findViewById(R.id.button_idea);
		ideaButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = IDEA;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton sparkButton = (ImageButton) findViewById(R.id.button_spark);
		sparkButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = SPARK;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		
		ImageButton tataDocomoButton = (ImageButton) findViewById(R.id.button_tatadocomo);
		tataDocomoButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = TATADOCOMO;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		
		ImageButton telenorButton = (ImageButton) findViewById(R.id.button_telenor);
		telenorButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = TELENOR;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		
		ImageButton vodafoneButton = (ImageButton) findViewById(R.id.button_vodafone);
		vodafoneButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = VODAFONE;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});


		Button prodButton = (Button) findViewById(R.id.button_start_exit);
		prodButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(OperatorsMenu.this, StartMenu.class); 
				finish();
				startActivity(intent);
			}
		});
	}
	
	

}
