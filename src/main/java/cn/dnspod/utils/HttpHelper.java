package cn.dnspod.utils;


import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author weixuan
 * @date 2022/5/26 10:51
 * @Description: Http请求工具类
 */
public class HttpHelper {

    /**
     *  Get 请求
     * @param url
     * @return
     */
    public static String httpGet(String url){
        return httpGet(url, getHeaders());
    }

    /**
     * Get 请求 自定义头
     * @param url
     * @param headers
     * @return
     */
    public static String httpGet(String url, Header[] headers){
        try{
            HttpClientContext context = HttpClientContext.create();
            // 创建httpClient实例对象 -- 添加Bastic Auth验证和绕过SSL
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createIgnoreSSLHttpClient()).build();
            HttpGet httpGet = new HttpGet(url);
            //添加请求头
            if (!Utils.isEmpty(headers)) {
                for (Header header : headers) {
                    httpGet.addHeader(header);
                }
            }
            //发起请求
            CloseableHttpResponse response = httpClient.execute(httpGet,context);
            if (response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Post 请求
     * @param url     地址
     * @param params  参数
     * @return
     */
    public static String httpPost(String url, Map params){
        return httpPost(url, getHeaders(), params);
    }
    /**
     * Post 请求
     * @param url     地址
     * @param params  参数
     * @return
     */
    public static String httpPost(String url, Header[] headers, Map params){
        // 返回的结果
        try{
            HttpClientContext context = HttpClientContext.create();
            // 创建httpClient实例对象 -- 添加Bastic Auth验证和绕过SSL
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(createIgnoreSSLHttpClient()).build();
            HttpPost httpPost = new HttpPost(url);
            //添加请求头
            if (!Utils.isEmpty(headers)) {
                for (Header header : headers) {
                    httpPost.addHeader(header);
                }
            }
            //添加参数
            if (!Utils.isEmpty(params)){
                ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                for (Object key : params.keySet()) {
                    nameValuePairs.add(new BasicNameValuePair(key.toString(),params.get(key).toString()));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));
            }
            //发起请求
            CloseableHttpResponse response = httpClient.execute(httpPost,context);
            //请求成功 则返回结果
            if (response.getStatusLine().getStatusCode() == 200){
                return EntityUtils.toString(response.getEntity(), "utf-8");
            }
        }catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static SSLConnectionSocketFactory createIgnoreSSLHttpClient(){
        // 生成SSL 连接
        SSLContext sslContext = null;
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
    }

    private static Header[] getHeaders() {
        return new HeaderBuilder.Builder()
                .add("Accept", "*/*")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36").build();
    }


    /**
     * 获取公网ip地址
     * @return
     */
    public static String getPublicNetworkAddress(){
        String publicIp = HttpHelper.httpGet("http://members.3322.org/dyndns/getip");
        // 判断是否获取到公网ip
        if (publicIp != null && !"".equals(publicIp)) {
            // 去掉所有的空格、换行符等
            Pattern compile = Pattern.compile("\\s*|\t|\r|\n");
            return compile.matcher(publicIp).replaceAll("");
        }
        return null;
    }
}
