package ComputeMethod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import getdata.fromDB.SensorData;
import getdata.fromweb.ForecastData;
import getdata.fromweb.Item;

public class ComputingMethod {
	private final static Logger logger = LoggerFactory.getLogger(ComputingMethod.class);

//	public static double ComputingForecastData(List<SensorData> dataList, int lenght,int countType) {
//		// TODO Auto-generated method stub
//		double sumValue = 0;
//		if(countType==0){
//			for (int i = lenght; i < dataList.size(); i++) {
//				sumValue=sumValue+ dataList.get(i).getValue();			
//			}
//		}else if(countType==1){
//			for (int i = dataList.size(); i > dataList.size() - lenght; i--) {
//				sumValue=sumValue+ dataList.get(i-1).getValue();			
//			}
//		}
//		return sumValue;
//	}
//	
	public static double ComputeSumForecastData(List<SensorData> dataList, int lenght) {
		// TODO Auto-generated method stub

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - lenght);
		double sumValue = 0;			

		for (int i = dataList.size(); i > 0; i--) {
			if (dataList.get(i-1).getDateTime().equals(new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime())+"00:00:00"))
			{
				break;
			} else {
				sumValue=sumValue+ dataList.get(i-1).getValue();
			}		
		}
		return sumValue;
	}
	
	public static double ComputeCount(List<SensorData> dataList, int lenght) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		double count = 0;
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - lenght);
			for (int i = dataList.size(); i > 0; i--) {
				if (dataList.get(i-1).getDateTime().equals(new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime())+"00:00:00"))
				{
//					System.out.println("same!!"+ new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime())+"00:00:00");
					break;
				} else if (dataList.get(i-1).getValue()>0){
//					System.out.println(new SimpleDateFormat("yyyy-MM-dd ").format(calendar.getTime())+"00:00:00");
					count++;	
				}		
			}
		return count;
	}
	
	
	public static ForecastData publishAvg(List<Item> itemList) {
		// TODO Auto-generated method stub
	    int count = 0;
	    int count1 = 0;
	    int count2 = 0;
	    int count3 = 0;
	    double Tmx=0;
	    double Tmx2=0;
	    double Tmn=9999;
	    double Tmn2=9999;
	    
	    double reh = 0;
		double reh2 = 0;
		int rehcount = 0;
		int rehcount2 = 0;

	    double t1h = 0;
	    double t1h2 = 0;
	    int t1hcount = 0;
	    int t1hcount2 = 0;

	    double r06 = 0;
	    double r062 = 0;
	    int r06count = 0;
	    int r06count2 = 0;

	    for (Item item : itemList) {
	      if (item.getCategory().equals("REH")) {
	        count++;
	        if (count <= 4) {
	          reh += item.getFcstValue();
	          rehcount++;
	        }
	        if (count > 4) {
	          reh2 += item.getFcstValue();
	          rehcount2++;
	        }
	        logger.debug(" {},{},{},{},{},{}", item.getNx(), item.getNy(), item.getFcstDate(), item.getFcstTime(),
	            item.getCategory(), item.getFcstValue());
	      } else if (item.getCategory().equals("T3H")) {
	        count1++;
	        if (count1 <= 4) {
	          t1h += item.getFcstValue();
	          if(Tmn>item.getFcstValue()){
	        	  Tmn=item.getFcstValue();
	          }
	          t1hcount++;
	        }
	        if (count1 > 4) {
	          t1h2 += item.getFcstValue();
	          if(Tmn2>item.getFcstValue()){
	        	  Tmn2=item.getFcstValue();
	          }
	          t1hcount2++;
	        }
	        logger.debug(" {},{},{},{},{},{}", item.getNx(), item.getNy(), item.getFcstDate(), item.getFcstTime(),
	            item.getCategory(), item.getFcstValue());

	      } else if (item.getCategory().equals("R06")) {
	        count2++;
	        if (count2 <= 2) {
	          r06 += item.getFcstValue();
	          r06count++;
	        }
	        if (count2 > 2) {
	          r062 += item.getFcstValue();
	          r06count2++;
	        }
	        logger.debug(" {},{},{},{},{},{}", item.getNx(), item.getNy(), item.getFcstDate(), item.getFcstTime(),
	            item.getCategory(), item.getFcstValue());
	      }else if (item.getCategory().equals("TMX")) {
	        count3++;
	        if(count3==1){
	          Tmx=item.getFcstValue();
	        }
	        if(count3==2){
	          Tmx2=item.getFcstValue();
	        }
	        logger.debug(" {},{},{},{},{},{}", item.getNx(), item.getNy(), item.getFcstDate(), item.getFcstTime(),
	            item.getCategory(), item.getFcstValue());

	      }

	    }
	    ForecastData  fData = new ForecastData();
	    
	    fData.setNxNy(itemList.get(0).getNx()+itemList.get(0).getNy());
	    fData.setTodayHum(reh / rehcount);
	    fData.setTodayRaf(r06 / r06count);
	    fData.setTodayTav(t1h / t1hcount);
	    fData.setTodayTmi(Tmn);
	    fData.setTodayTmx(Tmx);
	    
	    fData.setTomorryHum(reh2 / rehcount2);
	    fData.setTomorryRaf(r062 / r06count2);
	    fData.setTomorryTav(t1h2 / t1hcount2);
	    fData.setTomorryTmi(Tmn2);
	    fData.setTomorryTmx(Tmx2);
	    return fData;
	}
	
}
