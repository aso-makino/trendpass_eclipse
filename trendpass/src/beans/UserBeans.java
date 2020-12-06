package beans;

import java.awt.image.BufferedImage;

public class UserBeans {
	private int userId;
	private String userName;
	private BufferedImage userIcon;
	private String mail;
	private String sex;
	private int birth;

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
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
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getBirth() {
		return birth;
	}
	public void setBirth(int birth) {
		this.birth = birth;
	}

}
