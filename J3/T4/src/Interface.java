
public class Interface {

	private static void printStringArray(String[] stringArray) {
		System.out.println();
		for (String patientToDoctor : stringArray) {
			System.out.println(patientToDoctor);
		}
		System.out.println();
	}
	
	public static void addPatient(Registry registry, Patient patient) {
		registry.addPatient(patient);
		System.out.println(patient.getName() + (" successfully registred as patient"));
	}

	public static void addDoctor(Registry registry, Doctor doctor) {
		registry.addDoctor(doctor);
		StringBuilder result = new StringBuilder();
		System.out.println(result.append(doctor.getName()).append(" successfully registred as doctor"));
	}
	
	public static void addTicket(Registry registry, Patient patient, Doctor doctor) {
		System.out.println(SafeInterface.addTicketResult(registry, patient, doctor));
	}
	
	public static void deleteTicket(Registry registry, Patient patient, Doctor doctor) {
		System.out.println(SafeInterface.deleteTicketResult(registry, patient, doctor));
	}

	public static void printAllPatients(Registry registry) {
		printStringArray(SafeInterface.allPatientsResult(registry));
	}
	
	public static void printAllDoctors(Registry registry) {
		printStringArray(SafeInterface.allDoctorsResult(registry));
	}
	
	public static void printAllTickets(Registry registry) {
		printStringArray(SafeInterface.allTicketsResult(registry));
	}
	
	public static void printPatientOfDoctorCount(Registry registry, Doctor doctor) {
		StringBuilder result = new StringBuilder();
		System.out.println(result.append("Doctor ").append(doctor.getName()).append(" has ")
				.append(Integer.toString(registry.findPatientsOfDoctor(doctor))).append(" patients"));
	}

	public static void printNumberOfDoctors(Registry registry) {
		System.out.println("Number of doctors is: " + (Integer.toString(registry.getDoctorCount())));
	}

	public static void printNumberOfPatients(Registry registry) {
		System.out.println("Number of patients is: " + (Integer.toString(registry.getPatientCount())));
	}
}
