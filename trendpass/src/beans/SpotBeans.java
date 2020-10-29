package beans;

public class SpotBeans {
	private String spotId;
	private String spotName;
	private double ratitude;//　緯度
	private double longitude; //　経度
	private String genreId;
	private String spotImage;

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
	public double getRatitude() {
		return ratitude;
	}
	public void setRatitude(double ratitude) {
		this.ratitude = ratitude;
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

}
