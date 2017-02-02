package getdata.fromDB;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Configure {
	/**
	 * Class Name: WeatherDataSubscriber.java Description:
	 * 
	 * @author ChangQuan Piao
	 * @since 2016-7-18
	 * @version 3.0
	 * 
	 *          Copyright(c) 2016 by CILAB All right reserved.
	 */
	// private static Logger logger = LoggerFactory.getLogger(Configure.class);

	private static int nx;
	private static int ny;
	private static int aws;
	public static int h = 0;
//	private static String GetUrl;
//	private static String PostUrl;
	private static JsonNode rootNode;
	private static final String filePath = "WISEconf.json";
	private static ObjectMapper mapper;
	private static String local_id;
	private static String sri_id;

	public static AWSInfo[] GetAWSInfo()
			throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		int count = 0;
		mapper = new ObjectMapper();
		try {
			rootNode = mapper.readTree(new File(filePath));
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

		Resource resource = new ClassPathResource("config.properties");
		Properties props = PropertiesLoaderUtils.loadProperties(resource);

		String GetUrl = props.getProperty("dataserver.getUrl");
		String PostUrl = props.getProperty("dataserver.postUrl");
		
//		JsonNode RestUnitNode = rootNode.path("Rest_Unit");
//		GetUrl = RestUnitNode.path("GetUrl").asText();
//
//		PostUrl = RestUnitNode.path("PostUrl").asText();
		

		JsonNode sensorUnitNode = rootNode.path("sensor_unit");
		int snsrCount = sensorUnitNode.path("count").intValue();
		
		AWSInfo[] awsInfo = new AWSInfo[snsrCount];
		if (snsrCount > 0) {
			JsonNode sensorListNode = sensorUnitNode.path("elements");
			for (JsonNode sensorNode : sensorListNode) {
				// count
				awsInfo[count] = new AWSInfo();
				nx = sensorNode.path("nx").intValue();
				ny = sensorNode.path("ny").intValue();
				aws = sensorNode.path("AWS").intValue();

				awsInfo[count].setAws(aws);
				awsInfo[count].setNx(nx);
				awsInfo[count].setNy(ny);
				awsInfo[count].setGetUrl(GetUrl);
				
				awsInfo[count].setPostUrl(PostUrl);
				System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~"+awsInfo[count].getGetUrl()+"   "+awsInfo[count].getPostUrl());
				List<Map<String, String>> logicalSnsr = mapper.readValue(sensorNode.path("sensors").toString(),
						new TypeReference<LinkedList<HashMap<String, String>>>() {
						});
				// AddtoAWSInfo(logicalSnsr);
				for (Map<String, String> logicalSnsrMap : logicalSnsr) {

					local_id = logicalSnsrMap.get("local_id");
					sri_id = logicalSnsrMap.get("sri_id");
					if (local_id.equals("Pre")) {
						awsInfo[count].setPre(sri_id);
					} else if (local_id.equals("Tmax")) {
						awsInfo[count].setTmax(sri_id);
					} else if (local_id.equals("Tmin")) {
						awsInfo[count].setTmin(sri_id);
					} else if (local_id.equals("Hum")) {
						awsInfo[count].setHum(sri_id);
					}else if (local_id.equals("Tav")) {
						awsInfo[count].setTav(sri_id);
					}else if (local_id.equals("TYPE2RFTodayLandUse1")) {
						awsInfo[count].setRFTodayLandUse1(sri_id);
					}else if (local_id.equals("TYPE2RFTodayLandUse2")) {
						awsInfo[count].setRFTodayLandUse2(sri_id);
					}else if (local_id.equals("TYPE2RFTodayLandUse3")) {
						awsInfo[count].setRFTodayLandUse3(sri_id);
					}else if (local_id.equals("TYPE2RFTodayPlus1LandUse1")) {
						awsInfo[count].setRFTodayPlus1LandUse1(sri_id);
					}else if (local_id.equals("TYPE2RFTodayPlus1LandUse2")) {
						awsInfo[count].setRFTodayPlus1LandUse2(sri_id);
					}else if (local_id.equals("TYPE2RFTodayPlus1LandUse3")) {
						awsInfo[count].setRFTodayPlus1LandUse3(sri_id);
					}else if (local_id.equals("TYPE2RFTodayPlus2LandUse1")) {
						awsInfo[count].setRFTodayPlus2LandUse1(sri_id);
					}else if (local_id.equals("TYPE2RFTodayPlus2LandUse2")) {
						awsInfo[count].setRFTodayPlus2LandUse2(sri_id);
					}else if (local_id.equals("TYPE2RFTodayPlus2LandUse3")) {
						awsInfo[count].setRFTodayPlus2LandUse3(sri_id);
					}else if (local_id.equals("TYPE2cartTodayLandUse1")) {
						awsInfo[count].setCartTodayLandUse1(sri_id);
					}else if (local_id.equals("TYPE2cartTodayLandUse2")) {
						awsInfo[count].setCartTodayLandUse2(sri_id);
					}else if (local_id.equals("TYPE2cartTodayLandUse3")) {
						awsInfo[count].setCartTodayLandUse3(sri_id);
					}else if (local_id.equals("TYPE2cartTodayPlus1LandUse1")) {
						awsInfo[count].setCartTodayPlus1LandUse1(sri_id);
					}else if (local_id.equals("TYPE2cartTodayPlus1LandUse2")) {
						awsInfo[count].setCartTodayPlus1LandUse2(sri_id);
					}else if (local_id.equals("TYPE2cartTodayPlus1LandUse3")) {
						awsInfo[count].setCartTodayPlus1LandUse3(sri_id);
					}else if (local_id.equals("TYPE2cartTodayPlus2LandUse1")) {
						awsInfo[count].setCartTodayPlus2LandUse1(sri_id);
					}else if (local_id.equals("TYPE2cartTodayPlus2LandUse2")) {
						awsInfo[count].setCartTodayPlus2LandUse2(sri_id);
					}else if (local_id.equals("TYPE2cartTodayPlus2LandUse3")) {
						awsInfo[count].setCartTodayPlus2LandUse3(sri_id);
					}
				}

				System.out.println("num= " + count + " " + awsInfo[count].toString());
				count++;
			}
		}
		return awsInfo;
	}
}
