package beans;

public class SpotBeans {
	private int spotId;
	private String spotName;
	private String spotReview;
	private double ratitude;//
	private double longitude; //
	private int genreId;
	private String filename;
	private int ratingBar;
	private String error;//テスト用変数、あとで消す

	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getSpotId() {
		return spotId;
	}
	public void setSpotId(int spotId) {
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
	public int getGenreId() {
		return genreId;
	}
	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	public int getRatingBar() {
		return ratingBar;
	}
	public void setRatingBar(int ratingBar) {
		this.ratingBar = ratingBar;
	}
	public String getSpotReview() {
		return spotReview;
	}
	public void setSpotReview(String spotReview) {
		this.spotReview = spotReview;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}

}
