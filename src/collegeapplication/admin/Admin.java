package collegeapplication.admin;

import java.awt.Image;
import java.awt.image.BufferedImage;

import collegeapplication.common.ImageUtil;
import collegeapplication.common.ProfileImageUtil;

/*
 * Title : Admin.java
 * Created by : Ajaysinh Rathod
 * Purpose : To bind all data of admin
 * Mail : ajaysinhrathod1290@gmail.com
 */

public class Admin {
	private String website;
	private String contactnumber;
	private String emailid;
	private String collagename;
	private String password;
	private Image logoimage;
	private String facebook;
	private String instagram;
	private String twitter;
	private String lastlogin;
	private String linkedin;
	private String address;
	private boolean isactive = false;

	// --------- setters ---------
	public void setWebsite(String website) {
		this.website = website;
	}

	public void setContactNumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}

	public void setEmailId(String emailid) {
		this.emailid = emailid;
	}

	public void setCollageName(String collagename) {
		this.collagename = collagename;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// use shared helper instead of duplicating Toolkit logic
	public void setProfilePic(byte[] imagedata) {
		this.logoimage = ProfileImageUtil.fromBytes(imagedata);
	}

	public void setProfilePic(Image profilepic) {
		this.logoimage = profilepic;
	}

	public void setFaceBookLink(String facebooklink) {
		this.facebook = facebooklink;
	}

	public void setInstagramLink(String instagramlink) {
		this.instagram = instagramlink;
	}

	public void setLinkedinLink(String linkedinlink) {
		this.linkedin = linkedinlink;
	}

	public void setTwitterLink(String twitterlink) {
		this.twitter = twitterlink;
	}

	public void setLastLogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}

	public void setActiveStatus(boolean isactive) {
		this.isactive = isactive;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// --------- getters ---------
	public String getWebsite() {
		return website;
	}

	public String getContactNumber() {
		return contactnumber;
	}

	public String getEmailId() {
		return emailid;
	}

	public String getCollageName() {
		return collagename;
	}

	public String getPassword() {
		return password;
	}

	public boolean getActiveStatus() {
		return isactive;
	}

	public String getFacebookLink() {
		return facebook;
	}

	public String getInstagramLink() {
		return instagram;
	}

	public String getTwitterLink() {
		return twitter;
	}

	public String getLinkedinLink() {
		return linkedin;
	}

	public Image getProfilePic() {
		return logoimage;
	}

	// use shared helper instead of duplicating ImageIO + ByteArrayOutputStream
	public byte[] getProfilePicInBytes() {
		return ProfileImageUtil.toBytes(logoimage);
	}

	public Image getProfilePic(int width, int height) {
		return ProfileImageUtil.scaled(logoimage, width, height);
	}

	public BufferedImage getRoundedProfilePic(int width, int height, int radius) {
		return ProfileImageUtil.rounded(logoimage, width, height, radius);
	}

	public String getLastLogin() {
		return lastlogin;
	}

	public String getAddress() {
		return address;
	}
}
