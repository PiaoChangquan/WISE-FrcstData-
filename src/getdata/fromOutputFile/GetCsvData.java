package getdata.fromOutputFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import getdata.fromDB.AWSInfo;

public class GetCsvData {

	public static MosqData[] GetDataFromCsv(AWSInfo[] awsInfo, String OutputFileName) throws IOException {
		try {

			BufferedReader reader = new BufferedReader(new FileReader(OutputFileName));
			reader.readLine();
			String line = null;
			int count = 0;

			MosqData[] MosqData = new MosqData[awsInfo.length * 3];
			while ((line = reader.readLine()) != null) {

				String item[] = line.split(",");

				MosqData[count] = new MosqData();
				MosqData[count].setEvals(Double.valueOf(item[0]));
				MosqData[count].setProb(Double.valueOf(item[1]));

				System.out.println(" count : " + count + " " + MosqData[count++].toString());
			}
			// System.out.println("count = "+(count++)+"
			// "+MosqData[0].toString());
			reader.close();
			return MosqData;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}