package beans;

public class SpotBeans {
	private String spotId;
	private String spotName;
	private double ratitude;//�@�ܓx
	private double longitude; //�@�o�x
	private String genreId;
	private String filename;

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
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

}
