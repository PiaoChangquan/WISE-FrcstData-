package getdata.fromDB;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import ComputeMethod.ComputingMethod;
import getdata.fromOutputFile.GetCsvData;
import getdata.fromOutputFile.PostDataToDB;
import getdata.fromweb.ForecastData;
import outputdata.tofile.PreInputData;
import outputdata.tofile.SavePredictionInputDataOnFile;
import runRcode.RunRCode;

public class CombinationData {
	private static String filePath = "./WISEconf.json";
	static java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");

	public static void GetTheAllData(ForecastData[] forecastDataArray)
			throws JsonParseException, JsonMappingException, IOException, InterruptedException, URISyntaxException {
		// TODO Auto-generated method stub
		try {

			AWSInfo[] awsInfo = Configure.GetAWSInfo(filePath);

			for (int y = 1; y < 4; y++) {

				String InputFileName = y + "PredictionInputData.csv";
				String OutputFileName = y + "PredictionOutputData.csv";
				SavePredictionInputDataOnFile.OpenOutputFile(InputFileName);

				double PreSum = 0;
				double PreSum1 = 0;
				double PreSum2 = 0;

				double PreCount = 0;
				double PreCount1 = 0;
				double PreCount2 = 0;

				double HumSum = 0;
				double HumSum1 = 0;
				double HumSum2 = 0;

				double TmxSum = 0;
				double TmxSum1 = 0;
				double TmxSum2 = 0;

				double TmiSum = 0;
				double TmiSum1 = 0;
				double TmiSum2 = 0;

				double TavSum = 0;
				double TavSum1 = 0;
				double TavSum2 = 0;

				for (int i = 0; i < awsInfo.length; i++) {

					PreInputData[] PreInputData = new PreInputData[awsInfo.length];

					if (forecastDataArray[i].getNxNy().equals(awsInfo[i].getNx() + "" + awsInfo[i].getNy())) {

						PreInputData[0] = new PreInputData();
						PreInputData[1] = new PreInputData();
						PreInputData[2] = new PreInputData();
						List<SensorData> PredataList = GetPastData.GetPastData(awsInfo[i].getGetUrl(),
								awsInfo[i].getPre());
						List<SensorData> TmxdataList = GetPastData.GetPastData(awsInfo[i].getGetUrl(),
								awsInfo[i].getTmax());
						List<SensorData> TmidataList = GetPastData.GetPastData(awsInfo[i].getGetUrl(),
								awsInfo[i].getTmin());
						List<SensorData> HumdataList = GetPastData.GetPastData(awsInfo[i].getGetUrl(),
								awsInfo[i].getHum());
						List<SensorData> TavdataList = GetPastData.GetPastData(awsInfo[i].getGetUrl(),
								awsInfo[i].getTav());

						PreSum = ComputingMethod.ComputeSumForecastData(PredataList, 6);
						PreSum1 = ComputingMethod.ComputeSumForecastData(PredataList, 5);
						PreSum2 = ComputingMethod.ComputeSumForecastData(PredataList, 4);

						PreCount = ComputingMethod.ComputeCount(PredataList, 29);
						PreCount1 = ComputingMethod.ComputeCount(PredataList, 28);
						PreCount2 = ComputingMethod.ComputeCount(PredataList, 27);

						HumSum = ComputingMethod.ComputeSumForecastData(HumdataList, 5);
						HumSum1 = ComputingMethod.ComputeSumForecastData(HumdataList, 4);
						HumSum2 = ComputingMethod.ComputeSumForecastData(HumdataList, 3);

						TmxSum = ComputingMethod.ComputeSumForecastData(TmxdataList, 19);
						TmxSum1 = ComputingMethod.ComputeSumForecastData(TmxdataList, 18);
						TmxSum2 = ComputingMethod.ComputeSumForecastData(TmxdataList, 17);

						TmiSum = ComputingMethod.ComputeSumForecastData(TmidataList, 2);
						TmiSum1 = ComputingMethod.ComputeSumForecastData(TmidataList, 1);
						TmiSum2 = ComputingMethod.ComputeSumForecastData(TmidataList, 0);

						TavSum = ComputingMethod.ComputeSumForecastData(TavdataList, 5);
						TavSum1 = ComputingMethod.ComputeSumForecastData(TavdataList, 4);
						TavSum2 = ComputingMethod.ComputeSumForecastData(TavdataList, 3);

						PreInputData[0].setHum5(df.format(HumSum));
						PreInputData[0].setRfD29(df.format(PreCount));
						PreInputData[0].setRaf6(df.format(PreSum));
						PreInputData[0].setTav15(df.format(TavSum));
						PreInputData[0].setTmx19(df.format(TmxSum));
						PreInputData[0].setTmi2(df.format(TmiSum));
						PreInputData[0].setLandUse(df.format(y));
						PreInputData[0].setMosq("0");

						PreInputData[1].setHum5(df.format(HumSum1 + forecastDataArray[i].getTodayHum()));
						PreInputData[1].setRaf6(df.format(PreSum1 + forecastDataArray[i].getTodayRaf()));
						if (forecastDataArray[i].getTodayRaf() > 0) {
							PreInputData[1].setRfD29(df.format(PreCount1 + 1));
							PreInputData[2].setRfD29(df.format(PreCount2 + 1));
						} else {
							PreInputData[1].setRfD29(df.format(PreCount1));
							PreInputData[2].setRfD29(df.format(PreCount2));
						}
						PreInputData[1].setTav15(df.format(TavSum1 + forecastDataArray[i].getTodayTav()));
						PreInputData[1].setTmx19(df.format(TmiSum1 + forecastDataArray[i].getTodayTmi()));
						PreInputData[1].setTmi2(df.format(TmxSum1 + forecastDataArray[i].getTodayTmx()));
						PreInputData[1].setLandUse(df.format(y));
						PreInputData[1].setMosq("0");

						PreInputData[2].setHum5(df.format(
								HumSum2 + forecastDataArray[i].getTodayHum() + forecastDataArray[i].getTomorryHum()));
						PreInputData[2].setRaf6(df.format(
								PreSum2 + forecastDataArray[i].getTodayRaf() + forecastDataArray[i].getTomorryRaf()));
						if (forecastDataArray[i].getTomorryRaf() > 0) {
							PreInputData[2].setRfD29(df.format(Double.parseDouble(PreInputData[2].getRfD29()) + 1));
						} else {
							PreInputData[2].setRfD29(PreInputData[2].getRfD29());
						}
						PreInputData[2].setRfD29(df.format(PreCount2));
						PreInputData[2].setTav15(df.format(TavSum2 + forecastDataArray[i].getTomorryTav()));
						PreInputData[2].setTmx19(df.format(
								TmiSum2 + forecastDataArray[i].getTodayTmi() + forecastDataArray[i].getTomorryTmi()));
						PreInputData[2].setTmi2(df.format(
								TmxSum2 + forecastDataArray[i].getTodayTmx() + forecastDataArray[i].getTomorryTmx()));
						PreInputData[2].setLandUse(df.format(y));
						PreInputData[2].setMosq("0");

						new SavePredictionInputDataOnFile(InputFileName, PreInputData);
					}

				}
				RunRCode.MakeRCodeRun(InputFileName, OutputFileName);

				GetCsvData.GetDataFromCsv(awsInfo, "RF" + OutputFileName);
				GetCsvData.GetDataFromCsv(awsInfo, "Cart" + OutputFileName);

				PostDataToDB.MapPostData(awsInfo, "RF" + OutputFileName);
				PostDataToDB.MapPostData(awsInfo, "Cart" + OutputFileName);
			}

			// for (int x = 1; x <= 3; x++) {
			//
			// String InputFileName = x + "PredictionInputData.csv";
			// String OutputFileName = x + "PredictionOutputData.csv";
			// RunRCode.MakeRCodeRun(InputFileName, OutputFileName);
			// // Thread.sleep(200000);
			//
			// GetCsvData.GetDataFromCsv(awsInfo, "RF" + OutputFileName);
			// GetCsvData.GetDataFromCsv(awsInfo, "Cart" + OutputFileName);
			//
			// PostDataToDB.MapPostData(awsInfo, "RF" + OutputFileName);
			// PostDataToDB.MapPostData(awsInfo, "Cart" + OutputFileName);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
