package beans;

public class SpotReviewBeans {

	//　infomation of spot
	private String spotId;
	private String spotName;
	private double ratitude;//　緯度
	private double longitude; //　経度
	private String genreId;

	// infomation of review
	private String reviewNumber;
	private String reviewContent;
	private int evaluation;
	private String reviewImage;
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
	public String getReviewNumber() {
		return reviewNumber;
	}
	public void setReviewNumber(String reviewNumber) {
		this.reviewNumber = reviewNumber;
	}
	public String getReviewContent() {
		return reviewContent;
	}
	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}
	public int getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
	public String getReviewImage() {
		return reviewImage;
	}
	public void setReviewImage(String reviewImage) {
		this.reviewImage = reviewImage;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
