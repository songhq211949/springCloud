package songhq.com.cache.utils;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HttpClientUtil {
	public static Logger log = LoggerFactory.getLogger(HttpClientUtil.class);
	private static MultiThreadedHttpConnectionManager connectionManager;
	private static HttpClient client;
	/**
	 * maximum number of connections allowed per host
	 */
	private static int maxHostConnections = 100;//TODO:数值待定
	
	/**
	 * maximum number of connections allowed overall
	 */
	private static int maxTotalConnections = 50;//TODO:数值待定
	
	/**
	 * the timeout until a connection is etablished
	 */
	private static int connectionTimeOut = 10000;//TODO:数值待定
	
	static {
		connectionManager = new MultiThreadedHttpConnectionManager();
		HttpConnectionManagerParams params = new HttpConnectionManagerParams();
		params.setDefaultMaxConnectionsPerHost(maxHostConnections);
		params.setMaxTotalConnections(maxTotalConnections);
		params.setConnectionTimeout(connectionTimeOut);
		params.setSoTimeout(connectionTimeOut);
		connectionManager.setParams(params);
		client = new HttpClient(connectionManager);
		client.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
	}

	public static String requestGet(String url) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		return getResponseStr(getMethod);
	}
	
	public static String requestGet(String url,NameValuePair[] values) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.setQueryString(values);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		return getResponseStr(getMethod);
	}
	
	public static String requestGet(String url, int outTime) {
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		return getResponseStr(getMethod);
	}
	
	public static int getRequestStatusCode(String url, Map<String, String> headerMap, int outTime){
		// 模拟访问Header参数
		List<String> headerList = new ArrayList<String> ();
		headerList.add("X-UP-BEAR-TYPE".toLowerCase());
		headerList.add("x-up-calling-line-id".toLowerCase());
		headerList.add("referer".toLowerCase());
		headerList.add("User-Agent".toLowerCase());
		headerList.add("WDAccept-Encoding".toLowerCase());
		

		int statusCode = 0;
		GetMethod getMethod = new GetMethod(url);
		try {
			StringBuffer sb = new StringBuffer ();
			if(headerMap != null && headerMap.size() > 0){
				for(Iterator<String> it = headerMap.keySet().iterator(); it.hasNext(); ){
					String key = it.next();
					if(headerList.contains(key.toLowerCase())){
						getMethod.setRequestHeader(key, headerMap.get(key));
						sb.append(key + "=" + headerMap.get(key) + ", ");
					}
				}
			}
			log.info("[URL=" + url + ", " + sb.toString() + "]");
			statusCode = client.executeMethod(getMethod);
			if(statusCode == 200 
					&& getMethod.getResponseHeader("isError") != null
					&& StringUtil.nullToBoolean(getMethod.getResponseHeader("isError").getValue())){
				statusCode = 500;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			getMethod.releaseConnection();
		}
		return statusCode;
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值List
	 * @return 调用得到的字符串
	 */
	public static String httpClientPost(String url,List<NameValuePair[]> values){
		PostMethod postMethod = new PostMethod(url);
		//将表单的值放入postMethod中
		for (NameValuePair[] value : values) {
			postMethod.addParameters(value);
		}
		return getResponseStr(postMethod);

	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值
	 * @return 调用得到的字符串
	 */
	public static String httpClientPost(String url, NameValuePair[] values){
		List<NameValuePair[]> list = new ArrayList<NameValuePair[]>();
		list.add(values);
		return httpClientPost(url, list);
	}
	
	/**
	 * 使用get方式调用
	 * @param url调用的URL
	 * @return 调用得到的字符串
	 */
	public static String httpClientGet(String url){
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		return getResponseStr(getMethod);

	}
	
	
	/**
	 * 将MAP转换成HTTP请求参数
	 * @param pairArr
	 * @return
	 */
	public static NameValuePair[] praseParameterMap(Map<String, String> map){
		
		NameValuePair[] nvps = new NameValuePair[map.size()];
		
		Set<String> keys = map.keySet();
		int i=0;
		for(String key:keys){
			nvps[i] = new NameValuePair();
			nvps[i].setName(key);
			nvps[i].setValue(map.get(key));
			
			i++;
		}
		              
		return nvps;
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值
	 * @param xml 传递的xml参数
	 * @return
	 */
	public static String httpClientPost(String url, NameValuePair[] values, String xml){
		StringBuilder sb = new StringBuilder();
		
		log.debug(" http url :" + url);
	     for (NameValuePair nvp : values) {
	    	 log.debug(" http param :" + nvp.getName() + " = " + nvp.getValue());
	      }

		
		PostMethod method = new PostMethod(url);
		method.setQueryString(values);
		method.addRequestHeader("Content-Type", "text/xml;charset=UTF-8");
		RequestEntity reis = null;
		InputStream input = null;
		InputStream is = null;
		BufferedReader dis = null;
		try {
			input = new ByteArrayInputStream(xml.getBytes("utf-8"));
			reis = new InputStreamRequestEntity(input);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());

			method.setRequestEntity(reis);
			client.executeMethod(method);

			// 以流的行式读入，避免中文乱码
			is = method.getResponseBodyAsStream();
			dis = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String str = "";
			while ((str = dis.readLine()) != null) {
				sb.append(str);
			}
		} catch (HttpException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			try {
				if (dis != null)
					dis.close();
				if (is != null)
					is.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 发送post或get请求获取响应信息
	 * @param method	http请求类型,post或get请求
	 * @return			服务器返回的信息
	 */
	public static String getResponseStr(HttpMethodBase method) {
		StringBuilder sb = new StringBuilder();
		try {
			int statusCode = client.executeMethod(method);
			if (statusCode != 200) {
				log.error("HttpClient Error : statusCode = " + statusCode + ", uri :" + method.getURI() );
				return "";
			}
			//以流的行式读入，避免中文乱码
			InputStream is = method.getResponseBodyAsStream();
			BufferedReader dis = new BufferedReader(new InputStreamReader(is,"utf-8"));   
			String str = "";                           
			while ((str = dis.readLine()) != null) {
				sb.append(str);
			}
		} catch (Exception e) {
			log.info("调用远程出错;发生网络异常!"+e.getMessage());
//			e.printStackTrace();
		} finally {
			// 关闭连接
			method.releaseConnection();
		}
		return sb.toString();
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param values 传递的参数值List
	 * @param headers 头信息列表
	 */
	public static String httpClientPost(String url, NameValuePair[] values, Map<String, String> headers){
		InputStream input = null;
		String s = "";
		PostMethod method = null;
		try {
			method = new PostMethod(url);
			if (headers != null && headers.size() > 0) {
				for (Entry<String, String> entry : headers.entrySet()) {
					method.addRequestHeader(entry.getKey(), entry.getValue());
				}
			}
			
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			if (values != null) {
				method.setQueryString(values);
			}

			s = getResponseStr(method);
		} catch (Exception e) {
			log.error("exception occur, message:" + e.getMessage(), e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if(method != null) {
					method.releaseConnection();
				}
			} catch (IOException e) {
			}
		}
		return s;
	}
	
	/**
	 * 使用post方式调用
	 * @param url 调用的URL
	 * @param xml 传递的xml参数
	 * @return
	 */
	public static String httpClientPost(String url, String xml){
		StringBuilder sb = new StringBuilder();
		
		PostMethod method = new PostMethod(url);
		method.addRequestHeader("Content-Type", "text/xml;charset=UTF-8");
		RequestEntity reis = null;
		InputStream input = null;
		InputStream is = null;
		BufferedReader dis = null;
		try {
			input = new ByteArrayInputStream(xml.getBytes("utf-8"));
			reis = new InputStreamRequestEntity(input);
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
					new DefaultHttpMethodRetryHandler());

			method.setRequestEntity(reis);
			client.executeMethod(method);

			// 以流的行式读入，避免中文乱码
			is = method.getResponseBodyAsStream();
			dis = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String str = "";
			while ((str = dis.readLine()) != null) {
				sb.append(str);
			}
		} catch (HttpException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
			try {
				if (dis != null)
					dis.close();
				if (is != null)
					is.close();
				if (input != null)
					input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	
	public static String httpPost(String url, Map<String, Object> headerParam, String json) {
		RequestEntity reis = null;
		InputStream input = null;
		String s = "";
		PostMethod method = null;
		try {
			method = new PostMethod(url);
			method.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
			if (headerParam != null && !headerParam.isEmpty()) {
				for (Entry<String, Object> entry : headerParam.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					method.addRequestHeader(key, StringUtil.null2Str(value));
				}
			}
			if (json != null) {
				input = new ByteArrayInputStream(json.getBytes("utf-8"));
				reis = new InputStreamRequestEntity(input);
				method.setRequestEntity(reis);
			}
			s = getResponseStr(method);
		} catch (Exception e) {
			log.error("exception occur, message:" + e.getMessage(), e);
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (method != null) {
					method.releaseConnection();
				}
			} catch (IOException e) {
			}
		}
		return s;
	}
}
