package brazil.sheshan.wso2telco.com.brazilplayground;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.ArrayList;


import brazil.sheshan.wso2telco.com.brazilplayground.dto.EnvironmentDTO;
import brazil.sheshan.wso2telco.com.brazilplayground.ssl.SimpleSSLSocketFactory;

public class ProductionTokenRequest extends Activity{

	String aouthCodeValue;
	String tokenCodeValue;
	String userInfo;
	TextView aouthCode;
	TextView tokenCode;
	TextView user_Info;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_token_request);
		aouthCode = (TextView)findViewById(R.id.txt_code);
		tokenCode = (TextView)findViewById(R.id.txt_token);
		user_Info = (TextView)findViewById(R.id.txt_userinfo);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			aouthCodeValue = getAouthCodeFromUrl(extras.getString("URL"));
		    //The key argument here must match that used in the other activity
		}
		//in your OnCreate() method
		aouthCode.setText(aouthCodeValue);
		TokenRequest tokenTask = new TokenRequest();
	    if(Build.VERSION.SDK_INT >= 11)
	    	tokenTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	    else
	    	tokenTask.execute();
	    SystemClock.sleep(6000);
		UserInfoRequest userInfoTask = new UserInfoRequest();
	    if(Build.VERSION.SDK_INT >= 11)
	    	userInfoTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	    else
	    	tokenTask.execute();
		
		Button prodButton = (Button) findViewById(R.id.button_main_menu);
		prodButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent intent = new Intent(ProductionTokenRequest.this, ProductiontMenu.class); 
				finish();
				startActivity(intent);
			}
		});


	}
	
	private String getAouthCodeFromUrl(String url){
		String [] params = url.split("code=");
		String [] codeparams = params[1].split("&");
		return codeparams[0];
		
	}
	
    private class TokenRequest extends AsyncTask<Void, Void, String> {
    	HttpResponse response ;
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
    		ArrayList<NameValuePair> postParameters;
    		HttpPost httpPost = new HttpPost(EnvironmentDTO.getTokenEndpoint());

			httpPost.addHeader("Authorization", EnvironmentDTO.getAOutherizationHeaderValue());
    		httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");


    	    postParameters = new ArrayList<NameValuePair>();
    	    postParameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
    	    postParameters.add(new BasicNameValuePair("code",aouthCodeValue));
    	    postParameters.add(new BasicNameValuePair("redirect_uri", EnvironmentDTO.getCallBackUrl()));
    	    try {
    			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
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
    		}catch(RuntimeException e){
    			e.printStackTrace();
    		}
    	     return null;
    	   
        }

        protected void onPostExecute(String result) {
        	tokenCode.setText(tokenCodeValue);
        }
        
        private void getJsonObject(HttpResponse response){
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
    	    	if(o.get("access_token")!=null){
    	    	tokenCodeValue = (String) o.get("access_token");
    	    	}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }



}

    
    
    private class UserInfoRequest extends AsyncTask<Void, Void, String> {
    	HttpResponse response ;
        protected String doInBackground(Void... urls) {

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
    		HttpGet httpGet = new HttpGet(EnvironmentDTO.getUserInfoEndpoint());
    		httpGet.addHeader("Authorization", "Bearer "+tokenCodeValue);
    		httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");

    	    try {
    			 response = httpclient.execute(httpGet);
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
    		}catch(RuntimeException e){
    			e.printStackTrace();
    		}
    	     return null;
    	   
        }

        protected void onPostExecute(String result) {
        	user_Info.setText(userInfo);
        }
             
        private void getJsonObject(HttpResponse response){
       	 BufferedReader rd = null;
       	 String token =null;
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
    	    	userInfo = o.toString();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
       }
}

}
