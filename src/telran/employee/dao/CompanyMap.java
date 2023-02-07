package telran.employee.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanyMap implements Company {
	private int capacity;
	private Map<Integer, Employee> employees;

	public CompanyMap(int capacity) {
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
		Collection<Employee> values = employees.values();
		double res = 0;
		for (Employee employee : values) {
			res += employee.calcSalary();
		}
		return res;
	}

	@Override
	// O(n)
	public double totalSales() {
		Collection<Employee> values = employees.values();
		double sum = 0;
		for (Employee employee : values) {
			if (employee instanceof SalesManager) {
				SalesManager sm = (SalesManager) employee;
				sum += sm.getSalesValue();
			}
		}
		return sum;
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
		List<Employee> res = new ArrayList<>();
		for (Employee employee: employees.values()) {
			if (predicate.test(employee)) {
				res.add(employee);
			}
		}
		return res.toArray(new Employee[0]);
	}


}
