package brazil.sheshan.wso2telco.com.brazilplayground;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import brazil.sheshan.wso2telco.com.brazilplayground.dto.EnvironmentDTO;

public class StartMenu extends AppCompatActivity {

    final String SANDBOX ="sandbox";
    final String PRODUCTION ="production";
    final String PREPROD ="preprod";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_menu);

        Button productionButton = (Button) findViewById(R.id.button_production);
        productionButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, OperatorsMenu.class);
                EnvironmentDTO.environmnet = PRODUCTION;
                finish();
                startActivity(intent);
            }
        });

        Button preprodButton = (Button) findViewById(R.id.button_preprod);
        preprodButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, OperatorsMenu.class);
                EnvironmentDTO.environmnet = PREPROD;
                finish();
                startActivity(intent);
            }
        });

        Button sandboxButton = (Button) findViewById(R.id.button_sandbox);
        sandboxButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, OperatorsMenu.class);;
                EnvironmentDTO.environmnet = SANDBOX;
                finish();
                startActivity(intent);
            }
        });

        Button unregisterButton = (Button) findViewById(R.id.button_unregister);
        unregisterButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(StartMenu.this, UnregisterNumberRequest.class);
                finish();
                startActivity(intent);
            }
        });

        Button exitButton = (Button) findViewById(R.id.button_start_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
                finish();
            }
        });
    }
}
