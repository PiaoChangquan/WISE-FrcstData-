package runRcode;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

//import GetDatafromCsv.GetData;

public class RunRCode {
	public static void MakeRCodeRun(String InputFileName,String OutputFileName) {
		// TODO Auto-generated method stub
		try {
			Process process = Runtime.getRuntime().exec("chmod +x predict.TYPE2.R");
			InputStreamReader ir1 = new InputStreamReader(process.getInputStream());
			LineNumberReader input1 = new LineNumberReader(ir1);

			// Run R code  Use RF and Cart  
			Process p = Runtime.getRuntime().exec("./predict.TYPE2.R "+InputFileName+" cart.TYPE2.Rdata " + "RF"+OutputFileName); 
			Process p2 = Runtime.getRuntime().exec("./predict.TYPE2.R "+InputFileName+" rf.TYPE2.Rdata " + "Cart"+OutputFileName); 
			
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
//			Create I/O Line for Print output  
			LineNumberReader input = new LineNumberReader(ir); 
			
			InputStreamReader ir2 = new InputStreamReader(p2.getInputStream());
			LineNumberReader input2 = new LineNumberReader(ir2); 

//			Print output by line
			String line;
			while ((line = input.readLine()) != null) {
				System.out.println(line);
			}
			String line1;
			while ((line1 = input1.readLine()) != null) {
				System.out.println(line1);
			}
			String line2;
			while ((line2 = input2.readLine()) != null) { 
				System.out.println(line2);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
