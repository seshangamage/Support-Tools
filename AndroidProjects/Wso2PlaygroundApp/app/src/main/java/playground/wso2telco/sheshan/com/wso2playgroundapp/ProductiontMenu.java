package playground.wso2telco.sheshan.com.wso2playgroundapp;

import com.sheshan.wso2telco.plyground.model.EnvironmentDTO;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class ProductiontMenu extends Activity {

	EnvironmentDTO environmentDTO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_production_menu);

		Spinner spinner = (Spinner) findViewById(R.id.scope_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.scope_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);

		Button button = (Button) findViewById(R.id.button_production_webview);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Intent intent = new Intent(ProductiontMenu.this, ProductionWebView.class);
				Spinner mySpinner = (Spinner) findViewById(R.id.scope_spinner);
				EnvironmentDTO.scope = mySpinner.getSelectedItem().toString();
				if (EnvironmentDTO.scope.contains("mnv") | EnvironmentDTO.scope.contains("mc_india_tc")) {
					LayoutInflater li = LayoutInflater.from(ProductiontMenu.this);
					View promptsView = li.inflate(R.layout.prompt, null);
					AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ProductiontMenu.this);
					alertDialogBuilder.setView(promptsView);
					final EditText userInput = (EditText) promptsView
							.findViewById(R.id.editTextResult);
					// set dialog message
					alertDialogBuilder.setCancelable(false)
							.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									EnvironmentDTO.login_hint = userInput.getText().toString();
									finish();
									startActivity(intent);
								}
							});
					// create alert dialog
					AlertDialog alertDialog = alertDialogBuilder.create();
					alertDialog.show();
				}
				else{
				finish();
				startActivity(intent);
				}

			}

		});
		
		Button prodButton = (Button) findViewById(R.id.button_back_to_main);
		prodButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(ProductiontMenu.this, StartMenu.class); 
				finish();
				startActivity(intent);
			}
		});

	}

}
