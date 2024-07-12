package telran.employees;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

import org.json.JSONObject;

import telran.io.Persistable;

//So far we do consider optimization
public class CompanyMapsImpl implements Company ,Persistable {
	TreeMap<Long, Employee> employees = new TreeMap<>();
	HashMap<String, List<Employee>> employeesDepartment = new HashMap<>();
	TreeMap<Float, List<Manager>> factorManagers = new TreeMap<>();

	@Override
	public Iterator<Employee> iterator() {

		return employees.values().iterator();
	}

	@Override
	public void addEmployee(Employee empl) {
		if (employees.containsKey(empl.getId()) || empl == null) {
			throw new IllegalStateException();
		}

		employees.put(empl.getId(), empl);
		employeesDepartment.computeIfAbsent(empl.getDepartment(), k -> new ArrayList<>()).add(empl);

		if (empl instanceof Manager) {
			Manager manager = (Manager) empl;
			factorManagers.computeIfAbsent(manager.factor, k -> new ArrayList<>()).add(manager);
		}
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	@Override
	public Employee removeEmployee(long id) {
		Employee removed = employees.remove(id);
		if (removed != null) {
			employeesDepartment.get(removed.getDepartment()).remove(removed);
			if (employeesDepartment.get(removed.getDepartment()).isEmpty()) {
				employeesDepartment.remove(removed.getDepartment());
			}
			if (removed instanceof Manager) {
				Manager manager = (Manager) removed;
				factorManagers.get(manager.factor).remove(manager);
				if (factorManagers.get(manager.factor).isEmpty()) {
					factorManagers.remove(manager.factor);
				}
			}
		} else {
			throw new NoSuchElementException();
		}

		return removed;
	}

	@Override
	public int getDepartmentBudget(String department) {
		return employeesDepartment
				.getOrDefault(department, Collections.emptyList())
				.stream()
				.mapToInt(Employee::computeSalary)
				.sum();
	}

	@Override
	public String[] getDepartments() {
		return employeesDepartment.keySet().toArray(new String[0]);
	}

	public Manager[] getManagersWithMostFactor() {
		return factorManagers.lastEntry().getValue().toArray(new Manager[0]);
	}

	@Override
	public void save(String filePathStr) {
		try(PrintWriter writer = new PrintWriter(new FileWriter(filePathStr))) {
			employees.values().forEach(empl -> {
				JSONObject jsonObject = new JSONObject();
				empl.fillEmployee(jsonObject);
				writer.println();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void restore(String filePathStr) {
		// TODO Auto-generated method stub
		
	}
}
