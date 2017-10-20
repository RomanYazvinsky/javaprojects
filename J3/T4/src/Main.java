
public class Main {
	public static void main(String[] args) {
		Registry registry = new Registry();

		Doctor lobanov = new Doctor("Lobanov");
		Doctor acula = new Doctor("Dr. Acula");
		Doctor john = new Doctor("J.D.");

		Patient roma = new Patient("Roma");
		Patient ivan = new Patient("Ivan");
		Patient ivan4 = new Patient("Ivan IV The Terrible");
		
		Interface.addDoctor(registry, lobanov);
	//	Interface.printAllDoctors(registry);
		Interface.addPatient(registry, roma);
		Interface.addPatient(registry, ivan4);
		Interface.deleteTicket(registry, roma, lobanov);
		Interface.addTicket(registry, roma, lobanov);
		Interface.addTicket(registry, ivan4, lobanov);
		Interface.addTicket(registry, roma, lobanov);
		Interface.deleteTicket(registry, roma, lobanov);
		Interface.deleteTicket(registry, roma, lobanov);
		Interface.printAllTickets(registry);
		Interface.printAllDoctors(registry);
		Interface.printAllPatients(registry);
		Interface.printPatientOfDoctorCount(registry, lobanov);

	}
}
