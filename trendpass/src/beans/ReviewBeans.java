package beans;

public class ReviewBeans {
	private int spotId;
	private int reviewNumber;
	private String reviewContent;
	private int evaluation;
	private String reviewImage;
	private int userId;

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

}
