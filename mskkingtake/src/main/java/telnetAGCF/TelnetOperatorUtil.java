package telnetAGCF;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TelnetOperatorUtil {
	private TelnetUtil telnet = null;
	private int arisTag = 0;
	private Long devId;
	
	public TelnetUtil getTelnet() {
		return telnet;
	}

	public void setTelnet(TelnetUtil telnet) {
		this.telnet = telnet;
	}

	public int getArisTag() {
		return arisTag;
	}

	public void setArisTag(int arisTag) {
		this.arisTag = arisTag;
	}
	
	/*
	 * 初始化
	 */
	public TelnetOperatorUtil(TelnetUtil telnet) throws Exception {
		this.telnet = telnet;
		telnet.open();
	}
	
	/*
	 * 初始化
	 */
	public TelnetOperatorUtil(TelnetUtil telnet,String have) throws Exception {
		this.telnet = telnet;
		telnet.open(have);
	}

	/*
	 * 初始化
	 */
	public TelnetOperatorUtil(TelnetUtil telnet, int tag) throws Exception {
		super();
		this.telnet = telnet;
		telnet.open();
		this.arisTag = tag;
	}

	public Long getDevId() {
		return devId;
	}

	public void setDevId(Long devId) {
		this.devId = devId;
	}
	
	/**
	 * 执行命令
	 * @param command
	 * @param endFlag
	 * @return
	 * @throws Exception
	 */
	public String execute(String command, String endFlag) throws Exception {
		telnet.println(command);
		String retValue = telnet.readUtil(endFlag);
		return retValue;
	}

	/**
	 * 
	 * @param command
	 * @param endFlag
	 * @param moreFlag
	 * @return
	 * @throws Exception
	 */
	public String executeMore(String command, String endFlag, String moreFlag) throws Exception {
		telnet.println(command);
		telnet.readUtil(endFlag);
		StringBuffer sb = new StringBuffer();
		telnet.more(endFlag, sb, moreFlag);
		byte[] temp = sb.toString().getBytes("ISO-8859-1");
		String res = new String(temp, "GBK");
		String retValue = res.replace(moreFlag, "");
		return retValue;
	}

	/**
	 * 
	 * @param command
	 * @param endFlag
	 * @param moreFlag
	 * @return
	 * @throws Exception
	 */
	public String executeMore(String command, String endFlag, String moreFlag, String splitFlag) throws Exception {
		telnet.println(command);
		StringBuffer sb = new StringBuffer();
		telnet.more(endFlag, sb, moreFlag, splitFlag);
		String retValue = sb.toString();
		retValue = retValue.replaceAll(splitFlag + "\\s+", "");
		retValue = retValue.replaceAll(splitFlag, "");
		return retValue;
	}

	/**
	 * 
	 * @param command
	 * @param endFlag
	 * @param moreFlag
	 * @return
	 * @throws Exception
	 */
	public String executeMoreFilter(String command, String endFlag, String moreFlag, Set nonEndSet) throws Exception {
		telnet.println(command);
		StringBuffer sb = new StringBuffer();
		telnet.moreFilter(endFlag, sb, moreFlag, nonEndSet);
		String retValue = sb.toString();
		return retValue;
	}

	/**
	 * 
	 * @param command
	 * @param endFlag
	 * @param moreFlag
	 * @return
	 * @throws Exception
	 */
	public String moreExecute(String command, String endFlag, String moreFlag) throws Exception {
		telnet.println(command);
		StringBuffer sb = new StringBuffer();
		telnet.moreExecute(endFlag, sb, moreFlag);
		String retValue = sb.toString();
		return retValue;
	}

	/**
	 * 
	 * @param command
	 * @param endFlag
	 * @param moreFlag
	 * @return
	 * @throws Exception
	 */
	public String moreFilterExecute(String command, Set endFlag, String moreFlag, Set nonEndSet) throws Exception {
		telnet.println(command);
		StringBuffer sb = new StringBuffer();
		telnet.moreFilterExecute(endFlag, sb, moreFlag, nonEndSet);
		String retValue = sb.toString();
		return retValue;
	}

	public String execute(String command, Set endFlags) throws Exception {
		telnet.println(command);
		String retValue = telnet.readUtil(endFlags);
		return retValue;
	}

	public String execute(String command, int timeout) throws Exception {
		telnet.println(command);
		String retValue = telnet.readUtilTimeOut(timeout);
		return retValue;
	}

	public String executePwd(String command, Set endFlags) throws Exception {
		telnet.println(command);
		String retValue = telnet.readUtil(endFlags);
		return retValue;
	}

	public String read(String endFlag) throws Exception {
		return telnet.readUtil(endFlag);
	}

	public String read(String endFlag, int timeout) throws Exception {
		String ret = telnet.readUtil(endFlag, timeout);
		return ret;
	}

	public String read(Set endFlags, int timeout) throws Exception {
		String ret = telnet.readUtil(endFlags, timeout);
		return ret;
	}

	public String readUtilTimeOut(int timeOut) throws Exception {
		return telnet.readUtilTimeOut(timeOut);
	}

	public String readUtilTimeOut(String command, int timeOut) throws Exception {
		telnet.println(command);
		return telnet.readUtilTimeOut(timeOut);
	}

	public String readBlock(String command, int bocks) throws Exception {
		telnet.println(command);
		return telnet.readBlocks(bocks);
	}

	public String readBlock(String command, int bocks, Set endFlags) throws Exception {
		telnet.println(command);
		return telnet.readBlocks(bocks, endFlags);
	}

	public static String[] getLine(String str) {
		try {
			BufferedReader br = new BufferedReader(new StringReader(str + "\n"));
			List result = new ArrayList();
			String aLine = br.readLine();
			while (aLine != null) {
				result.add(aLine.trim());
				aLine = br.readLine();
			}

			return (String[]) result.toArray(new String[0]);
		} catch (IOException e) {
			return null;
		}
	}

	public void close() {
		if (telnet != null) {
			telnet.close();
		}
	}

	public String executeDynamicFlagAndPage(String command, Set moreFlag, int SoTimeout) throws Exception {
		telnet.println(" ");
		return telnet.exeComFlagMore(command, moreFlag, SoTimeout);
	}
}
