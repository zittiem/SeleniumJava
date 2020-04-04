package utils.helper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.apache.log4j.Logger;

public class CryptionHelper {
	private static Logger logger = Logger.getLogger(DataHelper.class);

	public static String encryptText(String value) {
		byte[] encodedBytes = null;
		try {
			encodedBytes = Base64.getEncoder().encode(value.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("Crypto error: Unable to encrypt password");
		}
		return new String(encodedBytes);
	}

	public static String decryptText(String encryptedString) {
		return new String(Base64.getDecoder().decode(encryptedString), StandardCharsets.UTF_8);
	}
}
