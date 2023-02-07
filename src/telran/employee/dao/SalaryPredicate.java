package telran.employee.dao;

import java.util.function.Predicate;

import telran.employee.model.Employee;

public class SalaryPredicate implements Predicate<Employee> {
	private double minSalary;
	private double maxSalary;

	public SalaryPredicate(double minSalary, double maxSalary) {
		this.minSalary = minSalary;
		this.maxSalary = maxSalary;
	}

	@Override
	public boolean test(Employee e) {
		return e.calcSalary() >= minSalary && e.calcSalary() < maxSalary;
	}

}
