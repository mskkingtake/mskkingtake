package httpInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpDoGet {
	
	/**
	 * HTTP GET 请求
	 * 使用HttpClient发送get请求
	 * 
	 * @param httpurl
	 * @return
	 */
	public static String doGetByHttpClien(String httpurl) {		
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
		
		return returnValue;
	}
	
	/**
	 * HTTP GET 请求
	 * 使用URLConnection发送get请求
	 * 
	 * @param urlParam
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String sendGetByURLConnection(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        
        BufferedReader br = null;
        try {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            URLConnection urlConnection = url.openConnection();
            
            // 设置连接主机服务器的超时时间：15000毫秒
            urlConnection.setConnectTimeout(10000);
            // 设置读取远程返回的数据时间：60000毫秒
            urlConnection.setReadTimeout(60000);
            
            // 设置请求属性
            urlConnection.setRequestProperty("accept", "*/*");
            urlConnection.setRequestProperty("connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            // 建立连接
            urlConnection.connect();
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }
	
	/**
	 * HTTP GET 请求
	 * 使用HttpURLConnection发送get请求
	 * 
	 * @param urlParam
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String sendGetByHttpURLConnection(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        HttpURLConnection httpURLConnection = null;
        BufferedReader br = null;
        try {
            URL url = null;
            if (sbParams != null && sbParams.length() > 0) {
                url = new URL(urlParam + "?" + sbParams.substring(0, sbParams.length() - 1));
            } else {
                url = new URL(urlParam);
            }
            
            
            httpURLConnection = (HttpURLConnection) url.openConnection();
            
            // 设置连接主机服务器的超时时间：15000毫秒
            httpURLConnection.setConnectTimeout(10000);
            // 设置读取远程返回的数据时间：60000毫秒
            httpURLConnection.setReadTimeout(60000);
            
            
            // 设置请求属性
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.setRequestProperty("connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            httpURLConnection.connect();
            
            resultBuffer = new StringBuffer();
            br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), charset));
            String temp;
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                } finally {
                    if (httpURLConnection != null) {
                    	httpURLConnection.disconnect();
                    	httpURLConnection = null;
                    }
                }
            }
        }
        return resultBuffer.toString();
    }
	
	/**
	 * HTTP GET 请求
	 * 使用socket发送get请求
	 * 
	 * @param urlParam
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String sendSocketGet(String urlParam, Map<String, Object> params, String charset) {
        String result = "";
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                sbParams.append(entry.getValue());
                sbParams.append("&");
            }
        }
        Socket socket = null;
        OutputStreamWriter osw = null;
        InputStream is = null;
        try {
            URL url = new URL(urlParam);
            String host = url.getHost();
            int port = url.getPort();
            if (-1 == port) {
                port = 80;
            }
            String path = url.getPath();
            socket = new Socket(host, port);
            StringBuffer sb = new StringBuffer();
            sb.append("GET " + path + " HTTP/1.1\r\n");
            sb.append("Host: " + host + "\r\n");
            sb.append("Connection: Keep-Alive\r\n");
            sb.append("Content-Type: application/x-www-form-urlencoded; charset=utf-8 \r\n");
            sb.append("Content-Length: ").append(sb.toString().getBytes().length).append("\r\n");
            // 这里一个回车换行，表示消息头写完，不然服务器会继续等待
            sb.append("\r\n");
            if (sbParams != null && sbParams.length() > 0) {
                sb.append(sbParams.substring(0, sbParams.length() - 1));
            }
            osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(sb.toString());
            osw.flush();
            is = socket.getInputStream();
            String line = null;
            // 服务器响应体数据长度
            int contentLength = 0;
            // 读取http响应头部信息
            do {
                line = readLine(is, 0, charset);
                if (line.startsWith("Content-Length")) {
                    // 拿到响应体内容长度
                    contentLength = Integer.parseInt(line.split(":")[1].trim());
                }
                // 如果遇到了一个单独的回车换行，则表示请求头结束
            } while (!line.equals("\r\n"));
            // 读取出响应体数据（就是你要的数据）
            result = readLine(is, contentLength, charset);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    is = null;
                    throw new RuntimeException(e);
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            socket = null;
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        return result;
    }
	
	/**
	 * 读取一行数据，contentLe内容长度为0时，读取响应头信息，不为0时读正文
	 * 
	 * @param is
	 * @param contentLength
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	private static String readLine(InputStream is, int contentLength, String charset) throws IOException {
        List<Byte> lineByte = new ArrayList<Byte>();
        byte tempByte;
        int cumsum = 0;
        if (contentLength != 0) {
            do {
                tempByte = (byte) is.read();
                lineByte.add(Byte.valueOf(tempByte));
                cumsum++;
            } while (cumsum < contentLength);// cumsum等于contentLength表示已读完
        } else {
            do {
                tempByte = (byte) is.read();
                lineByte.add(Byte.valueOf(tempByte));
            } while (tempByte != 10);// 换行符的ascii码值为10
        }

        byte[] resutlBytes = new byte[lineByte.size()];
        for (int i = 0; i < lineByte.size(); i++) {
            resutlBytes[i] = (lineByte.get(i)).byteValue();
        }
        return new String(resutlBytes, charset);
    }
	
	
	/**
	 * HTTP GET 请求
	 * 使用HttpClient发送get请求
	 * 
	 * @param urlParam
	 * @param params
	 * @param charset
	 * @return
	 */
	public static String httpClientGet(String urlParam, Map<String, Object> params, String charset) {
        StringBuffer resultBuffer = null;
        HttpClient client = new DefaultHttpClient();
        BufferedReader br = null;
        
        // 构建请求参数
        StringBuffer sbParams = new StringBuffer();
        if (params != null && params.size() > 0) {
            for (Entry<String, Object> entry : params.entrySet()) {
                sbParams.append(entry.getKey());
                sbParams.append("=");
                try {
                    sbParams.append(URLEncoder.encode(String.valueOf(entry.getValue()), charset));
                } catch (UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }
                sbParams.append("&");
            }
        }
        if (sbParams != null && sbParams.length() > 0) {
            urlParam = urlParam + "?" + sbParams.substring(0, sbParams.length() - 1);
        }
        
        // 设置头信息
        HttpGet httpGet = new HttpGet(urlParam);
        httpGet.setHeader("Content-Type", "application/json");
        httpGet.setHeader("X-APP-ID", "FFnN2hso42Wego3pWq4X5qlu");
        httpGet.setHeader("X-APP-KEY", "UtOCzqb67d3sN12Kts4URwy8");
        httpGet.setHeader("X-CTG-Request-ID", "92598bee-7d30-4086-afc9-a7be6bd2cda0");
        httpGet.setHeader("X-CTG-Region-ID", "8320100");
        
        try {
            HttpResponse response = client.execute(httpGet);
            // 读取服务器响应数据
            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String temp;
            resultBuffer = new StringBuffer();
            while ((temp = br.readLine()) != null) {
                resultBuffer.append(temp);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return resultBuffer.toString();
    }
	
	/**
	 * 
	 * @throws IOException
	 */
	public static void testHttpClientA() throws IOException {
        //使用默认配置的httpclient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //即将访问的url
        String url = "http://www.baidu.com";
        //get形式的访问
        HttpGet httpGet = new HttpGet(url);

        //执行请求
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            //打印请求的状态码  请求成功为200
            System.out.println(response.getStatusLine().getStatusCode());
            //打印请求的实体内容 返回json格式
            HttpEntity entity = response.getEntity();
            //获取所有头信息
            Header[] allHeaders = response.getAllHeaders();
            for (Header allHeader : allHeaders) {
                System.out.println(allHeader.getName());
                System.out.println(allHeader.getValue());
                System.out.println(allHeader.toString());
            }

            //方法二 官方推荐 使用流的形式处理请求结果
	        if (entity != null) {
	            InputStream content = entity.getContent();
	            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(content));
	            String line = "";
	            while ((line = bufferedReader.readLine()) != null){
	                System.out.println(line);
	            }
	            bufferedReader.close();
	        }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            response.close();
        }
	}

	
	/**
	 * 主函数
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		String httpurl = "https://189.125.124.1/GET/custAnInst?accNum=18902512345&prodType=1";
		
		// 使用HttpClient发送get请求
		// doGetByHttpClien(httpurl);
		
		// 使用HttpClient发送get请求
		// sendGetByURLConnection(httpurl, null, "UTF8");
		
		// 使用HttpURLConnection发送get请求
		// sendGetByHttpURLConnection(httpurl, null, "UTF8");
		
		// 使用socket发送get请求
		// sendSocketGet(httpurl, null, "UTF8");
		
		testHttpClientA();
	}
}
