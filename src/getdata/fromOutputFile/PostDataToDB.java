package getdata.fromOutputFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;

import getdata.fromDB.AWSInfo;

public class PostDataToDB {
	static String streamID;
	static String datatime;
	static String value;

	public static void MapPostData(AWSInfo[] awsInfo, String outputFileName) throws IOException, InterruptedException {

		MosqData[] MosqData = GetCsvData.GetDataFromCsv(awsInfo, outputFileName);

		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();

		long currTime = date.getTime();
		long todayTime = currTime + 1 * 24 * 60 * 60 * 1000;
		long today1Time = currTime + 2 * 24 * 60 * 60 * 1000;
		long today2Time = currTime + 3 * 24 * 60 * 60 * 1000;
		try {

			if (outputFileName.equals("Cart1PredictionOutputData.csv")) {
				int couut = 0;
				for (int i = 0; i < awsInfo.length; i++) {

					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayLandUse1(),
							new SimpleDateFormat("yyyy-MM-dd").format(todayTime), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayPlus1LandUse1(),
							new SimpleDateFormat("yyyy-MM-dd").format(today1Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayPlus2LandUse1(),
							new SimpleDateFormat("yyyy-MM-dd").format(today2Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					// System.out.println(couut);
					// System.out.println(awsInfo[i].getPostUrl()+""+awsInfo[i].getCartTodayPlus2LandUse1()+"
					// "+ new SimpleDateFormat("yyyy-MM-dd").format(today2Time)
					// +" "+MosqData[couut].getEvals()+""+
					// MosqData[couut++].getProb()+"");
				}
				System.out.println("++++++++++++++++++++++++++++++++cart1PredictionOutputData.csv");
			} else if (outputFileName.equals("Cart2PredictionOutputData.csv")) {
				int couut = 0;
				for (int i = 0; i < awsInfo.length; i++) {

					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayLandUse2(),
							new SimpleDateFormat("yyyy-MM-dd").format(todayTime), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayPlus1LandUse2(),
							new SimpleDateFormat("yyyy-MM-dd").format(today1Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayPlus2LandUse2(),
							new SimpleDateFormat("yyyy-MM-dd").format(today2Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");

				}
				System.out.println("++++++++++++++++++++++++++++++++cart2PredictionOutputData.csv");
			} else if (outputFileName.equals("Cart3PredictionOutputData.csv")) {
				int couut = 0;
				for (int i = 0; i < awsInfo.length; i++) {

					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayLandUse3(),
							new SimpleDateFormat("yyyy-MM-dd").format(todayTime), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayPlus1LandUse3(),
							new SimpleDateFormat("yyyy-MM-dd").format(today1Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getCartTodayPlus2LandUse3(),
							new SimpleDateFormat("yyyy-MM-dd").format(today2Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					// System.out.println(couut);

				}
				System.out.println("++++++++++++++++++++++++++++++++++cart3PredictionOutputData");
			} else if (outputFileName.equals("RF1PredictionOutputData.csv")) {
				int couut = 0;
				for (int i = 0; i < awsInfo.length; i++) {

					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayLandUse1(),
							new SimpleDateFormat("yyyy-MM-dd").format(todayTime), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayPlus1LandUse1(),
							new SimpleDateFormat("yyyy-MM-dd").format(today1Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayPlus2LandUse1(),
							new SimpleDateFormat("yyyy-MM-dd").format(today2Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					System.out.println(couut);

				}
				System.out.println("=============================RF1PredictionOutputData");
			} else if (outputFileName.equals("RF2PredictionOutputData.csv")) {
				int couut = 0;
				for (int i = 0; i < awsInfo.length; i++) {

					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayLandUse2(),
							new SimpleDateFormat("yyyy-MM-dd").format(todayTime), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayPlus1LandUse2(),
							new SimpleDateFormat("yyyy-MM-dd").format(today1Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayPlus2LandUse2(),
							new SimpleDateFormat("yyyy-MM-dd").format(today2Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					// System.out.println(couut);

				}
				System.out.println("==============================RF2PredictionOutputData");
			} else if (outputFileName.equals("RF3PredictionOutputData.csv")) {
				int couut = 0;
				for (int i = 0; i < awsInfo.length; i++) {

					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayLandUse3(),
							new SimpleDateFormat("yyyy-MM-dd").format(todayTime), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayPlus1LandUse3(),
							new SimpleDateFormat("yyyy-MM-dd").format(today1Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					PostDatatoDataBase(awsInfo[i].getPostUrl(), awsInfo[i].getRFTodayPlus2LandUse3(),
							new SimpleDateFormat("yyyy-MM-dd").format(today2Time), MosqData[couut].getEvals() + "",
							MosqData[couut++].getProb() + "");
					// System.out.println(couut);

				}
				System.out.println("===================================RF3PredictionOutputData");
			}
		
		} catch (NullPointerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Today's program is over");
	}

	private static void PostDatatoDataBase(String PostUrl, String streamID, String datatime, String value,
			String Probability) throws IOException {
		// TODO Auto-generated method stub
		ObjectMapper mapper = new ObjectMapper();
		System.out
				.println("ID:" + streamID + " time: " + datatime + " value: " + value + " Probability :" + Probability);

		PostData Postdata = new PostData();
		Postdata.setStreamID(streamID);
		Postdata.setDateTime(datatime + " 00:00:00");
		Postdata.setValue(value);
		Postdata.setProbability(Probability);

		String jsonInString = mapper.writeValueAsString(Postdata);
		System.out.println(jsonInString);
		try {
			URL targetUrl = new URL(PostUrl);
			System.out.println(PostUrl);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			// httpConnection.setRequestProperty("Accept", "application/json");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(jsonInString.getBytes());
			outputStream.flush();

			if (httpConnection.getResponseCode() != 201) {
				throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
			}

			BufferedReader responseBuffer = new BufferedReader(
					new InputStreamReader((httpConnection.getInputStream())));

			String output;
			// System.out.println("Output from Server:\n");
			while ((output = responseBuffer.readLine()) != null) {
				System.out.println(output);
			}

			httpConnection.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
}
