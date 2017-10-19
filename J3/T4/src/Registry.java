
public class Registry {
	private Patient[] patients;
	private Doctor[] doctors;
	private Ticket[] tickets;

	public Registry() {
		patients = new Patient[1];
		doctors = new Doctor[1];
		tickets = new Ticket[1];

	}

	private Boolean isInitializedPatients() {
		if (patients[0] == null)
			return false;
		return true;
	}

	private Boolean isInitializedDoctors() {
		if (doctors[0] == null)
			return false;
		return true;
	}

	private Boolean isInitializedTickets() {
		if (tickets[0] == null)
			return false;
		return true;
	}

	private int searchPatient(Patient patient) {
		for (int i = 0; i < patients.length; i++) {
			if (patient.equals(patients[i]))
				return i;
		}
		return -1;
	}

	private int searchDoctor(Doctor doctor) {
		for (int i = 0; i < doctors.length; i++) {
			if (doctor.equals(doctors[i]))
				return i;
		}
		return -1;
	}

	private int searchTicket(Ticket ticket) {
		for (int i = 0; i < tickets.length; i++) {
			if (ticket.equals(tickets[i]))
				return i;
		}
		return -1;
	}

	private void initializePatients(Patient patient) {
		patients[0] = patient;
	}

	private void initializeDoctors(Doctor doctor) {
		doctors[0] = doctor;
	}

	private void initializeTickets(int patientIndex, int doctorIndex) {
		tickets[0] = new Ticket(patientIndex, doctorIndex);
	}

	public Boolean addPatient(Patient patient) {
		if (!isInitializedPatients()) {
			initializePatients(patient);
			return true;
		}
		if (searchPatient(patient) >= 0)
			return false;

		Patient[] newPatients = new Patient[patients.length + 1];
		System.arraycopy(patients, 0, newPatients, 0, patients.length);
		newPatients[patients.length] = patient;
		patients = newPatients;
		return true;
	}

	public Boolean addDoctor(Doctor doctor) {
		if (!isInitializedDoctors()) {
			initializeDoctors(doctor);
			return true;
		}
		if (searchDoctor(doctor) >= 0)
			return false;

		Doctor[] newDoctors = new Doctor[doctors.length + 1];
		System.arraycopy(doctors, 0, newDoctors, 0, doctors.length);
		newDoctors[doctors.length] = doctor;
		doctors = newDoctors;
		return true;
	}

	public int addTicket(Patient patient, Doctor doctor) {
		if (!isInitializedPatients() || !isInitializedDoctors()) {
			return -3;
		}
		int patientIndex = searchPatient(patient);
		int doctorIndex = searchDoctor(doctor);
		if (patientIndex < 0)
			return -1;
		if (doctorIndex < 0)
			return -2;

		if (!isInitializedTickets()) {
			initializeTickets(patientIndex, doctorIndex);
			return 1;
		}
		Ticket ticket = new Ticket(patientIndex, doctorIndex);
		int ticketIndex = searchTicket(ticket);
		if (ticketIndex >= 0)
			return 0;
		Ticket[] newTickets = new Ticket[tickets.length + 1];
		System.arraycopy(tickets, 0, newTickets, 0, tickets.length);
		newTickets[tickets.length] = ticket;
		tickets = newTickets;
		return tickets.length;
	}

	public Boolean deleteTicket(Patient patient, Doctor doctor) {
		int patientIndex = searchPatient(patient);
		int doctorIndex = searchDoctor(doctor);
		if (patientIndex < 0)
			return false;
		if (doctorIndex < 0)
			return false;
		Ticket ticket = new Ticket(patientIndex, doctorIndex);
		int ticketIndex = searchTicket(ticket);
		if (ticketIndex < 0)
			return false;
		if (tickets.length == 1) {
			tickets[0] = null;
			return true;
		}
		Ticket[] newTickets = new Ticket[tickets.length - 1];
		System.arraycopy(tickets, 0, newTickets, 0, ticketIndex);
		System.arraycopy(tickets, ticketIndex + 1, newTickets, ticketIndex, newTickets.length - ticketIndex);
		tickets = newTickets;
		return true;
	}

	public int findPatientsOfDoctor(Doctor doctor) {
		if (!isInitializedTickets() || searchDoctor(doctor) < 0)
			return -1;
		int patientsCount = 0;
		for (Ticket ticket : tickets) {
			if (doctors[ticket.getDoctorIndex()].equals(doctor)) {
				patientsCount++;
			}
		}
		return patientsCount;
	}

	public String[] getPatientNames() {
		if (!isInitializedPatients())
			return null;
		String[] patientNames = new String[patients.length];
		for (int i = 0; i < patients.length; i++) {
			patientNames[i] = patients[i].getName();
		}
		return patientNames;
	}

	public String[] getDoctorNames() {
		if (!isInitializedDoctors())
			return null;
		String[] doctorNames = new String[doctors.length];
		for (int i = 0; i < doctors.length; i++) {
			doctorNames[i] = doctors[i].getName();
		}
		return doctorNames;
	}

	public String[] getTickets() {
		if (!isInitializedTickets())
			return null;
		String[] patientsToDoctors = new String[tickets.length];
		for (int i = 0; i < tickets.length; i++) {
			patientsToDoctors[i] = patients[tickets[i].getPatientIndex()].getName() + " to "
					+ doctors[tickets[i].getDoctorIndex()].getName();
		}
		return patientsToDoctors;
	}

	public int getDoctorCount() {
		if (!isInitializedDoctors())
			return 0;
		return doctors.length;
	}

	public int getPatientCount() {
		if (!isInitializedPatients())
			return 0;
		return patients.length;
	}
}
