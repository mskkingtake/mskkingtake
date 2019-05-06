package httpInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpTest {

	public static void main(String[] args) {
		String httpurl = "https://189.125.124.1/GET/custAnInst?accNum=18902512345&prodType=1";
		doGet(httpurl);
	}
	
	public static String doGet(String httpurl) {		
		HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String returnValue = null;// 返回结果字符串
        
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(10000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            
            // 设置头信息
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-APP-ID", "FFnN2hso42Wego3pWq4X5qlu");
            connection.setRequestProperty("X-APP-KEY", "UtOCzqb67d3sN12Kts4URwy8");
            connection.setRequestProperty("X-CTG-Request-ID", "92598bee-7d30-4086-afc9-a7be6bd2cda0");
            connection.setRequestProperty("X-CTG-Region-ID", "8320100");
            
            // 发送请求
            connection.connect();
            
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                returnValue = sbf.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }
        
		System.out.println("asd");
		
		return returnValue;
	}
	
	/*
	public void get2() {
		HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://bbs.e763.com/");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
        HttpResponse response = httpClient.execute(httpGet);
        String contents = EntityUtils.toString(response.getEntity(),"gbk");//utf-8
        Document document = Jsoup.parse(contents);
        Elements elements = document.select("div#hza11 div.boxtxthot a");
        
        for (Element element : elements) {
            System.out.println(element.text()+ " : " + element.attr("href"));
        }
	}
	*/
}
