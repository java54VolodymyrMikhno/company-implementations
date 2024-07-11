package telran.employees;

import org.json.JSONObject;

public class SalesPerson extends WageEmployee {
	
	float percent;
	long sales;
	public SalesPerson(long id, int basicSalary, String department, int hours, int wage, float percent, long sales) {
		super(id, basicSalary, department, hours, wage);
		this.percent = percent;
		this.sales = sales;
	}
	@Override
	public int computeSalary() {
		return Math.round(super.computeSalary() + sales * percent / 100);
	}
	protected void fillJSONObject(JSONObject jsonObject) {
		//TODO
	}

	protected void fillClassName(JSONObject jsonObject) {
		//TODO
	}

}