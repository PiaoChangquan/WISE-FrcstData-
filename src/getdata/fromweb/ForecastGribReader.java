package getdata.fromweb;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import getdata.fromDB.AWSInfo;

public class ForecastGribReader  {

	private final static Logger logger = LoggerFactory.getLogger(ForecastGribReader.class);

	public List<Item> HourlySubscribe(Item reqItem, AWSInfo[] awsInfo){

		logger.info(" REST Service Reader Starterd...");

		try {


			URIBuilder builder = new URIBuilder(awsInfo[0].getRestUrl());
			builder.addParameter("serviceKey", URLDecoder.decode(awsInfo[0].getServiceKey(), "UTF-8"));
			builder.addParameter("base_date", reqItem.getBaseDate());
			builder.addParameter("base_time", reqItem.getBaseTime());
			builder.addParameter("nx", reqItem.getNx());
			builder.addParameter("ny", reqItem.getNy());
			builder.addParameter("_type",awsInfo[0].getResType());
			builder.addParameter("numOfRows", "999");
			
			
			System.out.println(builder);

			// Call RESTful API for forecast
			HttpClient client = HttpClients.createDefault();
			String listStubsUri = builder.build().toString();
			HttpGet getStubMethod = new HttpGet(listStubsUri);
			HttpResponse getStubResponse = client.execute(getStubMethod);

			System.out.println("     "+ listStubsUri);
			int getStubStatusCode = getStubResponse.getStatusLine().getStatusCode();
			if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
				// Handle non-2xx status code
				return null;
			}
			String responseBody = EntityUtils.toString(getStubResponse.getEntity());

			ObjectMapper mapper = new ObjectMapper();
			JsonNode response = mapper.readTree(responseBody).path("response");
			if (response.path("header").path("resultMsg").asText().equals("OK")) {
				int totalCount = response.path("body").path("totalCount").intValue();
				if (totalCount == 0) {
					logger.error("Response with no Data.");
					throw new RuntimeException(" ===== ForecastGribReader 94 ===== No Result. =====");
				}
				JsonNode readItems = response.path("body").path("items").path("item");
				logger.debug(" ===== RESPONSE ITEM: {} =====", readItems.toString());

				List<Item> dataList = mapper.readValue(readItems.traverse(),
						mapper.getTypeFactory().constructCollectionType(List.class, Item.class));
				return dataList;
			} else {
				logger.debug(" ===== MSG: {} =====", response.path("header").path("resultMsg").asText());

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}


}
