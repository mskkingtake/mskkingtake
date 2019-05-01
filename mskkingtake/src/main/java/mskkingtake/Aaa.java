package mskkingtake;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class Aaa {

	public static void main(String args) {
		// TODO Auto-generated method stub
        byte a=(byte)127;
        byte b=(byte)128;
        byte c=(byte)100;
        int  x=0xff;//255
        byte d=(byte)x;
        x=0x80;//128
        byte f=(byte)x;
        c=(byte)(c*3);
//        System.out.println(a+" "+b+" "+c+" "+d+" "+f );
        
        String SIFCID = "111,";
        System.out.println(SIFCID.substring(0, SIFCID.length() - 1));
	}
	
	public static void main(String[] args) {
		
		 String SIFCID = "210106198404060050";
		System.out.println(SIFCID.substring(0, SIFCID.length() - 1));
		
		// 自定义三天时间
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -3);
		Calendar endCalendar = Calendar.getInstance();
				
		byte[] result = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		
		// 转换字节信息
		try {
			dos.writeInt(49);
			dos.writeInt(0x00000004);
			//dos.writeInt(seq++);
			//dos.write(Util.decodeString("13555852369", 21));
			dos.writeLong(startCalendar.getTimeInMillis() / 1000);
			dos.writeLong(endCalendar.getTimeInMillis() / 1000);
			dos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result = baos.toByteArray();
		
		// 日志信息
		//System.out.println("发送黑名单查询请求:::" + Util.toHexString(result));
	}
}
