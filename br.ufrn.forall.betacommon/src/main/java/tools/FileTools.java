package tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileTools {

	public static String getFileContent(File file) {
		StringBuffer fileContent = new StringBuffer("");
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String line = "";
			while((line = br.readLine()) != null) {
				fileContent.append(line + "\n");
			}
			return fileContent.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent.toString();
	}

	public static File createFileWithContent(String path, String content) {
		File file = new File(path);
		
		try {
			if(file.createNewFile()) {
				FileWriter writer = new FileWriter(file);
				writer.append(content);
				writer.close();
			} else {
				file.delete();
				file.createNewFile();
				FileWriter writer = new FileWriter(file);
				writer.append(content);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}

}
