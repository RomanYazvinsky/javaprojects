
public class Ticket {
	private int patientIndex;
	private int doctorIndex;

	public Ticket(int patientIndex, int doctorIndex) {
		this.patientIndex = patientIndex;
		this.doctorIndex = doctorIndex;

	}

	public int getPatientIndex() {
		return patientIndex;
	}

	public int getDoctorIndex() {
		return doctorIndex;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + doctorIndex;
		result = prime * result + patientIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		if (doctorIndex != other.doctorIndex)
			return false;
		if (patientIndex != other.patientIndex)
			return false;
		return true;
	}
}
