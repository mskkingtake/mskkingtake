package rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestUtil {

	public static void main(String[] args) {
		String query = "";
		query = "{\"Envelope\":{\"Body\":{\"mdn\":\"17790941813\",\"province\":\"06\",\"type\":\"cscf\"},\"Header\":{\"Esb\":{\"Router\":{\"AuthCode\":\"\",\"AuthType\":\"\",\"CarryType\":\"0\",\"EsbId\":\"\",\"MsgId\":\"21.1185.01201809131554430000000001\",\"MsgType\":\"request\",\"Sender\":\"21.1185.01\",\"ServCode\":\"10.1160.VOLTEYHQYXXCX_subscriptioninfo.SynReq\",\"ServTestFlag\":\"\",\"Time\":\"2018-09-13 15:54:43,317\",\"TransId\":\"21.1185.01201809131554439000000001\",\"Version\":\"V0.1\"}}}}}";
//		query = "{\"Envelope\":{\"Body\":{\"mdn\":\"17790941813\",\"province\":\"06\",\"type\":\"all\"},\"Header\":{\"Business\":{},\"Esb\":{\"Router\":{\"AuthCode\":\"\",\"AuthType\":\"\",\"CarryType\":\"0\",\"EsbId\":\"\",\"MsgId\":\"21.1185.01201809121636190000000001\",\"MsgType\":\"\",\"Sender\":\"21.0001\",\"ServCode\":\"10.1160.VOLTEYHQYXXCX_subscriptioninfo.SynReq\",\"ServTestFlag\":\"\",\"Time\":\"2018-09-12 16:36:19,205\",\"TransId\":\"21.1185.01201809121636199000000001\",\"Version\":\"V0.1\"}}}}}";
		
		System.out.println(query);
		
		try {
//			postMethod("http://132.63.9.50:53188/rest_json", query);
			postMethod("http://136.127.47.121:53099/rest_json_lin", query);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param query
	 * @throws Exception
	 */
	public static void postMethod(String url, String query) throws Exception {
	    URL restURL = new URL(url);
	 
	    HttpURLConnection conn = (HttpURLConnection) restURL.openConnection();
	    conn.setRequestMethod("POST");
	    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
	    conn.setConnectTimeout(60000);
	    conn.setReadTimeout(60000);
	    conn.setDoOutput(true);
	 
	    PrintStream ps = new PrintStream(conn.getOutputStream());
	    ps.print(query);
	    ps.close();
	 
	    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	    String line;
	    while((line = br.readLine()) != null ){
	      System.out.println(line);
	    }
	 
	    br.close();
	  }
}
