package utils;

import java.io.File;

public class DeleteImageFile {

	//@•¡”‰æ‘œíœ
	public static void delete(String[] fileNames) {
		for(String fileName : fileNames) {
			File file = new File("C:/Users/neco2/output_imgfile/" + fileName);
			file.delete();
		}
	}

	//@’P‘Ì‰æ‘œíœ
	public static void delete(String fileName) {
		File file = new File("C:/Users/neco2/output_imgfile/" + fileName);
		file.delete();
	}
}
