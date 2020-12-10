package beans;

public class SpotBeans {
	private String spotId;
	private String spotName;
	private double latitude;//
	private double longitude; //
	private String genreId;
	private String spotImage;
	private String userId;


	public String getSpotId() {
		return spotId;
	}
	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}
	public String getSpotName() {
		return spotName;
	}
	public void setSpotName(String spotName) {
		this.spotName = spotName;
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
	public String getGenreId() {
		return genreId;
	}
	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}

	public String getSpotImage() {
		return spotImage;
	}
	public void setSpotImage(String spotImage) {
		this.spotImage = spotImage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


}
