package beans;

public class ReviewBeans {
	private String spotId;
	private String reviewNumber;
	private String reviewContent;
	private String evaluation;
	private String reviewImage;
	private String userId;
	private double ratitude;
	private double longitude;


	public String getSpotId() {
		return spotId;
	}
	public void setSpotId(String spotId) {
		this.spotId = spotId;
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
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
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

}
