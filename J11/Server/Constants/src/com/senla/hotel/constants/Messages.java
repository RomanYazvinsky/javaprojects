package com.senla.hotel.constants;

public enum Messages {
	ASK_FOR_ORDER_FILE("Enter the orders file: "), ASK_FOR_CLIENT_FILE(
			"Enter the clients file: "), ASK_FOR_SERVICE_FILE("Enter the managers file: "), ASK_FOR_ROOM_FILE(
					"Enter the rooms file: "), ASK_FOR_DATE("Enter the current date (d m y): "), ASK_FOR_CLIENT_PARAMS(
							"Client parameters: (name)"), NO_LOG_FILE_FOUND("Log file is not found"), ERROR(
									"Error. See the log file"), ASK_FOR_ROOM_PARAMS(
											"Room parameters: (<number>, <capacity>, <stars>, <price per day>)"), ASK_FOR_ORDER_PARAMS(
													"Order parameters: (<date from>, <date to>)"), ASK_FOR_SERVICE_PARAMS(
															"Service parameters: (<price>, <name>, <date>)");

	private String message;

	private Messages(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return message;
	}
}
