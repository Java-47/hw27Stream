package telran.employee.dao;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyStreamImpl implements Company {
	private int capacity;
	private Map<Integer, Employee> employees;

	public CompanyStreamImpl(int capacity) {
		employees = new HashMap<>();
		this.capacity = capacity;
	}

	@Override
	// O(1)
	public boolean addEmployee(Employee employee) {
		if (employees.size() == capacity) {
			return false;
		}
		return employees.putIfAbsent(employee.getId(), employee) == null;
	}

	@Override
	// O(1)
	public Employee removeEmployee(int id) {
		return employees.remove(id);
	}

	@Override
	// O(1)
	public Employee findEmployee(int id) {
		return employees.get(id);
	}

	@Override
	// O(n)
	public double totalSalary() {
		return employees.values().stream()
							 	 		  .mapToDouble(Employee::calcSalary)
							 	 		  .reduce(0,(value1, value2) -> value1 + value2);
	}

	@Override
	// O(n)
	public double totalSales() {
		return employees.values().stream()
								 		  .filter(emp -> emp instanceof SalesManager)
								 		  .map(emp -> (SalesManager)emp)
								 		  .mapToDouble(SalesManager::getSalesValue)
								 		  .reduce(0,(value1, value2) -> value1 + value2);
	}

	@Override
	// O(1)
	public int quantity() {
		return employees.size();
	}

	@Override
	// O(n)
	public void printEmployees() {
		employees.values().forEach(e -> System.out.println(e));

	}

	@Override
	// O(n)
	public Employee[] findEmployeesHoursGreaterThan(int hours) {
		return findEmployeesByPredicate(e -> e.getHours() > hours);
	}

	@Override
	// O(n)
	public Employee[] findEmployeesSalaryBetween(int minSalary, int maxSalary) {
		Predicate<Employee> predicate = new Predicate<>() {
			@Override
			public boolean test(Employee e) {
				return e.calcSalary() >= minSalary && e.calcSalary() < maxSalary;
			}
		};
		return findEmployeesByPredicate(predicate);
	}

	// O(n)
	private Employee[] findEmployeesByPredicate(Predicate<Employee> predicate) {
		return employees.values().stream()
						  				  .filter(predicate::test)
						  				  .toArray(Employee[]::new);
	}

}
