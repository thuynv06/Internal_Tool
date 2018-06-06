package Com.IFI.InternalTool.BS.Service;

import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;

public interface EmployeeService {
	// get list employeess and paginations
	public List<Employee> getAllEmployees(final int page, final int pageSize);

	// save employess
	public void saveEmployee(final Employee employee);

	// delete employees by id
	public Boolean deleteEmployee(final long employee_id);

	// get employss by ID
	public Employee getEmployeeById(final long employee_id);

	// find Employees Name Like
	public List<Employee> findEmployeeNameLike(final String name, int page, int pageSize);

	// find Employees By Group ID
	public List<Employee> findEmployeeByGroupId(final String group_id, final int page, final int pageSize);

	
	public List<Long> getEmployeeByManager(final long manager_id);

}
