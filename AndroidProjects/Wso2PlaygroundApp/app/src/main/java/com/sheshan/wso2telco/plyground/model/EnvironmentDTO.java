package com.sheshan.wso2telco.plyground.model;

public class EnvironmentDTO {

	/**
	 public class EnvironmentDTO {

	 /**
	 *
	 */
	public static String environmnet;
	public static String operatorName;
	public static String scope;
	public static String login_hint;

	private static String sandbox_client_id = "b2IDaQ8FaVWUTg3a1O3dlt7ToB0a";
	private static String sandbox_callback_url = "http://jenkins.wso2telco.com:9763/playground2/oauth2.jsp";
	private static String sandbox_openid_endpoint = "http://sandbox.gateway.wso2telco.com/authorize/v1/%s/oauth2/authorize?state=123sdsd&nonce=565qwe&response_type=code&acr_values=2";
	private static String sandbox_mnv_endpoint = "https://sandbox.gateway.wso2telco.com/authorizemnv/v1/%s/oauth2/authorize?state=stateNjgwZjRhMzdlO&nonce=nonceOWFlMTE5NjUyN&max_age=3600&response_type=code&acr_values=2&login_hint=";
	private static String sandbox_cpi_endpoint = "http://sandbox.gateway.wso2telco.com/authorizecpi/v1/%s/oauth2/authorize?state=stateNjgwZjRhMzdlO&nonce=nonceOWFlMTE5NjUyN&max_age=3600";
	private static String sandbox_authorization = "Bearer YjJJRGFROEZhVldVVGczYTFPM2RsdDdUb0IwYTpLX0N1d0ZCbnJXVm1YX0F0YlBjcm5mazJrbVVh";
	private static String sandbox_tokenEndpoint = "https://sandbox.mconnect.wso2telco.com/oauth2/token";
	private static String sandbox_userInfoEndpoint = "https://sandbox.mconnect.wso2telco.com/oauth2/userinfo?schema=openid";

	private static String preprod_client_id = "wb75jm9qbvljPoofUkZDPjCThF0a";
	private static String preprod_callback_url = "http://jenkins.wso2telco.com:9763/playground2/oauth2.jsp";
	private static String preprod_openid_endpoint = "https://gateway.preprod.wso2telco.com/authorize/v1/%s/oauth2/authorize?state=123sdsd&nonce=565qwe&response_type=code&acr_values=2";
	private static String preprod_mnv_endpoint = "https://gateway.preprod.wso2telco.com/tokenmnv/v1/%s/oauth2/authorize?state=stateNjgwZjRhMzdlO&nonce=nonceOWFlMTE5NjUyN&max_age=3600&response_type=code&acr_values=2&login_hint=";
	private static String preprod_cpi_endpoint = "https://gateway.preprod.wso2telco.com/tokencpi/v1/%s/oauth2/authorize?state=stateNjgwZjRhMzdlO&nonce=nonceOWFlMTE5NjUyN&max_age=3600";
	private static String preprod_authorization = "Bearer d2I3NWptOXFidmxqUG9vZlVrWkRQakNUaEYwYTpDWjg3d29aaE9fMllQQTVEbktnSlBLc1lrS0lh";
	private static String preprod_tokenEndpoint = "https://gateway.preprod.wso2telco.com/token/v1/%s/oauth2/token";
	private static String preprod_userInfoEndpoint = "https://mconnect.preprod.wso2telco.com/oauth2/userinfo?schema=openid";

