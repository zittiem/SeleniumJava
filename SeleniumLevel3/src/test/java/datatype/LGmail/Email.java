package datatype.LGmail;

import utils.helper.RandomHelper;

public class Email {

	private String to = null;
	private String ccList = null;
	private String subject = null;
	private String attachedFiles = null;
	private String insertedPictures = null;
	private String content = null;

	@Override
	public String toString() {
		return "EmailInfo [to=" + to + ", ccList=" + ccList + ", subject=" + subject + ", attachedFiles="
				+ attachedFiles + ", insertedPictures=" + insertedPictures + ", content=" + content + "]";
	}

	public void showInfo() {
		System.out.println(this.toString());
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCcList() {
		return ccList;
	}

	public void setCcList(String ccList) {
		this.ccList = ccList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getAttachedFiles() {
		return attachedFiles;
	}

	public void setAttachedFiles(String attachedFiles) {
		this.attachedFiles = attachedFiles;
	}

	public String getInsertedPictures() {
		return insertedPictures;
	}

	public void setInsertedPictures(String insertedPictures) {
		this.insertedPictures = insertedPictures;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Email compileData() {
		if (getSubject().equals("[Random]")) {
			setSubject(RandomHelper.getRandomString("Subject-"));
		}
		if (getContent().equals("[Random]")) {
			setContent("Welcome to LogiGear - Selenium Test");

		}
		return this;
	}

}
