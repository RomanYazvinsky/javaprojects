
public class Main {
	public static void main(String[] args) {
		Registry registry = new Registry();

		Doctor lobanov = new Doctor("Lobanov");
		Doctor acula = new Doctor("Dr. Acula");
		Doctor john = new Doctor("J.D.");

		Patient roma = new Patient("Roma");
		Patient ivan = new Patient("Ivan");
		Patient ivan4 = new Patient("Ivan IV The Terrible");

		Handler.addDoctor(registry, lobanov);
		Handler.printAllDoctors(registry);
		Handler.printAllPatients(registry); // patients are not added
		Handler.addPatient(registry, roma);
		Handler.printAllPatients(registry);
		Handler.addTicket(registry, roma, john); // john is not registred as doctor
		Handler.addTicket(registry, roma, lobanov);
		Handler.addPatient(registry, ivan);
		Handler.addTicket(registry, ivan, lobanov);
		Handler.addTicket(registry, roma, lobanov); // the same ticket, as first
		Handler.printAllTickets(registry);
		Handler.deleteTicket(registry, roma, lobanov); // deleting
		Handler.printAllTickets(registry);
		Handler.deleteTicket(registry, roma, lobanov);
		Handler.deleteTicket(registry, ivan, lobanov);
		Handler.printAllTickets(registry);
		Handler.addTicket(registry, ivan, lobanov);
		Handler.printAllTickets(registry);
		Handler.deleteTicket(registry, ivan, lobanov);
		Handler.printAllTickets(registry);
		
		
		Handler.addTicket(registry, roma, lobanov);
		Handler.addTicket(registry, ivan, lobanov);
		Handler.printPatientOfDoctorCount(registry, lobanov);

	}
}
