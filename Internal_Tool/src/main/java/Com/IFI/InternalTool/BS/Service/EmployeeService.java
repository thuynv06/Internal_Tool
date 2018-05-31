package Com.IFI.InternalTool.BS.Service;
import Com.IFI.InternalTool.DS.Model.Employee;
import Com.IFI.InternalTool.Payloads.PagedResponse;
public interface EmployeeService {

	
	public Employee createEmployee(Employee emp);
	
	public void deleteEmployeeById(String employee_id);
	
	public Employee getEmployeeById(long employee_id);
	
	public PagedResponse<Employee>  getAllEmployee();
	
	public PagedResponse<Employee>  findEmployeeLikeName(String name);
	
}
