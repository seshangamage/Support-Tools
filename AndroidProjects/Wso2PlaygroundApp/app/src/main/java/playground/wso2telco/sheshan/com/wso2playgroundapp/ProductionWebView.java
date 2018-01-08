package playground.wso2telco.sheshan.com.wso2playgroundapp;



import com.sheshan.wso2telco.plyground.model.EnvironmentDTO;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;



public class ProductionWebView extends Activity {

	String endpoint;

    WebView web;
    
    String a;
 
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.production_webview);
        endpoint = getMyUrl();
        web = (WebView) findViewById(R.id.webview01);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(endpoint);
    }
 
    public class myWebClient extends WebViewClient
    {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);

        }
 
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            view.loadUrl(url);
        	if(view.getUrl().contains("playground2")&& view.getUrl().contains("&code=")){
        		Intent intent = new Intent(getBaseContext(), ProductionTokenRequest.class);
        		intent.putExtra("URL", view.getUrl());
        		finish();
        		startActivity(intent);
        	}
            return true;
 
        }
        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, final SslError error) {
            handler.proceed();
        }
    }
    
    private String getMyUrl(){
    	String scope = EnvironmentDTO.scope;
    	if(scope.contains("openid")){
    		String url = EnvironmentDTO.getOpenidEndpoint()+"&scope="+scope+"&redirect_uri="+EnvironmentDTO.getCallBackUrl()+"&client_id="+EnvironmentDTO.getClientID();
    		return url;
    	}
    	if(scope.contains("mnv")){
    		String url = EnvironmentDTO.getMnvEndpoint()+EnvironmentDTO.login_hint+"&scope="+scope+"&redirect_uri="+EnvironmentDTO.getCallBackUrl()+"&client_id="+EnvironmentDTO.getClientID();
    		return url;
    	}
    	if(scope.contains("mc_india_tc")){
    		String url = EnvironmentDTO.getMnvEndpoint()+EnvironmentDTO.login_hint+"&scope="+scope+"&redirect_uri="+EnvironmentDTO.getCallBackUrl()+"&client_id="+EnvironmentDTO.getClientID();
    		return url;
    	}
    	return null;
    }


}


