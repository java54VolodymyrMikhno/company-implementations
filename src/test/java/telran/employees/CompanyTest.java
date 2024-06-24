package telran.employees;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;


import org.junit.jupiter.api.Test;


abstract class  CompanyTest {
private static final long ID1 = 123;
private static final int SALARY1 = 1000;
private static final String DEPARTMENT1 = "Development";
private static final long ID2 = 120;
private static final int SALARY2 = 2000;
private static final long ID3 = 125;
private static final int SALARY3 = 3000;
private static final String DEPARTMENT2 = "QA";
private static final long ID4 = 200;
private static final String DEPARTMENT4 = "Audit";
private static final int WAGE1 = 100;
private static final int HOURS1 = 10;
private static final float FACTOR1 = 2;
private static final float FACTOR2 = 1.5f;
private static final float PERCENT1 = 0.01f;
private static final long SALES1 = 10000;
private static final long ID5 = 1;
private static final long ID6 = 2;
private static final long ID7 = 3;
Employee empl1 = new WageEmployee(ID1, SALARY1, DEPARTMENT1, WAGE1, HOURS1);
Employee empl2 = new Manager(ID2, SALARY2, DEPARTMENT1, FACTOR1);
Employee empl3 = new SalesPerson(ID3, SALARY3, DEPARTMENT2, WAGE1, HOURS1, PERCENT1, SALES1);
protected Company company;

void setCompany() {
	//before each test there will be created new object company 
	// with array of the given employee objects
	for(Employee emp:  new Employee[] {empl1, empl2, empl3}) {
		company.addEmployee(emp);
	}
}
	@Test
	void testAddEmployee()
	{
		Employee empl = new Employee(ID4, SALARY1, DEPARTMENT1);
		company.addEmployee(empl);
		assertThrowsExactly(IllegalStateException.class,
				() -> company.addEmployee(empl));
		assertThrowsExactly(IllegalStateException.class,
				() -> company.addEmployee(empl1));
	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, company.getEmployee(ID1));
		assertNull(company.getEmployee(ID4));
	}

	@Test
	void testRemoveEmployee() {
		assertEquals(empl1, company.removeEmployee(ID1));
		assertThrowsExactly(NoSuchElementException.class,
				() -> company.removeEmployee(ID1));
	}

	@Test
	void testGetDepartmentBudget() {
		
		assertEquals(SALARY1 + WAGE1 * HOURS1 + SALARY2 * FACTOR1, company.getDepartmentBudget(DEPARTMENT1));
		assertEquals(SALARY3 + WAGE1 * HOURS1 + PERCENT1 * SALES1 / 100, company.getDepartmentBudget(DEPARTMENT2));
		assertEquals(0, company.getDepartmentBudget(DEPARTMENT4));
	}

	@Test
	void testIterator() {
		Employee[] expected = {empl2, empl1, empl3};
		Iterator<Employee> it = company.iterator();
		int index = 0;
		while(it.hasNext()) {
			assertEquals(expected[index++], it.next());
		}
		assertEquals(expected.length, index);
		assertThrowsExactly(NoSuchElementException.class, it::next);
	}
	@Test
	void testGetDepartments() {
		String [] expected = {DEPARTMENT1, DEPARTMENT2};
		Arrays.sort(expected);
		assertArrayEquals(expected, company.getDepartments());
	}
	@Test
	void testGetManagersWithMostFactor() {
		Manager manager1 = new Manager(ID5, SALARY1, DEPARTMENT1, FACTOR2);
		Manager manager2 = new Manager(ID6, SALARY2, DEPARTMENT1, FACTOR1);
		Manager manager3 = new Manager(ID7, SALARY3, DEPARTMENT2, FACTOR2);
		company.addEmployee(manager3);
		company.addEmployee(manager2);
		company.addEmployee(manager1);
		Manager[]expected = {manager2,(Manager) empl2};
		assertArrayEquals(expected, company.getManagersWithMostFactor());
	}


}