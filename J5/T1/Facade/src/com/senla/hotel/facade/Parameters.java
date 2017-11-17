package com.senla.hotel.facade;

public class Parameters {
	private static String[] paths;

	public static Boolean isConfigured() {
		return  paths != null && paths.length == 4;
	}
	
	public static String[] getPaths() {
		return paths;
	}

	public static void setPaths(String[] paths) {
		Parameters.paths = paths;
	}


}
