package brazil.sheshan.wso2telco.com.brazilplayground;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import brazil.sheshan.wso2telco.com.brazilplayground.dto.EnvironmentDTO;

public class OperatorsMenu extends Activity{
	
	

	final String ALGAR ="algar";
	final String CLARO ="claro";
	final String TIM ="tim";
	final String VIVO ="vivo";
	final String OI ="oi";
	final String NII ="nii";
	final String SPARK ="spark";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opertaor_menu);
		
		ImageButton algarButton = (ImageButton) findViewById(R.id.button_algar);
		algarButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = ALGAR;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton claroButton = (ImageButton) findViewById(R.id.button_claro);
		claroButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = CLARO;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton timButton = (ImageButton) findViewById(R.id.button_tim);
		timButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = TIM;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		ImageButton vivoButton = (ImageButton) findViewById(R.id.button_vivo);
		vivoButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = VIVO;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		
		ImageButton oiButton = (ImageButton) findViewById(R.id.button_oi);
		oiButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = OI;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		
		ImageButton niiButton = (ImageButton) findViewById(R.id.button_nii);
		niiButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = NII;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});
		
		
		ImageButton sparkButton = (ImageButton) findViewById(R.id.button_spark);
		sparkButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				EnvironmentDTO.operatorName = SPARK;
				Intent intent = new Intent(OperatorsMenu.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});


		Button prodButton = (Button) findViewById(R.id.button_start_exit);
		prodButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(OperatorsMenu.this, StartMenu.class); 
				finish();
				startActivity(intent);
			}
		});
	}
	
	

}
