
public class Main {
	public static void main(String[] args) {
		Registry registry = new Registry();

		Doctor lobanov = new Doctor("Lobanov");
		Doctor acula = new Doctor("Dr. Acula");
		Doctor john = new Doctor("J.D.");

		Patient roma = new Patient("Roma");
		Patient ivan4 = new Patient("Ivan IV The Terrible");
		
		Printer.printAddDoctor(registry.addDoctor(lobanov));
		Printer.printAddPatient(registry.addPatient(roma));
		Printer.printAddPatient(registry.addPatient(ivan4));
		Printer.printDeleteTicket(registry.deleteTicket(roma, lobanov));
		Printer.printAddTicket(registry.addTicket(roma, lobanov));
		Printer.printAddTicket(registry.addTicket(ivan4, lobanov));
		Printer.printAddTicket(registry.addTicket(roma, lobanov));
		Printer.printDeleteTicket(registry.deleteTicket(roma, lobanov));
		Printer.printDeleteTicket(registry.deleteTicket(ivan4, lobanov));
		Printer.printAllTickets(registry.getTickets());
		Printer.printAllDoctors(registry.getDoctorNames());
		Printer.printAllPatients(registry.getPatientNames());
		Printer.printPatientOfDoctorCount(registry.findPatientsOfDoctor(lobanov));
		Printer.printPatientOfDoctorCount(registry.findPatientsOfDoctor(acula));
	}

}