	private static String production_client_id = "wb75jm9qbvljPoofUkZDPjCThF0a";
	private static String production_callback_url = "http://jenkins.wso2telco.com:9763/playground2/oauth2.jsp";
	private static String production_openid_endpoint = "http://india.gateway.wso2telco.com/authorize/v1/%s/oauth2/authorize?state=123sdsd&nonce=565qwe&response_type=code&acr_values=2";
	private static String production_mnv_endpoint = "http://india.gateway.wso2telco.com/authorizemnv/v1/%s/oauth2/authorize?state=stateNjgwZjRhMzdlO&nonce=nonceOWFlMTE5NjUyN&max_age=3600&response_type=code&acr_values=2&login_hint=";
	private static String production_cpi_endpoint = "http://india.gateway.wso2telco.com/authorizecpi/v1/%s/oauth2/authorize?state=stateNjgwZjRhMzdlO&nonce=nonceOWFlMTE5NjUyN&max_age=3600";
	private static String production_authorization = "Bearer d2I3NWptOXFidmxqUG9vZlVrWkRQakNUaEYwYTpDWjg3d29aaE9fMllQQTVEbktnSlBLc1lrS0lh";
	private static String production_tokenEndpoint = "https://india.gateway.wso2telco.com/token/v1/%s/oauth2/token";
	private static String production_userInfoEndpoint = "https://india.mconnect.wso2telco.com/oauth2/userinfo?schema=openid";
	private static String production_unregister_authorization = "Bearer 4pgEGTUiu7VIDNM69VppTK2emIsa";
	private static String production_unregister_endpoint = "https://india.gateway.wso2telco.com/deleteuser/v1";

	final static String sandboxString = "sandbox";
	final static String productionString = "production";
	final static String preprodString = "preprod";

	public static String getClientID() {
		if (environmnet.equals(sandboxString)) {
			return sandbox_client_id;
		}else if (environmnet.equals(productionString)) {
			return production_client_id;
		}else if (environmnet.equals(preprodString)) {
			return preprod_client_id;
		}
		return null;

	}

	public static String getOpenidEndpoint() {
		if (environmnet.equals(sandboxString)) {
			String url = sandbox_openid_endpoint;
			return String.format(url,operatorName);
		}else if (environmnet.equals(productionString)) {
			String url = production_openid_endpoint;
			return String.format(url,operatorName);
		}else if (environmnet.equals(preprodString)) {
            String url = preprod_openid_endpoint;
            return String.format(url,operatorName);
		}
		return null;
	}

	public static String getMnvEndpoint() {
		if (environmnet.equals(sandboxString)) {
			String url = String.format(sandbox_mnv_endpoint,operatorName);
			return url;
		}else if (environmnet.equals(productionString)) {
			return String.format(production_mnv_endpoint,operatorName);
		}else if (environmnet.equals(preprodString)) {
			return String.format(preprod_mnv_endpoint,operatorName);
		}
		return null;
	}

	public static String getCpiEndpoint() {
		if (environmnet.equals(sandboxString)) {
			return String.format(sandbox_cpi_endpoint,operatorName);
		}else if (environmnet.equals(productionString)) {
			return String.format(production_cpi_endpoint,operatorName);
		}else if (environmnet.equals(preprodString)) {
			return String.format(preprod_cpi_endpoint,operatorName);
		}
		return null;
	}

	public static String getCallBackUrl() {
		if (environmnet.equals(sandboxString)) {
			return sandbox_callback_url;
		}else if (environmnet.equals(productionString)) {
			return production_callback_url;
		}else if (environmnet.equals(preprodString)) {
			return preprod_callback_url;
		}
		return null;
	}

	public static String getAOutherizationHeaderValue() {
		if (environmnet.equals(sandboxString)) {
			return sandbox_authorization;
		}else if (environmnet.equals(productionString)) {
			return production_authorization;
		}else if (environmnet.equals(preprodString)) {
			return preprod_authorization;
		}
		return null;
	}

	public static String getTokenEndpoint() {
		if (environmnet.equals(sandboxString)) {
			return sandbox_tokenEndpoint;
		}else if (environmnet.equals(productionString)) {
			String url = production_tokenEndpoint;
			return String.format(url,operatorName);
		}else if (environmnet.equals(preprodString)) {
			String url = preprod_tokenEndpoint;
			return String.format(url,operatorName);
		}
		return null;
	}

	public static String getUserInfoEndpoint() {
		if (environmnet.equals(sandboxString)) {
			return sandbox_userInfoEndpoint;
		}else if (environmnet.equals(productionString)) {
			return production_userInfoEndpoint;
		}else if (environmnet.equals(preprodString)) {
			return preprod_userInfoEndpoint;
		}
		return null;
	}

	public static String getUnregisterAoutheriztionHeaderVlue() {

		return production_unregister_authorization;
	}

	public static String getUnregisterENdpoint() {

		return production_unregister_endpoint;
	}



}