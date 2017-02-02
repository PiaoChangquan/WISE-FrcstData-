package getdata.fromweb;

public class Item {

	private String baseDate;
	private String baseTime;
	private String category;
	private String fcstDate;
	private String fcstTime;
	private double fcstValue;
	private String nx;
	private String ny;

	public Item() {
	}

	public String getBaseDate() {
		return baseDate;
	}

	public void setBaseDate(String baseDate) {
		this.baseDate = baseDate;
	}

	public String getBaseTime() {
		return baseTime;
	}

	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getNx() {
		return nx;
	}

	public void setNx(String nx) {
		this.nx = nx;
	}

	public String getNy() {
		return ny;
	}

	public void setNy(String ny) {
		this.ny = ny;
	}

	public String getFcstDate() {
		return fcstDate;
	}

	public void setFcstDate(String fcstDate) {
		this.fcstDate = fcstDate;
	}

	public String getFcstTime() {
		return fcstTime;
	}

	public void setFcstTime(String fcstTime) {
		this.fcstTime = fcstTime;
	}

	public double getFcstValue() {
		return fcstValue;
	}

	public void setFcstValue(double fcstValue) {
		this.fcstValue = fcstValue;
	}

	@Override
	public String toString() {
		return "Item [baseDate=" + baseDate + ", baseTime=" + baseTime + ", category=" + category + ", fcstDate="
				+ fcstDate + ", fcstTime=" + fcstTime + ", fcstValue=" + fcstValue + ", nx=" + nx + ", ny=" + ny + "]";
	}

}
