package telran.employee.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employee.dao.Company;
import telran.employee.dao.CompanyImpl;
import telran.employee.dao.CompanyListImpl;
import telran.employee.dao.CompanyMap;
import telran.employee.dao.CompanySetImpl;
import telran.employee.dao.CompanyStreamImpl;
import telran.employee.model.Employee;
import telran.employee.model.Manager;
import telran.employee.model.SalesManager;
import telran.employee.model.WageEmployee;

class CompanyTest {
	Company company;
	Employee[] firm;

	@BeforeEach
	void setUp() throws Exception {
		company = new CompanyStreamImpl(5);
		firm = new Employee[4];
		firm[0] = new Manager(1000, "John", "Smith", 182, 20000, 20);
		firm[1] = new WageEmployee(2000, "Ann", "Smith", 182, 40);
		firm[2] = new SalesManager(3000, "Peter", "Jackson", 182, 40000, 0.1);
		firm[3] = new SalesManager(4000, "Tigran", "Petrosyan", 91, 80000, 0.1);
		for (int i = 0; i < firm.length; i++) {
			company.addEmployee(firm[i]);
		}
	}

	@Test
	void testAddEmployee() {
		assertFalse(company.addEmployee(firm[1]));
		Employee empl = new SalesManager(5000, "Tigran", "Petrosyan", 91, 80000, 0.1);
		assertTrue(company.addEmployee(empl));
		assertEquals(5, company.quantity());
		assertEquals(empl, company.findEmployee(5000));
		assertFalse(company.addEmployee(new SalesManager(6000, "Tigran", "Petrosyan", 91, 80000, 0.1)));
	}

	@Test
	void testRemoveEmployee() {
		assertNull(company.removeEmployee(5000));
		assertEquals(4, company.quantity());
		assertEquals(firm[0], company.removeEmployee(1000));
		assertEquals(3, company.quantity());
	}

	@Test
	void testFindEmployee() {
		assertNull(company.findEmployee(5000));
		Employee expected = company.findEmployee(2000);
		assertEquals(firm[1], expected);
		assertEquals(firm[1].getFirstName(), expected.getFirstName());
	}

	@Test
	void testTotalSalary() {
		assertEquals(42920.0, company.totalSalary(), 0.01);
	}

	@Test
	void testTotalSales() {
		assertEquals(120000.0, company.totalSales(), 0.01);
	}

	@Test
	void testQuantity() {
		assertEquals(4, company.quantity());
	}

	@Test
	void testAverageSalary() {
		assertEquals(42920.0 / 4, company.averageSalary(), 0.01);
	}

	@Test
	void testPrintEmployees() {
		System.out.println(Company.TITLE);
		company.printEmployees();
	}

	@Test
	void testFindEmployeesHoursGreaterThan() {
		Employee[] actual = company.findEmployeesHoursGreaterThan(100);
		Arrays.sort(actual, (e1, e2) -> Integer.compare(e1.getId(), e2.getId()));
		Employee[] expected = { firm[0], firm[1], firm[2] };
		assertArrayEquals(expected, actual);
		actual = company.findEmployeesHoursGreaterThan(200);
		expected = new Employee[0];
		assertArrayEquals(expected, actual);
	}

	@Test
	void testFindEmployeesSalaryBetween() {
		Employee[] actual = company.findEmployeesSalaryBetween(5000, 10000);
		Arrays.sort(actual, (e1, e2) -> Integer.compare(e1.getId(), e2.getId()));
		Employee[] expected = { firm[1], firm[3] };
		assertArrayEquals(expected, actual);
	}

}
