package org.apache.jmeter.performance.util;

import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * @since 2019年1月16日
 */
public class HttpUtil {

	public static String sendPost(String url, String data) {
		try {
			String encodeData = URLEncoder.encode(data, "UTF-8");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpPost post = new HttpPost(url);
			HttpEntity entity = new StringEntity(encodeData, "UTF-8");
			post.setEntity(entity);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30 * 1000).setConnectTimeout(30 * 1000).build();// 设置请求和传输超时时间
			post.setConfig(requestConfig);
			CloseableHttpResponse response = httpclient.execute(post);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
			} else {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
