package com.senla.hotel.utilities;

import com.danco.training.TextFileWorker;

public class Saver {
	public static void save(String path, String[] data) {
		new TextFileWorker(path).writeToFile(data);
	}
}
