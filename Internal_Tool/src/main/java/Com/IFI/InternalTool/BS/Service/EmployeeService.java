package Com.IFI.InternalTool.BS.Service;
import java.util.List;

import Com.IFI.InternalTool.DS.Model.Employee;
public interface EmployeeService {


	List<Employee>  getAllEmployees(int page, int pageSize);
	
	Boolean saveEmployee(Employee employee);
	
	Boolean deleteEmployee(long employee_id);
	
	Employee getEmployeeById(long employee_id);
	
	List<Employee> findEmployeeNameLike(final String name,int page, int pageSize);
	
	List<Employee> findEmployeeByGroupId(final String group_id,final int page,final int pageSize);

	List<Long> getEmployeeByManager(long manager_id);
	
	
}
