package utilities;

import com.danco.training.TextFileWorker;

public class Loader {
	public static String[] load(String path) {
		return new TextFileWorker(path).readFromFile();
	}
}
