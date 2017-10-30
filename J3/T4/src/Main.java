
public class Main {
	public static void main(String[] args) {
		Registry registry = new Registry();

		Doctor lobanov = new Doctor("Lobanov");
		Doctor acula = new Doctor("Dr. Acula");

		Patient roma = new Patient("Roma");
		Patient ivan4 = new Patient("Ivan IV The Terrible");

		registry.addDoctor(lobanov);
		registry.addPatient(roma);
		registry.addPatient(ivan4);
		registry.deleteTicket(roma, lobanov);
		registry.addTicket(roma, lobanov);
		registry.addTicket(ivan4, lobanov);
		registry.addTicket(roma, lobanov);
		registry.deleteTicket(roma, lobanov);
		registry.deleteTicket(ivan4, lobanov);
		printAllTickets(registry.getTickets());
		printStringArray(registry.getDoctorNames());
		printStringArray(registry.getPatientNames());
		System.out.println("Number of patients of Lobanov: " + registry.findPatientsOfDoctor(lobanov));
		System.out.println("Number of patients of Dr. Acula: " + registry.findPatientsOfDoctor(acula));
	}

	private static void printAllTickets(Ticket[] allTickets) {
		if (allTickets == null) {
			System.out.println("No tickets registred");
		} else {
			StringBuilder result = new StringBuilder();
			for (int i = 0; i < allTickets.length  && allTickets[i] != null; i++) {
				System.out.println(result.append(allTickets[i].getPatient().getName()).append(" to ").append(allTickets[i].getDoctor().getName()));
				result.delete(0, result.length());
			}
		}
	}

	private static void printStringArray(String[] stringArray) {
		System.out.println();
		for (String patientToDoctor : stringArray) {
			System.out.println(patientToDoctor);
		}
		System.out.println();
	}
}
