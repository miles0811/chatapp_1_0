package com.wowcow.chat10.Utils;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.http.AndroidHttpClient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.util.InetAddressUtils;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class HttpUtil {
  // Timeout (ms) for establishing a connection.
  private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 20 * 1000;

  // Timeout (ms) for read operations on connections.
  private static final int DEFAULT_READ_TIMEOUT_MILLIS = 30 * 1000;

  // Timeout (ms) for obtaining a connection from the connection pool.
  private static final int DEFAULT_GET_CONNECTION_FROM_POOL_TIMEOUT_MILLIS = 20 * 1000;

  public static String PHPSESSID = null;

  @SuppressLint("SimpleDateFormat")
  private static DateFormat formatter = new SimpleDateFormat(
      "yyyy-MM-dd-HH-mm-ss");

  private static AndroidHttpClient createHttpClient() {
    AndroidHttpClient httpClient = AndroidHttpClient.newInstance(null);
    HttpParams params = httpClient.getParams();
    HttpConnectionParams.setStaleCheckingEnabled(params, false);
    HttpConnectionParams.setConnectionTimeout(params,
            DEFAULT_CONNECT_TIMEOUT_MILLIS);
    HttpConnectionParams.setSoTimeout(params, DEFAULT_READ_TIMEOUT_MILLIS);
    HttpConnectionParams.setSocketBufferSize(params, 8192);
    ConnManagerParams.setTimeout(params,
            DEFAULT_GET_CONNECTION_FROM_POOL_TIMEOUT_MILLIS);
    // Don't handle redirects automatically
    HttpClientParams.setRedirecting(params, false);

    // Don't handle authentication automatically
    HttpClientParams.setAuthenticating(params, false);

    return httpClient;
  }

  public static String execute(HttpUriRequest request) throws Exception {
    AndroidHttpClient httpClient = null;
    // DefaultHttpClient client = null;
    String result = null;
    try {

      httpClient = createHttpClient();
        // Set Cookie Store
        HttpContext localContext = new BasicHttpContext();
        CookieStore cookieStore = new BasicCookieStore();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
      HttpResponse httpResponse = httpClient.execute(request,localContext);
      // client = new DefaultHttpClient();
      // HttpResponse httpResponse = client.execute(request);
        List<Cookie> cookies = cookieStore.getCookies();
        for (int i = 0; i < cookies.size(); i++) {
            //这里是读取Cookie['PHPSESSID']的值存在静态变量中，保证每次都是同一个值
            if ("PHPSESSID".equals(cookies.get(i).getName())) {
                PHPSESSID = cookies.get(i).getValue();
                break;
            }

        }
      int responseCode = httpResponse.getStatusLine().getStatusCode();

      HttpEntity entity = httpResponse.getEntity();

      InputStream instream = entity.getContent();

      char[] buffer = new char[1024];
      int flag = -1;
      StringWriter sw = new StringWriter();
      InputStreamReader in = new InputStreamReader(instream, "UTF-8");

      while ((flag = in.read(buffer)) > 0) {
        sw.write(buffer, 0, flag);
      }


      if (responseCode != HttpStatus.SC_OK) {
        throw new RuntimeException("http responseCode: " + responseCode);// TODO
      }

      result = sw.toString();
    } catch (Exception e) {

      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      e.printStackTrace(pw);
    } finally {
      if (httpClient != null) {
        httpClient.close();
      }
    }
    return result;
  }

  public static String get(String url, Map<String, String> params)
      throws Exception {
    HttpGet httpGet = null;

    List<NameValuePair> getParams = new ArrayList<NameValuePair>();

    if (params != null) {
      if (params.containsKey("")) {
        httpGet = new HttpGet(url + "?" + params.get(""));
      } else {
        for (String key : params.keySet()) {
          getParams.add(new BasicNameValuePair(key, params.get(key)));
        }
        if(PHPSESSID != null){
            httpGet.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
        }
        httpGet = new HttpGet(url + "?"
            + URLEncodedUtils.format(getParams, "UTF-8"));
      }
    }
    return execute(httpGet);
  }

  public static String get(String url) throws Exception {
    HttpGet httpGet = null;
    httpGet = new HttpGet(url);
    return execute(httpGet);
  }

  public static String post(String url, Map<String, String> params)
      throws Exception {
    HttpPost httpPost = new HttpPost(url);

    List<NameValuePair> postParams = new ArrayList<NameValuePair>();
    if (params != null) {
      if (params.containsKey("")) {
        httpPost.setEntity(new StringEntity(params.get("")));
      } else {
        for (String key : params.keySet()) {
          postParams.add(new BasicNameValuePair(key, params.get(key)));
        }

        if(PHPSESSID != null){
            httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
        }
        httpPost.setEntity(new UrlEncodedFormEntity(postParams, HTTP.UTF_8));
      }
    }
    return execute(httpPost);
  }

  public static String post(String url, String params) throws Exception {
    HttpPost httpPost = new HttpPost(url);
    httpPost.setHeader("Accept", "application/json");
    httpPost.setHeader("Content-type", "application/json");
      if(PHPSESSID != null){
          httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
      }
    httpPost.setEntity(new ByteArrayEntity(params.getBytes("UTF-8")));
//      System.out.println("====>post:"+params);
    return execute(httpPost);
  }

    public static String post(String url, List<NameValuePair> nameValuePair) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        if(PHPSESSID != null){
            httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
        }
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
        }catch (UnsupportedEncodingException e){

        }
