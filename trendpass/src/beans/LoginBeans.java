package beans;

import java.awt.image.BufferedImage;

public class LoginBeans {
	private String userId;
	private String userName;
	private BufferedImage userIcon;

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BufferedImage getUserIcon() {
		return userIcon;
	}
	public void setUserIcon(BufferedImage userIcon) {
		this.userIcon = userIcon;
	}

}
