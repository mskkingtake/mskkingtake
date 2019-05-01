package common;

/**
 * number 工具类
 * @author mskkingtake
 *
 */
public class NumberUtil {
	
	/** 
     * 数字验证 
     *  
     * @param val 
     * @return 提取的数字。 
     */  
    public static boolean isNum(String val) {  
        return val == null || "".equals(val) ? false:val.matches("^[0-9]*$");  
    }
}
