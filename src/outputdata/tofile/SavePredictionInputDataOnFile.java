package outputdata.tofile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class SavePredictionInputDataOnFile {
//	static String path = "PredictionInputData.txt";
	static File f; 
	public static void OpenOutputFile(String inputFileName) throws FileNotFoundException{
		File f  = new File(inputFileName);
		PrintWriter out = new PrintWriter(new FileOutputStream(f, false));
		out.println("Hum5,Raf6,RfD29,Tav15,Tmi2,Tmx19,LandUse");

		out.close();
	}

	
	public  SavePredictionInputDataOnFile(String inputFileName, PreInputData[] pre) throws FileNotFoundException   {
		// TODO Auto-generated method stub
		File f  = new File(inputFileName);
		PrintWriter out = new PrintWriter(new FileOutputStream(f, true));

		File logFile  = new File("log"+inputFileName);
		PrintWriter logFileOut = new PrintWriter(new FileOutputStream(logFile, true));
		for(int i =0;i<pre.length;i++){
			out.println(pre[i].toString());
			logFileOut.println(pre[i].toString());
		}
		out.close();
		logFileOut.close();
	}
}
