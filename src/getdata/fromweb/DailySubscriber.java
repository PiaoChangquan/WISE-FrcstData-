package getdata.fromweb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import ComputeMethod.ComputingMethod;
import getdata.fromDB.CombinationData;

public class DailySubscriber extends TimerTask {
	public static ForecastData[]  ForecastDataArray;
	private final static Logger logger = LoggerFactory.getLogger(DailySubscriber.class);

	public  void run() {

		ForecastGribReader forecaster = new ForecastGribReader();
		try { 
			// Current Time
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();

			long currTime = date.getTime();

			Resource resource = new ClassPathResource("config.properties");
			Properties props = PropertiesLoaderUtils.loadProperties(resource);

			int siteNum = Integer.parseInt(props.getProperty("site.num"));

			Item reqItem = new Item();
			Map<Integer, List<Item>> resultMap = new HashMap<Integer, List<Item>>();
			for (int i = 0; i < siteNum; i++) {
				resultMap.put(i, new ArrayList<Item>());
			}
//			long exeTime = currTime - 1 * 24 * 60 * 60 * 1000;
			long exeTime = currTime;
			
			reqItem.setBaseDate(new SimpleDateFormat("yyyyMMdd").format(exeTime));

			reqItem.setBaseTime("1700");
			logger.debug("=====  ===== Hourly Subscribe date: {}, time: {} ===== ===== ", reqItem.getBaseDate(),
					reqItem.getBaseTime());
			ForecastData[]  ForecastDataArray = new ForecastData[siteNum];
			for (int j = 0; j < siteNum; j++) {
				String grid = props.getProperty("site." + (j + 1) + ".grid");
				reqItem.setNx(grid.split(",")[0]);
				reqItem.setNy(grid.split(",")[1]);
				System.out.println(reqItem.getNx());
				List<Item> resItem = forecaster.HourlySubscribe(reqItem);
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
