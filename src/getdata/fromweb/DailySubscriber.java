package getdata.fromweb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ComputeMethod.ComputingMethod;
import getdata.fromDB.AWSInfo;
import getdata.fromDB.CombinationData;
import getdata.fromDB.Configure;

public class DailySubscriber extends TimerTask {
	public static ForecastData[]  ForecastDataArray;
	private final static Logger logger = LoggerFactory.getLogger(DailySubscriber.class);

	private static  String filePath = "./WISEconf.json";
	public  void run() {

		ForecastGribReader forecaster = new ForecastGribReader();
		try { 
			
			AWSInfo[] awsInfo = Configure.GetAWSInfo(filePath);
			
			// Current Time
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();
			
			int siteNum = awsInfo[0].getCount();		
			String exeTime =awsInfo[0].getExetime();

			String currHour = new SimpleDateFormat("HH").format(date);
			
			Item reqItem = new Item();
			Map<Integer, List<Item>> resultMap = new HashMap<Integer, List<Item>>();
			for (int i = 0; i < siteNum; i++) {
				resultMap.put(i, new ArrayList<Item>());
			}
			

			
//			String exeTime1 = new SimpleDateFormat("yyyyMMdd").format(calendar.getTime());
//			reqItem.setBaseDate(exeTime1);
			
			
			// FOR local Test
			long currTime = date.getTime();
			
			long exeTime11 = currTime - 1 * 24 * 60 * 60 * 1000;
			reqItem.setBaseDate(new SimpleDateFormat("yyyyMMdd").format(exeTime11));
			
			
			
			
			
			reqItem.setBaseTime(awsInfo[0].getBaseTime());
			logger.debug("=====  ===== Hourly Subscribe date: {}, time: {} ===== ===== ", reqItem.getBaseDate(),
					reqItem.getBaseTime());
			ForecastData[]  ForecastDataArray = new ForecastData[siteNum];
			for (int j = 0; j < siteNum; j++) {			
				
				reqItem.setNx(String.valueOf(awsInfo[j].getNx()));
				reqItem.setNy(String.valueOf(awsInfo[j].getNy()));
				
				List<Item> resItem = forecaster.HourlySubscribe(reqItem,awsInfo);
				
				for (Item item : resItem) {

					if (item.getCategory().equals("REH") || item.getCategory().equals("T3H")
							|| item.getCategory().equals("R06")||item.getCategory().equals("TMX")||item.getCategory().equals("TMN")) {
						if(item.getFcstTime().equals("0900")||item.getFcstTime().equals("1200")||item.getFcstTime().equals("1500")||item.getFcstTime().equals("1800")){
							resultMap.get(j).add(item);
						}
						
					}
				}
				
				ForecastDataArray[j]=ComputingMethod.publishAvg(resultMap.get(j));
			}
			
			
			CombinationData.GetTheAllData(ForecastDataArray);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}




	
}
