package telran.employee.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import telran.employee.model.Employee;
import telran.employee.model.SalesManager;

public class CompanySetImpl implements Company {
	private Set<Employee> employees;
	private int capacity;

	public CompanySetImpl(int capacity) {
		employees = new HashSet<>();
		this.capacity = capacity;
	}

	@Override
	// O(1)
	public boolean addEmployee(Employee employee) {
		if(employees.size() == capacity) {
			return false;
		}
		return employees.add(employee);
	}

	@Override
	// O(n)
	public Employee removeEmployee(int id) {
		Employee victim = findEmployee(id);
		employees.remove(victim);
		return victim;
	}

	@Override
	// O(n)
	public Employee findEmployee(int id) {
		for (Employee employee : employees) {
			if (employee.getId() == id) {
				return employee;
			}
		}
		return null;
	}

	@Override
	// O(n)
	public double totalSalary() {
		double sum = 0;
		for (Employee employee : employees) {
			sum += employee.calcSalary();
		}
		return sum;
	}

	@Override
	// O(n)
	public double totalSales() {
		double sum = 0;
		for (Employee employee : employees) {
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
		for (Employee employee : employees) {
			System.out.println(employee);
		}

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
		for (Employee employee: employees) {
			if (predicate.test(employee)) {
				res.add(employee);
			}
		}
		return res.toArray(new Employee[0]);
	}


}
