
public class SafeInterface {

	public static String[] allPatientsResult(Registry registry) {
		String[] allPatients = registry.getPatientNames();
		if (allPatients == null) {
			return new String[] {"No patients registred"};
		}
		return allPatients;
	}

	public static String[] allDoctorsResult(Registry registry) {
		String[] allDoctors = registry.getDoctorNames();
		if (allDoctors == null) {
			return new String[] {"No doctors registred"};
		}
		return allDoctors;
	}

	public static String[] allTicketsResult(Registry registry) {
		String[] allTickets = registry.getTickets();
		if (allTickets == null) {
			return new String[] {"No tickets registred"};		
		}
		return allTickets;
	}

	public static String addTicketResult(Registry registry, Patient patient, Doctor doctor) {
		int result = registry.addTicket(patient, doctor);
		if (result > 0) {
			return (patient.getName().concat(" is succesfully registred to ").concat(doctor.getName()));
		}
		if (result == 0) {
			return ("This ticket is already registred");
		}
		if (result == -1) {
			return ("Patient ".concat(patient.getName()).concat(" isn't registred"));
		}
		if (result == -2) {
			return ("Doctor ".concat(doctor.getName()).concat(" isn't registred"));
		}
		return ("No patients or doctors registred");
	}

	public static String deleteTicketResult(Registry registry, Patient patient, Doctor doctor) {
		if (registry.deleteTicket(patient, doctor)) {
			return ("Deleted successful");
		}
		else
			return ("Not found");
	}


}
