package common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * String工具类
 * @author mskkingtake
 *
 */
public class StringUtil {
	
	/**
	 * 判断是否为NULL
	 * @param param
	 * @return
	 */
	public static boolean isNull(Object param) {
		return param == null?true:false;
	}
	
	/**
	 * 判断是否为空
	 * @param param
	 * @return
	 */
	public static boolean isEmpty(String param) {
		if(param == null || param.length() <= 0) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * NVL(param, fillParam)的功能为：如果param为NULL，则函数返回fillParam，否则返回param本身
	 * @param param
	 * @param fillParam
	 * @return
	 */
	public static String nvl(String param, String fillParam) {
		if(isEmpty(param)) {
			return fillParam;
		} else {
			return param;
		}
	}
	
	/**
	 * 如果param为NULL，则函数返回emptyValue，若param不为null，则返回otherValue。
	 * @param param
	 * @param otherValue
	 * @param emptyValue
	 * @return
	 */
	public static String nvl2(String param, String otherValue, String emptyValue) {
		if(isEmpty(param)) {
			return emptyValue;
		} else {
			return otherValue;
		}
	}
	
	/**
	 * 如果param等于cheakValue，返回returnValue，否则返回cheakValue
	 * @param param
	 * @param cheakValue
	 * @param returnValue
	 * @return
	 */
	public static String decode(String param, String cheakValue, String returnValue) {
		if(isEmpty(param) && isEmpty(cheakValue)) {
			return returnValue;
		}
		
		if(param.equals(cheakValue)) {
			return returnValue;
		} else {
			return param;
		}
	}
	
	/**
	 * 编码格式进行变换
	 * @param param
	 * @param sourceDecoder 原始编码格式
	 * @param targetDecoder 目标编码格式
	 * @return
	 */
	public static String encodeConvert(String param, String sourceDecoder, String targetDecoder) {
		if(isEmpty(param)) {
			return param;
		}
		
		try {
			return new String(param.getBytes(sourceDecoder), targetDecoder);
 		} catch(Exception e) {
 			e.printStackTrace();
			return param;
		}
	}
	
	/**
	 * String 转换为 Date
	 * @param inDate
	 * @return
	 */
	public static Date stringToDate(String inDate, String format) {
		try {
			return new SimpleDateFormat(format).parse(inDate);
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * Date 转换为 String
	 * @param inDate
	 * @param format
	 * @return
	 */
	public static String dateToString(Date inDate, String format) {
		try {
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(inDate);
	        return new SimpleDateFormat(format).format(inDate);
		} catch(Exception e) {
			return null;
		}
	}
	
	/**
	 * 不定长空格split
	 * @param inDate
	 * @param format
	 * @return
	 */
	public static String[] splitByblank(String inParam) {
		if(inParam == null) {
			return null;
		}
		
		return inParam.split("\\s+");
	}

	public static void main(String[] args) {
		System.out.println(encodeConvert("111", "ISO-8859", "UTF-8"));
	}

}
