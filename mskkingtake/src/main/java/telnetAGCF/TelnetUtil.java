package telnetAGCF;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.commons.net.telnet.EchoOptionHandler;
import org.apache.commons.net.telnet.SuppressGAOptionHandler;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TerminalTypeOptionHandler;

import common.StringUtil;

/**
 * telnet 工具类
 * @author 马士凯
 *
 */
public class TelnetUtil {
	
	private TelnetClient telnet = new TelnetClient();

	private String ip;
	private String encoding = null;
	private int port;
	private String terminalType = null;
	private String dynamicFlag = "";   
	private BufferedReader reader = null;

	private boolean isActive = false;

	public TelnetUtil(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	public void setTerminalType(String type) {
		this.terminalType = type;
	}
	
	public TelnetUtil(String ip) {
		this(ip, 23);
	}
	
	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * 进行登录
	 * @throws Exception
	 */
	public void open() throws Exception {
		try {
			System.out.println("开始进行连接(" + ip + ":" + port + ")...");
			
			if (terminalType != null) {
				TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler(terminalType, false, false, true, false);
				telnet.addOptionHandler(ttopt);
			}
			
			telnet.connect(ip, port);
			telnet.setSoTimeout(1000 * 10);
			isActive = true;
			
			System.out.println("连接成功(" + ip + ":" + port + ")...");
		} catch (Exception e) {
			System.out.println("连接失败(" + ip + ":" + port + ")");
			throw e;
		}
	}
	
	/**
	 * 进行登录
	 * @throws Exception
	 */
	public void open(String param) throws Exception {
		try {
			System.out.println("开始进行连接(" + ip + ":" + port + ")...");
			
			// 连接初始化
			if (terminalType != null) {
				TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler(terminalType, false, false, true, false);
				telnet.addOptionHandler(ttopt);
			}
			
			// 开始进行连接
			EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
			SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
			telnet.addOptionHandler(echoopt);
			telnet.addOptionHandler(gaopt);
			telnet.connect(ip, port);
			
			// 设置超时时间
			setTimeout(1000 * 30);
			
			// 设定激活状态
			isActive = true;
			
			System.out.println("连接成功(" + ip + ":" + port + ")...");
		} catch (Exception e) {
			System.out.println("连接失败(" + ip + ":" + port + ")");
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置超时时间
	 * @param timeout
	 * @throws Exception
	 */
	public void setTimeout(int timeout) throws Exception {
		try {
			System.out.println("设置超时时间：" + timeout/1000 + "秒");
			telnet.setSoTimeout(timeout);
		} catch (SocketException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 是否激活
	 * @return
	 */
	public boolean isActive() {
		return isActive;
	}
	
	/**
	 * 关闭连接
	 */
	public void close() {
		try {
			telnet.disconnect();
			isActive = false;
			System.out.println("关闭连接成功...");
		} catch (IOException e) {
			System.out.println("关闭连接失败...");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param flags
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public String readUtil(Set flags) throws Exception {
		try {
			if (flags == null) {
				throw new IllegalArgumentException("ReadUtil's parameter is null");
			}
			if (telnet.isConnected()) {
				telnet.setSoTimeout(1000 * 10);
				Set lastChars = new HashSet();

				try {
					for (Iterator iter = flags.iterator(); iter.hasNext();) {
						String flag = (String) iter.next();
						lastChars.add(new Character(flag.charAt(flag.length() - 1)));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
				char ch = (char) bis.read();
				System.out.println("--- start char : " + ch);
				String encoding = reader.getEncoding();
				while (true) {
					boolean isEnd = false;
					sb.append(ch);
					if (lastChars.contains(new Character(ch))) {
						for (Iterator iter = flags.iterator(); iter.hasNext();) {
							String flag = (String) iter.next();
							if (sb.toString().endsWith(flag)) {
								isEnd = true;
								break;
							}
						}
					}
					if (isEnd) {
						break;
					}
					int ret = bis.read();
					ch = (char) ret;
					System.out.println("--- read char : " + ch);
					if (ret == -1) {
						break;
					}
				}
				if (sb.length() > 0) {
					byte[] valueArr = new byte[sb.length()];
					for (int i = 0; i < sb.length(); i++) {
						valueArr[i] = (byte) sb.charAt(i);
					}
					String ss = new String(valueArr, encoding);
					System.out.println(ss);
					return ss;
				}
			}
			return null;
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * @param flags
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	public String readByKeyWord(Set flags, String keyWord) throws Exception {
		try {
			if (flags == null) {
				throw new IllegalArgumentException("ReadUtil's parameter is null");
			}
			if (StringUtil.isEmpty(keyWord)) {
				throw new IllegalArgumentException("ReadUtil's parameter is null");
			}
			if (telnet.isConnected()) {
				telnet.setSoTimeout(1000 * 60);
				// char lastChar = 0;
				Set lastChars = new HashSet();

				try {
					for (Iterator iter = flags.iterator(); iter.hasNext();) {
						String flag = (String) iter.next();
						lastChars.add(new Character(flag.charAt(flag.length() - 1)));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
				char ch = (char) bis.read();
				String encoding = reader.getEncoding();
				boolean isKeyWord = false;
				System.out.println(" --- start char : " + ch);
				while (true) {
					boolean isEnd = false;
					sb.append(ch);
					if (!isKeyWord && sb.toString().indexOf(keyWord) != -1) {
						isKeyWord = true;
					}
					if (isKeyWord) {
						if (lastChars.contains(new Character(ch))) {
							for (Iterator iter = flags.iterator(); iter.hasNext();) {
								String flag = (String) iter.next();
								if (sb.toString().endsWith(flag)) {
									isEnd = true;
									break;
								}
							}
						}
					}
					if (isEnd) {
						break;
					}
					int ret = bis.read();
					ch = (char) ret;
					System.out.println("--- read char : " + ch);
					if (ret == -1) {
						break;
					}
					// System.out.println(ch + "(" + (byte)ch + ")");
				}
				if (sb.length() > 0) {
					byte[] valueArr = new byte[sb.length()];
					for (int i = 0; i < sb.length(); i++) {
						valueArr[i] = (byte) sb.charAt(i);
					}
					String ss = new String(valueArr, encoding);
					System.out.println(ss);
					return ss;
				}
			}
			return null;
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}

	}
	
	/**
	 * 
	 * @param flags
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public String readUtil(Set flags, int timeout) throws Exception {
		try {
			if (flags == null) {
				throw new IllegalArgumentException("ReadUtil's parameter is null");
			}
			if (telnet.isConnected()) {
				telnet.setSoTimeout(timeout);
				// char lastChar = 0;
				Set lastChars = new HashSet();

				try {
					for (Iterator iter = flags.iterator(); iter.hasNext();) {
						String flag = (String) iter.next();
						lastChars.add(new Character(flag.charAt(flag.length() - 1)));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
				char ch = (char) bis.read();
				String encoding = reader.getEncoding();
				while (true) {
					boolean isEnd = false;
					sb.append(ch);
					if (lastChars.contains(new Character(ch))) {
						for (Iterator iter = flags.iterator(); iter.hasNext();) {
							String flag = (String) iter.next();
							if (sb.toString().endsWith(flag)) {
								isEnd = true;
								break;
							}
						}
					}
					if (isEnd) {
						break;
					}
					int ret = bis.read();
					ch = (char) ret;
					if (ret == -1) {
						break;
					}
				}
				if (sb.length() > 0) {
					byte[] valueArr = new byte[sb.length()];
					for (int i = 0; i < sb.length(); i++) {
						valueArr[i] = (byte) sb.charAt(i);
					}
					String ss = new String(valueArr, encoding);
					System.out.println(ss);
					return ss;
				}
			}
			return null;
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}

	}

	/**
	 * 
	 * @param flags
	 * @param overLeap
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public String readUtil(Set flags, String overLeap, int timeout) throws Exception {
		try {
			if (flags == null) {
				throw new IllegalArgumentException("ReadUtil's parameter is null");
			}
			if (telnet.isConnected()) {
				telnet.setSoTimeout(timeout);
				// char lastChar = 0;
				Set lastChars = new HashSet();

				try {
					for (Iterator iter = flags.iterator(); iter.hasNext();) {
						String flag = (String) iter.next();
						lastChars.add(new Character(flag.charAt(flag.length() - 1)));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
				char ch = (char) bis.read();
				String encoding = reader.getEncoding();
				boolean isOverLeap = false;
				while (true) {
					boolean isEnd = false;
					sb.append(ch);
					if (sb.toString().endsWith(overLeap)) {
						isOverLeap = true;
						continue;
					}
					if (lastChars.contains(new Character(ch)) && isOverLeap) {
						for (Iterator iter = flags.iterator(); iter.hasNext();) {
							String flag = (String) iter.next();
							if (sb.toString().endsWith(flag)) {
								isEnd = true;
								break;
							}

						}
					}
					if (isEnd)
						break;
					int ret = bis.read();
					ch = (char) ret;
					if (ret == -1) {
						break;
					}
				}
				if (sb.length() > 0) {
					byte[] valueArr = new byte[sb.length()];
					for (int i = 0; i < sb.length(); i++) {
						valueArr[i] = (byte) sb.charAt(i);
					}
					String ss = new String(valueArr, encoding);
					System.out.println(ss);
					return ss;
				}
			}
			return null;
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 读取数据
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public String readUtil(String flag) throws Exception {
		try {
			if (telnet.isConnected()) {
				telnet.setSoTimeout(1000 * 10);
				if (flag == null) {
					throw new IllegalArgumentException("ReadUtil's parameter is null");
				}
				char lastChar = 0;
				try {
					lastChar = flag.charAt(flag.length() - 1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				String encoding = "GBK";
				char ch = (char) bis.read();
				while (true) {
					sb.append(ch);
					if (ch == lastChar) {
						byte[] temp = sb.toString().getBytes("ISO-8859-1");
						String res = new String(temp, "GBK");
						if (res.endsWith(flag)) {
							break;
						}
					}
					int ret = bis.read();
					ch = (char) ret;
					if (ret == -1) {
						break;
					}
				}

				if (sb.length() > 0) {
					byte[] valueArr = new byte[sb.length()];
					for (int i = 0; i < sb.length(); i++) {
						valueArr[i] = (byte) sb.charAt(i);
					}
					String ss = new String(valueArr, encoding);
					return ss;
				}
			} else {
				return null;
			}
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
		
		return null;
	}
	
	/**
	 * 读取数据
	 * @param flag
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public String readUtil(String flag, int timeout) throws Exception {
		int oldTimeout = -1;
		try {
			if (telnet.isConnected()) {
				oldTimeout = telnet.getSoTimeout();
				telnet.setSoTimeout(timeout);
				if (flag == null) {
					throw new IllegalArgumentException("ReadUtil's parameter is null");
				}
				char lastChar = 0;
				try {
					lastChar = flag.charAt(flag.length() - 1);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuffer sb = new StringBuffer();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
				String encoding = reader.getEncoding();
				char ch = (char) bis.read();
				while (true) {
					sb.append(ch);
					if (ch == lastChar) {
						if (sb.toString().endsWith(flag)) {
							break;
						}
					}
					int ret = bis.read();
					ch = (char) ret;
					if (ret == -1) {
						break;
					}
				}

				if (sb.length() > 0) {
					byte[] valueArr = new byte[sb.length()];
					for (int i = 0; i < sb.length(); i++) {
						valueArr[i] = (byte) sb.charAt(i);
					}
					String ss = new String(valueArr, encoding);
					System.out.println(ss);
					return ss;
				}
			} else {
				return null;
			}
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (oldTimeout != -1) {
				try {
					telnet.setSoTimeout(oldTimeout);
				} catch (SocketException e) {
					throw new Exception(e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 读取数据
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public String readUtilTimeOut(int timeout) throws Exception {
		StringBuffer sb = new StringBuffer();
		String ss = null;
		int oldTimeout = -1;
		try {
			if (telnet.isConnected()) {
				oldTimeout = telnet.getSoTimeout();
				telnet.setSoTimeout(timeout);
				InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
				encoding = reader.getEncoding();
				BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
				char ch = (char) bis.read();
				while (ch != -1) {
					if (sb.length() > 4 * 1024 * 1024) {
						break;
					}
					sb.append(ch);
					ch = (char) bis.read();
				}
			} else {
				return null;
			}
		} catch (SocketTimeoutException ex) {
		} catch (IOException e) {
			throw new Exception(e);
		} finally {
			if (oldTimeout != -1) {
				try {
					telnet.setSoTimeout(oldTimeout);
				} catch (SocketException e) {
					throw new Exception(e);
				}
			}
		}
		try {
			if (sb.length() > 0) {
				byte[] valueArr = new byte[sb.length()];
				for (int i = 0; i < sb.length(); i++) {
					valueArr[i] = (byte) sb.charAt(i);
				}
				ss = new String(valueArr, encoding);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return ss;
	}
	
	/**
	 * 读取数据
	 * @return
	 * @throws Exception
	 */
	public String readLine() throws Exception {
		try {
			if (reader == null) {
				reader = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
			}
			return reader.readLine();
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}

	/**
	 * 读取数据
	 * @param flags
	 * @return
	 * @throws Exception
	 */
	public String readLines(Set flags) throws Exception {
		StringBuffer buf = new StringBuffer();
		try {
			if (reader == null) {
				reader = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
			}
			while (true) {
				boolean isEnd = false;
				String line = reader.readLine();
				if (line != null && line.trim().length() != 0) {
					line = line.trim();
					// System.out.println(line) ;
					buf.append(line);
					for (Iterator iter = flags.iterator(); iter.hasNext();) {
						String flag = (String) iter.next();
						int index = line.lastIndexOf(flag);
						if (index != -1) {
							int location = line.length() - flag.length();
							if (index == location) {
								isEnd = true;
							}
						}
					}
					if (isEnd) {
						break;
					}
				}
			}
			return buf.toString();
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 读取数据
	 * @param blocks
	 * @return
	 * @throws Exception
	 * @throws SocketException
	 */
	public String readBlocks(int blocks) throws Exception, SocketException {
		if (telnet.isConnected()) {
			telnet.setSoTimeout(10 * 1000);
		}

		if (blocks <= 0) {
			return "";
		}
		char[] blocksSize = new char[blocks * 1024];
		try {
			if (reader == null) {
				reader = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
			}
			int size = reader.read(blocksSize);
			if (size < 10) {
				reader.read(blocksSize, size, blocks * 1024 - size);
			}
			return new String(blocksSize).trim();
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @param sentence
	 * @return
	 */
	public List parseLine(String sentence) {
		StringTokenizer st = new StringTokenizer(sentence, "\n");
		List result = new ArrayList();
		while (st.hasMoreTokens()) {
			result.add(st.nextToken().trim());
		}
		return result;
	}
	
	/**
	 * 
	 * @param blocks
	 * @param flags
	 * @return
	 * @throws Exception
	 * @throws SocketException
	 */
	public String readBlocks(int blocks, Set flags) throws Exception, SocketException {

		if (telnet.isConnected())
			telnet.setSoTimeout(10 * 1000);

		if (blocks <= 0)
			return "";
		char[] blocksSize = new char[blocks * 1024];
		try {
			if (reader == null) {
				reader = new BufferedReader(new InputStreamReader(telnet.getInputStream()));
			}
			int location = 0;
			int blockSzie = blocks * 1024;
			while (true) {
				boolean end = false;
				int length = reader.read(blocksSize, location, blockSzie - location);
				String result = new String(blocksSize).trim();
				for (Iterator iter = flags.iterator(); iter.hasNext();) {
					String flag = (String) iter.next();
					int index = result.lastIndexOf(flag);
					if (index != -1) {
						if (index == (result.length() - flag.length())) {
							end = true;
							break;
						}
					}
				}
				if (end) {
					break;
				}
				if (length == -1) {
					break;
				}
				location = location + length;
			}
			return new String(blocksSize).trim();
		} catch (SocketTimeoutException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @param aStr
	 * @throws Exception
	 */
	public void write(String aStr) throws Exception {
		if (telnet.isConnected()) {
			if (aStr != null) {
				try {
					telnet.getOutputStream().write(aStr.getBytes());
					telnet.getOutputStream().flush();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 
	 * @param end_flag
	 * @param sb
	 * @throws Exception
	 */
	public void more(String end_flag, StringBuffer sb) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			char last = end_flag.charAt(end_flag.length() - 1);
			char ch;
			int readStream;
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				if (ch == '-') {
					if (sb.toString().endsWith("--More--")) {
						sb.delete(sb.lastIndexOf("--More--"), sb.length());
						write(" ");
						sb.deleteCharAt(sb.length() - 1);
					} else if (sb.toString().endsWith("---- More ----")) {
						sb.delete(sb.lastIndexOf("---- More ----"), sb.length());
						write(" ");
						sb.deleteCharAt(sb.length() - 1);
					}
				} else if (ch == last && sb.toString().endsWith(end_flag)) {
					return;
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}
	
	/**
	 * 
	 * @param sb
	 * @throws Exception
	 */
	public void more(StringBuffer sb) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			// char last = end_flag.charAt (end_flag.length () - 1);
			char ch;
			int readStream = 0;
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				if (ch == '-') {
					if (sb.toString().endsWith("--More--")) {
						sb.delete(sb.lastIndexOf("--More--"), sb.length());
						write(" ");
						sb.deleteCharAt(sb.length() - 1);
					} else if (sb.toString().endsWith("---- More ----")) {
						write(" ");
					}
				} else if ((sb.toString().endsWith("return"))) {
					return;
				} else if (sb.toString().endsWith("[42D")) {
					sb.append("\n");
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}
	
	/**
	 * 
	 * @param end_flag
	 * @param sb
	 * @param moreStr
	 * @throws Exception
	 */
	public void more(String end_flag, StringBuffer sb, String moreStr) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			char last = end_flag.charAt(end_flag.length() - 1);
			char ch;
			int readStream = 0;
			boolean isMore = moreStr.indexOf("-") == -1 ? false : true;
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				if (sb.length() > 4 * 1024 * 1024)
					return;
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				byte[] temp = sb.toString().getBytes("ISO-8859-1");
				String res = new String(temp, "GBK");
				if (isMore) {
					if (ch == '-') {
						if (sb.toString().endsWith(moreStr)) {
							sb.delete(sb.lastIndexOf(moreStr), sb.length());
							write(" ");
							sb.deleteCharAt(sb.length() - 1);
						}
					} else if (ch == last && sb.toString().endsWith(end_flag)) {
						return;
					}
				} else {
					if (res.endsWith(moreStr)) {
						 write(" ");
					} else if (ch == last && sb.toString().endsWith(end_flag)) {
						return;
					}
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}
	
	/**
	 * 
	 * @param end_flag
	 * @param sb
	 * @param moreStr
	 * @param splitFlag
	 * @throws Exception
	 */
	public void more(String end_flag, StringBuffer sb, String moreStr, String splitFlag) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			char last = end_flag.charAt(end_flag.length() - 1);
			char ch;
			int readStream = 0;
			boolean isMore = moreStr.indexOf("-") == -1 ? false : true;
			String endStr = "";
			boolean mored = false;
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				if (sb.length() > 4 * 1024 * 1024)
					return;
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				if (mored) {
					sb.deleteCharAt(sb.length() - 1);
					sb.append(splitFlag);
					mored = false;
				}
				if (isMore) {
					if (ch == '-') {
						if (sb.toString().endsWith(moreStr)) {
							sb.delete(sb.lastIndexOf(moreStr), sb.length());
							write(" ");
							mored = true;
						}
					} else if (ch == last && sb.toString().endsWith(end_flag)) {
						return;
					}
				} else {
					if (sb.length() >= moreStr.length())
						endStr = sb.substring(sb.length() - moreStr.length(),
								sb.length());
					if (moreStr.equals(endStr)) {
						sb.delete(sb.lastIndexOf(moreStr), sb.length());
						write(" ");
						mored = true;
					} else if (ch == last && sb.toString().endsWith(end_flag)) {
						return;
					}
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}
	
	/**
	 * 
	 * @param end_flag
	 * @param sb
	 * @param moreStr
	 * @param nonEndSet
	 * @throws Exception
	 */
	public void moreFilter(String end_flag, StringBuffer sb, String moreStr, Set nonEndSet) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			char last = end_flag.charAt(end_flag.length() - 1);
			char ch;
			int readStream = 0;
			boolean isMore = moreStr.indexOf("-") == -1 ? false : true;
			String endStr = "";
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				if (sb.length() > 4 * 1024 * 1024)
					return;
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				if (isMore) {
					if (ch == '-') {
						if (sb.toString().endsWith(moreStr)) {
							sb.delete(sb.lastIndexOf(moreStr), sb.length());
							write(" ");
							sb.deleteCharAt(sb.length() - 1);
						}
					} else if (ch == last && sb.toString().endsWith(end_flag)) {
						return;
					}
				} else {
					if (sb.length() >= moreStr.length())
						endStr = sb.substring(sb.length() - moreStr.length(),
								sb.length());
					if (moreStr.equals(endStr)) {
						sb.delete(sb.lastIndexOf(moreStr), sb.length());
						write(" ");
						sb.deleteCharAt(sb.length() - 1);
					} else if (ch == last && sb.toString().endsWith(end_flag)) {
						boolean flag = true;
						for (Iterator it = nonEndSet.iterator(); it.hasNext();) {
							String nonEndFlag = (String) it.next();
							if (sb.toString().endsWith(nonEndFlag)) {
								flag = false;
							}
						}
						if (flag) {
							return;
						}
					}
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}
	
	/**
	 * 
	 * @param end_flag
	 * @param sb
	 * @param moreStr
	 * @throws Exception
	 */
	public void moreExecute(String end_flag, StringBuffer sb, String moreStr) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			char last = end_flag.charAt(end_flag.length() - 1);
			char ch;
			int readStream = 0;
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				if (ch == '-') {
					if (sb.toString().endsWith(moreStr)) {
						while ((!sb.toString().endsWith("[42D")) && !(sb.toString().endsWith("[m"))) {
							ch = (char) bis.read();
							sb.append(ch);
						}
						int lastIndex = sb.lastIndexOf("\n");
						sb.delete(lastIndex, sb.length());
						write(" ");
						// sb.append("\n");
					}
				} else if (ch == last && sb.toString().endsWith(end_flag)) {
					return;
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public void moreFilterExecute(Set end_flag, StringBuffer sb, String moreStr, Set nonEndSet) throws Exception {
		try {
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			Set lastChars = new HashSet();
			try {
				for (Iterator iter = end_flag.iterator(); iter.hasNext();) {
					String flag = (String) iter.next();
					lastChars.add(new Character(flag.charAt(flag.length() - 1)));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			char ch;
			int readStream = 0;
			while ((readStream = bis.read()) != -1) {
				ch = (char) readStream;
				System.out.println("--- read char : " + ch);
				if (ch >= ' ' || ch == '\n')
					sb.append(ch);
				if (ch == '-' || ch == ')') {
					if (sb.toString().endsWith(moreStr)) {
						int lastIndex = sb.lastIndexOf("\n");
						sb.delete(lastIndex, sb.length());
						write(" ");
						// sb.append("\n");
					}
				} else {
					boolean isEnd = false;
					if (lastChars.contains(new Character(ch))) {
						for (Iterator iter = end_flag.iterator(); iter.hasNext();) {
							String flag = (String) iter.next();
							if (lastChars.contains(ch) && sb.toString().endsWith(flag)) {
								isEnd = true;
								break;
							}
						}
					}
					if (isEnd) {
						boolean flag = true;
						for (Iterator it = nonEndSet.iterator(); it.hasNext();) {
							String nonEndFlag = (String) it.next();
							if (sb.toString().endsWith(nonEndFlag)) {
								flag = false;
							}
						}
						if (flag) {
							return;
						}
					}
				}
			}
		} catch (IOException ex) {
			throw new Exception(ex);
		}
	}

	public void println(String aStr) throws Exception {
		write(aStr + "\n");
	}

	/*
	 * 
	 * exeCommandUtil(List commands({enable---#/display adsl port state all
	 * active---:}/{} ),List defaultflags( #/>),int 30000,int 100)
	 */
	@SuppressWarnings("unchecked")
	public String[] exeCommandUtil(List commands, List defaultflags, int timeout, int exetime) throws Exception {
		try {
			Set defaultlastChars = new HashSet();
			Map argmap = new HashMap();
			try {
				for (Iterator iter = defaultflags.iterator(); iter.hasNext();) {
					String flag = (String) iter.next();
					defaultlastChars.add(new Character(flag.charAt(flag.length() - 1)));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			String lineSeparator = (String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
			telnet.setSoTimeout(timeout);
			StringBuffer sb = new StringBuffer();
			String encoding0 = "";
			long time = 0;
			for (Iterator iter = commands.iterator(); iter.hasNext();) {
				String elementO = (String) iter.next();
				String[] el = elementO.split(";");
				for (int k = 0; k < el.length; k++) {
					time = System.currentTimeMillis();
					List currentflags = null;
					Set currentlastChars = null;
					currentflags = defaultflags;
					currentlastChars = defaultlastChars;
					if (!el[k].equals("loginxx")) {
						String command = el[k];
						if (el[k].split("---").length == 2) {
							command = el[k].split("---")[0];
							String flag = el[k].split("---")[1];
							if (!argmap.containsKey(flag)) {
								String[] flagtemp = flag.split("/");
								List flagstemp = new ArrayList();
								Set lastCharstemp = new HashSet();
								for (int w = 0; w < flagtemp.length; w++) {
									flagstemp.add(flagtemp[w]);
									lastCharstemp.add(new Character(flagtemp[w].charAt(flagtemp[w].length() - 1)));
								}
								argmap.put(flag, flagstemp);
								argmap.put(flag + "lastChars", lastCharstemp);
							}
							currentflags = (List) argmap.get(flag);
							currentlastChars = (Set) argmap.get(flag + "lastChars");
						}
						println(command);
					}
					BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());

					InputStreamReader reader = null;
					if (encoding == null) {
						reader = new InputStreamReader(telnet.getInputStream());
					} else {
						reader = new InputStreamReader(telnet.getInputStream(), encoding);
					}
					;
					char ch = (char) bis.read();
					encoding0 = reader.getEncoding();
					sb.append(" ");
					int i = 0;
					try {
						Thread.sleep(exetime);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					while (true) {
						boolean isEnd = false;
						sb.append(ch);
						if (ch == '-') {
							if (sb.toString().trim().endsWith("More ( Press 'Q' to break ) ----")
									|| sb.toString().endsWith("--More--")
									|| sb.toString().endsWith("---- More ----")
									|| sb.toString().endsWith("quit)--") // MA5605
																			// MA5615
																			// MA5105
									|| sb.toString().endsWith(
											"More (Press CTRL+C break) ---") // MA5300
							) {
								println(" ");
								System.out.println("   More? ");
							}
						}
						if (ch == 'e') {
							if (sb.toString().trim().endsWith("any other key: next page")) {
								println(" ");
								System.out.println("   More? ");
							}
						}
						if (currentlastChars.contains(new Character(ch))) {
							// System.out.println("end ch is " + ch);
							for (Iterator iter2 = currentflags.iterator(); iter2.hasNext();) {
								String flag = (String) iter2.next();
								if (sb.toString().endsWith(flag)) {
									isEnd = true;
									System.out.println(el[k] + " end");
									break;
								}
							}
						}

						if (isEnd) {
							System.out.println("  x " + sb.length() + "  " + i);
							break;
						}
						int ret = bis.read();
						ch = (char) ret;
						if (ret == -1 || i > 500000010 || System.currentTimeMillis() - time > 180000) {
							System.out.println("   ?  kill me ");
							break;
						}

						i++;
					}
					System.out.println("  c " + el[k] + "  length " + sb.length() + "  " + i);
				}
			}

			String ss = "";
			System.out.println("  x " + sb.toString());
			if (sb.length() > 0) {
				byte[] valueArr = new byte[sb.length()];
				for (int i = 0; i < sb.length(); i++) {
					valueArr[i] = (byte) sb.charAt(i);
				}
				ss = new String(valueArr, encoding0);
				String[] sss = ss.split(lineSeparator);
				return sss;
			}

			return new String[0];
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @param commands
	 * @param defaultflags
	 * @param moreFlag
	 * @param timeout
	 * @param exetime
	 * @return
	 * @throws Exception
	 */
	public String[] exeMoreCommandUtil(List commands, List defaultflags, List moreFlag, int timeout, int exetime) throws Exception {
		try {
			Set defaultlastChars = new HashSet();
			Map argmap = new HashMap();
			try {
				for (Iterator iter = defaultflags.iterator(); iter.hasNext();) {
					String flag = (String) iter.next();
					defaultlastChars.add(new Character(flag.charAt(flag.length() - 1)));
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			String lineSeparator = (String) java.security.AccessController.doPrivileged(new sun.security.action.GetPropertyAction("line.separator"));
			telnet.setSoTimeout(timeout);
			StringBuffer sb = new StringBuffer();
			String encoding0 = "";
			long time = 0;
			for (Iterator iter = commands.iterator(); iter.hasNext();) {
				String elementO = (String) iter.next();
				String[] el = elementO.split(";");
				for (int k = 0; k < el.length; k++) {
					time = System.currentTimeMillis();
					List currentflags = null;
					Set currentlastChars = null;
					currentflags = defaultflags;
					currentlastChars = defaultlastChars;
					if (!el[k].equals("loginxx")) {
						String command = el[k];
						if (el[k].split("---").length == 2) {
							command = el[k].split("---")[0];
						}
						println(command);
					}
					BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
					InputStreamReader reader = null;
					if (encoding == null) {
						reader = new InputStreamReader(telnet.getInputStream());
					} else {
						reader = new InputStreamReader(telnet.getInputStream(), encoding);
					}
					
					char ch = (char) bis.read();
					encoding0 = reader.getEncoding();
					sb.append(" ");
					int i = 0;
					try {
						Thread.sleep(exetime);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					while (true) {
						boolean isEnd = false;
						sb.append(ch);
						if (ch == '-') {
							for (Iterator iterator = moreFlag.iterator(); iterator.hasNext();) {
								String more = String.valueOf(iterator.next());
								if (sb.toString().trim().endsWith(more)) {
									println(" ");
									System.out.println("   More? ");
									break;
								}
							}
						}
						if (currentlastChars.contains(new Character(ch))) {
							for (Iterator iter2 = currentflags.iterator(); iter2.hasNext();) {
								String flag = (String) iter2.next();
								if (sb.toString().endsWith(flag)) {
									isEnd = true;
									break;
								}
							}
						}

						if (isEnd) {
							System.out.println("  x " + sb.length() + "  " + i);
							break;
						}
						int ret = bis.read();
						ch = (char) ret;
						if (ret == -1 || i > 500000010 || System.currentTimeMillis() - time > 180000) {
							System.out.println("   ?  kill me ");
							break;
						}
						i++;
					}
					System.out.println("  c " + el[k] + "  length " + sb.length() + "  " + i);
				}
			}

			String ss = "";
			System.out.println("  x " + sb.toString());
			if (sb.length() > 0) {
				byte[] valueArr = new byte[sb.length()];
				for (int i = 0; i < sb.length(); i++) {
					valueArr[i] = (byte) sb.charAt(i);
				}
				ss = new String(valueArr, encoding0);
				String[] sss = ss.split(lineSeparator);
				return sss;
			}

			return new String[0];
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 
	 * @param command
	 * @param moreFlag
	 * @param SoTimeout
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public String exeComFlagMore(String command, Set moreFlag, int SoTimeout) throws IOException, Exception {
		StringBuffer sbFlag = new StringBuffer();
		String Result = "";
		int oldTime = -1;
		oldTime = telnet.getSoTimeout();
		if (telnet.isConnected()) {
			InputStreamReader reader = new InputStreamReader(telnet.getInputStream());
			encoding = reader.getEncoding();
			BufferedInputStream bis = new BufferedInputStream(telnet.getInputStream());
			boolean blFlag = true;
			while (blFlag) {
				int i = -1;
				try {
					telnet.setSoTimeout(1000);
					i = bis.read();
				} catch (Exception e) {
					i = -1;
				}
				if (i == -1)
					break;
				if (i == 13 || i == 10) {
					sbFlag.delete(0, sbFlag.length());
					continue;
				}
				sbFlag.append((char) i);
			}
			telnet.setSoTimeout(8000);
			if (sbFlag.length() > 0) {
				byte[] b = new byte[sbFlag.length()];
				for (int i = 0; i < b.length; i++) {
					b[i] = (byte) sbFlag.charAt(i);
				}
				dynamicFlag = new String(b, encoding);
			}

			this.println(command);
			StringBuffer sbRet = new StringBuffer();

			while (blFlag) {
				int i = -1;
				try {
					telnet.setSoTimeout(SoTimeout);
					i = bis.read();
				} catch (Exception e) {
					i = -1;
				}
				if (i == -1) {
					String tmpFlag = "";
					if (sbFlag.length() > 0) {
						byte[] b = new byte[sbFlag.length()];
						for (int si = 0; si < b.length; si++) {
							b[si] = (byte) sbFlag.charAt(si);
						}
						tmpFlag = new String(b, encoding);
					}
					if (dynamicFlag.equals(tmpFlag)) {
						blFlag = false;
					}
					if (tmpFlag.toLowerCase().indexOf("more") != -1 && tmpFlag.toLowerCase().indexOf("--") != -1) {
						write(" ");
					} else if (tmpFlag.toLowerCase().indexOf("any") != -1 && tmpFlag.toLowerCase().indexOf("key") != -1) {
						write(" ");
					} else {
						write(" ");
					}
				} else {
					sbRet.append((char) i);
					if (i == 13 || i == 10) {
						sbFlag.delete(0, sbFlag.length());
						continue;
					}
					sbFlag.append((char) i);
				}
			}

			if (sbRet.length() > 0) {
				byte[] b = new byte[sbRet.length()];
				for (int i = 0; i < b.length; i++) {
					b[i] = (byte) sbRet.charAt(i);
				}
				Result = new String(b, encoding);
			}
			telnet.setSoTimeout(oldTime);
		}
		return Result;
	}

	public String getIp() {
		return ip;
	}
}
