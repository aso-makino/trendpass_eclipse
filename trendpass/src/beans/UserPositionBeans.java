package beans;

import java.util.Date;

public class UserPositionBeans {
	private String userId;
	private double latitude;//　緯度
	private double longitude;//　経度
	private Date stayStart;
	private long stayEnd;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Date getStayStart() {
		return stayStart;
	}
	public void setStayStart(Date stayStart) {
		this.stayStart = stayStart;
	}
	public long getStayEnd() {
		return stayEnd;
	}
	public void setStayEnd(long stayEnd) {
		this.stayEnd = stayEnd;
	}

}
