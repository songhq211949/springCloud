package songhq.com.cache.utils;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;//这个是什么包啊

public class HttpsRequest {

	private static TrustManager trustManager = new X509TrustManager() {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
	};

	private static SSLConnectionSocketFactory socketFactory;

	protected static void enableSSL() throws NoSuchAlgorithmException,
			KeyManagementException {
		Security.addProvider(new BouncyCastleProvider());
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, new TrustManager[] { trustManager }, null);
		socketFactory = new SSLConnectionSocketFactory(context,
				NoopHostnameVerifier.INSTANCE);
	}

	public static String httpGet(String url, String respEncode) {
		if (StringUtil.isNullStr(respEncode)) {
			respEncode = "UTF-8";
		}
		try {
			enableSSL();
		} catch (KeyManagementException e2) {
			e2.printStackTrace();
		} catch (NoSuchAlgorithmException e2) {
			e2.printStackTrace();
		}
		RequestConfig defaultRequestConfig = RequestConfig
				.custom()
				.setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(
						Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.build();
		Registry<ConnectionSocketFactory> socketFactoryManager = RegistryBuilder
				.<ConnectionSocketFactory> create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", socketFactory).build();

		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(
				socketFactoryManager);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setConnectionManager(connectionManager)
				.setDefaultRequestConfig(defaultRequestConfig).build();

		HttpGet get = new HttpGet(url);
		CloseableHttpResponse closeableHttpResponse = null;
		try {
			closeableHttpResponse = httpClient.execute(get);
			String result = EntityUtils.toString(
					closeableHttpResponse.getEntity(), respEncode);
			return result;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				closeableHttpResponse.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
}
