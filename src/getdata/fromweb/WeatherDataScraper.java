package getdata.fromweb;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import getdata.fromDB.AWSInfo;
import getdata.fromDB.Configure;

public class WeatherDataScraper {

	private static  String filePath = "./WISEconf.json";
	private final static Logger logger = LoggerFactory.getLogger(WeatherDataScraper.class);
	
	public static void main(String[] args) {		
		
		try{
			AWSInfo[] awsInfo = Configure.GetAWSInfo(filePath);
//						
			String exeTime =awsInfo[0].getExetime();
			String period =awsInfo[0].getPeriod();
			
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			
			String currHour = new SimpleDateFormat("HH").format(date);
			if(Integer.parseInt(currHour) < Integer.parseInt(exeTime)){
//				calendar.add(Calendar.DATE, -1);
				String exeTime1 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()) + " "+exeTime+":00";

				date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(exeTime1);
			}
			
//			if(Integer.parseInt(currHour) >= Integer.parseInt(exeTime)){
//				String exeTime1 = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime()) + " "+currHour+":00";
//
//				date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(exeTime1);
//			}
			DailySubscriber job = new DailySubscriber();
					
			Timer jobScheduler = new Timer();
		
			//For local Test
			date = new Date();
			logger.debug("===== Start Daily Subscribe At {}, Period: {} ===== ", date, period);
			
			jobScheduler.scheduleAtFixedRate(job, date, getTimePrecision(period));	
		} catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public static long getTimePrecision(String value) throws Exception {
		long l = 0;
		String val = "";
		try {
			if (value.endsWith("d") || value.endsWith("D")) {
				val = value.substring(0, value.length() - 1);
				l = Long.parseLong(val) * 24 * 60 * 60 * 1000;
			}

			else if (value.endsWith("h") || value.endsWith("H")) {

				val = value.substring(0, value.length() - 1);
				l = Long.parseLong(val) * 60 * 60 * 1000;

			} else if (value.endsWith("m") || value.endsWith("M")) {
				val = value.substring(0, value.length() - 1);
				l = Long.parseLong(val) * 60 * 1000;
			} else if (value.endsWith("s") || value.endsWith("S")) {

				val = value.substring(0, value.length() - 1);
				l = Long.parseLong(val) * 1000;
			} else {

				l = Long.parseLong(value);
			}

		} catch (Exception e) {

			throw new Exception(e);
		}

		return l;
	}
}