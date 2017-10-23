
public class Printer {

	private static void printStringArray(String[] stringArray) {
		System.out.println();
		for (String patientToDoctor : stringArray) {
			System.out.println(patientToDoctor);
		}
		System.out.println();
	}

	public static void printAddPatient(Boolean isSuccesful) {
		if (isSuccesful) {
			System.out.println("Patient successfully registred");
		} else {
			System.out.println("Patient is already registred");
		}
	}

	public static void printAddDoctor(Boolean isSuccesful) {
		if (isSuccesful) {
			System.out.println("Doctor successfully registred");
		} else {
			System.out.println("Doctor is already registred");
		}
	}

	public static void printPatientOfDoctorCount(int patientCount) {
		if (patientCount == -1) {
			System.out.println("This doctor is not registred or does not has any patients");
		} else {
			StringBuilder result = new StringBuilder();
			System.out.println(result.append("Doctor has ").append(Integer.toString(patientCount)).append(" patients"));
		}
	}

	public static void printNumberOfDoctors(int doctorCount) {
		System.out.println("Number of doctors is: " + (Integer.toString(doctorCount)));
	}

	public static void printNumberOfPatients(int patientCount) {
		System.out.println("Number of patients is: " + (Integer.toString(patientCount)));
	}

	public static void printAllPatients(String[] allPatients) {
		if (allPatients == null) {
			System.out.println("No patients registred");
		} else {
			printStringArray(allPatients);
		}
	}

	public static void printAllDoctors(String[] allDoctors) {
		if (allDoctors == null) {
			System.out.println("No doctors registred");
		} else {
			printStringArray(allDoctors);
		}
	}

	public static void printAllTickets(Ticket[] allTickets) {
		if (allTickets == null) {
			System.out.println("No tickets registred");
		} else {
			StringBuilder result = new StringBuilder();
			for (Ticket ticket : allTickets) {
				System.out.println(result.append(ticket.getPatient()).append(" to ").append(ticket.getDoctor()));
				result.delete(0, result.length());
			}
		}
	}

	public static void printAddTicket(int result) {
		if (result > 0) {
			System.out.println("Ticket succesfully registred");
		}
		if (result == 0) {
			System.out.println("This ticket is already registred");
		}
		if (result == -1) {
			System.out.println("Patient isn't registred");
		}
		if (result == -2) {
			System.out.println("Doctor isn't registred");
		}
		if (result == -3) {
			System.out.println("No patients or doctors registred");
		}
	}

	public static void printDeleteTicket(Boolean result) {
		if (result) {
			System.out.println("Deleted successful");
		} else {
			System.out.println("Not found");
		}
	}

}
