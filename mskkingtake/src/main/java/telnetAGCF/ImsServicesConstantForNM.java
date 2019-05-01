package telnetAGCF;

/**
 * 
 * @author bingxin.xie
 * @description 内蒙地区与编码常量类
 * @date 2017-08-21
 * 
 */

public class ImsServicesConstantForNM {
	
	public static int PORT = 31114;
	
	public static String PROMPT = "---    END";
	
	public static String USER = "MMLUser";
	
	public static String PASSWORD = "M2000nbi";
	
	public static String IP = "136.224.52.13";
	
	public static String SUFFIX_SERVER = "@nm.ctcims.cn";
	
	public static String HUSHI = "呼和浩特市";
	public static String HUSHI_NUM = "471";
	
	public static String JINING = "乌兰察布市";
	public static String JINING_NUM = "474";
	
	public static String XIMENG = "锡林郭勒盟";
	public static String XIMENG_NUM = "479";
	
	public static String CHIFENG = "赤峰市";
	public static String CHIFENG_NUM = "476";
	
	public static String TONGLIAO = "通辽市";
	public static String TONGLIAO_NUM = "475";
	
	public static String XINGANMENG = "兴安盟";
	public static String XINGANMENG_NUM = "482";
	
	public static String HAILAER = "呼伦贝尔";
	public static String HAILAER_NUM = "470";
	
	public static String BAOTOU = "包头市";
	public static String BAOTOU_NUM = "472";
	
	public static String EERDUOSI = "鄂尔多斯市";
	public static String EERDUOSI_NUM = "477";
	
	public static String LINHE = "巴彦淖尔市";
	public static String LINHE_NUM = "478";
	
	public static String WUHAI = "乌海市";
	public static String WUHAI_NUM = "473";
	
	public static String AMENG = "阿拉善盟";
	public static String AMENG_NUM = "483";
	
	public static String JQ_URL = "136.224.51.9";
	public static String CZ_URL = "136.224.51.137";
	
	public static String USER_SOAP = "Guangkuan";
	
	public static String USER_SOAP_PASS = "Guangkuan@123";
	
	public static String KEY = "00000000000000000000000000000000";
	
	
	/**
	 * 获取网元名称
	 * @param region
	 * @return
	 */
	public static String getNetUnit(String region) {
		if ("471".equals(region)) {
			return "JQAGCF";
		} else if ("474".equals(region)) {
			return "JQAGCF";
		} else if ("479".equals(region)) {
			return "JQAGCF";
		} else if ("476".equals(region)) {
			return "JQAGCF";
		} else if ("475".equals(region)) {
			return "JQAGCF";
		} else if ("482".equals(region)) {
			return "JQAGCF";
		} else if ("470".equals(region)) {
			return "JQAGCF";
		} else if ("472".equals(region)) {
			return "CZAGCF";
		} else if ("477".equals(region)) {
			return "CZAGCF";
		} else if ("478".equals(region)) {
			return "CZAGCF";
		} else if ("473".equals(region)) {
			return "CZAGCF";
		} else if ("483".equals(region)) {
			return "CZAGCF";
		}
		return null;
	}
}
