package datatype.LGmail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import utils.helper.RandomHelper;

public class Email {

	private static final String IMAGE_PREFIX = "image=";
	
	private String to = null;
	private String ccList = null;
	private String subject = null;
	private String[] attachFiles = null;
	private String[] insertedImages = null;
	private String[] content = null;

	public void showInfo() {
		System.out.println(this.toString());
	}

	public Email compileData() {
		if (subject.equals("[Random]")) {
			subject = RandomHelper.getRandomString("Subject-");
		}
		if(content != null) {
			List<String> images = new ArrayList<String>();
			for (int i = 0; i < content.length; i++) {
				if (content[i].equals("[Random]")) {
					content[i] = "Welcome to LogiGear - Selenium Test";
				}
				if (isImage(content[i]))
				{
					images.add(getImageName(content[i]));
				}
			}
			if (images.size() > 0) {
				insertedImages = images.toArray(new String[images.size()]);
			}
		}
		return this;
	}

	/**
	 * @return the to
	 */
	public String getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(String to) {
		this.to = to;
	}

	/**
	 * @return the ccList
	 */
	public String getCcList() {
		return ccList;
	}

	/**
	 * @param ccList the ccList to set
	 */
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the attachFiles
	 */
	public String[] getAttachFiles() {
		return attachFiles;
	}

	/**
	 * @param attachFiles the attachFiles to set
	 */
	public void setAttachFiles(String[] attachFiles) {
		this.attachFiles = attachFiles;
	}
	
	/**
	 * @return the attachFiles
	 */
	public String getAttachFilesAsString() {
		if (attachFiles != null)
			return Arrays.toString(attachFiles);
		return null;
	}
	
	/**
	 * @return the insertedImages
	 */
	public String[] getInsertedImages() {
		return insertedImages;
	}

	/**
	 * @param insertedImages the insertedImages to set
	 */
	public void setInsertedImages(String[] insertedImages) {
		this.insertedImages = insertedImages;
	}

	/**
	 * @return the content
	 */
	public String[] getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String[] content) {
		this.content = content;
	}
	
	/**
	 * @return the content as String
	 */
	public String getContentAsString() {
		String contentAsString = "";
		for (String item : content) {
			if (!isImage(item))
			{
				contentAsString += item;
			}
		}
		return contentAsString;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Email [to=" + to + ", ccList=" + ccList + ", subject=" + subject + ", attachFiles="
				+ Arrays.toString(attachFiles) + ", content=" + Arrays.toString(content) + "]";
	}
	
	public static boolean isImage(String item)
	{
		if (StringUtils.startsWithIgnoreCase(item, IMAGE_PREFIX))
		{
			return true;
		}
		return false;
	}
	
	public static String getImageName(String item)
	{
		return item.substring(IMAGE_PREFIX.length());
	}
}
