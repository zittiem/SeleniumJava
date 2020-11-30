package helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Utilities {
	public static String readFileFromFilePath(String filePath) throws IOException{
		Path path = Paths.get(filePath);
		String read = "";
		for (String line : Files.readAllLines(path)) {
			read += line;
		}
	    return read;	     
	}
}
