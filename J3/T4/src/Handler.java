
public class Handler {

	public static void addPatient(Registry registry, Patient patient) {
		registry.addPatient(patient);
		System.out.println(patient.getName() + " successfully registred as patient");
	}

	public static void addDoctor(Registry registry, Doctor doctor) {
		registry.addDoctor(doctor);
		System.out.println(doctor.getName() + " successfully registred as doctor");
	}

	public static void printAllPatients(Registry registry) {
		String[] allPatients = registry.getPatientNames();
		if (allPatients == null) {
			System.out.println("No patients registred");
			return;
		}
		printStringArray(allPatients);
	}

	public static void printAllDoctors(Registry registry) {
		String[] allDoctors = registry.getDoctorNames();
		if (allDoctors == null) {
			System.out.println("No doctors registred");
			return;
		}
		printStringArray(allDoctors);
	}

	public static void printAllTickets(Registry registry) {
		String[] allTickets = registry.getTickets();
		if (allTickets == null) {
			System.out.println("No tickets registred");
			return;
		}
		printStringArray(allTickets);
	}

	private static void printStringArray(String[] stringArray) {
		System.out.println();
		for (String patientToDoctor : stringArray) {
			System.out.println(patientToDoctor);
		}
		System.out.println();
	}

	public static void printPatientOfDoctorCount(Registry registry, Doctor doctor) {
		System.out
				.println("Doctor " + doctor.getName() + " has " + registry.findPatientsOfDoctor(doctor) + " patients");
	}

	public static void addTicket(Registry registry, Patient patient, Doctor doctor) {
		int result = registry.addTicket(patient, doctor);
		if (result > 0)
			System.out.println(patient.getName() + " is succesfully registred to " + doctor.getName());
		if (result == 0)
			System.out.println("This ticket is already registred");
		if (result == -1)
			System.out.println("Patient " + patient.getName() + " isn't registred");
		if (result == -2)
			System.out.println("Doctor " + doctor.getName() + " isn't registred");
		if (result == -3)
			System.out.println("No patients or doctors registred");
	}

	public static void deleteTicket(Registry registry, Patient patient, Doctor doctor) {
		if (registry.deleteTicket(patient, doctor))
			System.out.println("Deleted successful");
		else
			System.out.println("Not found");
	}

	public static void printNumberOfDoctors(Registry registry) {
		System.out.println("Number of doctors is: " + registry.getDoctorCount());
	}

	public static void printNumberOfPatients(Registry registry) {
		System.out.println("Number of patients is: " + registry.getPatientCount());
	}
}
