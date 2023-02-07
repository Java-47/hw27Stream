package telran.employee.dao;

import telran.employee.model.Employee;

public interface Company {
	String TITLE = "Twitter";
	
	boolean addEmployee(Employee employee);

	Employee removeEmployee(int id);

	Employee findEmployee(int id);

	double totalSalary();

	double totalSales();

	int quantity();

	default double averageSalary() {
		return totalSalary() / quantity();
	}

	void printEmployees();
	
	Employee[] findEmployeesHoursGreaterThan(int hours);
	
	Employee[] findEmployeesSalaryBetween(int minSalary, int maxSalary);
}
