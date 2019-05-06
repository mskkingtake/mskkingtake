package common;

public class ByteUtil {
	
	/**
	 * 
	 * @param bytes
	 * @return
	 */
	public static String byteToHexString(byte[] bytes) {
		if(bytes==null) {
            return null;
        }
        
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i < bytes.length; i++) {
        	String strHex=Integer.toHexString(bytes[i]);
        	if(strHex.length() > 3) {
        		sb.append(strHex.substring(6));
        	} else {
        		if(strHex.length() < 2) {
        			sb.append("0" + strHex);
        		} else {
        			sb.append(strHex);
        		}
        	}
        }
        return  sb.toString();
    }
	
	/** 
     * 16进制字符串转2进制 
     * @param s 
     * @return 
     */  
    public static byte[] hexStringToByte(String hexString){  
        if (hexString == null || hexString.equals("")) {  
            return null;  
        }  
        hexString = hexString.toUpperCase();  
        int length = hexString.length() / 2;  
        char[] hexChars = hexString.toCharArray();  
        byte[] d = new byte[length];  
        for (int i = 0; i < length; i++) {  
            int pos = i * 2;  
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));  
        }  
        return d;  
    }  
    
    /** 
     * 字符转为byte 
     * @param c 
     * @return 
     */  
    public static byte charToByte(char c) {  
        return (byte) "0123456789ABCDEF".indexOf(c);  
    }  
    
    
    public static String StrToBinstr(String str) {        
		char[] strChar=str.toCharArray();        
		String result="";        
		
		for(int i=0;i<strChar.length;i++) {            
			result +=Integer.toBinaryString(strChar[i])+ " ";        
		}                
		return result;    
	}
    
    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
    
    /**
     * int转byte[]
     * 该方法将一个int类型的数据转换为byte[]形式，因为int为32bit，而byte为8bit所以在进行类型转换时，知会获取低8位，
     * 丢弃高24位。通过位移的方式，将32bit的数据转换成4个8bit的数据。注意 &0xff，在这当中，&0xff简单理解为一把剪刀，
     * 将想要获取的8位数据截取出来。
     * @param i 一个int数字
     * @return byte[]
     */
    public static byte[] int2ByteArray(int i){
    	byte[] result = new byte[4];
    	result[0] = (byte)((i >> 24)& 0xFF);
    	result[1] = (byte)((i >> 16)& 0xFF);
    	result[2] = (byte)((i >> 8)& 0xFF);
    	result[3] = (byte)(i & 0xFF);
    	return result;
    }
    
    public static void main(String[] args) {
//		System.out.println(strTo16("msk"));
    	System.out.println(int2ByteArray(1));
	}
}
