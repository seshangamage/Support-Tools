package playground.wso2telco.sheshan.com.wso2playgroundapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import com.sheshan.wso2telco.plyground.model.EnvironmentDTO;
import com.sheshan.wso2telco.ssl.SimpleSSLSocketFactory;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UnregisterNumberRequest extends Activity {

	String phoneNumber;
	String reponseMessage;
	EditText userPhoneNumberInput;
	TextView responseMesageText;
	ProgressDialog progress;
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_unregister);
		userPhoneNumberInput = (EditText) findViewById(R.id.editText1);
		responseMesageText = (TextView)findViewById(R.id.text_response_message);

		Button prodButton = (Button) findViewById(R.id.button_back_to_main);
		prodButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(UnregisterNumberRequest.this, StartMenu.class);
				finish();
				startActivity(intent);
			}
		});

		Button unregisterRequestButton = (Button) findViewById(R.id.button_production_unregister);
		progress = new ProgressDialog(this);
		progress.setTitle("Waiting For Response");
		progress.setMessage("........................");
		progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
		
		// To dismiss the dialog
		unregisterRequestButton.setOnClickListener(new OnClickListener() {

			@SuppressLint({ "InlinedApi", "NewApi" })
			public void onClick(View v) {
				phoneNumber = userPhoneNumberInput.getText().toString().trim();
				progress.show();
				UnregisterRequest unregisterTask = new UnregisterRequest();
				if (Build.VERSION.SDK_INT >= 11){
					unregisterTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
					
				}
				else{
					unregisterTask.execute();
					
				}
			}
		});
		
		 
		 
		  
	}

	// url: https://india.gateway.wso2telco.com/deleteuser/
	//
	// Headers:
	// Content-Type:application/json
	// Authorization:Bearer 4pgEGTUiu7VIDNM69VppTK2emIsa
	//
	// Body :
	// {"address":{
	// "address":["91###########","91###########""]}
	// }

	private class UnregisterRequest extends AsyncTask<Void, Void, String> {
		HttpResponse response;

		protected String doInBackground(Void... params) {
			// we use the OkHttp library from https://github.com/square/okhttp
			SSLSocketFactory sslFactory = null;
			try {
				sslFactory = new SimpleSSLSocketFactory(null);
			} catch (KeyManagementException | UnrecoverableKeyException | NoSuchAlgorithmException
					| KeyStoreException e1) {
				e1.printStackTrace();
			}
    		sslFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
    		 
    		HttpParams paramss = new BasicHttpParams();
    		HttpProtocolParams.setVersion(paramss, HttpVersion.HTTP_1_1);
    		HttpProtocolParams.setContentCharset(paramss, HTTP.UTF_8);
    		 
    		// Register the HTTP and HTTPS Protocols. For HTTPS, register our custom SSL Factory object.
    		SchemeRegistry registry = new SchemeRegistry();
    		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    		registry.register(new Scheme("https", sslFactory, 443));
    		 
    		// Create a new connection manager using the newly created registry and then create a new HTTP client
    		// using this connection manager
    		ClientConnectionManager ccm = new ThreadSafeClientConnManager(paramss, registry);
    		DefaultHttpClient httpclient = new DefaultHttpClient(ccm, paramss);

			HttpPost httpPost = new HttpPost(EnvironmentDTO.getUnregisterENdpoint());
			httpPost.addHeader("Authorization", EnvironmentDTO.getUnregisterAoutheriztionHeaderVlue());
			httpPost.addHeader("Content-Type", "application/json");

			String body = "{\"address\":{" + "\"address\":[\""+ phoneNumber + "\"]}" + "}";
			try {
				StringEntity se = new StringEntity(body);
				httpPost.setEntity(se);
				response = httpclient.execute(httpPost);
				getJsonObject(response);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
			return null;

		}

		protected void onPostExecute(String result) {
			responseMesageText.setText(reponseMessage);
			progress.dismiss();
		}

		private void getJsonObject(HttpResponse response) {
			BufferedReader rd = null;
			try {
				rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			} catch (UnsupportedOperationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			StringBuffer result = new StringBuffer();
			String line = "";
			try {
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				JSONObject o = new JSONObject(result.toString());
				if (o.get("status") != null) {
					reponseMessage = (String) o.get("status");
				}else{
					reponseMessage =  o.toString();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				progress.dismiss();
			}
		}

	}

}
