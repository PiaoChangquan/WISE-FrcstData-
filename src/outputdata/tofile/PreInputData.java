package outputdata.tofile;

public class PreInputData {

	private String Hum5;
	private String Raf6;
	private String RfD29;
	private String Tav15;
	private String Tmi2;
	private String Tmx19;
	private String Mosq;
	private String LandUse;
	public String getHum5() {
		return Hum5;
	}
	public void setHum5(String hum5) {
		Hum5 = hum5;
	}
	public String getRaf6() {
		return Raf6;
	}
	public void setRaf6(String raf6) {
		Raf6 = raf6;
	}
	public String getRfD29() {
		return RfD29;
	}
	public void setRfD29(String rfD29) {
		RfD29 = rfD29;
	}
	public String getTav15() {
		return Tav15;
	}
	public void setTav15(String tav15) {
		Tav15 = tav15;
	}
	public String getTmi2() {
		return Tmi2;
	}
	public void setTmi2(String tmi2) {
		Tmi2 = tmi2;
	}
	public String getTmx19() {
		return Tmx19;
	}
	public void setTmx19(String tmx19) {
		Tmx19 = tmx19;
	}
	public String getMosq() {
		return Mosq;
	}
	public void setMosq(String mosq) {
		Mosq = mosq;
	}
	public String getLandUse() {
		return LandUse;
	}
	public void setLandUse(String landUse) {
		LandUse = landUse;
	}
	@Override
	public String toString() {
		return Hum5 + "," + Raf6 + "," + RfD29 + "," + Tav15 + "," + Tmi2 + "," + Tmx19 + "," + LandUse;
//		return Hum5 + "," + Raf6 + "," + RfD29 + "," + Tav15 + "," + Tmi2 + "," + Tmx19 + "," + LandUse  + "," + Mosq ;
	}
//	public String toString() {
//		return Hum5 + "," + Raf6 + "," + Tav15 + "," + Tmi2 + "," + Tmx19 + ","  + Mosq ;
//	}
	
	
	
}
