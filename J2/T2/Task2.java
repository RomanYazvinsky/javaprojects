class Date{
	int day;
	int month;
	int year;
	public Date()
	{
		System.out.println("Date");
	}
	public Date(int day, int month, int year)
	{
		this.day = day;
		this.month = month;
		this.year = year;
		System.out.println("Date");
	}
	public int getDay()
	{
		return day;
	}
	public int getMonth()
	{
		return month;
	}
	public int getYear()
	{
		return year;
	}
}
class WorkPlace{
	String organizationName;
	Date startDate = new Date();
	Date endDate = new Date();
	String description;
	public WorkPlace()
	{
		System.out.println("WorkPlace");
	}
	public WorkPlace(String organizationName, Date startDate, String description){
		this.organizationName = organizationName;
		this.startDate = startDate;
		this.description = description;
		this.endDate = new Date (0,0,0);
	}
	public String getOrganizationName(){
		return organizationName;
	}
	public Date getStartDate(){
		return startDate;
	}
	public Date getEndDate(){
		return endDate;
	}
	public String getDescription(){
		return description;
	}
	public void setEndDate(Date endDate){
		this.endDate = endDate;
	}
}
class Man{
	String name;
	String secondName;
	String lastName;
	Date birthDate = new Date();
	String sex;
	public Man()
	{
		System.out.println("Man");
	}
	public Man(String name, String secondName, String lastName, Date birthDate, String sex)
	{
		this.name = name;
		this.secondName = secondName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.sex = sex;
		System.out.println("Man");
	}
	public String getName()
	{
		return name;
	}
	public String getSecondName()
	{
		return secondName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public Date getDate()
	{
		return birthDate;
	}
	public String getSex()
	{
		return sex;
	}
}
class Citizen extends Man{
	String addressCity;
	String addressStreet;
	String addressBuilding;
	String passportNumber;
	protected WorkPlace[] employmentHistory ;
	protected int moneyBalance;
	public Citizen(){
		System.out.println("Citizen");
		employmentHistory = new WorkPlace[1];
		employmentHistory[0] = new WorkPlace();
	}
	public Citizen(String addressCity, String addressStreet, String addressBuilding, String passportNumber){
		this.addressCity = addressCity;
		this.addressStreet = addressStreet;
		this.addressBuilding = addressBuilding;
		moneyBalance = 0;
		employmentHistory = new WorkPlace[1];
		employmentHistory[0] = new WorkPlace();
		System.out.println("Citizen");
	}
	public String getAddressCity()
	{
		return addressCity;
	}
	public String getAddressBuilding()
	{
		return addressBuilding;
	}
	public String getAddressStreet()
	{
		return addressStreet;
	}
	public String getPassportNumber()
	{
		return passportNumber;
	}
	public void setAddressCity(String addressCity)
	{
		this.addressCity = addressCity;
	}
	public void setAddressBuilding(String addressBuilding)
	{
		this.addressBuilding = addressBuilding;
	}
	public void setAddressStreet(String addressStreet)
	{
		this.addressStreet = addressStreet;
	}
	public void addWorkPlace(WorkPlace newWorkPlace)
	{
		if (employmentHistory.length == 1)employmentHistory[0] = newWorkPlace;
		else 
		{
			WorkPlace[] newArray = new WorkPlace[employmentHistory.length + 1];
			for(int i = 0; i < employmentHistory.length; i++)
			{
				newArray[i] = employmentHistory[i];
			}
			newArray[employmentHistory.length] = newWorkPlace;
			employmentHistory = newArray;
		}
	}
	public int getMoneyBalance()
	{
		return moneyBalance;
	}
	public WorkPlace[] getEmploymentHistory()
	{
		return employmentHistory;
	}
	public WorkPlace getCurrentWorkplace()
	{
		return employmentHistory[employmentHistory.length - 1];
	}
}
class Task {
	String description;
	Date deadline = new Date();
	Citizen customer = new Citizen();
	int reward;
	public Task(){
		System.out.println("WorkPlace");
	}
	public String getDescription(){
		return description;
	}
	public Date getDeadline(){
		return deadline;
	}
	public Citizen getCustomer(){
		return customer;
	}
	public int getReward()
	{
		return reward;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public void setDeadline(Date deadline){
		this.deadline = deadline;
	}
	public void setReward(int reward)
	{
		this.reward = reward;
	}
}
class Employee extends Citizen{
	int lastSalary = 0;
	String employeeID;
	String department;
	Task[] tasksDone;
	Task currentTask = new Task();
	public Employee(){
		System.out.println("Employee");
		tasksDone = new Task[1];
		tasksDone[0] = new Task();
	}
	public int getLastSalary()
	{
		return lastSalary;
	}
	public void calculateSalary(){
		lastSalary = tasksDone.length;
	}
	public String getEmployeeID(){
		return employeeID;
	}
	public String getDepartment(){
		return department;
	}
	public void setDepartment(String department){
		this.department = department;
	}
}
class HumanResources{
	Employee[] employees;
	public HumanResources(){
		System.out.println("HumanResources");
		employees = new Employee[1];
		employees[0] = new Employee();
	}
	public Employee[] getEmployees(){
		return employees;
	}
	public void addEmployee(Employee employee){
		if (employees.length == 1)employees[0] = employee;
		else 
		{
			Employee[] newArray = new Employee[employees.length + 1];
			for(int i = 0; i < employees.length; i++)
			{
				newArray[i] = employees[i];
			}
			newArray[employees.length] = employee;
			employees = newArray;
		}
	}
	public void layOff(Employee employee){
		System.out.println("Wow");
	}
}
class Task2{
	public static void main(String[] args){
		HumanResources hr = new HumanResources();
	}
}
