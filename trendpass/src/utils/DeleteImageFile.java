package utils;

import java.io.File;

public class DeleteImageFile {

	//�@�����摜�폜
	public static void delete(String[] fileNames) {
		for(String fileName : fileNames) {
			File file = new File("C:/Users/neco2/output_imgfile/" + fileName);
			file.delete();
		}
	}

	//�@�P�̉摜�폜
	public static void delete(String fileName) {
		File file = new File("C:/Users/neco2/output_imgfile/" + fileName);
		file.delete();
	}
}
