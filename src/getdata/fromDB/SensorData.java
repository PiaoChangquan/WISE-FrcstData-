package getdata.fromDB;

public class SensorData {

//	int ValueID;
	String streamID;
	String dateTime;
	double value;
//	public int getValueID() {
//		return ValueID;
//	}
//	public void setValueID(int valueID) {
//		ValueID = valueID;
//	}
	
	public String getStreamID() {
		return streamID;
	}
	public void setStreamID(String streamID) {
		this.streamID = streamID;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	
}
