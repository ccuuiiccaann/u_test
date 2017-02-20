
package instrument_transformer;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	/**
	 * @param dateStr
	 * @param formatStr 默认为yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static Date convertString2Date(String dateStr, String formatStr) {
		if(formatStr==null){
			formatStr="yyyy-MM-dd HH:mm:ss";
		}
		DateFormat format = new SimpleDateFormat(formatStr);
		Date date = null;
		try {
			date = format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}

	/**
	 * 查询当前时间，返回时间类型 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Date currentDate() {
		long dateLong = System.currentTimeMillis();
		Date date = new Date(dateLong);
		return date;
	}
	
	/**
	 * 查询当前时间之后的某个时间，返回时间类型 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Date currentDate( int day, int hour, int minute) {
		long addTime = day * 24 * 60 * 60 * 1000;
		addTime += hour * 60 * 60 * 1000;
		addTime += minute * 60 * 1000;
		
		long dateLong = System.currentTimeMillis() + addTime;
		Date date = new Date(dateLong);
		return date;
	}

	/**
	 * 查询当前时间，返回时间类型 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static Timestamp currentTimeStamp() {
		long dateLong = System.currentTimeMillis();
		Timestamp date = new Timestamp(dateLong);
		return date;
	}

	/**
	 * 根据转换格式获取当前时间字符串类型 <功能详细描述>
	 * 
	 * @param formatStr
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public static String currentDate2String(String formatStr) {
		if (formatStr==null || "".equals(formatStr))
			formatStr = "yyyy-MM-dd HH:mm:ss";
		Date date = currentDate();
		return convertDate2String(date, formatStr);
	}
	
	/**
	 * 获取当前时间
	 * @return yyyy-MM-dd HH:mm:ss格式的时间字符串
	 */
	public static String getCurDate(){
			String formatStr = "yyyy-MM-dd HH:mm:ss";
			Date date = currentDate();
			return convertDate2String(date, formatStr);
	}

	public static String convertDate2String(Date date) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = "";
		try {
			dateStr = format.format(date);
		} catch (Exception e) {
		}

		return dateStr;
	}

	public static String convertDate2String(Date date, String formatStr) {
		if(formatStr==null || "".equals(formatStr)){
			formatStr="yyyy-MM-dd HH:mm:ss";
		}
		DateFormat format = new SimpleDateFormat(formatStr);
		String dateStr = "";
		try {
			dateStr = format.format(date);
		} catch (Exception e) {
		}

		return dateStr;
	}
	
	public static String convertTimeStamp2String(Timestamp date) {

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = "";
		try {
			dateStr = format.format(date);
		} catch (Exception e) {
		}

		return dateStr;
	}

	/**
	 * 获取时差的绝对值
	 * 
	 * @param date
	 * @return
	 */
	public static long dateDiffer(Date date) {
		long nowTime = System.currentTimeMillis();
		long compareTime = date.getTime();
		return Math.abs(nowTime - compareTime);
	}

	/**
	 * 比较当前时间是否在某个时间段内
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean dateDiffer(Date start, Date end) {
		long now = System.currentTimeMillis();
		long startLong = start.getTime();
		long endLong = end.getTime();
		if (startLong <= now && now <= endLong)
			return true;
		else
			return false;
	}
	
	/**
	 * 获得该日期指定天数之前的日期
	 * 
	 * @param dtDate
	 * @param lDays
	 * @return 返回日期
	 */
	public static Date before(Date dtDate, long lDays) {
		long lCurrentDate = 0;
		lCurrentDate = dtDate.getTime() - lDays * 24 * 60 * 60 * 1000;
		Date dtBefor = new Date(lCurrentDate);
		return dtBefor;
	}
	
	/**
	 * 将时间字符串转换为时间类型
	 * @param str yyyy-MM-dd类型
	 * @return
	 */
	public static Date parseToDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据日期计算days天后的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static String getAddedDate(String date,int days){
		return getAddedDate(date,days,null);
	}
	/**
	 * 根据日期计算days天后的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static String getAddedDate(String date,int days,String format){
		if(format==null || "".equals(format)){
			format="yyyy-MM-dd HH:mm:ss";
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseToDate(date));
		cal.add(Calendar.DATE, days);
		return (new SimpleDateFormat(format)).format(cal.getTime());
	}
	
	/**
	 * 根据日期计算days天后的日期
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getAddedDate(Date date,int days){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}
	
	/**
	 * 根据日期计算hours小时后的日期
	 * @param date 当前时间
	 * @param hours 小时数
	 * @return
	 */
	public static Date getAddedDateByHour(Date date,int hours){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, hours);
		return cal.getTime();
	}
	/**
	 * 根据日期计算months月后的时间
	 * @param date
	 * @param months 增加的月份
	 * @return
	 */
	public static String getAddedDateByMonth(String date,int months){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseToDate(date));
		cal.add(Calendar.MONTH, months);
		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(cal.getTime());
	}
	
	/**
	 * 计算两个日期之间的天数
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date beginDate,Date endDate) throws ParseException { 
		
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        beginDate=sdf.parse(sdf.format(beginDate));  
        endDate=sdf.parse(sdf.format(endDate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(beginDate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(endDate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
           
        return Integer.parseInt(String.valueOf(between_days));           
	 }    
	
	/**
	 * 计算两个日期之间的月数
	 * @param beginDate
	 * @param endDate
	 * @return 月数
	 * @throws ParseException
	 */
	public static int monthBetween(Date beginDate,Date endDate) throws ParseException{ 
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        beginDate=sdf.parse(sdf.format(beginDate));  
        endDate=sdf.parse(sdf.format(endDate));  
        String begin=convertDate2String(beginDate,"yyyy-MM-dd");
        String end=convertDate2String(endDate,"yyyy-MM-dd");
        String beginMonth=begin.split("-")[1];
        String endMonth=end.split("-")[1];
        int month=Integer.parseInt(endMonth)-Integer.parseInt(beginMonth);
        return month;           
	 }    
	      
	 /**
     * 计算两个日期之间的天数
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int daysBetween(String beginDate,String endDate) {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        Calendar cal = Calendar.getInstance();    
        try {
			cal.setTime(sdf.parse(beginDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}    
        long time1 = cal.getTimeInMillis();                 
        try {
			cal.setTime(sdf.parse(endDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);  
            
        return Integer.parseInt(String.valueOf(between_days));     
   }  

	
    /**
	 * 比较两个时间的大小
	 * @param date1 时间1
	 * @param date2 时间2
	 * @return 1:时间1>时间2，-1:时间1<时间2，0:相等
	 */
	public static int compareDate(Date date1,Date date2){
		 long d1=date1.getTime() ;
		 long d2=date2.getTime();
		 if(d1>d2){
			 return 1;
		 }else if(d1<d2){
			 return -1;
		 }else {
			 return 0;
		 }
	}
	
	 /**
		 * 比较两个时间的大小
		 * @param date1 时间1
		 * @param date2 时间2
		 * @param format 时间格式，默认为yyyy-MM-dd HH:mm:ss
		 * @return 1:时间1>时间2，-1:时间1<时间2，0:相等
		 */
	public static int compareDate(String date1,String date2,String format){
		if(format==null || "".equals(format)){
			format="yyyy-MM-dd HH:mm:ss";
		}
		Date d1=convertString2Date(date1,format);
		Date d2=convertString2Date(date2,format);
		return compareDate(d1,d2);
	}
	
	/**
	 * 计算两个日期之间的小时数
	 * @param beginDate
	 * @param endDate
	 * @return
	 * @throws ParseException
	 */
	public static int hoursBetween(Date beginDate,Date endDate) throws ParseException { 
		
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        beginDate=sdf.parse(sdf.format(beginDate));  
        endDate=sdf.parse(sdf.format(endDate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(beginDate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(endDate);    
        long time2 = cal.getTimeInMillis();         
        long between_hours=(time2-time1)/(1000*3600);  
           
        return Integer.parseInt(String.valueOf(between_hours));           
	 }    

	public static void main(String[] args) throws ParseException {
		int m=monthBetween(new Date(),parseToDate("2016-07-01"));
		System.out.println(m);
	}
	
	
	
}