//      System.out.println("====>post:"+params);
        return execute(httpPost);
    }

    //upload img use this
  public static String multipart(String url, Map<String, String> params,
      Map<String, File> fileParams) throws Exception {
    // String mParams = "";

    HttpPost httpPost = new HttpPost(url);

    MultipartEntity reqEntity = new MultipartEntity(
        HttpMultipartMode.BROWSER_COMPATIBLE);
    Charset charset = Charset.forName("UTF-8");

    if (params != null) {
      // mParams = " params : " + params.toString();
      for (String key : params.keySet()) {
        reqEntity.addPart(key, new StringBody(params.get(key), charset));
      }
    }

    if (fileParams != null) {
      // mParams += " fileParams : " + fileParams.toString();
      for (String key : fileParams.keySet()) {
        reqEntity.addPart(key, new FileBody(fileParams.get(key)));
      }
    }

      if(PHPSESSID != null){
          httpPost.setHeader("Cookie", "PHPSESSID=" + PHPSESSID);
      }
    httpPost.setEntity(reqEntity);

    return execute(httpPost);
  }

  public static String getHTML(String url) throws Exception {
    HttpGet httpGet = null;
    httpGet = new HttpGet(url);
    return execute(httpGet);
  }

  public static String getIpAddress() {
    String ipv4;
    try {
      for (Enumeration<NetworkInterface> en = NetworkInterface
          .getNetworkInterfaces(); en.hasMoreElements();) {
        NetworkInterface intf = en.nextElement();
        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
            .hasMoreElements();) {
          InetAddress inetAddress = enumIpAddr.nextElement();
//          System.out.println("ip1--:" + inetAddress);
//          System.out.println("ip2--:" + inetAddress.getHostAddress());

          // for getting IPV4 format
          if (!inetAddress.isLoopbackAddress()
              && InetAddressUtils.isIPv4Address(ipv4 = inetAddress
                  .getHostAddress())) {

            String ip = inetAddress.getHostAddress().toString();
            // return inetAddress.getHostAddress().toString();
            return ipv4;
          }
        }
      }
    } catch (Exception ex) {
    }
    return null;
  }

  public static boolean connectionPresent(final ConnectivityManager cMgr) {
    if (cMgr != null) {
      NetworkInfo netInfo = cMgr.getActiveNetworkInfo();
      if ((netInfo != null) && (netInfo.getState() != null)) {
        return netInfo.getState().equals(State.CONNECTED);
      } else {
        return false;
      }
    }
    return false;
  }
}