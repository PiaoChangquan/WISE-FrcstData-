package getdata.fromDB;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import getdata.fromDB.SensorData;

public class GetPastData {
	public static List<SensorData> GetPastData(String Url,String StreamID) throws IOException, URISyntaxException {

		URIBuilder builder = new URIBuilder(Url+StreamID);
		// Call RESTful API for forecast
		HttpClient client = HttpClients.createDefault();
		String listStubsUri = builder.build().toString();
		HttpGet getStubMethod = new HttpGet(listStubsUri);
		HttpResponse getStubResponse = client.execute(getStubMethod);
		System.out.println(listStubsUri);
		int getStubStatusCode = getStubResponse.getStatusLine().getStatusCode();
		if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
			System.out.println("error");
		}
		String responseBody = EntityUtils.toString(getStubResponse.getEntity());
		
		ObjectMapper mapper = new ObjectMapper();
		List<SensorData> dataList = mapper.readValue(responseBody.trim(),
				mapper.getTypeFactory().constructCollectionType(List.class, SensorData.class));
		
		return dataList;
		
	}
}
