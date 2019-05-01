package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 * @author mskkingtake
 *
 */
public class DateUtil {
	public static final String FORMAT_SLASH = "yyyy/MM/dd HH:mm:ss";
	public static final String FROMAT_HYPHEN = "yyyy-MM-dd HH:mm:ss";
	public static final String FROMAT_COM = "yyyyMMddHHmmss";

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurDateTime() {
		return getCurDateTime(FROMAT_COM);
	}
	
	/**
	 * 获取当前时间
	 * @param formatType 返回数据格式化
	 * @return
	 */
	public static String getCurDateTime(String formatType) {
		return new SimpleDateFormat(formatType).format(new Date());
	}
	
	/**
	 * 获取时间对应年份
	 * @param inDate
	 * @return
	 */
	public static int getYear(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * 获取时间对应月份
	 * @param inDate
	 * @return
	 */
	public static int getMonth(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获取时间对应天数
	 * @param inDate
	 * @return
	 */
	public static int getDay(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 获取时间对应小时
	 * @param inDate
	 * @return
	 */
	public static int getHour(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取时间对应分钟
	 * @param inDate
	 * @return
	 */
	public static int getMinute(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.get(Calendar.MINUTE);
	}
	
	/**
	 * 获取时间对应秒钟
	 * @param inDate
	 * @return
	 */
	public static int getSecond(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.get(Calendar.SECOND);
	}
	
	/**
	 * 获取时间对应毫秒数
	 * @param inDate
	 * @return
	 */
	public static long getTimeInMillis(Date inDate) {
		Calendar calendar = Calendar.getInstance();
        calendar.setTime(inDate);
        return calendar.getTimeInMillis();
	}
	
	/**
	 * 两个时间相差毫秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getTimeDiffMillis(Date startDate, Date endDate) {
		return endDate.getTime() - startDate.getTime();
	}
	
	/**
	 * 两个时间相差毫秒数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getTimeDiffMillis(String startDate, String endDate, String format) {
		Date dateStart = stringToDate(startDate, format);
		Date dateEnd = stringToDate(endDate, format);		
		return dateStart.getTime() - dateEnd.getTime();
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
	 * 获得当天0点时间
	 * @return
	 */
	public static Date getTimesMorning() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获得0点时间
	 * @return
	 */
	public static Date getTimesMorning(Date inDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inDate);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		return calendar.getTime();
	}
	
	/**
	 * 获得当天24点时间
	 * @return
	 */
	public static Date getTimesNight() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		return calendar.getTime();
	}
	
	/**
	 * 获得24点时间
	 * @return
	 */
	public static Date getTimesNight(Date inDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(inDate);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		return calendar.getTime();
	}
	
	/**
	 * 获取本周某一天的日期
	 * week: 1-7   周日-周六
	 * @return 日期
	 */
	public static Date getDayOfWeek(int week, boolean nextWeek) {
		Calendar calendar = Calendar.getInstance();
		int dayofweek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (dayofweek == 0) {
			dayofweek = week;
		}
		calendar.add(Calendar.DATE, -dayofweek + week);
		return calendar.getTime();
	}
	
//	public static Date getToDate(Date inputDate) {
//		Date startDate = new Date();
//		Date endDate = new Date();
//	}






	
	
	public static void main(String[] args) {
//		System.out.println(getDayOfWeek(2, true));
//		System.out.println(getCurDateTime(FROMAT_HYPHEN));
//		System.out.println(getTimesNight(new Date()));
		
//		Date startDate = new Date();
//		Date endDate = startDate.
		
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.add(Calendar.DATE, -3);
		Calendar endCalendar = Calendar.getInstance();


		
		System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(startCalendar.getTime()));
		System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(endCalendar.getTime()));
		System.out.println(startCalendar.getTimeInMillis() / 1000);
		System.out.println(endCalendar.getTimeInMillis() / 1000);
		/*
		 * 20181202144549537
20181205144549554
1543733149
1543992349

1543733149
1543992349
		 * */
		
		
		try {
			System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").parse("20181202144549537").getTime() / 1000);
			System.out.println(new SimpleDateFormat("yyyyMMddHHmmssSSS").parse("20181205144549554").getTime() / 1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
