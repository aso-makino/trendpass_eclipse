package beans;

public class ReviewBeans {
	private int spotId;
	private int reviewNumber;
	private String reviewTitle;
	private String reviewContent;
	private int evaluation;
	private String reviewImage;
	private int userId;
	private double ratitude;
	private double longitude;
	private String error,error2,error3;//テスト用変数、あとで消す


	public int getSpotId() {
		return spotId;
	}
	public void setSpotId(int spotId) {
		this.spotId = spotId;
	}
	public int getReviewNumber() {
		return reviewNumber;
	}
	public void setReviewNumber(int reviewNumber) {
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
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getReviewTitle() {
		return reviewTitle;
	}
	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
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
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getError2() {
		return error2;
	}
	public void setError2(String error2) {
		this.error2 = error2;
	}
	public String getError3() {
		return error3;
	}
	public void setError3(String error3) {
		this.error3 = error3;
	}

}
