package beans;

public class UserPositionBeans {
	private String userId;
	private double latitude;//�@�ܓx
	private double longitude;//�@�o�x
	private long stayStart;
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
	public long getStayStart() {
		return stayStart;
	}
	public void setStayStart(long stayStart) {
		this.stayStart = stayStart;
	}
	public long getStayEnd() {
		return stayEnd;
	}
	public void setStayEnd(long stayEnd) {
		this.stayEnd = stayEnd;
	}

}
