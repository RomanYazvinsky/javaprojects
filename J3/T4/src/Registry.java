
public class Registry {
	private Patient[] patients;
	private int patientCount;
	private Doctor[] doctors;
	private int doctorCount;
	private Ticket[] tickets;
	private int ticketCount;

	public Registry() {
		patients = new Patient[6];
		doctors = new Doctor[6];
		tickets = new Ticket[6];
		patientCount = 0;
		doctorCount = 0;
		ticketCount = 0;
	}

	private Boolean isPatientsEmpty() {
		if (patientCount == 0) {
			return true;
		}
		return false;
	}

	private Boolean isDoctorsEmpty() {
		if (doctorCount == 0) {
			return true;
		}
		return false;
	}

	private Boolean isTicketsEmpty() {
		if (ticketCount == 0) {
			return true;
		}
		return false;
	}

	private int searchPatient(Patient patient) {
		for (int i = 0; i < patientCount; i++) {
			if (patient.equals(patients[i])) {
				return i;
			}
		}
		return -1;
	}

	private int searchDoctor(Doctor doctor) {
		for (int i = 0; i < doctorCount; i++) {
			if (doctor.equals(doctors[i])) {
				return i;
			}
		}
		return -1;
	}

	private int searchTicket(Ticket ticket) {
		for (int i = 0; i < ticketCount; i++) {
			if (ticket.equals(tickets[i])) {
				return i;
			}
		}
		return -1;
	}

	public Boolean addPatient(Patient patient) {
		if (searchPatient(patient) >= 0) {
			return false;
		}
		if (patients.length == patientCount) {
			Patient[] newPatients = new Patient[patients.length + patients.length / 3];
			System.arraycopy(patients, 0, newPatients, 0, patients.length);
			patients = newPatients;
		}
		patients[patientCount++] = patient;
		return true;
	}

	public Boolean addDoctor(Doctor doctor) {
		if (searchDoctor(doctor) >= 0) {
			return false;
		}
		if (doctors.length == doctorCount) {

			Doctor[] newDoctors = new Doctor[doctors.length + doctors.length / 3];
			System.arraycopy(doctors, 0, newDoctors, 0, doctors.length);
			doctors = newDoctors;
		}
		doctors[doctorCount++] = doctor;
		return true;
	}

	public int addTicket(Patient patient, Doctor doctor) {
		if (isPatientsEmpty() || isDoctorsEmpty()) {
			return -3;
		}
		int patientIndex = searchPatient(patient);
		int doctorIndex = searchDoctor(doctor);
		if (patientIndex < 0) {
			return -1;
		}
		if (doctorIndex < 0) {
			return -2;
		}
		Ticket ticket = new Ticket(patientIndex, doctorIndex);
		int ticketIndex = searchTicket(ticket);
		if (ticketIndex >= 0) {
			return 0;
		}
		if (tickets.length == ticketCount) {
			Ticket[] newTickets = new Ticket[tickets.length + tickets.length / 3];
			System.arraycopy(tickets, 0, newTickets, 0, tickets.length);
			tickets = newTickets;
		}
		tickets[ticketCount++] = ticket;
		return 1;
	}

	public Boolean deleteTicket(Patient patient, Doctor doctor) {
		int patientIndex = searchPatient(patient);
		int doctorIndex = searchDoctor(doctor);
		if (patientIndex < 0) {
			return false;
		}
		if (doctorIndex < 0) {
			return false;
		}
		Ticket ticket = new Ticket(patientIndex, doctorIndex);
		int ticketIndex = searchTicket(ticket);
		if (ticketIndex < 0) {
			return false;
		}
		tickets[ticketIndex] = tickets[ticketCount-1];
		tickets[ticketCount-1] = null;
		ticketCount--;
		return true;
	}

	public int findPatientsOfDoctor(Doctor doctor) {
		if (isTicketsEmpty() || searchDoctor(doctor) < 0) {
			return -1;
		}
		int patientsCount = 0;
		for (int i = 0; i < ticketCount; i++) {
			if (doctors[tickets[i].getDoctorIndex()].equals(doctor)) {
				patientsCount++;
			}
		}
		return patientsCount;
	}

	public String[] getPatientNames() {
		if (isPatientsEmpty()) {
			return null;
		}
		String[] patientNames = new String[patientCount];
		for (int i = 0; i < patientCount; i++) {
			patientNames[i] = patients[i].getName();
		}
		return patientNames;
	}

	public String[] getDoctorNames() {
		if (isDoctorsEmpty()) {
			return null;
		}
		String[] doctorNames = new String[doctorCount];
		for (int i = 0; i < doctorCount; i++) {
			doctorNames[i] = doctors[i].getName();
		}
		return doctorNames;
	}

	public String[] getTickets() {
		if (isTicketsEmpty()) {
			return null;
		}
		String[] patientsToDoctors = new String[ticketCount];
		for (int i = 0; i < ticketCount; i++) {
			patientsToDoctors[i] = (patients[tickets[i].getPatientIndex()].getName()).concat(" to ")
					.concat(doctors[tickets[i].getDoctorIndex()].getName());
		}
		return patientsToDoctors;
	}

	public int getDoctorCount() {
		return doctorCount;
	}

	public int getPatientCount() {
		return patientCount;
	}
}
