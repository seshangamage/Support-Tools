package brazil.sheshan.wso2telco.com.brazilplayground.dto;

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

	private static String sandbox_client_id = "zv7KPRsu2hOJdT66_JKMxiqZeEIa";
	private static String sandbox_callback_url = "https://54.232.197.202:9444/playground2/oauth2.jsp";
	private static String sandbox_openid_endpoint = "https://54.232.197.202:8243/authorize/v1/oauth2/authorize/operator/%s?state=state123&nonce=nonce456&response_type=code&acr_values=2";
	private static String sandbox_authorization = "Bearer enY3S1BSc3UyaE9KZFQ2Nl9KS014aXFaZUVJYTpYdXhvVkxlRTBUcm51SHQ1X2FjRThfTzFfQzBh";
	private static String sandbox_tokenEndpoint = "https://54.232.197.202:8243/token/v1/oauth2/token";
	private static String sandbox_userInfoEndpoint = "https://54.232.197.202:8243/userinfo/v1/oauth2/userinfo?schema=openid";


	//https://54.232.197.202:8243/authorize/v1/oauth2/authorize/operator/spark?state=state123&nonce=nonce456&scope=openid&acr_values=2&response_type=code&redirect_uri=https://54.232.197.202:9444/wifi/progress.html&client_id=1244a638-c82b-4dec-9889-46c7bb0a6e01

	private static String preprod_client_id = "1244a638-c82b-4dec-9889-46c7bb0a6e01";
	private static String preprod_callback_url = "https://54.232.197.202:9444/wifi/progress.html";
	private static String preprod_openid_endpoint = "https://54.232.197.202:8243/authorize/v1/oauth2/authorize/operator/%s?state=123sdsd&nonce=565qwe&response_type=code&acr_values=2";
	private static String preprod_authorization = "Bearer MTI0NGE2MzgtYzgyYi00ZGVjLTk4ODktNDZjN2JiMGE2ZTAxOjk1MzY5MjFhLTUwNjktNGNhYi1hMTkxLTBhNDJiM2QzNmY0YQ==";
	private static String preprod_tokenEndpoint = "https://54.232.197.202:8243/token/v1/oauth2/token";
	private static String preprod_userInfoEndpoint = "https://mconnect.preprod.wso2telco.com/oauth2/userinfo?schema=openid";

	private static String production_client_id = "fqDnwbo1yoX7Fr23oZfmA2uu5U4a";
	private static String production_callback_url = "https://mconnect.abrmobileconnect.com.br/playground2/oauth2.jsp";
	private static String production_openid_endpoint = "https://gateway.abrmobileconnect.com.br/authorize/v2/%s?state=state123&nonce=nonce456&response_type=code&acr_values=2";
	private static String production_authorization = "Bearer ZnFEbndibzF5b1g3RnIyM29aZm1BMnV1NVU0YTpfU25SUnVkbXlQeGFzc0kzRm5TdVp6UGJ2UU1h";
	private static String production_tokenEndpoint = "https://gateway.abrmobileconnect.com.br/token/v2/%s";
	private static String production_userInfoEndpoint = "https://gateway.abrmobileconnect.com.br/userinfo/v2/spark?schema=openid";
	private static String production_unregister_authorization = "Bearer 4pgEGTUiu7VIDNM69VppTK2emIsa";
	private static String production_unregister_endpoint = "https://india.gateway.wso2telco.com/deleteuser/v1";
	//

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